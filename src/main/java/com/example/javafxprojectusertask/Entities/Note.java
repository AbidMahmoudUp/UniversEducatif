package com.example.javafxprojectusertask.Entities;

import java.util.List;

public class Note {
    private int idUser;
    private int idExam;
    private double note;
    private int module;

    public Note() {
    }

    public Note(int idUser, int idExam, double note, int module) {
        this.idUser = idUser;
        this.idExam = idExam;
        this.note = note;
        this.module = module;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Note{" +
                "idUser=" + idUser +
                ", idExam=" + idExam +
                ", note=" + note +
                ", module='" + module + '\'' +
                '}';
    }
    public int CalculeNote(List<String> reponses,List<Question> questions)
    {
        int note=0;
        for (int i =0;i<2;i++)
        {
            if(reponses.get(i).equals(questions.get(i)))
            {
                note++;
            }
        }
        return note;
    }


    public String getClassName()
    {
        return "Note";
    }
}
