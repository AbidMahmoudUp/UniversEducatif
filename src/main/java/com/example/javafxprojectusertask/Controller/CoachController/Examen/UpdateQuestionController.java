package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceQuestion;
import com.example.javafxprojectusertask.Entities.Question;

public class UpdateQuestionController {
    @FXML
    private TextField questionTextField;

    @FXML
    private TextField choix1TextField;

    @FXML
    private TextField choix2TextField;

    @FXML
    private TextField choix3TextField;

    @FXML
    private TextField choixCorrectTextField;
    @FXML
    private TextField dureeTextField;
    private Question question;
    private ServiceQuestion serviceQuestion;
    private String questionoriginal;

    public UpdateQuestionController() {
        serviceQuestion = new ServiceQuestion();
    }

    public void setQuestion(Question question) {
        this.question = question;
        populateFields();
    }

    private void populateFields() {
        if (question != null) {
            questionTextField.setText(question.getQuestion());
            choix1TextField.setText(question.getChoix1());
            choix2TextField.setText(question.getChoix2());
            choix3TextField.setText(question.getChoix3());
            choixCorrectTextField.setText(question.getChoixCorrect());
            dureeTextField.setText(String.valueOf(question.getDuree()));
            this.questionoriginal=question.getQuestion();
        }
    }

    @FXML
    private void updateQuestion() {
        String questionText = questionTextField.getText();
        String choix1 = choix1TextField.getText();
        String choix2 = choix2TextField.getText();
        String choix3 = choix3TextField.getText();
        String choixcorrect = choixCorrectTextField.getText();
        String dureeText = dureeTextField.getText();
            question.setQuestion(questionText);
            question.setChoix1(choix1);
            question.setChoix2(choix2);
            question.setChoix3(choix3);
            question.setChoixCorrect(choixcorrect);
            question.setDuree(Integer.valueOf(dureeText));
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
            return;
        }
        if (serviceQuestion.exist(question)&&(!question.getQuestion().equals(questionoriginal)))
        {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Ce Question existe deja");
                return;
        }
        else
        {
            serviceQuestion.update(question);
            closeWindow();
        }


    }

        public void initData(int id) {
            this.question = serviceQuestion.getElementByIndex(id);
            populateFields();
        }
    private void closeWindow() {
        Stage stage = (Stage) questionTextField.getScene().getWindow();
        stage.close();
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
