package com.example.javafxprojectusertask.Controller.UserController.Quiz;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.print.PrinterJob;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CertificationPrintController {

    @FXML
    private Label FullName;

    @FXML
    private Label ModuleName;

    @FXML
    private AnchorPane certificationPane;

    @FXML
    private Label dateCertification;

    @FXML
    private Circle pictureUser;
    public static Module module;
    @FXML
    public void initialize() {
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
    }





}
