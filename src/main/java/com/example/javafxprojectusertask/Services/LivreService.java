package com.example.javafxprojectusertask.Services;


import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Entities.Bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreService implements IService<Livre> {

    private final Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;


    public LivreService() {
        conn= DataBaseConnection.getInstance().getCnx();
    }




    @Override
    public void add(Livre l) {
        String requete="insert into livre (type,titre,auteur,langue,idBib) values (?,?,?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
            //pst.setInt(1,l.getIdLivre());
            pst.setString(1,l.getType());
            pst.setString(2,l.getTitre());
            pst.setString(3,l.getAuteur());
            pst.setString(4,l.getLangue());
            pst.setInt(5,l.getIdBib().getIdBib());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(Livre l) {
        String query = "DELETE FROM livre WHERE idLivre=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, l.getIdLivre());
            return pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du livre", e);
        }



    }

    @Override
    public void update(Livre l) {String query = "UPDATE livre SET type=?, titre=?, auteur=?, langue=?, idBib=? WHERE idLivre=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, l.getType());
            pst.setString(2, l.getTitre());
            pst.setString(3, l.getAuteur());
            pst.setString(4, l.getLangue());
            pst.setInt(5, l.getIdBib().getIdBib());
            pst.setInt(6, l.getIdLivre());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du livre", e);
        }

    }

    @Override
    public  ArrayList<Livre> getAll() {
        String requete = "SELECT * FROM livre";
        ArrayList<Livre> listL = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                int idLivre = rs.getInt("idLivre");
                String type = rs.getString("type");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                String langue = rs.getString("langue");
                int idBib = rs.getInt("idBib");

                // Récupérer l'objet Centre correspondant à id_centre
                BibliothequeService bibliothequeService = new BibliothequeService();
                Bibliotheque bibliotheque = bibliothequeService.getBibliothequeById(idBib);

                // Créer l'objet livre avec l'objet bibliotheque obtenu
                Livre l = new Livre(idLivre, type, titre, auteur, langue,bibliotheque);
                listL.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listL;
    }



    public Livre readById(int id) {
        String query = "SELECT * FROM livre WHERE idLivre=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Livre l = new Livre();
                l.setIdLivre(rs.getInt("idLivre"));
                l.setType(rs.getString("type"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setLangue(rs.getString("langue"));
                return l;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du livre par ID", e);
        }
        return null;
    }


    public  ArrayList<Livre> getByIdBiblio(int id) {
        String requete = "SELECT * FROM livre WHERE idBib = "+id;
        ArrayList<Livre> listL = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                int idLivre = rs.getInt("idLivre");
                String type = rs.getString("type");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                String langue = rs.getString("langue");
                int idBib = rs.getInt("idBib");

                // Récupérer l'objet Centre correspondant à id_centre
                BibliothequeService bibliothequeService = new BibliothequeService();
                Bibliotheque bibliotheque = bibliothequeService.getBibliothequeById(idBib);

                // Créer l'objet livre avec l'objet bibliotheque obtenu
                Livre l = new Livre(idLivre, type, titre, auteur, langue,bibliotheque);
                listL.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listL;
    }
}




















