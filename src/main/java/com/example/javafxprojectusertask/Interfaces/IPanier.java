package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Panier;
import com.example.javafxprojectusertask.Entities.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IPanier  {
    void addPanier(List<Map<Produit, Integer>> produitsQuantite);

    ArrayList<Panier> getPanierAll();

    void updatePanier(Panier panier);

    Panier getPanierById(int id);

    boolean deletePanier(Panier panier);

    int SommeDeQte(Map<Produit, Integer> pan);
}
