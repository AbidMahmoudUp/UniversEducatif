package com.example.javafxprojectusertask.Entities;

import java.util.Date;

public class Logs {
    private int idLog;
    private int idUser;
    private String description1;
    private Date date;

    private String description2;
    private String action1;
    private String action2;
    public Logs(){

    }
    public Logs(int idUser, String description1, Date date,String action1,String action2,String description2) {
        this.idUser = idUser;
        this.description1 = description1;
        this.date = date;
        this.action1=action1;
        this.action2=action2;
        this.description2=description2;
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDescription1() {
        return description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getAction1() {
        return action1;
    }

    public void setAction1(String action1) {
        this.action1 = action1;
    }

    public String getAction2() {
        return action2;
    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "idUser=" + idUser +
                ", description1='" + description1 + '\'' +
                ", date=" + date +
                '}';
    }
}
