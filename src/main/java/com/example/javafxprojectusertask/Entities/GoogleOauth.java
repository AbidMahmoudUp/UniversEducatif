package com.example.javafxprojectusertask.Entities;

public class GoogleOauth {

    private String id ;



    private String email;

    private  boolean verfied_Email ;

    private String name ;

    private String given_name;

    private String family_name;

    private String picture;

    public String getId() {
        return id;
    }

    private String locale;


    public GoogleOauth() {
    }


    public String getEmail() {
        return email;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerfied_Email() {
        return verfied_Email;
    }

    public void setVerfied_Email(boolean verfied_Email) {
        this.verfied_Email = verfied_Email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public GoogleOauth(String id, String email, boolean verfied_Email, String name, String given_name, String family_name, String picture, String locale) {
        this.id = id;
        this.email = email;
        this.verfied_Email = verfied_Email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.picture = picture;
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "GoogleOauth{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", verfied_Email=" + verfied_Email +
                ", name='" + name + '\'' +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", picture='" + picture + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
