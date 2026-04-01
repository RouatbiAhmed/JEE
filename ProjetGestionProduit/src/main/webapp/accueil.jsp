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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table-styles.css">

<style>
    .search-form {
        background: white;
        padding: 20px;
        border-radius: 12px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        margin-bottom: 25px;
    }
    
    .search-form label {
        font-weight: 500;
        color: #2c3e50;
        margin-right: 10px;
    }
    
    .search-input {
        width: 250px;
        display: inline-block;
        margin-right: 10px;
    }
    
    @media (max-width: 768px) {
        .search-input {
            width: 100%;
            margin-bottom: 10px;
        }
        
        .search-form .btn {
            width: 100%;
        }
    }
</style>

</head>
<body>

<%@include file="nav.jsp" %>

<div class="container mt-4">

    <div class="search-form">
        <form method="get" action="search" class="d-flex flex-wrap align-items-center gap-2">
            <label><i class="fas fa-search"></i> Mot clé :</label>
            <input type="text" name="mc" class="form-control search-input"
                   value="${param.mc}" placeholder="Rechercher un produit...">
            <button type="submit" class="btn btn-new">
                <i class="fa fa-search"></i> Rechercher
            </button>
        </form>
    </div>

    <div class="table-container">
        <table class="table">
            <thead>
                <tr>
                    <th width="5%"><i class="fas fa-hashtag"></i> #</th>
                    <th width="5%"><i class="fas fa-id-card"></i> Id</th>
                    <th width="25%"><i class="fas fa-box"></i> Nom</th>
                    <th width="15%"><i class="fas fa-euro-sign"></i> Prix</th>
                    <th width="15%"><i class="fas fa-cubes"></i> Quantite</th>
                    <th width="20%"><i class="fas fa-tag"></i> Catégorie</th> 
                    <th width="15%" class="text-center"><i class="fas fa-cogs"></i> Actions</th>
                </tr>
            </thead>

            <tbody>

                <c:if test="${empty products}">
                    <tr>
                        <td colspan="7">
                            <div class="empty-state">
                                <i class="fas fa-box-open"></i>
                                <h5>Aucun produit trouvé</h5>
                                <p>Cliquez sur "Ajouter Produit" pour créer un nouveau produit</p>
                            </div>
                        </td>
                    </tr>
                </c:if>

                <!-- ====== BOUCLE JSTL ====== -->
                <c:forEach var="p" items="${products}" varStatus="s">

                    <tr>

                        <!-- Index -->
                        <td data-label="#">
                            <span class="badge-category">${s.count}</span>
                            <c:if test="${s.first}">
                                <span class="badge bg-success ms-2">Premier</span>
                            </c:if>
                            <c:if test="${s.last}">
                                <span class="badge bg-danger ms-2">Dernier</span>
                            </c:if>
                        </td>

                        <!-- Id -->
                        <td data-label="Id">
                            <span class="badge-category">${p.id}</span>
                        </td>

                        <!-- Nom -->
                        <td data-label="Nom">
                            <i class="fas fa-box text-primary me-2"></i>
                            <strong>${p.nom}</strong>
                        </td>

                        <!-- Prix -->
                        <td data-label="Prix">
                            <i class="fas fa-euro-sign text-success me-1"></i>
                            ${p.prix} €
                        </td>

                        <!-- Quantite -->
                        <td data-label="Quantite">
                            <i class="fas fa-cubes text-info me-1"></i>
                            ${p.quantite}
                        </td>
                        
                        <!-- Catégorie -->
                        <td data-label="Catégorie">
                            <c:choose>
                                <c:when test="${not empty p.categorie}">
                                    <i class="fas fa-tag text-primary me-1"></i>
                                    ${p.categorie.nom}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted fst-italic">
                                        <i class="fas fa-ban me-1"></i>Sans catégorie
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <!-- Actions -->
                        <td data-label="Actions">
                            <div class="action-buttons">
                                <% if (isAdmin) { %>
                                    <a href="update?id=${p.id}" 
                                       class="btn btn-warning btn-sm btn-action"
                                       title="Modifier">
                                       <i class="fas fa-edit"></i>
                                    </a>

                                    <a href="delete?id=${p.id}" 
                                       class="btn btn-danger btn-sm btn-action"
                                       onclick="return confirm('⚠️ Confirmer la suppression ?\n\nProduit: ${p.nom}\nCette action est irréversible !');"
                                       title="Supprimer">
                                       <i class="fas fa-trash-alt"></i>
                                    </a>
                                <% } else { %>
                                    <span class="text-muted">
                                        <i class="fas fa-lock me-1"></i>Aucune action
                                    </span>
                                <% } %>
                            </div>
                        </td>

                    </tr>

                </c:forEach>

            </tbody>
        </table>
    </div>

</div>

</body>
</html>