package dao;

import java.util.List;
import javax.persistence.*;
import entity.Categorie;

public class GestionCategorieImpJpa implements IGestionCategorie {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public GestionCategorieImpJpa() {
        try {
            // Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Créer l'EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("MonUniteDePersistence");
            em = emf.createEntityManager();
            
            System.out.println("GestionCategorieImpJpa initialisé avec succès");
        } catch (ClassNotFoundException e) {
            System.err.println("ERREUR: Driver MySQL non trouvé !");
            e.printStackTrace();
            throw new RuntimeException("Driver MySQL manquant", e);
        } catch (Exception e) {
            System.err.println("ERREUR lors de l'initialisation de JPA");
            e.printStackTrace();
            throw new RuntimeException("Impossible d'initialiser JPA", e);
        }
    }
    
    @Override
    public List<Categorie> getAllCategories() {
        try {
            // Requête JPQL pour récupérer toutes les catégories
            return em.createQuery("SELECT c FROM Categorie c ORDER BY c.nom", Categorie.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des catégories");
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Categorie getCategorieById(Long id) {
        try {
            return em.find(Categorie.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la catégorie ID: " + id);
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void addCategorie(Categorie c) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(c);
            tx.commit();
            System.out.println("Catégorie ajoutée: " + c.getNom());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Erreur lors de l'ajout de la catégorie: " + c.getNom());
            e.printStackTrace();
        }
    }
    
    @Override
    public void updateCategorie(Categorie c) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(c);
            tx.commit();
            System.out.println("Catégorie mise à jour: " + c.getNom());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Erreur lors de la mise à jour de la catégorie: " + c.getNom());
            e.printStackTrace();
        }
    }
    
    @Override
    public void deleteCategorie(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Categorie c = em.find(Categorie.class, id);
            if (c != null) {
                // Vérifier si la catégorie a des produits
                if (!c.getProduits().isEmpty()) {
                    System.out.println("Attention: La catégorie " + c.getNom() + 
                                     " contient " + c.getProduits().size() + " produit(s).");
                }
                em.remove(c);
                tx.commit();
                System.out.println("Catégorie supprimée: " + c.getNom());
            } else {
                System.out.println("Catégorie avec ID " + id + " non trouvée");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Erreur lors de la suppression de la catégorie ID: " + id);
            e.printStackTrace();
        }
    }
    
    // Méthode pour fermer les ressources (à appeler quand l'application s'arrête)
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        System.out.println("Ressources JPA fermées");
    }
}