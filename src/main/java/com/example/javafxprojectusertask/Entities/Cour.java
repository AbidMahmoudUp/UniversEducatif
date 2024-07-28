package com.example.javafxprojectusertask.Entities;

public class Cour {
    private  int idCour ;
    private  String nom ;
    private  String description ;
    private String FichierPath;
    private int ID_Module ;
    private int tempsEstimeC ;
    private String audioPath ;
    private String videoPath ;



    public Cour ( String nom, String description,String FichierPath,int ID_Module,int tempsEstimeC,String audioPath,String videoPath){
        this.nom = nom;
        this.description = description;
        this.ID_Module=ID_Module;
        this.FichierPath=FichierPath;
        this.tempsEstimeC=tempsEstimeC;
        this.audioPath=audioPath;
        this.videoPath=videoPath;
    }
    public Cour(){}
    public Cour(int idCour, String nom, String description,String FichierPath,int ID_Module,int tempsEstimeC,String audioPath,String videopath) {
        this.idCour = idCour;
        this.nom = nom;
        this.description = description;
        this.ID_Module=ID_Module;
        this.FichierPath=FichierPath;
        this.tempsEstimeC=tempsEstimeC;
        this.audioPath=audioPath;
        this.videoPath=videopath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public int getIdCour() {
        return idCour;
    }

    public String getFichierPath() {
        return FichierPath;
    }

    public void setFichierPath(String fichierPath) {
        FichierPath = fichierPath;
    }

    public void setIdCour(int idCour) {
        this.idCour = idCour;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID_Module() {
        return ID_Module;
    }

    public void setID_Module(int ID_Module) {
        this.ID_Module = ID_Module;
    }
    public int getTempsEstimeC() {
        return tempsEstimeC;
    }

    public void setTempsEstimeC(int tempsEstime) {
        this.tempsEstimeC = tempsEstime;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public String toString() {
        return "Cour{" +
                "idCour=" + idCour +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", FichierPath='" + FichierPath + '\'' +
                ", ID_Module=" + ID_Module +
                '}';
    }
}
