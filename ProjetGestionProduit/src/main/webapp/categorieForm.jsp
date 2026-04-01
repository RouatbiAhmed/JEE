<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${empty categorie.id}">Ajouter Catégorie</c:if>
        <c:if test="${not empty categorie.id}">Modifier Catégorie</c:if>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-styles.css">
</head>
<body>

<%@include file="nav.jsp" %>

<div class="container mt-5">
    <div class="card shadow form-card">
        <div class="card-header form-header">
            <h3 class="mb-0">
                <i class="fa fa-tag"></i>
                <c:if test="${empty categorie.id}">Ajouter Catégorie</c:if>
                <c:if test="${not empty categorie.id}">Modifier Catégorie</c:if>
            </h3>
        </div>
        <div class="card-body form-body">
            
            <form method="post" action="categories">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="id" value="${categorie.id}">
                
                <div class="mb-3">
                    <label class="form-label">
                        <i class="fas fa-tag"></i> Nom :
                    </label>
                    <input type="text" name="nom" value="${categorie.nom}" 
                           class="form-control" required
                           placeholder="Entrez le nom de la catégorie">
                </div>
                
                <div class="mb-3">
                    <label class="form-label">
                        <i class="fas fa-align-left"></i> Description :
                    </label>
                    <textarea name="description" class="form-control" rows="3" 
                              placeholder="Entrez une description (optionnelle)">${categorie.description}</textarea>
                </div>
                
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-success">
                        <i class="fa fa-save"></i> Enregistrer
                    </button>
                    
                    <a href="categories?action=list" class="btn btn-secondary">
                        <i class="fa fa-arrow-left"></i> Annuler
                    </a>
                </div>
            </form>
            
        </div>
    </div>
</div>

</body>
</html>