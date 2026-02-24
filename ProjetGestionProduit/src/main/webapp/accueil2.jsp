<%@page import="entity.Produit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des Produits</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet">

<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

</head>
<body>

<%@include file="nav.html" %>

<%
    List<Produit> liste = (List<Produit>) request.getAttribute("products");
%>

<div class="container mt-4">

    <form method="get" action="search" class="mb-3">
        <label>Mot clé :</label>
        <input type="text" name="mc" class="form-control w-25 d-inline">
        <button type="submit" class="btn btn-primary">
            <i class="fa fa-search"></i> Rechercher
        </button>
    </form>

   
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>Id</th>
                <th>Nom</th>
                <th>Prix</th>
                <th>Quantite</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <%
            if (liste != null) {
                for (Produit p : liste) {
        %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getNom() %></td>
                <td><%= p.getPrix() %></td>
                <td><%= p.getQuantite() %></td>
                
                <td>
    <!-- Bouton Modifier -->
    <a href="update?id=<%= p.getId() %>" 
       class="btn btn-warning btn-sm">
       <i class="fa fa-edit"></i>
    </a>

    <!-- Bouton Supprimer -->
    <a href="delete?id=<%= p.getId() %>" 
       class="btn btn-danger btn-sm"
       onclick="return confirm('Confirmer la suppression ?');">
       <i class="fa fa-trash"></i>
    </a>
</td>

            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

</div>

</body>
</html>
