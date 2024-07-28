package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Question;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceQuestion implements IService<Question> {
    private Connection cnx;

    public ServiceQuestion() {
        cnx = DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void add(Question question) {
        String query = "INSERT INTO questions (question, choix1, choix2, choix3, choixcorrect, idexam,duree) VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, question.getQuestion());
            stm.setString(2, question.getChoix1());
            stm.setString(3, question.getChoix2());
            stm.setString(4, question.getChoix3());
            stm.setString(5, question.getChoixCorrect());
            stm.setInt(6, question.getIdExam());
            stm.setInt(7, question.getDuree());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Question> getAll() {
        ArrayList<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Question question = new Question();
                question.setIdquestion(rs.getInt("idquestion"));
                question.setQuestion(rs.getString("question"));
                question.setChoix1(rs.getString("choix1"));
                question.setChoix2(rs.getString("choix2"));
                question.setChoix3(rs.getString("choix3"));
                question.setChoixCorrect(rs.getString("choixcorrect"));
                question.setIdExam(rs.getInt("idexam"));
                question.setDuree(rs.getInt("duree"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    @Override
    public boolean delete(Question question) {
        String query = "DELETE FROM questions WHERE idquestion = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, question.getIdquestion());
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllByExamId(int examId) {
        try {
            // Supprimer les questions liées à l'examen spécifié
            String query = "DELETE FROM questions WHERE idexam = ?";
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, examId);
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getIndexByElement(Question question) {
        String query = "SELECT idquestion FROM questions WHERE question = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, question.getQuestion());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("idquestion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public boolean exist(Question question) {
        String query = "SELECT COUNT(*) AS count FROM questions WHERE question = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, question.getQuestion());

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    public Question getElementByIndex(int id) {
        String query = "SELECT * FROM questions WHERE idquestion = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Question question = new Question();
                question.setIdquestion(rs.getInt("idquestion"));
                question.setQuestion(rs.getString("question"));
                question.setChoix1(rs.getString("choix1"));
                question.setChoix2(rs.getString("choix2"));
                question.setChoix3(rs.getString("choix3"));
                question.setChoixCorrect(rs.getString("choixcorrect"));
                question.setIdExam(rs.getInt("idExam"));
                question.setDuree(rs.getInt("duree"));
                return question;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void update(Question question) {
            String query = "UPDATE questions SET question=?, choix1=?, choix2=?, choix3=?, choixcorrect=?, idexam=?,duree=? WHERE idquestion=?";
            try {
                PreparedStatement stm = cnx.prepareStatement(query);
                stm.setString(1, question.getQuestion());
                stm.setString(2, question.getChoix1());
                stm.setString(3, question.getChoix2());
                stm.setString(4, question.getChoix3());
                stm.setString(5, question.getChoixCorrect());
                stm.setInt(6, question.getIdExam());
                stm.setInt(7, question.getDuree());
                stm.setInt(8, question.getIdquestion());
                stm.executeUpdate();
                System.out.println("Question updated: " + question);
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public List<Question> getQuestionsByExamId(int examId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE idExam = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, examId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setIdquestion(rs.getInt("idquestion"));
                question.setQuestion(rs.getString("question"));
                question.setChoix1(rs.getString("choix1"));
                question.setChoix2(rs.getString("choix2"));
                question.setChoix3(rs.getString("choix3"));
                question.setChoixCorrect(rs.getString("choixCorrect"));
                question.setIdExam(rs.getInt("idExam"));
                question.setDuree(rs.getInt("duree"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    public List<Question> getQuestionsByExam(int examId, String tri, String chercher, String tripar) {
        List<Question> questions = new ArrayList<>();
        Comparator<Question> comparator = null;
        String query = "SELECT * FROM questions WHERE idExam = ? AND question LIKE ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, examId);
            stm.setString(2,  chercher + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setIdquestion(rs.getInt("idquestion"));
                question.setQuestion(rs.getString("question"));
                question.setChoix1(rs.getString("choix1"));
                question.setChoix2(rs.getString("choix2"));
                question.setChoix3(rs.getString("choix3"));
                question.setChoixCorrect(rs.getString("choixCorrect"));
                question.setIdExam(rs.getInt("idExam"));
                question.setDuree(rs.getInt("duree"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ("question".equalsIgnoreCase(tripar)) {
            comparator = (q1, q2) -> q1.getQuestion().compareTo(q2.getQuestion());
        } else {
            comparator = (q1, q2) -> q1.getDuree()- q2.getDuree();
        }

        if ("DESC".equalsIgnoreCase(tri)) {
            comparator = comparator.reversed();
        }

        return questions.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }





}
