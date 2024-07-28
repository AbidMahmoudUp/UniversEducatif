package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliothequeService implements IService<Bibliotheque> {

    private final Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;
    private Statement ste;

    public BibliothequeService() {
        conn = DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void add(Bibliotheque b) {
        String query = "INSERT INTO bibliotheque ( nom, mail, nbrLivre) VALUES ( ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);

            pst.setString(1, b.getNom());
            pst.setString(2, b.getMail());
            pst.setInt(3, b.getNbrLivre());
           // pst.setInt(4, b.getIdBib());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de bibliotheque", e);
        }
    }

    @Override
    public boolean delete(Bibliotheque b) {
        String query = "DELETE FROM bibliotheque WHERE idBib=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, b.getIdBib());
            return pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de bibliotheque", e);
        }
    }

    @Override
    public void update(Bibliotheque b) {
        String query = "UPDATE bibliotheque SET nom=?, mail=?, nbrLivre=? WHERE idBib=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, b.getNom());
            pst.setString(2, b.getMail());
            pst.setInt(3, b.getNbrLivre());
            pst.setInt(4, b.getIdBib());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de bibliotheque", e);
        }
    }


    @Override
    public ArrayList<Bibliotheque> getAll() {
        String requete = "SELECT * FROM bibliotheque";
        ArrayList<Bibliotheque> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                int idBib = rs.getInt("idBib");
                String nom = rs.getString("nom");
                String mail = rs.getString("mail");
                int nbrLivre = rs.getInt("nbrlivre");
                Bibliotheque b = new Bibliotheque(idBib, nom, mail, nbrLivre);
                list.add(b);
                //list.delete(b);
                //list.update(b);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public Bibliotheque readById(int id) {
        String query = "SELECT * FROM bibliotheque WHERE idBib=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Bibliotheque b = new Bibliotheque();
                b.setIdBib(rs.getInt("idBib"));
                b.setNom(rs.getString("nom"));
                b.setMail(rs.getString("mail"));
                b.setNbrLivre(rs.getInt("nbrLivre"));
                return b;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de bibliotheque par ID", e);
        }
        return null;
    }


    public boolean checkIfBibliothequeExists(String nomBibliotheque) {
        // Implémentez la logique pour vérifier si la bibliotheque existe déjà dans la base de données
        // Par exemple, vous pouvez récupérer tous les bibliotheques de la base de données et vérifier s'il y en a un avec le même nom

        ArrayList<Bibliotheque> bibliotheques = getAll(); // Supposons que vous ayez une méthode readAll() pour récupérer tous les bibliotheques de la base de données

        for (Bibliotheque bibliotheque : bibliotheques) {
            if (bibliotheque.getNom().equals(nomBibliotheque)) {
                return true; // La bibliotheque existe déjà
            }
        }

        return false; // La bibliotheque n'existe pas
    }

    public Bibliotheque getBibliothequeById(int id) {
        Bibliotheque bibliotheque = null;
        String query = "SELECT * FROM bibliotheque WHERE idBib=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int idBib = rs.getInt("idBib");
                String nom = rs.getString("nom");
                String mail = rs.getString("mail");
                int nbrLivre = rs.getInt("nbrLivre");
                // Créer l'objet Centre avec les informations récupérées
                 bibliotheque = new Bibliotheque(idBib, nom, mail, nbrLivre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bibliotheque;
    }

}


