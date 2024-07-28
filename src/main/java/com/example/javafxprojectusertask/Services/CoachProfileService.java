package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileAdmin;
import com.example.javafxprojectusertask.Entities.ProfileCoach;
import com.example.javafxprojectusertask.Interfaces.CoachProfileInterface;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachProfileService implements CoachProfileInterface<ProfileCoach> {

    private Connection conection ;
    public CoachProfileService(){
        conection= DataBaseConnection.getInstance().getCnx() ;
    }

    @Override
    public void addCoachProfile(ProfileCoach profileCoach , int id) throws SQLException {
        String SQLQureyProfileAdmin = "INSERT INTO profile_Coach (idProfile,idModule)VALUES(?,?)";
        PreparedStatement statementProfileAdmin = conection.prepareStatement(SQLQureyProfileAdmin);
        statementProfileAdmin.setInt(1,id);
        statementProfileAdmin.setInt(2, profileCoach.getIdModule());

        statementProfileAdmin.executeUpdate();
    }

    @Override
    public void updateCoachProfile(ProfileCoach profileCoach) throws SQLException {
        String SQLQueryProfileAdmin = "UPDATE profile_Coach SET idModule = ? WHERE idProfile = ?";
        PreparedStatement statementProfileAdmin = conection.prepareStatement(SQLQueryProfileAdmin);
        statementProfileAdmin.setInt(1, profileCoach.getIdModule());
        statementProfileAdmin.setInt(2, profileCoach.getIdProfile());
        statementProfileAdmin.executeUpdate();
    }

    @Override
    public ProfileCoach getCoachProfile(Profile profile) throws SQLException {
        ProfileCoach profileCoach = new ProfileCoach();
        try {

            System.out.println("idProfileAdmin is : "+profile.getIdProfile());
            String SQLQuery2 = "SELECT * FROM profile_Coach WHERE idProfile = ?";
            PreparedStatement statement2 = conection.prepareStatement(SQLQuery2);
            statement2.setInt(1, profile.getIdProfile());
            ResultSet result2 = statement2.executeQuery();

            while (result2.next()) {
                profileCoach.setIdModule(result2.getInt("idModule"));

            }
            return profileCoach;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCoachProfile(ProfileCoach profileCoach) throws SQLException {

    }
}
