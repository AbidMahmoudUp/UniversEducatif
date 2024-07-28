package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Produit;

import java.util.ArrayList;

public interface IProduit {

    void add(Produit produit);
    ArrayList<Produit> getProduitAll();
    boolean deleteProduit(Produit produit);
    void updateProduit(Produit produit);
     Produit getProduitById(int id);
//getOne getById

}
