package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Profile;

import com.example.javafxprojectusertask.Interfaces.ProfileInterface;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ProfileService implements ProfileInterface<Profile> {

    private Connection conection ;
    public ProfileService(){
        conection= DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void AddProfileUser(Profile profile , int id) throws SQLException {
        try {
            String SQLQurey = "INSERT INTO Profile (idUser,firstName,lastName,picture,address,location,phoneNumber)VALUES(?,?,?,?,?,?,?)";
            PreparedStatement statement = conection.prepareStatement(SQLQurey);

            statement.setInt(1,id);
            statement.setString(2, profile.getFirstName());
            statement.setString(3, profile.getLastName());
            statement.setString(4, profile.getPicture());
            statement.setString(5,profile.getAddress());
            statement.setString(6,profile.getLocation());
            statement.setInt(7,profile.getPhoneNumber());
            statement.executeUpdate();

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }
   public ArrayList<Profile> getAllProfiles() throws SQLException{
       ArrayList<Profile> ProfileList =new ArrayList<>();
       try {
           String SQLQurey = "SELECT * FROM profile";
           Statement statement = conection.createStatement();
           ResultSet Result =statement.executeQuery(SQLQurey);
           while(Result.next())
           {
               Profile profile = new Profile();
               profile.setIdUser(Result.getInt("IdUser"));
               profile.setLastName(Result.getString("lastname"));
               profile.setFirstName(Result.getString("firstname"));
               profile.setAddress(Result.getString("address"));
               profile.setLocation(Result.getString("location"));
               profile.setPhoneNumber(Result.getInt("phoneNumber"));
               profile.setPicture(Result.getString("picture"));
               ProfileList.add(profile);
           }

       }catch (SQLException exception){
           System.out.println(exception.getMessage());
       }
       System.out.println("userlistSize:"+ProfileList.size());
       return ProfileList;
    }
    public ArrayList<Profile> getAllProfilesFiltred(String orderBy, String sortOrder) throws SQLException{
        ArrayList<Profile> ProfileList =new ArrayList<>();
        try {
            String SQLQurey = "SELECT * FROM profile ORDER BY " +orderBy+ " " +sortOrder;
            Statement statement = conection.createStatement();
            ResultSet Result =statement.executeQuery(SQLQurey);
            while(Result.next())
            {
                Profile profile = new Profile();
                profile.setIdUser(Result.getInt("IdUser"));
                profile.setLastName(Result.getString("lastname"));
                profile.setFirstName(Result.getString("firstname"));
                profile.setAddress(Result.getString("address"));
                profile.setLocation(Result.getString("location"));
                profile.setPhoneNumber(Result.getInt("phoneNumber"));
                profile.setPicture(Result.getString("picture"));
                ProfileList.add(profile);
            }

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:"+ProfileList.size());
        return ProfileList;
    }
    @Override
    public Profile getProfileUser(int idUser) {
        Profile profile = new Profile();
        try {
            String SQLQuery = "SELECT * FROM Profile WHERE idUser = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setInt(1, idUser);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                profile.setIdUser(result.getInt("idUser"));
                profile.setPicture(result.getString("picture"));
                profile.setLocation(result.getString("location"));
                profile.setFirstName(result.getString("firstName"));
                profile.setLastName(result.getString("LastName"));
                profile.setIdProfile(result.getInt("idProfile"));
                profile.setAddress(result.getString("address"));
                profile.setPhoneNumber(result.getInt("phoneNumber"));
            }

            return profile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateProfileUser(Profile profile) throws SQLException {
        try {
            String SQLQuery = "UPDATE Profile SET firstName = ?, lastName = ?, address = ?, location = ?, phoneNumber = ? WHERE idUser = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1, profile.getFirstName());
            statement.setString(2, profile.getLastName());
            statement.setString(3, profile.getAddress());
            statement.setString(4, profile.getLocation());
            statement.setInt(5, profile.getPhoneNumber());
            statement.setInt(6, profile.getIdUser());
            statement.executeUpdate();


        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void DeleteProfileUser(Profile profile) throws SQLException {
        try {
            String SQLQurey = "DELETE FROM Profile WHERE idUser="+profile.getIdUser();
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.executeUpdate();

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void UpdatePictureUser(Profile profile) throws SQLException {
        try {
            String SQLQuery = "UPDATE Profile SET Picture = ? WHERE idUser = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1, profile.getPicture());
            statement.setInt(2, profile.getIdUser());
            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}
