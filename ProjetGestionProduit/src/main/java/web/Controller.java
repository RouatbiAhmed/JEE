package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GestoionProduitsImp;
import dao.IGestionProduit;
import entity.Produit;

@WebServlet(urlPatterns = { "/", "/acceuil" , "/search" , "/delete" ,"/ajout" , "/save" , "/update" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IGestionProduit gestion;

	public void init(ServletConfig config) throws ServletException {
		gestion = new GestoionProduitsImp();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getServletPath();

		if (path.equals("/") || path.equals("/acceuil")) {

			List<Produit> liste = gestion.getAllProduits();
			request.setAttribute("products", liste);

		} else if (path.equals("/search")) {

			String mc = request.getParameter("mc");
			List<Produit> liste = gestion.getProductByMC(mc);
			request.setAttribute("products", liste);

		} else if (path.equals("/delete")) {

			int id = Integer.parseInt(request.getParameter("id"));
			gestion.deleteProduit(id);

			List<Produit> liste = gestion.getAllProduits();
			request.setAttribute("products", liste);
			response.sendRedirect("accueil");
			return;
		}
		else if(path.equals("/ajout")) {
			request.getRequestDispatcher("ajouter.jsp").forward(request, response);
			return;
			
		}
		else if (path.equals("/update")) {

		    int id = Integer.parseInt(request.getParameter("id"));

		    Produit p = gestion.getProduct(id);

		    request.setAttribute("produit", p);

		    request.getRequestDispatcher("ajouter.jsp")
		           .forward(request, response);

		    return;
		}


		request.getRequestDispatcher("accueil.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String path = request.getServletPath();

	    if(path.equals("/save")) {

	        int id = 0;

	        if(request.getParameter("id") != null && 
	           !request.getParameter("id").isEmpty()) {

	            id = Integer.parseInt(request.getParameter("id"));
	        }

	        String nom = request.getParameter("nom");
	        double prix = Double.parseDouble(request.getParameter("prix"));
	        int quantite = Integer.parseInt(request.getParameter("quantite"));

	        Produit p = new Produit(id, nom, prix, quantite);

	        if(id == 0) {
	            gestion.addProduct(p);      // INSERT
	        } else {
	            gestion.updateProduit(p);   // UPDATE
	        }

	        response.sendRedirect("acceuil");
	        return;
	    }

	    doGet(request, response);
	}

}
