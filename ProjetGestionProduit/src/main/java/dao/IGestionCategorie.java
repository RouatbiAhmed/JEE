package dao;

import java.util.List;
import entity.Categorie;

public interface IGestionCategorie {
    
    // Lister toutes les catégories
    List<Categorie> getAllCategories();
    
    // Récupérer une catégorie par son ID
    Categorie getCategorieById(Long id);
    
    // Ajouter une nouvelle catégorie
    void addCategorie(Categorie c);
    
    // Modifier une catégorie existante
    void updateCategorie(Categorie c);
    
    // Supprimer une catégorie par son ID
    void deleteCategorie(Long id);
}