package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.UserClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserInterface<TYPE> {
     void addUser(TYPE user)throws SQLException;
     void updateUser(TYPE user)throws SQLException;
     void deleteUser(TYPE user)throws SQLException;
     TYPE getUserBynameAndEmail(String userName , String Email)throws SQLException;
     ArrayList<TYPE> getAllUsers()throws SQLException;
     ArrayList<TYPE> getAllAdmins()throws SQLException;
     ArrayList<TYPE> getAllCoaches()throws SQLException;
     ArrayList<TYPE> getOnlyUsers()throws SQLException;
     TYPE verfieLogin(String email , String password) throws SQLException;

    public  boolean doesUserExist(String Email) throws SQLException;
    public void updateStateUser(UserClass user) throws SQLException;
      void updateEmailAdmin(TYPE user) throws SQLException;
      TYPE getUserById(int idUser) throws SQLException;
     TYPE getUserByEmail(String email) throws SQLException;
    public ArrayList<UserClass> getAllUsersByName(String filterBy , String orderBy ) throws SQLException;
      void updatePassword(TYPE user , String password) throws SQLException;
}
