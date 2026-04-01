package entity;

import javax.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nom;
    
    private double prix;
    private int quantite;
    
    @ManyToOne(fetch = FetchType.EAGER, optional= false)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // Constructeurs
    public Produit() {}

    public Produit(String nom, double prix, int quantite) {
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Produit(int id, String nom, double prix, int quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }
    
    public Produit(String nom, double prix, int quantite, Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    // GETTERS et SETTERS
    public int getId() { 
        return id; 
    }
    
    public String getNom() { 
        return nom; 
    }
    
    public double getPrix() { 
        return prix; 
    }
    
    public int getQuantite() { 
        return quantite; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public void setNom(String nom) { 
        this.nom = nom; 
    }
    
    public void setPrix(double prix) { 
        this.prix = prix; 
    }
    
    public void setQuantite(int quantite) { 
        this.quantite = quantite; 
    }
    
    public Categorie getCategorie() { 
        return categorie; 
    }
    
    public void setCategorie(Categorie categorie) { 
        this.categorie = categorie; 
    }

    @Override
    public String toString() {
        return "Produit [id=" + id + ", nom=" + nom + ", prix=" + prix + 
               ", quantite=" + quantite + ", categorie=" + 
               (categorie != null ? categorie.getNom() : "null") + "]";
    }
}