package test;

import dao.GestionProduitsImpJpa; 
import entity.Produit;

public class TestDAO {
    public static void main(String[] args) {
    	GestionProduitsImpJpa dao = new GestionProduitsImpJpa();

        
        dao.addProduct(new Produit("Laptop", 1200.50, 10));
        

        System.out.println(dao.getAllProduits());
    }
}