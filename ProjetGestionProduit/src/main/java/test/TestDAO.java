package test;

import dao.GestoionProduitsImp; 
import entity.Produit;

public class TestDAO {
    public static void main(String[] args) {
    	GestoionProduitsImp dao = new GestoionProduitsImp();

        
        dao.addProduct(new Produit("Laptop", 1200.50, 10));
        

        System.out.println(dao.getAllProduits());
    }
}