package dao;

import entity.Utilisateur;

public interface IGestionUtilisateur {
    
    // Authentifier un utilisateur
    Utilisateur authenticate(String username, String password);
    
    // Récupérer un utilisateur par son username
    Utilisateur getUtilisateurByUsername(String username);
    
    // Ajouter un utilisateur (pour admin)
    void addUtilisateur(Utilisateur u);
    
    // Lister tous les utilisateurs (pour admin)
    java.util.List<Utilisateur> getAllUtilisateurs();
}