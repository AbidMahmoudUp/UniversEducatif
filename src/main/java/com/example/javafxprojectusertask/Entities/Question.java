package com.example.javafxprojectusertask.Entities;

import java.util.Objects;

public class Question {
    private int idquestion;
    private String question;
    private String choix1;
    private String choix2;
    private String choix3;
    private String choixCorrect;
    private int idExam;
    private int duree;

    // Constructors
    public Question() {
    }

    public Question(String question, String choix1, String choix2, String choix3, String choixCorrect, int idExam, int duration) {
        this.question = question;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.choixCorrect = choixCorrect;
        this.idExam = idExam;
        this.duree = duration;
    }

    // Getters and Setters
    public int getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(int id) {
        this.idquestion = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoix1() {
        return choix1;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public String getChoixCorrect() {
        return choixCorrect;
    }

    public void setChoixCorrect(String choixCorrect) {
        this.choixCorrect = choixCorrect;
    }

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return idquestion == question1.idquestion && Objects.equals(question, question1.question);
    }

    @Override
    public String toString() {
        return "Question : " + question + "\n" +
                "question : " + question + "\n" +
                "choix1 : " + choix1 + "\n" +
                "choix2 : " + choix2 + "\n" +
                "choix3 : " + choix3 + "\n" +
                "idExam : " + idExam + "\n" +
                "duration : " + duree + "\n"
                ;
    }


    public String getClassName()
    {
        return "Question";
    }
}
