package com.example.javafxprojectusertask.Utilities;

import com.mysql.jdbc.Connection;


import java.sql.*;

public class DataBaseConnection {

    String URL ="jdbc:mysql://localhost/brogramers";
    String USERNAME = "root";

    String PASSWORD = "";
    Connection cnx ;
    private  static DataBaseConnection instance;

    private DataBaseConnection(){

        try {
            cnx = (Connection) DriverManager.getConnection(URL,USERNAME,PASSWORD);

            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("____not connected____ ");

        }

    }
    public static DataBaseConnection getInstance(){
        if (instance == null)
            instance = new DataBaseConnection();

        return instance;
    }
    public Connection getCnx(){
        return cnx;
    }

}
