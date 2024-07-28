package com.example.javafxprojectusertask.Entities;

import java.util.ArrayList;

public class ProfileUser extends Profile{

    protected String certification;
    protected String niveau;
    protected String statistique;

    public ProfileUser(int idProfile,int idUser, String firstName, String lastName, String picture, String address, String location,int phoneNumber, String certification, String niveau, String statistique) {
        super(idProfile,idUser, firstName, lastName, picture, address, location,phoneNumber);
        this.certification = certification;
        this.niveau = niveau;
        this.statistique = statistique;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getStatistique() {
        return statistique;
    }

    public void setStatistique(String statistique) {
        this.statistique = statistique;
    }

    @Override
    public String toString() {
        return "ProfileUser{" +
                "certification='" + certification + '\'' +
                ", niveau='" + niveau + '\'' +
                ", statistique='" + statistique + '\'' +
                '}';
    }



    public String getClassName()
    {
        return "Profile User";
    }
}
