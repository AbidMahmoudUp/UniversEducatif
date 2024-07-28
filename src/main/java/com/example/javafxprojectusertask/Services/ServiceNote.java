package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Entities.Exam;
import com.example.javafxprojectusertask.Services.ServiceNote;
import com.example.javafxprojectusertask.Entities.Note;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;

import com.example.javafxprojectusertask.Interfaces.IService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceNote implements IService<Note> {
    private Connection cnx;

    public ServiceNote() {
        cnx = DataBaseConnection.getInstance().getCnx();
    }

    @Override
    public void add(Note note) {
        String query = "INSERT INTO notes (idUser, idexam, note, module) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, note.getIdUser());
            stm.setInt(2, note.getIdExam());
            stm.setDouble(3, note.getNote());
            stm.setInt(4, note.getModule());


            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Note> getAll() {
        ArrayList<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Note note = new Note();
                note.setIdUser(rs.getInt("idUser"));
                note.setIdExam(rs.getInt("idexam"));
                note.setNote(rs.getDouble("note"));
                note.setModule(rs.getInt("module"));
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public void update(Note note) {
        String query = "UPDATE notes SET note = ? WHERE idUser = ? AND idexam = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setDouble(1, note.getNote());
            stm.setInt(2, note.getIdUser());
            stm.setInt(3, note.getIdExam());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Note note) {
        String query = "DELETE FROM notes WHERE idUser = ? AND idexam = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, note.getIdUser());
            stm.setInt(2, note.getIdExam());
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Note> getNotesbyuser(int iduser) {
        ArrayList<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes WHERE idUser = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);
            stm.setInt(1, iduser);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Note note = new Note();
                note.setIdUser(rs.getInt("idUser"));
                note.setIdExam(rs.getInt("idexam"));
                note.setNote(rs.getDouble("note"));
                note.setModule(rs.getInt("module"));
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }



}
