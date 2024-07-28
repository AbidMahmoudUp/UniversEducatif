package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileAdmin;

import java.sql.SQLException;

public interface AdminProfileInterface<TYPE> {
    void addProfileAdmin(TYPE profileAdmin,int id)throws SQLException;
    void updateProfileAdmin(TYPE profileAdmin)throws SQLException;

    ProfileAdmin getAdminData(Profile profile);
    void updatePictureAdmin(TYPE profileAdmin)throws SQLException;

    void updateDetailsProfileAdmin(TYPE profileAdmin)throws SQLException;

    void deleteProfileAdmin(TYPE profileAdmin)throws SQLException;
}
