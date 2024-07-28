package com.example.javafxprojectusertask.Entities;

public class Achat  {
    private  Commande idc;
    private int id_produit;
    private int qte;
    private double mont_tot;
    public Commande getIdc() {
        return idc;
    }

    public void setIdc(Commande idc) {
        this.idc = idc;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getMont_tot() {
        return mont_tot;
    }

    public void setMont_tot(double mont_tot) {
        this.mont_tot = mont_tot;
    }


}
