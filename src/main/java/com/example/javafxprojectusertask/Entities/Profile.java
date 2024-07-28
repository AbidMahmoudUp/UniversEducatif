package com.example.javafxprojectusertask.Entities;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private  int idProfile;
    private int idUser;
    private String firstName;
    private String lastName;
    private String picture;
    private String address;

    private int phoneNumber;


    private String location;
    private ArrayList<Logs> logs;

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public Profile(int idProfile, int idUser, String firstName, String lastName, String picture, String address, String location, int phoneNumber) {
        this.idProfile=idProfile;
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.address = address;
        this.location = location;
        this.phoneNumber=phoneNumber;
    }
    public Profile(){}

    public Profile(int idUser,String picture)
    {
        this.picture=picture;
        this.idUser=idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Logs> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Logs> logs) {
        this.logs = logs;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "idUser=" + idUser +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture='" + picture + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", logs=" + logs +
                '}';
    }


    public String getClassName()
    {
        return "Offre";
    }
}
