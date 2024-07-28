package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.ICommande;
import com.example.javafxprojectusertask.Entities.Commande;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Utilities.UserUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandeService implements ICommande {
    public List<Map<Produit, Integer>> panierr;
    private Connection cnx;

    public CommandeService() {
        this.cnx = DataBaseConnection.getInstance().getCnx();
    }

    public void addCommande(Commande commande) {
        int aa = 0;
        int bb = 0;
        int cc = 0;
        try {
            // Insérer la commande dans la table commande
            String insertCommandeQuery = "INSERT INTO commande (date, id_user, adresse) VALUES (?, ?, ?)";
            PreparedStatement stmCommande = cnx.prepareStatement(insertCommandeQuery);

            // Convertir LocalDate en java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());

            stmCommande.setDate(1, sqlDate); // Utiliser setDate au lieu de setInt
            stmCommande.setInt(2, UserUtils.ConnectedUser.getIdUser()); // Assurez-vous que c'est le bon setter pour l'ID utilisateur     commande.getIdUser()
            stmCommande.setString(3, commande.getAdresse()); // Supposons que adresse est une String dans votre objet Commande

            stmCommande.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public ArrayList<Commande> getAllCommandes() {
        ArrayList<Commande> commandes = new ArrayList<>();
        try {
            // Requête SQL pour sélectionner toutes les commandes
            String query = "SELECT * FROM commande";
            PreparedStatement stm = cnx.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            // Parcours des résultats et ajout des commandes à la liste
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId_cmd(rs.getInt("idc"));
                // Vous pouvez également récupérer les autres attributs de la commande si nécessaire
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }


    @Override
    public void updateCommande(Commande commande) {

    }

    @Override
    public Commande getCommandeById(int id) {
        Commande commande = null;
        try {
            String query = "SELECT * FROM commande WHERE id_cmd = ?";
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                commande = new Commande();
                commande.setId_cmd(rs.getInt("idc"));
                // Vous pouvez ajouter d'autres attributs à la commande si nécessaire
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }


    public ArrayList<Commande> searchCommandes(String searchText) {
        ArrayList<Commande> commandesTrouvees = new ArrayList<>();

        // Requête SQL pour rechercher les commandes en fonction du texte de recherche
        String query = "SELECT * FROM commande WHERE id_cmd LIKE ?";

        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            // Remplissage des paramètres de la requête avec le texte de recherche
            stm.setString(1, "%" + searchText + "%"); // Recherche par ID de commande

            // Exécution de la requête
            try (ResultSet rs = stm.executeQuery()) {
                // Parcours des résultats et ajout des commandes correspondantes à la liste
                while (rs.next()) {
                    Commande c = new Commande();
                    c.setId_cmd(rs.getInt("id_cmd"));
                    // Ajoutez d'autres attributs de commande si nécessaire
                    commandesTrouvees.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des erreurs SQL
        }

        return commandesTrouvees;
    }

    @Override
    public void displayCommande(Commande commande) {
        try {
            // Récupérer les informations de la commande
            String selectCommandeQuery = "SELECT * FROM commande WHERE id_cmd = ?";
            PreparedStatement stmCommande = cnx.prepareStatement(selectCommandeQuery);
            stmCommande.setInt(1, commande.getId_cmd());
            ResultSet rsCommande = stmCommande.executeQuery();
            if (rsCommande.next()) {
                System.out.println("Commande ID: " + rsCommande.getInt("id_cmd"));
                System.out.println("Panier ID: " + rsCommande.getInt("id_panier"));
                System.out.println("État: " + rsCommande.getInt("etat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteCommande(Commande commande) {
        try {
            // Supprimer la commande de la table commande
            String deleteCommandeQuery = "DELETE FROM commande WHERE id_cmd = ?";
            PreparedStatement stmDeleteCommande = cnx.prepareStatement(deleteCommandeQuery);
            stmDeleteCommande.setInt(1, commande.getId_cmd());
            int rowsAffected = stmDeleteCommande.executeUpdate();

            // Vérifier si la suppression a réussi
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}

