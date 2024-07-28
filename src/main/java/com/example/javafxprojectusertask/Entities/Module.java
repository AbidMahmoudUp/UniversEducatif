package com.example.javafxprojectusertask.Entities;

import com.example.javafxprojectusertask.Services.ServiceCour;
import com.example.javafxprojectusertask.Services.ServiceModule;

import java.util.ArrayList;

public class Module {

    private  int idModule ;
    private  String nom ;
    private  String description ;
    private  String ModuleImgPath;
    private int tempsEstimeM ;

    private ArrayList<Cour> cours ;
    public Module(){
        cours=new ArrayList<>();
    }
    public void ajouterCour(Cour cour) {

        this.cours = new ArrayList<>();
        ServiceCour serviceCour = new ServiceCour();
        serviceCour.add(cour);
        cours = serviceCour.getCoursByIdModule(this.idModule);
        ServiceModule serviceModule =new ServiceModule();
        this.setTempsEstimeM(sommeTempsM(cours));
        serviceModule.update(this);

    }
    public void supprimerCour(Cour cour){
        this.cours = new ArrayList<>();
        ServiceCour serviceCour = new ServiceCour();
        serviceCour.delete(cour);
        cours = serviceCour.getCoursByIdModule(this.idModule);
        ServiceModule serviceModule =new ServiceModule();
        this.setTempsEstimeM(sommeTempsM(cours));
        serviceModule.update(this);

    }
    public Integer sommeTempsM(ArrayList<Cour> cours){
        Integer somme=0;
        for (Cour c :cours){
            somme=c.getTempsEstimeC()+somme;
        }
        return somme ;
    }

    public String getModuleImgPath() {
        return ModuleImgPath;
    }

    public void setModuleImgPath(String moduleImgPath) {
        ModuleImgPath = moduleImgPath;
    }

    public ArrayList<Cour> getCours() {
        return cours;
    }

    public void setCours(ArrayList<Cour> cours) {
        this.cours = cours;
    }



    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
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

    public int getTempsEstimeM() {
        return tempsEstimeM;
    }

    public void setTempsEstimeM(int tempsEstimeM) {
        this.tempsEstimeM = tempsEstimeM;
    }

    public Module(int idModule, String nom, String description, String ModuleImgPath, int tempsEstimeM) {
        this.idModule = idModule;
        this.nom = nom;
        this.description = description;
        this.ModuleImgPath=ModuleImgPath;
        this.tempsEstimeM=tempsEstimeM;
    }

    @Override
    public String toString() {
        return nom;
    }
}
