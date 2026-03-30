<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entity.Utilisateur" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Catégories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
</head>
<body>

<%@include file="nav.html" %>

<%
    // Vérifier si l'utilisateur est ADMIN
    HttpSession sessionHttp = request.getSession(false);
    Utilisateur currentUser = (sessionHttp != null) ? (Utilisateur) sessionHttp.getAttribute("user") : null;
    boolean isAdmin = currentUser != null && "ADMIN".equals(currentUser.getRole());
    
    if (!isAdmin) {
        response.sendRedirect("acceuil");
        return;
    }
%>

<div class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h3><i class="fa fa-list"></i> Gestion des Catégories</h3>
        </div>
        <div class="card-body">
            
            <a href="categories?action=add" class="btn btn-success mb-3">
                <i class="fa fa-plus"></i> Nouvelle Catégorie
            </a>
            
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty categories}">
                        <tr>
                            <td colspan="4" class="text-center text-warning">
                                Aucune catégorie trouvée.
                             </td>
                         </tr>
                    </c:if>
                    
                    <c:forEach var="cat" items="${categories}">
                        <tr>
                            <td>${cat.id}</td>
                            <td>${cat.nom}</td>
                            <td>${cat.description}</td>
                            <td>
                                <a href="categories?action=edit&id=${cat.id}" 
                                   class="btn btn-warning btn-sm">
                                   <i class="fa fa-edit"></i>
                                </a>
                                <a href="categories?action=delete&id=${cat.id}" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Supprimer cette catégorie ?')">
                                   <i class="fa fa-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <a href="acceuil" class="btn btn-secondary">
                <i class="fa fa-arrow-left"></i> Retour à l'accueil
            </a>
        </div>
    </div>
</div>

</body>
</html>