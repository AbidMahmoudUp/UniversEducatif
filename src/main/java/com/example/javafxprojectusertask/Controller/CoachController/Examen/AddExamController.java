package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileCoach;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Entities.Exam;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class AddExamController {
    @FXML
    private TextField moduleField;

    @FXML
    private DatePicker datePicker; // New date picker field

    private final ServiceExam serviceExam = new ServiceExam();

    @FXML
    private void addExam(MouseEvent event) {

        LocalDate date = datePicker.getValue(); // Get selected date from the date picker
        ProfileCoach profileCoach = new ProfileCoach();
        CoachProfileService coachProfileService = new CoachProfileService();
        ProfileService profileService = new ProfileService();
        Profile profile = new Profile();
        profile=profileService.getProfileUser(UserUtils.ConnectedUser.getIdUser());
        try {
            profileCoach=coachProfileService.getCoachProfile(profile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        com.example.javafxprojectusertask.Entities.Module module = new Module();
        ServiceModule serviceModule=new ServiceModule();
        module = serviceModule.getModulebyId(profileCoach.getIdModule());

        if (date != null) {
            Exam newExam = new Exam(module.getNom(), date.atStartOfDay());

                LogsService logsService = new LogsService();
                Logs log = new Logs();
            //System.out.println(java.sql.Date.valueOf(LocalDate.now().toString()));
            //System.out.println(LocalDateTime.now().toString());
            //System.out.println(test);
            Date test1 = Timestamp.valueOf(LocalDateTime.now());

            //System.out.println(test1);
            //new java.sql.Date(d.getTime())
                log.setDate(test1);
                log.setIdUser(UserUtils.ConnectedUser.getIdUser());
                log.setDescription1("a ajouté Examen " );
                log.setAction1(newExam.getModule());
                log.setDescription2("dans la liste ");
                log.setAction2(newExam.getClassName());
                logsService.addLog(log);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Examen ajouté!");
                serviceExam.add(newExam);
            FacebookPoster facebookPoster = new FacebookPoster();
            facebookPoster.postToFacebook("Examen " + newExam.getModule() + "seraa le " + newExam.getDate().toString());
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.close();

        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer le module et sélectionner la date.");
        }

    }

    @FXML
    private void clearFields() {
        moduleField.clear();
        datePicker.setValue(null); // Clear the selected date in the date picker
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
