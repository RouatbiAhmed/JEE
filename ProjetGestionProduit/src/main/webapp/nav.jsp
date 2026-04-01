<%@ page import="entity.Utilisateur" %>
<%
    HttpSession sessionHttp = request.getSession(false);
    Utilisateur currentUser = (sessionHttp != null) ? (Utilisateur) sessionHttp.getAttribute("user") : null;
    boolean isAdmin = currentUser != null && "ADMIN".equals(currentUser.getRole());
    String username = currentUser != null ? currentUser.getUsername() : "Invité";
    
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Navbar</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Poppins', sans-serif;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        min-height: 100vh;
    }

    .navbar {
        background: rgba(255, 255, 255, 0.95);
        backdrop-filter: blur(10px);
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        position: sticky;
        top: 0;
        z-index: 1000;
        padding: 0 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
    }

    .navbar-brand {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 15px 0;
    }

    .navbar-brand i {
        font-size: 28px;
        color: #667eea;
    }

    .navbar-brand span {
        font-size: 22px;
        font-weight: 600;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        -webkit-background-clip: text;
        background-clip: text;
        color: transparent;
    }

    .nav-links {
        display: flex;
        gap: 10px;
        align-items: center;
        flex-wrap: wrap;
    }

    .nav-links a {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 10px 20px;
        text-decoration: none;
        font-weight: 500;
        border-radius: 10px;
        transition: all 0.3s ease;
        position: relative;
    }

    /* Left side links (Accueil, Ajouter, Categories) */
    .nav-links .nav-link {
        color: #2c3e50;
    }

    .nav-links .nav-link i {
        font-size: 16px;
        transition: transform 0.3s ease;
    }

    .nav-links .nav-link:hover {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        transform: translateY(-2px);
        box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
    }

    .nav-links .nav-link:hover i {
        transform: scale(1.1);
    }

    /* Active page indicator */
    .nav-links .nav-link.active {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
    }

    .logout-btn {
        background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
        color: white !important;
        margin-left: 15px;
        box-shadow: 0 2px 10px rgba(231, 76, 60, 0.3);
    }

    .logout-btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 5px 20px rgba(231, 76, 60, 0.4);
        background: linear-gradient(135deg, #c0392b 0%, #e74c3c 100%);
    }

    .user-info {
        display: flex;
        align-items: center;
        gap: 15px;
        padding: 10px 0;
        border-left: 2px solid #e0e0e0;
        margin-left: 15px;
        padding-left: 20px;
    }

    .user-avatar {
        width: 40px;
        height: 40px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-weight: bold;
        font-size: 16px;
    }

    .user-name {
        color: #2c3e50;
        font-weight: 500;
        font-size: 14px;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .navbar {
            flex-direction: column;
            padding: 15px;
        }
        
        .nav-links {
            justify-content: center;
            margin: 10px 0;
        }
        
        .user-info {
            border-left: none;
            border-top: 1px solid #e0e0e0;
            margin-left: 0;
            padding-left: 0;
            margin-top: 10px;
        }
    }
</style>

</head>
<body>

<div class="navbar">
    <div class="navbar-brand">
        <i class="fas fa-store"></i>
        <span>Gestion Produits</span>
    </div>

    <div class="nav-links">
        <a href="acceuil" class="nav-link" id="linkAccueil">
            <i class="fas fa-home"></i>
            <span>Accueil</span>
        </a>
        <% if (isAdmin) { %>
        <a href="ajout" class="nav-link" id="linkAjout">
            <i class="fas fa-plus-circle"></i>
            <span>Ajouter Produit</span>
        </a>
        
        <a href="categories" class="nav-link" id="linkCategories">
            <i class="fas fa-tags"></i>
            <span>Gérer Catégories</span>
        </a>
        <% } %>
    </div>

    <div class="user-info">
        <div class="user-avatar">
            <i class="fas fa-user"></i>
        </div>
        <div class="user-name">
            <span><%= username %></span>
        </div>
        
        <% if (currentUser != null) { %>
            <a href="logout" class="nav-link logout-btn" onclick="return confirm('Voulez-vous vraiment vous déconnecter ?');">
                <i class="fas fa-sign-out-alt"></i>
                <span>Déconnexion</span>
            </a>
        <% } %>

    </div>
</div>

<script>
    // Active link highlighting
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    
    if (currentPath.includes('acceuil') || currentPath === '/' || currentPath === '/ProjetGestionProduit/') {
        document.getElementById('linkAccueil')?.classList.add('active');
    } else if (currentPath.includes('ajout')) {
        document.getElementById('linkAjout')?.classList.add('active');
    } else if (currentPath.includes('categories')) {
        document.getElementById('linkCategories')?.classList.add('active');
    }
</script>

</body>
</html>