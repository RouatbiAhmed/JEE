package dao;

import java.util.List;

import entity.Produit;

public interface IGestionProduit {

	void addProduct(Produit p);
	Produit getProduct(int id);
	List<Produit> getAllProduits();
	List<Produit> getProductByMC(String mc);
	void deleteProduit(int id);
	void updateProduit(Produit p);
}
