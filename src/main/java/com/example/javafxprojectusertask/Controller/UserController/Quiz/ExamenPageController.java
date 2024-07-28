package com.example.javafxprojectusertask.Controller.UserController.Quiz;

import com.example.javafxprojectusertask.Entities.*;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExamenPageController  {

    @FXML
    private FlowPane FlowPaneExamen;

    @FXML
    private ScrollPane ScrollPaneExamen;

    private ServiceExam serviceExam = new ServiceExam();

    public void loadExamData() {
        FlowPaneExamen.getChildren().clear();
        UserUtils userUtils1 = new UserUtils();
        UserClass userClass = new UserClass();
        UserService userService1 = new UserService();
        UserUtils.ConnectedUser = userUtils1.getConnectedUser();
        ServiceSuivi serviceSuivi = new ServiceSuivi();
        ArrayList<Suivi> suivis = serviceSuivi.getSuivipariduser(UserUtils.ConnectedUser.getIdUser());
        ArrayList<Module> modules = new ArrayList<>();
        ServiceModule serviceModule = new ServiceModule();
        for (Suivi suivi : suivis) {
            modules.add(serviceModule.getModulebyId(suivi.getIdModule()));
        }
        ArrayList<Exam> exams = new ArrayList<>();
        ServiceExam serviceExam = new ServiceExam();
        for (Module module : modules) {
            exams.addAll(serviceExam.getExambymodule(module.getNom()));
        }
        ArrayList<Note> notes = new ArrayList<>();
        ServiceNote serviceNote = new ServiceNote();
        notes= serviceNote.getNotesbyuser(UserUtils.ConnectedUser.getIdUser());
        for (Note note : notes)
        {
            for (Exam exam : exams)
            {
                if(note.getIdExam()==exam.getIdexams())
                {
                    exams.remove(exam);
                    break;
                }
            }
        }
        for (Exam exam : exams) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Examen/CardExamenUser.fxml"));
                AnchorPane card = loader.load();
                CardExamenController controller = loader.getController();
                controller.setExam(exam);
           //   controller.setExamController(this);
                FlowPaneExamen.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void initialize() {
        loadExamData();
    }
}
