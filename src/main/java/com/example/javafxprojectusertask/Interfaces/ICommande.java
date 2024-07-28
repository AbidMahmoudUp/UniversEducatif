package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Commande;

import java.util.ArrayList;

public interface ICommande {
    void addCommande (Commande commande);

     boolean deleteCommande(Commande commande);
    public void displayCommande(Commande commande);
    ArrayList<Commande> getAllCommandes();
    void updateCommande(Commande commande);
    Commande getCommandeById(int id);
}
