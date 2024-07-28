package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Suivi;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ServiceSuivi implements IService <Suivi> {
    private Connection cnx ;
    public ServiceSuivi(){
        cnx = DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void add(Suivi suivi) {
        String qry ="INSERT INTO `suivi`( `idUser`, `idModule`, `dateConsultation`) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,suivi.getIdUser());
            stm.setInt(2,suivi.getIdModule());
            stm.setDate(3,suivi.getDateConsultation());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Suivi> getAll() {
        ArrayList<Suivi> suivis = new ArrayList();
        String qry ="SELECT * FROM `suivi`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Suivi suivi = new Suivi();
                suivi.setIdSuivi(rs.getInt(1));
                suivi.setIdUser(rs.getInt(2));
                suivi.setIdModule(rs.getInt(3));
                suivi.setDateConsultation(rs.getDate(4));
                suivis.add(suivi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suivis;
    }

    @Override
    public void update(Suivi suivi) {
        String qry ="UPDATE `suivi` SET `dateConsultation`=CURRENT_DATE() WHERE `idModule`=? AND `idUser`=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, suivi.getIdModule());
            stm.setInt(2, suivi.getIdUser());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean aDejaConsulteModule(int idUser, int idModule) {
        String qry = "SELECT COUNT(*) FROM suivi WHERE idUser = ? AND idModule = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, idUser);
            stm.setInt(2, idModule);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(Suivi suivi) {
        String qry = "DELETE FROM suivi WHERE idSuivi = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, suivi.getIdSuivi());
            stm.executeUpdate();
            return true ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false ;
        }
    }
   /* public void verifierDateConsultation() {
        System.out.println("Vérification de la date de consultation...");

        // Obtenez la date d'il y a une semaine
        LocalDate ilYaUneSemaine = LocalDate.now().minus(1, ChronoUnit.WEEKS);

        // Récupérez les enregistrements de suivi avec une date de consultation dépassant une semaine
        ArrayList<Suivi> suiviDepassantUneSemaine = getSuiviDepassantUneSemaine(ilYaUneSemaine);

        // Traitez les enregistrements de suivi dépassement une semaine (par exemple, envoyez des notifications)
        traiterSuiviDepassantUneSemaine(suiviDepassantUneSemaine);
    }*/


    public ArrayList<Suivi> getSuivipariduser(int iduser) {
        ArrayList<Suivi> suivis = new ArrayList();
        String qry ="SELECT * FROM `suivi` WHERE idUser = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,iduser);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Suivi suivi = new Suivi();
                suivi.setIdSuivi(rs.getInt(1));
                suivi.setIdUser(rs.getInt(2));
                suivi.setIdModule(rs.getInt(3));
                suivi.setDateConsultation(rs.getDate(4));
                suivis.add(suivi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return suivis;
    }
    public void verifierDateConsultation() {
        System.out.println("Vérification de la date de consultation...");

        // Obtenez la date d'il y a une semaine
        LocalDate ilYaUneSemaine = LocalDate.now().minus(1, ChronoUnit.WEEKS);

        // Récupérez les enregistrements de suivi avec une date de consultation dépassant une semaine
        ArrayList<Suivi> suiviDepassantUneSemaine = getSuiviDepassantUneSemaine(ilYaUneSemaine);

        // Traitez les enregistrements de suivi dépassement une semaine (par exemple, envoyez des notifications)
        traiterSuiviDepassantUneSemaine(suiviDepassantUneSemaine);
    }

    private ArrayList<Suivi> getSuiviDepassantUneSemaine(LocalDate ilYaUneSemaine) {
        ArrayList<Suivi> suiviDepassantUneSemaine = new ArrayList<>();
        String qry = "SELECT * FROM suivi WHERE dateConsultation < ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setDate(1, Date.valueOf(ilYaUneSemaine));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Suivi suivi = new Suivi();
                suivi.setIdSuivi(rs.getInt("idSuivi"));
                suivi.setIdUser(rs.getInt("idUser"));
                suivi.setIdModule(rs.getInt("idModule"));
                suivi.setDateConsultation(rs.getDate("dateConsultation"));
                suiviDepassantUneSemaine.add(suivi);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return suiviDepassantUneSemaine;
    }

    private void traiterSuiviDepassantUneSemaine(ArrayList<Suivi> suiviDepassantUneSemaine) {
        for (Suivi suivi : suiviDepassantUneSemaine) {
            System.out.println("Utilisateur avec ID " + suivi.getIdUser() + " a une consultation dépassant une semaine : " + suivi.getDateConsultation());
            ServiceSms serviceSms =new ServiceSms();
            ServiceModule serviceModule =new ServiceModule();
            String nomModule =serviceModule.getModulebyId(suivi.getIdModule()).getNom();

            serviceSms.SendSMS("Cher étudiant,\n" +
                    "\n" +
                    "Nous avons remarqué que vous n'avez pas consulté le module "+nomModule+". Il est important de suivre les cours régulièrement pour réussir dans vos études. Nous vous encourageons à consulter le module dès que possible pour ne pas prendre de retard.");
        }
    }
    public int getOccurrenceForModule(int idModule) {
        int occurrence = 0;
        String qry = "SELECT COUNT(*) FROM suivi WHERE idModule = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, idModule);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                occurrence = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return occurrence;
    }
}
