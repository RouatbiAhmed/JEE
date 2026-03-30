<%@page import="entity.Produit"%>
<%@page import="entity.Categorie"%>
<%@page import="entity.Produit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // Récupération du produit envoyé par le Controller (cas UPDATE)
    Produit p = (Produit) request.getAttribute("produit");

    String action = "Ajouter";

    if(p == null){
        p = new Produit();   
    } else {
        action = "Modifier"; 
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= action %> Produit</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet">

<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

</head>
<body>

<%@include file="nav.html" %>

<div class="container mt-5">
   <div class="card shadow">

        <div class="card-header bg-primary text-white">
            <h3><i class="fa fa-box"></i> <%= action %> Produit</h3>
        </div>

        <div class="card-body">

            <form method="post" action="save">

                <!-- Champ caché ID (important pour UPDATE) -->
                <input type="hidden" name="id" value="<%= p.getId() %>">

                <div class="mb-3">
                    <label class="form-label">Nom :</label>
                    <input type="text" 
                           name="nom"
                           value="<%= p.getNom() != null ? p.getNom() : "" %>"
                           class="form-control" 
                           required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Prix :</label>
                    <input type="number" 
                           step="0.01" 
                           name="prix"
                           value="<%= p.getPrix() %>"
                           class="form-control" 
                           required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Quantite :</label>
                    <input type="number" 
                           name="quantite"
                           value="<%= p.getQuantite() %>"
                           class="form-control" 
                           required>
                </div>
                <!-- Catégorie -->
                <div class="mb-3">
               <label class="form-label">Catégorie :</label>
             <select name="categorieId" class="form-select">
              <option value="">-- Sélectionner une catégorie --</option>
           <%
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
               if (categories != null) {
                   for (Categorie cat : categories) {
              %>
              <option value="<%= cat.getId() %>" 
            <%= (p != null && p.getCategorie() != null && p.getCategorie().getId().equals(cat.getId())) ? "selected" : "" %>>
            <%= cat.getNom() %>
            </option>
             <%
                          }
                  }
             %>
               </select>
              </div>

                <button type="submit" class="btn btn-success">
                    <i class="fa fa-save"></i> <%= action %>
                </button>

                <a href="acceuil" class="btn btn-secondary">
                    <i class="fa fa-arrow-left"></i> Retour
                </a>

            </form>

        </div>
   </div>
</div>

</body>
</html>
