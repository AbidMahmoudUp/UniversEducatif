package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IAchat;
import com.example.javafxprojectusertask.Entities.Achat;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AchatService implements IAchat {



    public List<Map<Produit, Integer>> panierr;
    private Connection cnx;

    public AchatService() {
        this.cnx = DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void addAchat(Achat achat) {
        int lastId = 0;
        try {
            String query = "SELECT idc FROM commande ORDER BY idc DESC LIMIT 1";
            PreparedStatement stm = cnx.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("idc"); // Assurez-vous que le nom de la colonne est correct
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        try {
            // Insérer l'achat dans la table achat
            String insertAchatQuery = "INSERT INTO achat (idc, id_produit, qte, mont_tot) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = cnx.prepareStatement(insertAchatQuery);

            stm.setInt(1, lastId);
            stm.setInt(2, achat.getId_produit());
            stm.setInt(3, achat.getQte());
            stm.setDouble(4, achat.getMont_tot());

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}




