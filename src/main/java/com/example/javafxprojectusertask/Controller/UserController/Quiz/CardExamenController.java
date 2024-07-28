package com.example.javafxprojectusertask.Controller.UserController.Quiz;

import com.example.javafxprojectusertask.Entities.Exam;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CardExamenController {


        @FXML
        private Label dateExamen;
        @FXML
        private AnchorPane AnchorParent;
        @FXML
        private Label descriptionModule;

        @FXML
        private Label examenTitle;

        @FXML
        private Button goToQuiz;

        @FXML
        private ImageView imageModule;
        private  Exam exam =new Exam();
        public void setExam(Exam exam) {
                this.exam = exam;
                displayExamDetails();
                //generateAndSetQRCode();
                //Examenid.setVisible(false);
                DropShadow dropShadow = new DropShadow();

                dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
                dropShadow.setRadius(10);
                dropShadow.setOffsetX(0);
                dropShadow.setOffsetY(5);
                AnchorParent.setEffect(dropShadow);
        }

        private void displayExamDetails() {
                if (exam != null) {
                        ServiceModule serviceModule = new ServiceModule();
                        Module module = new Module();
                         module=serviceModule.getModulebyNom(exam.getModule());
                        examenTitle.setText(exam.getModule());
                        dateExamen.setText(exam.getDate().toString());
                        descriptionModule.setText(module.getDescription());
                        //Image image = new Image(module.getModuleImgPath());
                        //imageModule.setImage(image);
                }
        }

        @FXML
        public void openQuiz(MouseEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Quiz/QuizPage.fxml"));
                QuizPageController.examId = exam.getIdexams();
                Parent root = loader.load();
                QuizPageController controller=loader.getController();
                controller.initialize();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();
        }

}
