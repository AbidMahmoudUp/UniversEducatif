package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ServiceDossier implements IService<Dossier> {

    private Connection cnx;

    public ServiceDossier() {
        cnx = DataBaseConnection.getInstance().getCnx();
    }
    @Override
    public void add(Dossier d) {

        String qry ="INSERT INTO `dossier`(`idOffre`, `idUser`, `status`) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,d.getOffre().getId());
            stm.setInt(2,d.getIdUser());
            stm.setString(3,d.getStatus());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public ArrayList<Dossier> getAll() {
        ArrayList<Dossier> dossiers = new ArrayList<>();
        ServiceOffre serviceOffre = new ServiceOffre();
        String qry ="SELECT * FROM `dossier`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Dossier d = new Dossier();
                System.out.println("1");
                d.setIdUser(rs.getInt("idUser"));
                System.out.println("2");
                d.setStatus(rs.getString("status"));
                System.out.println("3");
                int id = rs.getInt("idOffre");
                System.out.println(id);
                Offre o = serviceOffre.getOffreByID(id);
                System.out.println("4");
                d.setOffre(o);
                dossiers.add(d);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


        return dossiers;
    }


    public ArrayList<Dossier> getFiltered(String filtre) {
        ArrayList<Dossier> dossiers = new ArrayList<>();
        ServiceOffre serviceOffre = new ServiceOffre();
        String qry ="SELECT * FROM `dossier` WHERE `status` like ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,filtre);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Dossier d = new Dossier();
                System.out.println("1");
                d.setIdUser(rs.getInt("idUser"));
                System.out.println("2");
                d.setStatus(rs.getString("status"));
                System.out.println("3");
                int id = rs.getInt("idOffre");
                System.out.println(id);
                Offre o = serviceOffre.getOffreByID(id);
                System.out.println("4");
                d.setOffre(o);
                dossiers.add(d);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


        return dossiers;
    }


    @Override
    public void update(Dossier d) {

        String qry ="UPDATE `dossier` SET `status`= ? WHERE idOffre = ? and idUser = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,d.getStatus());
            stm.setInt(2,d.getOffre().getId());
            stm.setInt(3,d.getIdUser());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public boolean delete(Dossier d) {
        String qry ="DELETE FROM `dossier` WHERE idOffre = ? and idUser = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,d.getOffre().getId());
            stm.setInt(2,d.getIdUser());

            return stm.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return false;
    }

    public Dossier getDossierByID(int idUser,int idOffre)
    {
        Dossier d = null;
        String qry ="SELECT * FROM `dossier` WHERE idOffre = ? and idUser = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            //System.out.println("FFF");
            stm.setInt(1,idOffre);
            stm.setInt(2,idUser);
            //System.out.println("FFF");
            ResultSet rs = stm.executeQuery();
            //System.out.println("FFF");
            while (rs.next()){
                d = new Dossier();
                ServiceOffre serviceOffre = new ServiceOffre();
                Offre o = serviceOffre.getOffreByID(idOffre);
                d.setOffre(o);
                d.setIdUser(idUser);
                d.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return d;
    }

    public ArrayList<Dossier> getDossierByUser(int idUser)
    {
        ArrayList<Dossier> dossiers = new ArrayList<>();
        Dossier d = null;
        String qry ="SELECT * FROM `dossier` WHERE idUser = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            //System.out.println("FFF");
            stm.setInt(1,idUser);
            //System.out.println("FFF");
            ResultSet rs = stm.executeQuery();
            //System.out.println("FFF");
            while (rs.next()){
                d = new Dossier();
                ServiceOffre serviceOffre = new ServiceOffre();
                Offre o = serviceOffre.getOffreByID(rs.getInt("idOffre"));
                d.setOffre(o);
                d.setIdUser(idUser);
                d.setStatus(rs.getString("status"));
                dossiers.add(d);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dossiers;
    }
}
