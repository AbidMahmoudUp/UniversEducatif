package com.example.javafxprojectusertask.Controller.UserController.Quiz;

import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.Gmailer;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class    CertificationController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane certificationPane;
    @FXML
    private Label FullName;
    @FXML
    private Label dateCertification;
    @FXML
    private Label ModuleName;
    @FXML
    private Circle pictureUser;
    public static Module module;
    @FXML
    private FlowPane flowPaneCertification;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your controller
        UserClass userClass = new UserClass();
        Profile profile = new Profile();
        UserService userService = new UserService();
        ProfileService profileService = new ProfileService();
        profile = profileService.getProfileUser(UserUtils.ConnectedUser.getIdUser());
        try {
            userClass=userService.getUserById(UserUtils.ConnectedUser.getIdUser());
            FullName.setText(profile.getFirstName()+" "+profile.getLastName());
            ModuleName.setText(module.getNom());
            String pictureUrl = profile.getPicture();
            if (pictureUrl != null) {
                javafx.scene.image.Image originalImage = new javafx.scene.image.Image(pictureUrl);
                originalImage.errorProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // Handle error loading image
                        System.out.println("Error loading image from URL: " + pictureUrl);
                    }
                });
                ImagePattern imagePattern = new ImagePattern(originalImage);
                pictureUser.setFill(imagePattern);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Platform.runLater(() -> imprimerCertificat());
        Platform.runLater(() -> getinmail());

    }


    public void imprimerCertificat() {
        try {
            CertificationPrintController.module = module;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Certification/certificationprint.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);



            // Check if the AnchorPane is attached to a scene before capturing the snapshot
            AnchorPane certificationPane = (AnchorPane) root.lookup("#certificationPane");
            if (certificationPane != null && certificationPane.getScene() != null) {
                // Capture the content of the scene as a PNG image
                WritableImage image = certificationPane.snapshot(new SnapshotParameters(), null);

                // Specify the file path where you want to save the PNG image
                String filePath = "certification.png";

                // Write the image to the file
                File file = new File(filePath);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

                // Close the scene
                stage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getinmail() {
        try {
            Gmailer gmailer = new Gmailer();
            gmailer.sendMail("test","test","certification.png","aloulou.neymar.2002@gmail.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




    @FXML
    public void closePopUp(MouseEvent event)
    {
        mainPane.setVisible(false);

           /*   try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/WelcomePage.fxml"));
                  AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/UserTemplate/WelcomePage.fxml"));

                 WelcomePageController controller = loader.getController();


                controller.setDataUser(UserUtils.ConnectedUser.getIdUser());
                controller.SwitchToExamen(event);
                  FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/ExamenPage.fxml"));
                   FXMLLoader.load(getClass().getResource("/GUI/UserTemplate/ExamenPage.fxml"));

                  ExamenPageController controller1 = loader1.getController();
                    controller1.loadExamData();
                  FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/QuizPage.fxml"));
                  FXMLLoader.load(getClass().getResource("/GUI/UserTemplate/QuizPage.fxml"));

                  QuizPageController controller2 = loader2.getController();
                  controller2.initialize();


                  FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/reponse.fxml"));
                  FXMLLoader.load(getClass().getResource("/GUI/UserTemplate/reponse.fxml"));

                  ReponseController controller3 = loader3.getController();
                  controller3.initialize();
                  controller3.HideCertif();

                  Parent root =  loader.load();
           Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }*/
    }
}
