package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceQuestion;
import com.example.javafxprojectusertask.Entities.Question;

public class AddQuestionController {
    @FXML
    private TextField questionField;

    @FXML
    private TextField choix1Field;

    @FXML
    private TextField choix2Field;

    @FXML
    private TextField choix3Field;

    @FXML
    private TextField choixcorrectField;
    @FXML
    private TextField dureeField;

    public static int idexam;

    private ServiceQuestion serviceQuestion;
    private QuestionController questionController;


    public AddQuestionController() {
        this.serviceQuestion = new ServiceQuestion();
        this.questionController = new QuestionController();
    }

    @FXML
    void addQuestion(ActionEvent event) {
        String questionText = questionField.getText();
        String choix1 = choix1Field.getText();
        String choix2 = choix2Field.getText();
        String choix3 = choix3Field.getText();
        String choixcorrect = choixcorrectField.getText();
        String dureeText = dureeField.getText();

        if (questionText.isEmpty() || choix1.isEmpty() || choix2.isEmpty() || choix3.isEmpty() || choixcorrect.isEmpty() || dureeText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }
        int duree;
        try {
            duree = Integer.parseInt(dureeText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La durée doit être un nombre entier.");
            return;
        }

        if (!choixcorrect.equals(choix1) && !choixcorrect.equals(choix2) && !choixcorrect.equals(choix3)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le choix correct doit être l'un des choix proposés.");
            clearFields();
            return;
        }
        Question question = new Question(questionText, choix1, choix2, choix3, choixcorrect, idexam, duree);
        if (serviceQuestion.exist(question))
        {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Ce question exist deja");
            return;
        }
        serviceQuestion.add(question);
        Stage stage = (Stage) questionField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void clearFields() {
        questionField.clear();
        choix1Field.clear();
        choix2Field.clear();
        choix3Field.clear();
        choixcorrectField.clear();
        dureeField.clear();
    }


    public void initData(int idexams) {
        idexam = idexams;
        System.out.println(idexams);
        System.out.println(idexam);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
