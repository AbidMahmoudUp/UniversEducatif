package com.example.javafxprojectusertask.Services;



import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private final DataBaseConnection dataSource;

    public LivreDAO() {
        this.dataSource = DataBaseConnection.getInstance();
    }

    public ArrayList<Livre> getAllLivres() {
        ArrayList<Livre> livres = new ArrayList<>();

        try (Connection connection = dataSource.getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM livre");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Livre livre = new Livre();
                livre.setIdLivre(resultSet.getInt("idLivre"));
                livre.setType(resultSet.getString("type"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setLangue(resultSet.getString("langue"));
                // Supposons que votre entité Livre a une référence à l'entité Bibliotheque
                // livre.setIdBib(new Bibliotheque(resultSet.getInt("idBib")));
                livres.add(livre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }
}
