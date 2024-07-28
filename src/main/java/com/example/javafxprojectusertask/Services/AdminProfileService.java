package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Entities.ProfileAdmin;
import com.example.javafxprojectusertask.Interfaces.AdminProfileInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminProfileService implements AdminProfileInterface<ProfileAdmin> {

    private Connection conection ;


    public AdminProfileService(){
        conection= DataBaseConnection.getInstance().getCnx() ;
    }


    @Override
    public void addProfileAdmin(ProfileAdmin profileAdmin,int id) throws SQLException {
        try {
            String SQLQureyProfileAdmin = "INSERT INTO profile_admin (idProfile,reponsabilty,title)VALUES(?,?,?)";
            PreparedStatement statementProfileAdmin = conection.prepareStatement(SQLQureyProfileAdmin);
            statementProfileAdmin.setInt(1,id);
            statementProfileAdmin.setString(2, profileAdmin.getReponsabilty());
            statementProfileAdmin.setString(3, profileAdmin.getTitle());
            statementProfileAdmin.executeUpdate();

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void updateProfileAdmin(ProfileAdmin profileAdmin) throws SQLException {
        try {


            String SQLQueryProfileAdmin = "UPDATE profile_admin SET reponsabilty = ?, title = ? WHERE idProfile = ?";
            PreparedStatement statementProfileAdmin = conection.prepareStatement(SQLQueryProfileAdmin);
            statementProfileAdmin.setString(1, profileAdmin.getReponsabilty());
            statementProfileAdmin.setString(2, profileAdmin.getTitle());
            statementProfileAdmin.setInt(3, profileAdmin.getIdProfile());
            statementProfileAdmin.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }


    @Override
    public ProfileAdmin getAdminData(Profile profile) {
        ProfileAdmin profileAdmin = new ProfileAdmin();
        try {

            System.out.println("idProfileAdmin is : "+profile.getIdProfile());
            String SQLQuery2 = "SELECT * FROM profile_admin WHERE idProfile = ?";
            PreparedStatement statement2 = conection.prepareStatement(SQLQuery2);
            statement2.setInt(1, profile.getIdProfile());
            ResultSet result2 = statement2.executeQuery();

            while (result2.next()) {
                profileAdmin.setReponsabilty(result2.getString("responsabilty"));
                profileAdmin.setTitle(result2.getString("title"));
            }

            return profileAdmin;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePictureAdmin(ProfileAdmin profileAdmin) throws SQLException {

           try {
               String SQLQuery = "UPDATE Profile SET Picture = ? WHERE idUser = ?";
               PreparedStatement statement = conection.prepareStatement(SQLQuery);
               statement.setString(1, profileAdmin.getPicture());
               statement.setInt(2, profileAdmin.getIdUser());
               statement.executeUpdate();
           }
           catch (SQLException exception)
           {
               System.out.println(exception.getMessage());
           }


    }

    @Override
    public void updateDetailsProfileAdmin(ProfileAdmin profileAdmin) throws SQLException {

    }



    @Override
    public void deleteProfileAdmin(ProfileAdmin profileAdmin) throws SQLException {

    }
}
