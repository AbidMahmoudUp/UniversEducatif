package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Interfaces.IService;
import com.example.javafxprojectusertask.Entities.Exam;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ServiceExam implements IService<Exam> {
    private Connection cnx;

    public ServiceExam() {
        cnx = DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void add(Exam exam) {
        String qry = "INSERT INTO `exams`(`module`, `date`) VALUES (?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, exam.getModule());
            stm.setDate(2, Date.valueOf(exam.getDate().toLocalDate())); // Set date
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Exam> getAll() {
        ArrayList<Exam> exams = new ArrayList<>();
        String query = "SELECT * FROM exams";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setIdexams(rs.getInt("idexam"));
                exam.setModule(rs.getString("module"));
                exam.setDate((rs.getDate("date")).toLocalDate().atStartOfDay());
                exams.add(exam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exams;
    }
    public ArrayList<Exam> getAl(String recherche, String tri,String tripar) {
        Comparator<Exam> comparator;
        ArrayList<Exam> exams = new ArrayList<>();
        String query = "SELECT * FROM exams WHERE module LIKE ? ";

        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, recherche + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setIdexams(rs.getInt("idexam"));
                exam.setModule(rs.getString("module"));
                exam.setDate(rs.getTimestamp("date").toLocalDateTime()); // Fetch and set the date
                exams.add(exam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ("Module".equals(tripar)) {
            comparator = (exam1, exam2) -> exam1.getModule().compareTo(exam2.getModule());
        } else {
            comparator = (exam1, exam2) -> exam1.getDate().compareTo(exam2.getDate());
        }

        if ("DESC".equals(tri)) {
            comparator = comparator.reversed();
        }

        return (ArrayList<Exam>) exams.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }






    @Override
    public boolean delete(Exam exam) {
        try {
            ServiceQuestion serviceQuestion = new ServiceQuestion();
            serviceQuestion.deleteAllByExamId(exam.getIdexams());
            String deleteExamQuery = "DELETE FROM exams WHERE idexam = ?";
            PreparedStatement deleteExamStatement = cnx.prepareStatement(deleteExamQuery);
            deleteExamStatement.setInt(1, exam.getIdexams());
            int rowsAffected = deleteExamStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public boolean exist(Exam exam) {
        String query = "SELECT COUNT(*) AS count FROM exams WHERE module = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, exam.getModule());
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



    @Override
    public void update(Exam exam) {
        String query = "UPDATE exams SET module = ?, date = ? WHERE idexam = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, exam.getModule());
            stm.setDate(2, Date.valueOf(exam.getDate().toLocalDate())); // Set date
            stm.setInt(3, exam.getIdexams());
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Exam updated: " + exam);
            } else {
                System.out.println("Exam with ID '" + exam.getIdexams() + "' not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exam getElementByIndex(int index) {
        String query = "SELECT * FROM exams WHERE idexam = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, index);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Exam exam = new Exam();
                exam.setIdexams(rs.getInt("idexam"));
                exam.setModule(rs.getString("module"));
                exam.setDate(rs.getTimestamp("date").toLocalDateTime()); // Fetch and set the date
                return exam;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Exam> getExambymodule(String nomModule) {
        ArrayList<Exam>  exams = new ArrayList<>();
        String query = "SELECT * FROM exams WHERE module = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setString(1, nomModule);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setIdexams(rs.getInt("idexam"));
                exam.setModule(rs.getString("module"));
                exam.setDate((rs.getDate("date")).toLocalDate().atStartOfDay());
                exams.add(exam);
            }
            return exams;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}



