package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GestionCategorieImpJpa;
import dao.IGestionCategorie;
import entity.Categorie;
import entity.Utilisateur;

@WebServlet("/categories")
public class CategorieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IGestionCategorie gestionCategorie;
    
    public void init(ServletConfig config) throws ServletException {
        gestionCategorie = new GestionCategorieImpJpa();
    }
    
    // Vérifier si l'utilisateur est connecté et est ADMIN
    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        HttpSession session = request.getSession(false);
        Utilisateur user = (session != null) ? (Utilisateur) session.getAttribute("user") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        
        if (!"ADMIN".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                "Accès non autorisé. Cette fonctionnalité est réservée aux administrateurs.");
            return false;
        }
        
        return true;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Vérifier les droits ADMIN
        if (!isAdmin(request, response)) {
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listCategories(request, response);
                break;
            case "add":
                showForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCategorie(request, response);
                break;
            default:
                listCategories(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Vérifier les droits ADMIN
        if (!isAdmin(request, response)) {
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action != null && action.equals("save")) {
            saveCategorie(request, response);
        }
    }
    
    private void listCategories(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Categorie> categories = gestionCategorie.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/categories.jsp").forward(request, response);
    }
    
    private void showForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setAttribute("categorie", new Categorie());
        request.getRequestDispatcher("/categorieForm.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Long id = Long.parseLong(request.getParameter("id"));
        Categorie categorie = gestionCategorie.getCategorieById(id);
        request.setAttribute("categorie", categorie);
        request.getRequestDispatcher("/categorieForm.jsp").forward(request, response);
    }
    
    private void saveCategorie(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String idParam = request.getParameter("id");
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        
        Categorie categorie;
        
        if (idParam != null && !idParam.isEmpty()) {
            // Modification
            Long id = Long.parseLong(idParam);
            categorie = gestionCategorie.getCategorieById(id);
            categorie.setNom(nom);
            categorie.setDescription(description);
            gestionCategorie.updateCategorie(categorie);
        } else {
            // Ajout
            categorie = new Categorie(nom, description);
            gestionCategorie.addCategorie(categorie);
        }
        
        response.sendRedirect(request.getContextPath() + "/categories?action=list");
    }
    
    private void deleteCategorie(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        Long id = Long.parseLong(request.getParameter("id"));
        gestionCategorie.deleteCategorie(id);
        response.sendRedirect(request.getContextPath() + "/categories?action=list");
    }
}