package com.example.javafxprojectusertask.Entities;

import java.sql.Date;

public class Suivi {
    private int idSuivi ;
    private int idUser ;
    private int idModule ;
    private Date dateConsultation ;
    public Suivi(){
    }
    public Suivi(int idUser, int idModule, Date date){
        this.idUser=idUser;
        this.idModule=idModule;
        this.dateConsultation=date;
    }

    public int getIdSuivi() {
        return idSuivi;
    }

    public void setIdSuivi(int idSuivi) {
        this.idSuivi = idSuivi;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    @Override
    public String toString() {
        return "Suivi{" +
                "idSuivi=" + idSuivi +
                ", idUser=" + idUser +
                ", idModule=" + idModule +
                ", dateConsultation=" + dateConsultation +
                '}';
    }
}
