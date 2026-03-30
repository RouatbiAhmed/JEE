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
import dao.GestionProduitsImpJpa;
import dao.IGestionCategorie;
import dao.IGestionProduit;
import entity.Categorie;
import entity.Produit;
import entity.Utilisateur;

@WebServlet(urlPatterns = { "/", "/acceuil", "/search", "/delete", "/ajout", "/save", "/update" })
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    IGestionProduit gestion;
    IGestionCategorie gestionCategorie;

    public void init(ServletConfig config) throws ServletException {
        gestion = new GestionProduitsImpJpa();
        gestionCategorie = new GestionCategorieImpJpa();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        
        System.out.println("=== doGet appelé avec path: " + path + " ===");
        
        // ====== TRAITEMENT DES REQUÊTES ======
        if (path.equals("/") || path.equals("/acceuil")) {
            
            System.out.println("Traitement de /acceuil");
            
            List<Produit> liste = gestion.getAllProduits();
            request.setAttribute("products", liste);
            
            System.out.println("Nombre de produits trouvés: " + (liste != null ? liste.size() : 0));
            
            request.getRequestDispatcher("accueil.jsp").forward(request, response);
            return; // IMPORTANT: arrêter l'exécution ici

        } else if (path.equals("/search")) {

            String mc = request.getParameter("mc");
            System.out.println("Recherche avec mot clé: " + mc);
            
            List<Produit> liste = gestion.getProductByMC(mc);
            request.setAttribute("products", liste);
            
            request.getRequestDispatcher("accueil.jsp").forward(request, response);
            return; // IMPORTANT: arrêter l'exécution ici

        } else if (path.equals("/delete")) {

            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Suppression du produit ID: " + id);
            
            gestion.deleteProduit(id);
            
            response.sendRedirect("acceuil");
            return; // IMPORTANT: arrêter l'exécution ici
            
        } else if (path.equals("/ajout")) {
            
            System.out.println("Affichage formulaire ajout");
            
            List<Categorie> categories = gestionCategorie.getAllCategories();
            request.setAttribute("categories", categories);
            
            request.getRequestDispatcher("ajouter.jsp").forward(request, response);
            return; // IMPORTANT: arrêter l'exécution ici
            
        } else if (path.equals("/update")) {

            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Modification du produit ID: " + id);
            
            Produit p = gestion.getProduct(id);
            request.setAttribute("produit", p);
            
            List<Categorie> categories = gestionCategorie.getAllCategories();
            request.setAttribute("categories", categories);
            
            request.getRequestDispatcher("ajouter.jsp").forward(request, response);
            return; // IMPORTANT: arrêter l'exécution ici
        }

        // Si aucun chemin ne correspond, rediriger vers accueil
        System.out.println("Aucun chemin trouvé, redirection vers accueil");
        response.sendRedirect("acceuil");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        
        System.out.println("=== doPost appelé avec path: " + path + " ===");

        if (path.equals("/save")) {

            int id = 0;
            if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                id = Integer.parseInt(request.getParameter("id"));
            }

            String nom = request.getParameter("nom");
            double prix = Double.parseDouble(request.getParameter("prix"));
            int quantite = Integer.parseInt(request.getParameter("quantite"));
            
            Long categorieId = null;
            if (request.getParameter("categorieId") != null && !request.getParameter("categorieId").isEmpty()) {
                categorieId = Long.parseLong(request.getParameter("categorieId"));
            }

            Produit p = new Produit(id, nom, prix, quantite);
            
            if (categorieId != null && categorieId > 0) {
                Categorie categorie = gestionCategorie.getCategorieById(categorieId);
                if (categorie != null) {
                    p.setCategorie(categorie);
                }
            }

            if (id == 0) {
                gestion.addProduct(p);
                System.out.println("Produit ajouté: " + nom);
            } else {
                gestion.updateProduit(p);
                System.out.println("Produit modifié: " + nom);
            }

            response.sendRedirect("acceuil");
            return;
        }

        // Si ce n'est pas /save, rediriger vers doGet
        doGet(request, response);
    }
}