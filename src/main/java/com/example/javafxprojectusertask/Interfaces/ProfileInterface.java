package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Profile;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProfileInterface <TYPE> {

    void AddProfileUser(TYPE profile , int id) throws SQLException;
    TYPE getProfileUser(int idUser) throws SQLException;
    void UpdateProfileUser(TYPE profile) throws SQLException;
    void DeleteProfileUser(TYPE profile) throws SQLException;
    ArrayList<Profile> getAllProfiles() throws SQLException;
    void UpdatePictureUser(TYPE profile) throws SQLException;

}
