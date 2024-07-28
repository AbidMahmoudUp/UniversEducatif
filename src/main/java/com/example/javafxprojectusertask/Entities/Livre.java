package com.example.javafxprojectusertask.Entities;

public class Livre {
    private int idLivre;
    private String type;
    private String titre;
    private String auteur ;
    private String langue ;
    private Bibliotheque idBib;

    public Livre() {
    }

    public Livre(int idLivre, String type, String titre, String auteur, String langue,Bibliotheque idBib) {
        this.idLivre = idLivre;
        this.type = type;
        this.titre = titre;
        this.auteur = auteur;
        this.langue = langue;
        this.idBib=idBib;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Bibliotheque getIdBib() {
        return idBib;
    }

    public void setIdBib(Bibliotheque idBib) {
        this.idBib = idBib;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "idLivre=" + idLivre +
                ", type='" + type + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", langue='" + langue + '\'' +
                ", idBib=" + idBib +
                '}';
    }
}
