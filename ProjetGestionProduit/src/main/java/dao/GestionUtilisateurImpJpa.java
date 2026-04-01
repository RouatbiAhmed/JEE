
package dao;

import java.util.List;
import javax.persistence.*;
import entity.Utilisateur;

public class GestionUtilisateurImpJpa implements IGestionUtilisateur {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public GestionUtilisateurImpJpa() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            emf = Persistence.createEntityManagerFactory("MonUniteDePersistence");
            em = emf.createEntityManager();
            System.out.println("GestionUtilisateurImpJpa initialisé");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur JPA", e);
        }
    }
    
    @Override
    public Utilisateur authenticate(String username, String password) {
        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.username = :username AND u.password = :password",
                Utilisateur.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            
            List<Utilisateur> resultats = query.getResultList();
            
            if (!resultats.isEmpty()) {
                System.out.println("Authentification réussie pour: " + username);
                return resultats.get(0);
            } else {
                System.out.println("Échec authentification pour: " + username);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Utilisateur getUtilisateurByUsername(String username) {
        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.username = :username",
                Utilisateur.class);
            query.setParameter("username", username);
            
            List<Utilisateur> resultats = query.getResultList();
            return resultats.isEmpty() ? null : resultats.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void addUtilisateur(Utilisateur u) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(u);
            tx.commit();
            System.out.println("Utilisateur ajouté: " + u.getUsername());
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void close() {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }
}