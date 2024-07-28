package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Services.LogsService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceExam;
import com.example.javafxprojectusertask.Entities.Exam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class UpdateExamController {
    @FXML
    private TextField moduleField;

    @FXML
    private DatePicker datePicker;
    private Exam exam;
    private ServiceExam serviceExam;

    public void setModuleoriginal(String moduleoriginal) {
        this.moduleoriginal = moduleoriginal;
    }

    private String moduleoriginal;

    public UpdateExamController() {
        serviceExam = new ServiceExam();
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        populateFields();
    }

    private void populateFields() {
        if (exam != null) {
            moduleField.setText(exam.getModule());

            // Create a LocalDate object for January 1, 2000
            LocalDate date = LocalDate.of(2000, 1, 1);

            // Set the value of the datePicker to January 1, 2000
            datePicker.setValue(date);
        }
    }

    @FXML
    private void updateExam() {
        String updatedModule = moduleField.getText();
        LocalDate updatedDate = datePicker.getValue(); // Get the selected date from the date picker

        if (!updatedModule.isEmpty() && updatedDate != null) {
            exam.setModule(updatedModule);
            exam.setDate(updatedDate.atStartOfDay()); // Update the exam's date
            if (serviceExam.exist(exam) && (!(exam.getModule().equals(moduleoriginal)))) {
                showAlert(Alert.AlertType.ERROR, "Error", "Cet examen existe déjà");
            } else {

                LogsService logsService = new LogsService();
                Logs log = new Logs();
                Date test1 = Timestamp.valueOf(LocalDateTime.now());
                log.setDate(test1);
                log.setIdUser(UserUtils.ConnectedUser.getIdUser());
                log.setDescription1("a modifié Examen " );
                log.setAction1(exam.getModule());
                log.setDescription2("dans la liste ");
                log.setAction2(exam.getClassName());
                logsService.addLog(log);
                serviceExam.update(exam);
                closeWindow();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Entrez votre module et sélectionnez une date.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) moduleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initData(int id) {
        this.exam = serviceExam.getElementByIndex(id);
        moduleField.setText(exam.getModule());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String dateString = exam.getDate().format(formatter);
        datePicker.setValue(LocalDate.parse(dateString, formatter));
        this.moduleoriginal = exam.getModule();

    }
}