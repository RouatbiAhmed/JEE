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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table-styles.css">
</head>
<body>

<%@include file="nav.jsp" %>

<div class="container mt-5">
    <div class="card shadow-lg border-0">
        <div class="card-header" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
            <h3 class="mb-0">
                <i class="fas fa-tags me-2"></i>
                Gestion des Catégories
            </h3>
        </div>
        <div class="card-body p-4">
            
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div class="table-stats">
                    <i class="fas fa-database"></i> Total: <strong>${categories.size()}</strong> catégorie(s)
                </div>
                <a href="categories?action=add" class="btn btn-new">
                    <i class="fas fa-plus-circle me-2"></i> Nouvelle Catégorie
                </a>
            </div>
            
            <div class="table-container">
                <table class="table">
	                    <thead><th width="10%">ID</th>
                            <th width="25%">Nom</th>
                            <th width="45%">Description</th>
                            <th width="20%" class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty categories}">
                            <tr>
                                <td colspan="4">
                                    <div class="empty-state">
                                        <i class="fas fa-folder-open"></i>
                                        <h5>Aucune catégorie trouvée</h5>
                                        <p>Cliquez sur "Nouvelle Catégorie" pour en créer une</p>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                        
                        <c:forEach var="cat" items="${categories}">
                            <tr>
                                <td data-label="ID">
                                    <span class="badge-category">#${cat.id}</span>
                                </td>
                                <td data-label="Nom">
                                    <i class="fas fa-tag text-primary me-2"></i>
                                    <strong>${cat.nom}</strong>
                                </td>
                                <td data-label="Description">
                                    <c:choose>
                                        <c:when test="${empty cat.description}">
                                            <span class="text-muted fst-italic">
                                                <i class="fas fa-edit me-1"></i> Aucune description
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-align-left text-muted me-1"></i>
                                            ${cat.description}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td data-label="Actions">
                                    <div class="action-buttons">
                                        <a href="categories?action=edit&id=${cat.id}" 
                                           class="btn btn-warning btn-sm btn-action"
                                           title="Modifier">
                                           <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="categories?action=delete&id=${cat.id}" 
                                           class="btn btn-danger btn-sm btn-action"
                                           onclick="return confirm('⚠️ Supprimer cette catégorie ?\n\nTous les produits de cette catégorie seront également supprimés !');"
                                           title="Supprimer">
                                           <i class="fas fa-trash-alt"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <div class="mt-4 text-center">
                <a href="acceuil" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-2"></i> Retour à l'accueil
                </a>
            </div>
        </div>
    </div>
</div>

</body>
</html>