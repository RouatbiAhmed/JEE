package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Produit;

public class GestoionProduitsImp implements IGestionProduit{
	
	private Connection cnx= SingletonConnection.getInstance();
	
	
	

	@Override
	public void addProduct(Produit p) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO produit(nom,prix,quantite) VALUES (?,?,?)";
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(sql);
			ps.setString(1, p.getNom());
			ps.setDouble(2, p.getPrix());
			ps.setInt(3, p.getQuantite());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Produit getProduct(int id) {
		// TODO Auto-generated method stub
		Produit p = null;
	    String sql = "SELECT * FROM produit WHERE id = ?";
	    try {
	        PreparedStatement ps = cnx.prepareStatement(sql);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            p = new Produit(
	                    rs.getInt("id"),
	                    rs.getString("nom"),
	                    rs.getDouble("prix"),
	                    rs.getInt("quantite")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return p;
	}

	@Override
	public List<Produit> getAllProduits() {
		// TODO Auto-generated method stub
		List<Produit> liste = new ArrayList<Produit>();
		try {
			PreparedStatement ps = cnx.prepareStatement("select * from produit");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				liste.add(new Produit(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	@Override
	public List<Produit> getProductByMC(String mc) {
		// TODO Auto-generated method stub
		List<Produit> liste = new ArrayList<>();
	    String sql = "SELECT * FROM produit WHERE nom LIKE ?";
	    try {
	        PreparedStatement ps = cnx.prepareStatement(sql);
	        ps.setString(1, "%" + mc + "%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            liste.add(new Produit(
	                    rs.getInt("id"),
	                    rs.getString("nom"),
	                    rs.getDouble("prix"),
	                    rs.getInt("quantite")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}

	@Override
	public void deleteProduit(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM produit WHERE id = ?";
	    try {
	        PreparedStatement ps = cnx.prepareStatement(sql);
	        ps.setInt(1, id);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void updateProduit(Produit p) {
		// TODO Auto-generated method stub
		String sql = "UPDATE produit SET nom = ?, prix = ?, quantite = ? WHERE id = ?";
	    try {
	        PreparedStatement ps = cnx.prepareStatement(sql);
	        ps.setString(1, p.getNom());
	        ps.setDouble(2, p.getPrix());
	        ps.setInt(3, p.getQuantite());
	        ps.setInt(4, p.getId());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
