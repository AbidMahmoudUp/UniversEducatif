package com.example.javafxprojectusertask.Entities;

public class Bibliotheque {
private int idBib;
private String nom;
private String mail;
private int nbrLivre;
//private List<Livre> livres;

public Bibliotheque() {
      //  this.livres= new ArrayList<>();
        }

public Bibliotheque(int idBib, String  nom, String mail, int nbrLivre) {
        this.idBib = idBib;
        this.nom= nom;
        this.mail = mail;
        this.nbrLivre = nbrLivre;
        //this.livres= new ArrayList<>();
  //      this.idLivre = idLivre;
        }

public int getIdBib() {
        return idBib;
        }

public void setIdBib(int idBib) {
        this.idBib = idBib;
        }

public String getNom() {
        return nom;
        }

public void setNom(String nom) {
        this.nom = nom;
        }

public String getMail() {
        return mail;
        }

public void setMail(String mail) {
        this.mail = mail;
        }

public int getNbrLivre() {
        return nbrLivre;
        }

public void setNbrLivre(int nbrLivre) {
        this.nbrLivre = nbrLivre;
        }
      /*  public List<Livre> getLivress() {
                return livres;
        }

        public void setActivitess(List<Activite> terrains) {
                this.livres = livres;
        }*/


        @Override
        public String toString() {
                return "Bibliotheque{" +
                        "idBib=" + idBib +
                        ", nom='" + nom + '\'' +
                        ", mail='" + mail + '\'' +
                        ", nbrLivre=" + nbrLivre +
                        '}';
        }


}
