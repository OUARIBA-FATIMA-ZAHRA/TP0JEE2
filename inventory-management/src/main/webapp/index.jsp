<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion d'Inventaire - Accueil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/header.jsp" />

    <main class="container mt-4">
        <div class="jumbotron bg-light p-5 rounded-3">
            <h1 class="display-4">Bienvenue dans l'application de gestion d'inventaire</h1>
            <p class="lead">Solution complète de gestion des catégories et fournisseurs avec Jakarta EE et Hibernate.</p>
            <hr class="my-4">
            <p>Utilisez les menus de navigation pour gérer les catégories et les fournisseurs.</p>

            <div class="row mt-4">
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h5 class="mb-0">Gestion des Catégories</h5>
                        </div>
                        <div class="card-body">
                            <p>Créez, modifiez et supprimez des catégories de produits.</p>
                            <a href="${pageContext.request.contextPath}/categories" class="btn btn-primary">
                                Accéder aux catégories
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-header bg-success text-white">
                            <h5 class="mb-0">Gestion des Fournisseurs</h5>
                        </div>
                        <div class="card-body">
                            <p>Gérez les informations des fournisseurs et leurs coordonnées.</p>
                            <a href="${pageContext.request.contextPath}/suppliers" class="btn btn-success">
                                Accéder aux fournisseurs
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/fragments/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>