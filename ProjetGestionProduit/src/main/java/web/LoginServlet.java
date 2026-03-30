package web;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GestionUtilisateurImpJpa;
import dao.IGestionUtilisateur;
import entity.Utilisateur;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IGestionUtilisateur gestionUtilisateur;
    
    public void init(ServletConfig config) throws ServletException {
        gestionUtilisateur = new GestionUtilisateurImpJpa();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/acceuil");
            return;
        }
        
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Utilisateur user = gestionUtilisateur.authenticate(username, password);
        
        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            
            System.out.println("Connexion: " + user.getUsername() + " (" + user.getRole() + ")");
            
            response.sendRedirect(request.getContextPath() + "/acceuil");
        } else {
            request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}