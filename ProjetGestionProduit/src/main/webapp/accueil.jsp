<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entity.Utilisateur" %>
<%@ page import="javax.servlet.http.HttpSession" %>

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
    // Récupérer l'utilisateur connecté depuis la session
    HttpSession sessionHttp = request.getSession(false);
    Utilisateur currentUser = (sessionHttp != null) ? (Utilisateur) sessionHttp.getAttribute("user") : null;
    boolean isAdmin = currentUser != null && "ADMIN".equals(currentUser.getRole());
%>

<div class="container mt-4">

    <!-- ====== FORMULAIRE RECHERCHE ====== -->
    <form method="get" action="search" class="mb-3">
        <label>Mot clé :</label>
        <input type="text" name="mc" class="form-control w-25 d-inline"
               value="${param.mc}">
        <button type="submit" class="btn btn-primary">
            <i class="fa fa-search"></i> Rechercher
        </button>
    </form>

    <!-- ====== TABLE PRODUITS ====== -->
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Id</th>
                <th>Nom</th>
                <th>Prix</th>
                <th>Quantite</th>
                <th>Catégorie</th> 
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>

            <!-- ====== SI LISTE VIDE ====== -->
            <c:if test="${empty products}">
                <tr>
                    <td colspan="7" class="text-center text-warning">
                        Aucun produit trouvé.
                    </td>
                </tr>
            </c:if>

            <!-- ====== BOUCLE JSTL ====== -->
            <c:forEach var="p" items="${products}" varStatus="s">

                <tr class="${s.index % 2 == 0 ? 'table-light' : 'table-secondary'}">

                    <!-- Index -->
                    <td>${s.count}
                        <c:if test="${s.first}">
                            <span class="badge bg-success ms-2">Premier</span>
                        </c:if>

                        <c:if test="${s.last}">
                            <span class="badge bg-danger ms-2">Dernier</span>
                        </c:if>
                    </td>

                    <!-- Id -->
                    <td>${p.id}</td>

                    <!-- Nom -->
                    <td>${p.nom}</td>

                    <!-- Prix -->
                    <td>${p.prix}</td>

                    <!-- Quantite -->
                    <td>${p.quantite}</td>
                    
                    <!-- Catégorie -->
                    <td>${p.categorie != null ? p.categorie.nom : 'Sans catégorie'}</td>

                    <!-- Actions -->
                    <td>
                        <% if (isAdmin) { %>
                            <a href="update?id=${p.id}" 
                               class="btn btn-warning btn-sm">
                               <i class="fa fa-edit"></i>
                            </a>

                            <a href="delete?id=${p.id}" 
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Confirmer la suppression ?');">
                               <i class="fa fa-trash"></i>
                            </a>
                        <% } else { %>
                            <span class="text-muted">Aucune action</span>
                        <% } %>
                    </td>

                </tr>

            </c:forEach>

        </tbody>
    </table>

</div>

</body>
</html>