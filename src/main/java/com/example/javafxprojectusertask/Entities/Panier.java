package com.example.javafxprojectusertask.Entities;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Panier {
    private int id_panier;
    private Date date;
    private Map<Produit, Integer> produitsQuantite; // Mapping des produits avec leur quantité dans le panier

    // Constructeur par défaut
    public Panier() {
        this.produitsQuantite = new TreeMap<>();
    }

    // Constructeur paramétré
    public Panier(int id_panier, Date date, Map<Produit, Integer> produitsQuantite) {
        this.id_panier = id_panier;
        this.date = date;
        this.produitsQuantite = produitsQuantite;
    }

    // Getters et setters
    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<Produit, Integer> getProduitsQuantite() {
        return produitsQuantite;
    }

    public void setProduitsQuantite(Map<Produit, Integer> produitsQuantite) {
        this.produitsQuantite = produitsQuantite;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id_panier=" + id_panier +
                ", date=" + date +
                ", produitsQuantite=" + produitsQuantite +
                '}';
    }
}
