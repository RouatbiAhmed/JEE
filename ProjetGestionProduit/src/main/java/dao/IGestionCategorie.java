package dao;

import java.util.List;
import entity.Categorie;

public interface IGestionCategorie {
    
    List<Categorie> getAllCategories();
    
    Categorie getCategorieById(Long id);
    
    void addCategorie(Categorie c);
    
    void updateCategorie(Categorie c);
    
    void deleteCategorie(Long id);
}