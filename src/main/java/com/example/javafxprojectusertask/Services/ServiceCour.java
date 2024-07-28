package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Cour;

import java.sql.*;
import java.util.ArrayList;

public class ServiceCour implements IService <Cour> {

    private Connection cnx ;
    public ServiceCour(){
        cnx = DataBaseConnection.getInstance().getCnx();
    }
    @Override
    public void add(Cour cour) {
        String qry ="INSERT INTO cours(`nom`, description,`ID_Module`,`fichierPath`,`tempsC`,`audioPath`,`videoPath`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,cour.getNom());
            stm.setString(2,cour.getDescription());
            stm.setInt(3,cour.getID_Module());
            stm.setString(4,cour.getFichierPath());
            stm.setInt(5,cour.getTempsEstimeC());
            stm.setString(6,cour.getAudioPath());
            stm.setString(7, cour.getVideoPath());
            stm.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    @Override
    public ArrayList<Cour> getAll() {
        ArrayList<Cour> cours = new ArrayList();
        String qry ="SELECT * FROM cours";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Cour c = new Cour();
                c.setIdCour(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString(3));
                c.setID_Module(rs.getInt(4));
                c.setFichierPath(rs.getString(5));
                c.setTempsEstimeC(rs.getInt(6));

                cours.add(c);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return cours;
    }

    @Override
    public void update(Cour cour) {
        String qry ="UPDATE cours SET nom`=?,description`=?,`ID_Module`=? ,`fichierPath`=? ,`tempsC`=? WHERE `idCours`=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,cour.getNom());
            stm.setString(2,cour.getDescription());
            stm.setInt(3,cour.getID_Module());
            stm.setString(4,cour.getFichierPath());
            stm.setInt(5,cour.getTempsEstimeC());
            stm.setInt(6,cour.getIdCour());


            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    @Override
    public boolean delete(Cour cour) {
        String qry = "DELETE FROM cours WHERE idCours = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, cour.getIdCour());

            stm.executeUpdate();
            return true ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false ;
        }
    }
    public ArrayList<Cour> getCoursByIdModule(int idModule){
        ArrayList<Cour> cours = new ArrayList();
        String qry ="SELECT * FROM cours where ID_Module=?";
        try {
            PreparedStatement statement = cnx.prepareStatement(qry);
            statement.setInt(1, idModule); // Définir la valeur de idModule comme paramètre

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Cour c = new Cour();
                c.setIdCour(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString(3));
                c.setID_Module(rs.getInt(4));
                c.setFichierPath(rs.getString(5));
                c.setTempsEstimeC(rs.getInt(6));

                cours.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cours;
    }
    public ArrayList<Cour> triByTempsEstimeC (String ascDesc) {
        ArrayList<Cour> cours = new ArrayList();
        String qry = "SELECT * FROM cours ORDER BY tempsC " + ascDesc;
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Cour c = new Cour();
                c.setIdCour(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString(3));
                c.setFichierPath(rs.getString("fichierPath"));
                c.setTempsEstimeC(rs.getInt("tempsC"));
                c.setID_Module(rs.getInt("ID_Module"));

                cours.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cours;
    }

    public Cour getCoursbyId(int id) {
        String query = "SELECT * FROM cours WHERE idCours = ? ";

        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Cour c = new Cour();
                c.setIdCour(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString(3));
                c.setID_Module(rs.getInt(4));
                c.setFichierPath(rs.getString(5));
                c.setTempsEstimeC(rs.getInt(6));
                c.setAudioPath(rs.getString("audioPath"));
                c.setVideoPath(rs.getString("videoPath"));
                return c;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID du module", e);
        }
        return null;
    }
    public Integer getIdCourbyNom(String nom) {
        String query = "SELECT idCours FROM cours WHERE nom = ? ";

        try  {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, nom);


            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("idCours");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID du module", e);
        }
    }
}

