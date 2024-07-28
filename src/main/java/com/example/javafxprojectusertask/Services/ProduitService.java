package com.example.javafxprojectusertask.Services;


import com.example.javafxprojectusertask.Interfaces.IProduit;
import com.example.javafxprojectusertask.Entities.*;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;

import java.util.ArrayList;

public class ProduitService implements IProduit {


    private Connection cnx ;

    public ProduitService() {
        this.cnx = cnx = DataBaseConnection.getInstance().getCnx();;
    }

    @Override
    public void add(Produit produit) {
        // ajouter une personne dans la bd
        //1 - req SQL done
        //2 - executer la req SQL done
        String qry ="INSERT INTO `produit`(`nom`, `type`, `prix_init`, `marge`, `video`,  `audio`, `image`,`descrip`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,produit.getNom());
            stm.setString(2,produit.getType());
            stm.setFloat(3,produit.getPrix_init());
            stm.setFloat(4,produit.getMarge());
            stm.setString(5,produit.getVideo());
            stm.setString(6,produit.getAudio());
            stm.setString(7,produit.getImage());
            stm.setString(8,produit.getDescrip());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }


    }


    @Override
    public ArrayList<Produit> getProduitAll() {
        //retourner toute les perosnnes dans la bd
        //1- req SQL done
        //2 -execution de la req done
        // 3- remplire la liste done
        // 4 - retourner la liste done
        ArrayList<Produit> produit = new ArrayList();
        String qry ="SELECT * FROM `produit`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Produit p = new Produit();
                p.setId_produit(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setType(rs.getString(3));
                p.setMarge(rs.getFloat(4));
                p.setDescrip(rs.getString("descrip"));
                p.setPrix_init(rs.getFloat("prix_init"));
                produit.add(p);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return produit;
    }

    @Override
    public void updateProduit(Produit produit) {
        String query = "UPDATE produit SET nom = ?, type = ?, prix_init = ?, marge = ?, video = ?, audio = ?, image = ?, descrip = ? WHERE id_produit = ?";

            try (PreparedStatement stm = cnx.prepareStatement(query)) {
            // Débogage : Afficher la requête SQL générée
            System.out.println("Requête SQL : " + query);

            stm.setString(1, produit.getNom());
            stm.setString(2, produit.getType());
            stm.setFloat(3, produit.getPrix_init());
            stm.setFloat(4, produit.getMarge());
            stm.setString(5, produit.getVideo());
            stm.setString(6, produit.getAudio());
            stm.setString(7, produit.getImage());
            stm.setString(8, produit.getDescrip());
            stm.setInt(9, produit.getId_produit());

            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Le produit a été mis à jour avec succès !");
            } else {
                System.out.println("Aucun produit n'a été mis à jour. Vérifiez l'ID du produit.");
            }
        } catch (SQLException e) {
            // Débogage : Afficher toute exception SQL
            System.out.println("Erreur SQL lors de la mise à jour du produit : " + e.getMessage());
        }
    }

    public ArrayList<Produit> getProduitsByType(String type) {
        ArrayList<Produit> produitsFiltres = new ArrayList<>();
        String query = "SELECT * FROM produit WHERE type = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setString(1, type);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Produit p = new Produit();
                p.setId_produit(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setType(rs.getString(3));
                p.setPrix_init(rs.getInt("prix_init"));
                produitsFiltres.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produitsFiltres;
    }
    public ArrayList<String> getTypesProduits() {
        ArrayList<String> typesProduits = new ArrayList<>();
        String query = "SELECT DISTINCT type FROM produit"; // Requête pour récupérer les types de produits distincts

        try (PreparedStatement stm = cnx.prepareStatement(query);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                String typeProduit = rs.getString("type");
                typesProduits.add(typeProduit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typesProduits;
    }



    public ArrayList<Produit> searchProduits(String searchText) {
        ArrayList<Produit> produitsTrouves = new ArrayList<>();

        // Requête SQL pour rechercher les produits en fonction du nom ou de la description
        String query = "SELECT * FROM produit WHERE nom LIKE ? OR descrip LIKE ?";

        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            // Remplissage des paramètres de la requête avec le texte de recherche
            stm.setString(1, "%" + searchText + "%"); // Recherche dans le nom
            stm.setString(2, "%" + searchText + "%"); // Recherche dans la description

            // Exécution de la requête
            try (ResultSet rs = stm.executeQuery()) {
                // Parcours des résultats et ajout des produits correspondants à la liste
                while (rs.next()) {
                    Produit p = new Produit();
                    p.setId_produit(rs.getInt("id_produit"));
                    p.setNom(rs.getString("nom"));
                    p.setType(rs.getString("type"));
                    p.setPrix_init(rs.getFloat("prix_init"));
                    p.setMarge(rs.getFloat("marge"));
                    p.setVideo(rs.getString("video"));
                    p.setAudio(rs.getString("audio"));
                    p.setImage(rs.getString("image"));
                    p.setDescrip(rs.getString("descrip"));

                    produitsTrouves.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des erreurs SQL
        }

        return produitsTrouves;
    }



    @Override
    public boolean deleteProduit(Produit produit) {

        String qry =" DELETE FROM produit WHERE id_produit=? ";

        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,produit.getId_produit());


            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;

    }

    public Produit getProduitById(int id){
         String query="SELECT * FROM produit where id_produit=?";

        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1,id);


            ResultSet resultSet = stm.executeQuery();
            Produit result = new Produit();
            while (resultSet.next()) {
                result.setId_produit(resultSet.getInt("id_produit"));
                result.setNom( resultSet.getString("nom"));
                result.setType(resultSet.getString("type"));
                result.setPrix_init(Float.parseFloat(resultSet.getString("prix_init")));
                result.setVideo(resultSet.getString("video"));
                result.setAudio(resultSet.getString("audio"));
                result.setMarge(Float.parseFloat(resultSet.getString("marge")));


                //System.out.println("ID Utilisateur : " + idUtilisateur + ", Nom : " + nomUtilisateur);
            }

            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
    public boolean isProduitExists(String nomProduit) {
        String query = "SELECT COUNT(*) AS count FROM produit WHERE nom = ?";
        try (PreparedStatement stm = cnx.prepareStatement(query)) {
            stm.setString(1, nomProduit);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0; // Retourne vrai si le produit existe déjà, sinon faux
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // En cas d'erreur ou si aucun produit avec le même nom n'est trouvé
    }
}