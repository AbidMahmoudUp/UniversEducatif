package com.example.javafxprojectusertask.Entities;


import java.time.LocalDateTime;

public class Exam {
    private int idexams;
    private String module;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    private LocalDateTime date;
    public Exam() {

    }

    public Exam(int idexams, String module) {
        this.idexams = idexams;
        this.module = module;
    }
    public Exam(String module,LocalDateTime date)
    {
        this.module=module;
        this.date=date;
    }

    public Exam(String module) {
        this.module = module;
    }

    public int getIdexams() {
        return idexams;
    }

    public void setIdexams(int idexams) {
        this.idexams = idexams;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }



    @Override
    public String toString() {
        return "Exam{" +
                "idexams=" + idexams +
                ", module='" + module + '\'' +
                ", date=" + date.toString() +
                '}';
    }

    public String getClassName()
    {
        return "Exam";
    }

}
