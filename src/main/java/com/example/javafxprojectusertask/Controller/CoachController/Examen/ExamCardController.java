package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Entities.Exam;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ExamCardController {

    @FXML
    private Label ExamenModule;
    @FXML
    private Label description;

    @FXML
    private Label Examenid;
    @FXML
    private  Label ExamenDate;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView edit;
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private AnchorPane AnchorParent;
    @FXML
    private ImageView img;


    private ServiceExam se = new ServiceExam();
    private Exam exam =new Exam();
    private ExamController examController;


    public void setExam(Exam exam) {
        this.exam = exam;
        displayExamDetails();
        generateAndSetQRCode();
        Examenid.setVisible(false);
        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        AnchorParent.setEffect(dropShadow);
    }

    private void displayExamDetails() {
        if (exam != null) {
            ExamenModule.setText(exam.getModule());
            Examenid.setText(String.valueOf(exam.getIdexams()));
            ExamenDate.setText(exam.getDate().toString());
            ServiceModule serviceModule = new ServiceModule();
            Module module =serviceModule.getModulebyNom(exam.getModule()) ;
            description.setText(module.getDescription());
            Image image= new Image(module.getModuleImgPath());
            img.setImage(image);

        }
    }


    private boolean deleteExam() {
        if (exam != null) {
            LogsService logsService = new LogsService();
            Logs log = new Logs();
            Date test1 = Timestamp.valueOf(LocalDateTime.now());
            log.setDate(test1);
            log.setIdUser(UserUtils.ConnectedUser.getIdUser());
            log.setDescription1("a supprimé Examen " );
            log.setAction1(exam.getModule());
            log.setDescription2("depuis la liste ");
            log.setAction2(exam.getClassName());
            logsService.addLog(log);
            return se.delete(exam);
        }
        return false;
    }

    @FXML
    private void handleDeleteIconClicked() {
        boolean success = deleteExam();
        if (success) {
            examController.loadExamData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Exam deleted successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the exam.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void setExamController(ExamController examController) {
        this.examController=examController;
    }
    @FXML
    private void openUpdateExam() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Examen/updateexamen.fxml"));
            Parent root;
            try {
                root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                UpdateExamController controller = loader.getController();
                controller.initData(exam.getIdexams());
                controller.setModuleoriginal(exam.getModule());
                stage.showAndWait();
                examController.loadExamData();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public void afficherquestions() {
        if (exam != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Question/questionManagement.fxml"));
                AnchorPane pane = loader.load();

                QuestionController questionController = loader.getController();
                questionController.initData(exam);
                examController.replaceModuleAnchorPaneContent(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   private void generateAndSetQRCode() {
        ServiceQuestion serviceQuestion = new ServiceQuestion();
        String list =(serviceQuestion.getQuestionsByExamId(exam.getIdexams())).toString();
        try {
            QRGenerator qrGenerator = new QRGenerator(list, BarcodeFormat.QR_CODE, 165, 165);
            BufferedImage qrImage = qrGenerator.qrImage();
            if (qrImage != null) {
                Image fxImage = SwingFXUtils.toFXImage(qrImage, null);
                qrCodeImageView.setImage(fxImage);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
