package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceQuestion;
import com.example.javafxprojectusertask.Entities.Question;

import java.io.IOException;

public class QuestionCardController {

    @FXML
    private Label Question;

    @FXML
    private Label choix1;

    @FXML
    private Label choix2;

    @FXML
    private Label choix3;

    @FXML
    private Label choixcorrecte;
    @FXML
    private Label duree;
    @FXML
    private AnchorPane AnchorParent;



    private final ServiceQuestion sq = new ServiceQuestion();
private Question q;
private QuestionController questionController;

    public void setQuestion(Question question) {
        this.q = question;
        displayQuestionDetails();
        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        AnchorParent.setEffect(dropShadow);
    }
    private void displayQuestionDetails() {
        if (q != null) {
            Question.setText(q.getQuestion());
            choix1.setText(q.getChoix1());
            choix2.setText(q.getChoix2());
            choix3.setText(q.getChoix3());
            choixcorrecte.setText(q.getChoixCorrect());
            duree.setText(String.valueOf(q.getDuree()));
        }
    }
    public void setQc(QuestionController questionController) {
        this.questionController = questionController;
    }

    private boolean deleteExam() {
        if (q != null) {
            return sq.delete(q);
        }
        return false;
    }

    @FXML
    private void handleDeleteIconClicked() {
        boolean success = deleteExam();
        if (success) {
           questionController.loadQuestionData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Exam deleted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the exam.");
        }
    }
    @FXML
    void openUpdateQuestion(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Question/editQuestion.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            UpdateQuestionController controller = loader.getController();
            controller.initData(q.getIdquestion());
            stage.showAndWait();
            questionController.loadQuestionData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
