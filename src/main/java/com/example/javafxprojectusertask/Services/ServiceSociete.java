package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ServiceSociete implements IService<Societe> {

    private Connection cnx;

    public ServiceSociete() {
        cnx = DataBaseConnection.getInstance().getCnx();
    }
    @Override
    public void add(Societe societe) {



        String qry ="INSERT INTO `societe`(`nomSociete`, `description`) VALUES (?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,societe.getNom());
            stm.setString(2,societe.getDesc());


            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public ArrayList<Societe> getAll() {
        ArrayList<Societe> societes = new ArrayList();
        String qry ="SELECT * FROM `societe`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Societe s = new Societe();
                s.setId(rs.getInt(1));
                s.setNom(rs.getString("nomSociete"));
                s.setDesc(rs.getString("description"));

                societes.add(s);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return societes;
    }


    public ArrayList<Societe> getAllOrdered(String champ, String ordre) {
        ArrayList<Societe> societes = new ArrayList();
        String qry ="SELECT * FROM `societe` ORDER BY " + champ + " " + ordre;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Societe s = new Societe();
                s.setId(rs.getInt(1));
                s.setNom(rs.getString("nomSociete"));
                s.setDesc(rs.getString("description"));

                societes.add(s);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return societes;
    }


    @Override
    public void update(Societe societe) {

        String qry ="UPDATE `societe` SET `nomSociete`=?,`description`=? WHERE idSociete = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,societe.getNom());
            stm.setString(2,societe.getDesc());
            stm.setInt(3,societe.getId());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    @Override
    public boolean delete(Societe societe) {
        String qry ="DELETE FROM `societe` WHERE idSociete = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,societe.getId());

            return stm.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return false;
    }

    public Societe getSocieteByID(int id)
    {
        Societe s = null;
        String qry ="SELECT * FROM `societe` WHERE idSociete = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            //System.out.println("FFF");
            stm.setInt(1,id);
            //System.out.println("FFF");
            ResultSet rs = stm.executeQuery();
            //System.out.println("FFF");
            while (rs.next()){
                s = new Societe();
                s.setId(rs.getInt(1));
                s.setNom(rs.getString("nomSociete"));
                s.setDesc(rs.getString("description"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
}
