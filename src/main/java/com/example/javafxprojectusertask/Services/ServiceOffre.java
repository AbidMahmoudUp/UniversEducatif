package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ServiceOffre implements IService<Offre> {

    private Connection cnx;

    public ServiceOffre()
    {
        cnx = DataBaseConnection.getInstance().getCnx();
    }
    @Override
    public void add(Offre offre) {
        String qry ="INSERT INTO `offre`(`description`, `niveau`, `dateDebut`, `dateFin`, `idSociete`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,offre.getDesc());
            stm.setInt(2,offre.getNiveau());
            stm.setDate(3, (Date) offre.getDateDeb());
            stm.setDate(4,  (Date) offre.getDateFin());
            stm.setInt(5,offre.getSociete().getId());


            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public ArrayList<Offre> getAll() {
        ArrayList<Offre> offres = new ArrayList();
        ServiceSociete serviceSociete = new ServiceSociete();
        System.out.println("TTT");
        String qry = "SELECT * FROM `offre`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            //System.out.println("TTT");
            while (rs.next()) {
                Offre o = new Offre();
                //  System.out.println("TTT1");
                o.setId(rs.getInt(1));
                o.setNiveau(rs.getInt("niveau"));
                o.setDesc(rs.getString("description"));
                //Date date = new Date(2002);
                //System.out.println(date);
                //System.out.println();
                o.setDateDeb(rs.getDate("dateDebut"));

                //System.out.println("DDD");
                o.setDateFin(rs.getDate("dateFin"));
                //System.out.println("DDDD");
                int id = rs.getInt("idSociete");
                //System.out.println(id);
                Societe s = serviceSociete.getSocieteByID(id);
                //System.out.println(s);
                o.setSociete(s);
                offres.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return offres;
    }



    public ArrayList<Offre> getAllOrdered(String champ, String ordre) {
        ArrayList<Offre> offres = new ArrayList();
        ServiceSociete serviceSociete = new ServiceSociete();
        System.out.println("TTT");
        String qry = "SELECT * FROM `offre` ORDER BY " + champ + " " + ordre;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            //System.out.println("TTT");
            while (rs.next()) {
                Offre o = new Offre();
                //  System.out.println("TTT1");
                o.setId(rs.getInt(1));
                o.setNiveau(rs.getInt("niveau"));
                o.setDesc(rs.getString("description"));
                //Date date = new Date(2002);
                //System.out.println(date);
                //System.out.println();
                o.setDateDeb(rs.getDate("dateDebut"));

                //System.out.println("DDD");
                o.setDateFin(rs.getDate("dateFin"));
                //System.out.println("DDDD");
                int id = rs.getInt("idSociete");
                //System.out.println(id);
                Societe s = serviceSociete.getSocieteByID(id);
                //System.out.println(s);
                o.setSociete(s);
                offres.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return offres;
    }


    public ArrayList<Offre> getBySociete(Societe s) {
        ArrayList<Offre> offres = new ArrayList();
        ServiceSociete serviceSociete = new ServiceSociete();
        //System.out.println("TTT");
        String qry = "SELECT * FROM `offre` WHERE idSociete = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,s.getId());
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Offre o = new Offre();

                o.setId(rs.getInt(1));
                o.setNiveau(rs.getInt("niveau"));
                o.setDesc(rs.getString("description"));

                o.setDateDeb(rs.getDate("dateDebut"));

                //System.out.println("DDD");
                o.setDateFin(rs.getDate("dateFin"));

                int id = rs.getInt("idSociete");

                o.setSociete(s);
                offres.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return offres;
    }

        @Override
    public void update(Offre offre) {

            String qry ="UPDATE `offre` SET `description`=?,`niveau`=?,`dateDebut`=?,`dateFin`=?,`idSociete`=? WHERE idOffre = ?";
            try {
                PreparedStatement stm = cnx.prepareStatement(qry);
                stm.setString(1,offre.getDesc());
                stm.setInt(2,offre.getNiveau());
                stm.setDate(3, (Date) offre.getDateDeb());
                stm.setDate(4, (Date) offre.getDateFin());
                stm.setInt(5,offre.getSociete().getId());
                stm.setInt(6,offre.getId());

                stm.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }

    }

    @Override
    public boolean delete(Offre offre) {
        String qry ="DELETE FROM `offre` WHERE idOffre = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,offre.getId());

            return stm.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return false;
    }

    public Offre getOffreByID(int id) {
        ArrayList<Offre> offres = new ArrayList();
        Offre o = new Offre();
        ServiceSociete serviceSociete = new ServiceSociete();
        String qry = "SELECT * FROM `offre` WHERE idOffre = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                o.setId(rs.getInt(1));
                o.setNiveau(rs.getInt("niveau"));
                o.setDesc(rs.getString("description"));
                System.out.println("FFF");
                o.setDateDeb(rs.getDate("dateDebut"));
                System.out.println("FFF");
                o.setDateFin(rs.getDate("dateFin"));
                int idSociete = rs.getInt("idSociete");
                Societe s = serviceSociete.getSocieteByID(idSociete);
                o.setSociete(s);
                offres.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return o;
    }
}
