package com.example.javafxprojectusertask.Entities;

public class Produit {
    private int id_produit;
    private String nom,type,descrip;
    private float prix_init,marge;
    private String video  ;
    private String image ;
    private String audio ;


    public Produit() {

    }
    public Produit(int id_produit, String nom, String type, String descrip, float prix_init, float marge, String video, String image, String audio) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.type = type;
        this.descrip = descrip;
        this.prix_init = prix_init;
        this.marge = marge;
        this.video = video;
        this.image = image;
        this.audio = audio;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.image = audio;
    }
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
    public int getId_produit() {
        return id_produit;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getDescrip() {
        return descrip;
    }

    public float getPrix_init() {
        return prix_init;
    }

    public float getMarge() {
        return marge;
    }

    // Setters
    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setPrix_init(float prix_init) {
        this.prix_init = prix_init;
    }

    public void setMarge(float marge) {
        this.marge = marge;
    }

    // ToString
    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", descrip='" + descrip + '\'' +
                ", prix_init=" + prix_init +
                ", marge=" + marge +
                '}';
    }
}
