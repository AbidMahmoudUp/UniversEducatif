package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Profile;

import java.sql.SQLException;

public interface CoachProfileInterface<TYPE>{

    void  addCoachProfile(TYPE profileCoach , int id) throws SQLException;
    void  updateCoachProfile(TYPE profileCoach) throws SQLException;
    TYPE  getCoachProfile(Profile profile) throws SQLException;
    void  deleteCoachProfile(TYPE profileCoach) throws SQLException;
    //void  addCoachProfile(TYPE profileCoach) throws SQLException;

}
