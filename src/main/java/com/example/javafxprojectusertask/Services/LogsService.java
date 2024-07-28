package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Interfaces.LogsInterface;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class LogsService implements LogsInterface {
    private Connection conection ;
    public LogsService(){
        conection= DataBaseConnection.getInstance().getCnx() ;
    }
    @Override
    public void addLog(Logs log) {
        try {

            String SQLQurey = "INSERT INTO LOGS (idUser,description1,date,action1,action2,description2)VALUES(?,?,?,?,?,?)";
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.setInt(1, log.getIdUser());
            statement.setString(2,log.getDescription1());

            statement.setTimestamp(3, (Timestamp) log.getDate());
            System.out.println((Timestamp) log.getDate());
            statement.setString(4,log.getAction1());
            statement.setString(5,log.getAction2());
            statement.setString(6,log.getDescription2());
            System.out.println(log);
            statement.executeUpdate();


        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Logs> getLogsByUser(int idUser) {
        ArrayList<Logs> logs = new ArrayList();
        String qry ="SELECT * FROM logs where idUser=?";
        try {
            PreparedStatement statement = conection.prepareStatement(qry);
            statement.setInt(1, idUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Logs log = new Logs();
                log.setIdLog(rs.getInt(1));
                log.setIdUser(rs.getInt(2));
                log.setDescription1(rs.getString(3));
                log.setDate(rs.getDate(4));
                log.setAction1(rs.getString(5));
                log.setAction2(rs.getString(6));
                log.setDescription2(rs.getString(7));
                logs.add(log);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logs;
    }
    }

