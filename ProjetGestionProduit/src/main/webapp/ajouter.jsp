<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>
    <c:if test="${empty produit}">Ajouter Produit</c:if>
    <c:if test="${not empty produit}">Modifier Produit</c:if>
</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet">

<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
      
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-styles.css">

</head>
<body>

<%@include file="nav.jsp" %>

<div class="container mt-5">
    <!-- Added form-card class -->
    <div class="card shadow form-card">
        <!-- Changed to form-header class -->
        <div class="card-header form-header">
            <h3 class="mb-0">
                <i class="fa fa-box"></i>
                <c:if test="${empty produit}">Ajouter Produit</c:if>
                <c:if test="${not empty produit}">Modifier Produit</c:if>
            </h3>
        </div>

        <!-- Changed to form-body class -->
        <div class="card-body form-body">

            <form method="post" action="${pageContext.request.contextPath}/save">

                <!-- ID caché pour UPDATE -->
                <input type="hidden" name="id" value="${produit.id}">

                <!-- Nom -->
                <div class="mb-3">
                    <label class="form-label">Nom :</label>
                    <input type="text"
                           name="nom"
                           value="${produit.nom}"
                           class="form-control"
                           required>
                </div>

                <!-- Prix -->
                <div class="mb-3">
                    <label class="form-label">Prix :</label>
                    <input type="number"
                           step="0.01"
                           name="prix"
                           value="${produit.prix}"
                           class="form-control"
                           required>
                </div>

                <!-- Quantite -->
                <div class="mb-3">
                    <label class="form-label">Quantite :</label>
                    <input type="number"
                           name="quantite"
                           value="${produit.quantite}"
                           class="form-control"
                           required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label">Catégorie :</label>
                    <select name="categorieId" class="form-select" required>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.id}" 
                                <c:if test="${produit.categorie.id eq cat.id}">selected</c:if>>
                                ${cat.nom}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">Veuillez sélectionner une catégorie</div>
                </div>

                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-success">
                        <i class="fa fa-save"></i>
                        <c:if test="${empty produit}">Ajouter</c:if>
                        <c:if test="${not empty produit}">Modifier</c:if>
                    </button>

                    <a href="acceuil" class="btn btn-secondary">
                        <i class="fa fa-arrow-left"></i> Retour
                    </a>
                </div>

            </form>

        </div>
    </div>
</div>

</body>
</html>