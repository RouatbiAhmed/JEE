package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "categorie")
public class Categorie implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nom;
    
    @Column(length = 500)
    private String description;
    
    @OneToMany(mappedBy = "categorie", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Produit> produits = new ArrayList<>();
    
    // Constructeurs
    public Categorie() {}
    
    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<Produit> getProduits() {
        return produits;
    }
    
    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
    
    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addProduit(Produit produit) {
        produits.add(produit);
        produit.setCategorie(this);
    }
    
    public void removeProduit(Produit produit) {
        produits.remove(produit);
        produit.setCategorie(null);
    }
    
    @Override
    public String toString() {
        return nom;
    }
}