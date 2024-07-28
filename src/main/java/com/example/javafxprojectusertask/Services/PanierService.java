package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IPanier;
import com.example.javafxprojectusertask.Entities.Panier;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanierService implements IPanier {

    private Connection cnx;

    public PanierService() {
        this.cnx = DataBaseConnection.getInstance().getCnx();
    }


    @Override
    public void addPanier(List<Map<Produit, Integer>> paniers) {
        try {
            String insertPanierProduitQuery = "INSERT INTO panier (id_panier, id_produit, qte) VALUES (?, ?, ?)";
            PreparedStatement stmPanierProduit = cnx.prepareStatement(insertPanierProduitQuery);

            // Pour chaque panier dans la liste
            for (int i = 0; i < paniers.size(); i++) {
                Map<Produit, Integer> panier = paniers.get(i);

                // Pour chaque entrée dans le panier actuel
                for (Map.Entry<Produit, Integer> entry : panier.entrySet()) {
                    Produit produit = entry.getKey();
                    int quantite = entry.getValue();

                    stmPanierProduit.setInt(1, i + 1); // Ajouter 1 à l'index car les indices commencent à 1 dans SQL
                    stmPanierProduit.setInt(2, produit.getId_produit());
                    stmPanierProduit.setInt(3, quantite);
                    stmPanierProduit.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ArrayList<Panier> getPanierAll() {
        return null;
    }

    @Override
    public void updatePanier(Panier panier) {

    }

    public void displayPanier(Panier panier) {
        try {
            // Récupérer les informations du panier
            String selectPanierQuery = "SELECT * FROM panier WHERE id_panier = ?";
            PreparedStatement stmPanier = cnx.prepareStatement(selectPanierQuery);
            stmPanier.setInt(1, panier.getId_panier());
            ResultSet rsPanier = stmPanier.executeQuery();
            if (rsPanier.next()) {
                System.out.println("Panier ID: " + rsPanier.getInt("id_panier"));
                System.out.println("Date: " + rsPanier.getString("date"));
            }

            // Récupérer les produits associés à ce panier
            String selectProduitsQuery = "SELECT pp.id_produit, p.nom, pp.qte FROM panier_produit pp INNER JOIN produit p ON pp.id_produit = p.id_produit WHERE pp.id_panier = ?";
            PreparedStatement stmProduits = cnx.prepareStatement(selectProduitsQuery);
            stmProduits.setInt(1, panier.getId_panier());
            ResultSet rsProduits = stmProduits.executeQuery();
            System.out.println("Produits dans le panier :");
            while (rsProduits.next()) {
                System.out.println("Produit ID: " + rsProduits.getInt("id_produit"));
                System.out.println("Nom: " + rsProduits.getString("nom"));
                System.out.println("Quantité: " + rsProduits.getInt("qte"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Panier getPanierById(int id) {
        return null;
    }

    @Override
    public boolean deletePanier(Panier panier) {
        try {
            // Supprimer les produits associés à ce panier de la table panier_produit
            String deletePanierProduitQuery = "DELETE FROM panier_produit WHERE id_panier = ?";
            PreparedStatement stmDeletePanierProduit = cnx.prepareStatement(deletePanierProduitQuery);
            stmDeletePanierProduit.setInt(1, panier.getId_panier());
            stmDeletePanierProduit.executeUpdate();

            // Supprimer le panier de la table panier
            String deletePanierQuery = "DELETE FROM panier WHERE id_panier = ?";
            PreparedStatement stmDeletePanier = cnx.prepareStatement(deletePanierQuery);
            stmDeletePanier.setInt(1, panier.getId_panier());
            int rowsAffected = stmDeletePanier.executeUpdate();

            // Vérifier si la suppression a réussi
            if (rowsAffected > 0) {
                return true; // La suppression a réussi
            } else {
                return false; // Aucun enregistrement n'a été supprimé
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // La suppression a échoué
        }
    }

    @Override
    public int SommeDeQte(Map<Produit, Integer> pan) {
        int ss = 0;
        for (Integer value : pan.values()) {
            ss += value;
        }
        return ss;
    }
}
