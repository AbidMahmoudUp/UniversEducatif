package com.example.javafxprojectusertask.Entities;

import java.time.LocalDate;

public class Commande {
    private  int id_user;
    private String adresse;
    private int id_cmd;
   private LocalDate date;

    @Override
    public String toString() {
        return "Commande{" +
                "id_cmd=" + id_cmd +
                ", date=" + date +
                ", id_user=" + id_user +
                ", adresse='" + adresse + '\'' +
                '}';
    }


    public Commande(int id_cmd, LocalDate date, int id_user, String adresse) {
        this.id_cmd = id_cmd;
        this.date = date;
        this.id_user = id_user;
        this.adresse = adresse;
    }

    public Commande()
    {

    }



    public int getId_cmd() {
        return id_cmd;
    }

    public void setId_cmd(int id_cmd) {
        this.id_cmd = id_cmd;
    }

    public LocalDate getDatee() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date =LocalDate.now();

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    }


