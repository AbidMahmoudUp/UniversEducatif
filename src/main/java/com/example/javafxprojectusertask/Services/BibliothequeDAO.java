


package com.example.javafxprojectusertask.Services;


import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BibliothequeDAO {

    private final DataBaseConnection dataSource;

    public BibliothequeDAO() {
        this.dataSource = DataBaseConnection.getInstance();
    }

    public ArrayList<Bibliotheque> getAllBibliotheques() {
        ArrayList<Bibliotheque> bibliotheques = new ArrayList<>();

        try (Connection connection = dataSource.getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bibliotheque");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Bibliotheque bibliotheque = new Bibliotheque();
                bibliotheque.setIdBib(resultSet.getInt("idBib"));
                bibliotheque.setNom(resultSet.getString("nom"));
                bibliotheque.setMail(resultSet.getString("mail"));
                bibliotheque.setNbrLivre(resultSet.getInt("nbrLivre"));
                bibliotheques.add(bibliotheque);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bibliotheques;
    }
}
