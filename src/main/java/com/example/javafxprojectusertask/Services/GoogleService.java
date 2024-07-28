package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.GoogleOauth;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoogleService {

    private Connection conection ;
    public GoogleService(){
        conection= DataBaseConnection.getInstance().getCnx() ;
    }

    public void addUser(GoogleOauth googleAuth) throws SQLException {

        try {

            String SQLQurey = "INSERT INTO GoogleOauth (id,email,verified_Email,name,given_Name,familly_name,picture,locale)VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.setString(1, googleAuth.getId());
            statement.setString(2, googleAuth.getEmail());
            statement.setBoolean(3, googleAuth.isVerfied_Email());
            statement.setString(4, googleAuth.getName());
            statement.setString(5, googleAuth.getGiven_name());
            statement.setString(6, googleAuth.getFamily_name());
            statement.setString(7, googleAuth.getPicture());
            statement.setString(8, googleAuth.getLocale());
            statement.executeUpdate();


        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }



    }
    public GoogleOauth verfieLogin(int id , String email ) throws SQLException{

        GoogleOauth  googleOauth = new GoogleOauth();
        googleOauth=null;
        try {

            String SQLQuery = "SELECT * FROM GoogleOauth WHERE id = ? AND Email = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setInt(1, id);
            statement.setString(2, email);

            ResultSet result = statement.executeQuery();

            // Check if the ResultSet has any rows
            if (result.next()) {

                googleOauth.setId(result.getString("id"));
                googleOauth.setEmail(result.getString("email"));
                googleOauth.setGiven_name(result.getString("given_name"));
                googleOauth.setName(result.getString("name"));
                googleOauth.setFamily_name(result.getString("familly_name"));
                googleOauth.setVerfied_Email(result.getBoolean("verified_email"));
                googleOauth.setPicture(result.getString("picture"));
                googleOauth.setLocale(result.getString("locale"));
            } else {
                // Handle the case where the user does not exist (for example, return null)
                System.out.println("User not found for the given credentials.");
            }
        } catch (SQLException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return googleOauth ;
}

    public GoogleOauth getUserById(String id) throws SQLException {
        GoogleOauth googleOauth = new GoogleOauth();
        try {
            String SQLQuery = "SELECT * FROM GoogleOauth WHERE id ="+id;
            PreparedStatement statement = conection.prepareStatement(SQLQuery);


            ResultSet result = statement.executeQuery();

            // Check if there is at least one result
            if (result.next()) {
                googleOauth.setId(result.getString("id"));
                googleOauth.setEmail(result.getString("email"));
                googleOauth.setGiven_name(result.getString("given_name"));
                googleOauth.setName(result.getString("name"));
                googleOauth.setFamily_name(result.getString("familly_name"));
                googleOauth.setVerfied_Email(result.getBoolean("verified_email"));
                googleOauth.setPicture(result.getString("picture"));
                googleOauth.setLocale(result.getString("locale"));
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return googleOauth;
    }
    public GoogleOauth getUserByEmail(String email) throws SQLException {
        GoogleOauth googleOauth = new GoogleOauth();
        try {
            String SQLQuery = "SELECT * FROM GoogleOauth WHERE email = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1,email);

            ResultSet result = statement.executeQuery();

            // Check if there is at least one result
            if (result.next()) {
                googleOauth.setId(result.getString("id"));
                googleOauth.setEmail(result.getString("email"));
                googleOauth.setGiven_name(result.getString("given_name"));
                googleOauth.setName(result.getString("name"));
                googleOauth.setFamily_name(result.getString("familly_name"));
                googleOauth.setVerfied_Email(result.getBoolean("verified_email"));
                googleOauth.setPicture(result.getString("picture"));
                googleOauth.setLocale(result.getString("locale"));
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return googleOauth;
    }
    public void deleteUser(GoogleOauth googleOauth) throws SQLException {
        try {
            String SQLQurey = "DELETE FROM GoogleOauth WHERE id="+googleOauth.getId();
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }
}
