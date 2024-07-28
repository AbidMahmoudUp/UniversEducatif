package com.example.javafxprojectusertask.Entities;


import java.util.Date;

public class Offre {

    private int id;
    private String desc;
    private int niveau;
    private Date dateDeb;
    private Date dateFin;
    private Societe societe;

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString()
    {
        return societe.getNom() + " - " + desc ;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o instanceof Offre offre)
        {
            return this.id == offre.getId();
        }
        return false;
    }


    public String getClassName()
    {
        return "Offre";
    }

}
