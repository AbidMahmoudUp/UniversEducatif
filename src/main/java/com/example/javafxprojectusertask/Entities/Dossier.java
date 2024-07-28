package com.example.javafxprojectusertask.Entities;

public class Dossier {


    String status;

    Offre offre;

    int idUser;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o instanceof Dossier d)
        {
            return this.idUser == d.getIdUser();
        }
        return false;
    }


    public String getClassName()
    {
        return "Dossier";
    }
}
