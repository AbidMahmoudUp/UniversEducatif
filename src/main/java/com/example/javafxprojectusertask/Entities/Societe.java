package com.example.javafxprojectusertask.Entities;

public class Societe {



    int id;
    String nom;
    String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNom() {
        return nom;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {

        return Integer.toString(id) + " - "+ nom;
    }


    public String getClassName()
    {
        return "Societe";
    }
}
