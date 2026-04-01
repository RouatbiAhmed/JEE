package dao;

import java.util.List;
import javax.persistence.*;

import entity.Produit;

public class GestionProduitsImpJpa implements IGestionProduit {

    private EntityManagerFactory emf;
    private EntityManager em;

    public GestionProduitsImpJpa() {
        try {
            // Charger explicitement le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL chargé avec succès !");
            
            emf = Persistence.createEntityManagerFactory("MonUniteDePersistence");
            em = emf.createEntityManager();
            System.out.println("EntityManager créé avec succès !");
            
        } catch (ClassNotFoundException e) {
            System.err.println("ERREUR: Driver MySQL non trouvé !");
            e.printStackTrace();
            throw new RuntimeException("Driver MySQL manquant", e);
        } catch (Exception e) {
            System.err.println("ERREUR lors de l'initialisation de JPA:");
            e.printStackTrace();
            throw new RuntimeException("Impossible d'initialiser JPA", e);
        }
    }

    
    @Override
    public void addProduct(Produit p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
            System.out.println("Produit ajouté: " + p.getNom());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Produit getProduct(int id) {
        return em.find(Produit.class, id);
    }

    @Override
    public List<Produit> getAllProduits() {
        try {
            List<Produit> produits = em.createQuery("FROM Produit", Produit.class).getResultList();
            System.out.println("Nombre de produits trouvés: " + (produits != null ? produits.size() : 0));
            return produits;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Produit> getProductByMC(String mc) {
        try {
            return em.createQuery("FROM Produit p WHERE p.nom LIKE :mc", Produit.class)
                    .setParameter("mc", "%" + mc + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteProduit(int id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Produit p = em.find(Produit.class, id);
            if (p != null) {
                em.remove(p);
                System.out.println("Produit supprimé: ID " + id);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduit(Produit p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(p);
            tx.commit();
            System.out.println("Produit mis à jour: " + p.getNom());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}