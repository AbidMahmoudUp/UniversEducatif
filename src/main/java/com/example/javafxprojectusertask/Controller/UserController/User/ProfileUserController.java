package com.example.javafxprojectusertask.Controller.UserController.User;

import com.example.javafxprojectusertask.Entities.*;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileUserController implements Initializable {


    UserUtils userUtils = new UserUtils();
    UserClass userClass = new UserClass();
    UserService userService = new UserService();
    Profile  profile = new Profile();
    ProfileService profileService = new ProfileService();
    @FXML
    private Label FullNameUser;

    @FXML
    private Rectangle PictureUser;

    @FXML
    private Label UserAddresse;
    @FXML
    private Label UserEmail;

    @FXML
    private Label UserLocation;

    @FXML
    private Label UserPhoneNumber;
    @FXML
    private ScrollPane CertifcationScrollPane;
    @FXML
    private FlowPane CertificationFlowPane;

    @FXML
    private FlowPane EventFlowPane;
    @FXML
    private ScrollPane EventScrollPane;

    public void setData()
    {

    }


    @FXML
    public void UpdatePhotoUser(MouseEvent event) {
        final String serverImgPath = "C:/xampp/htdocs/img/"; // Server directory path
        final String localhostImgUrl = "http://127.0.0.1/img/"; // URL prefix for localhost
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        // Set the file chooser to filter only image files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Construct the destination path on the server
                String formattedFileName = selectedFile.getName().replaceAll(" ", "%20");
                String destinationPath = serverImgPath + formattedFileName;
                File destinationFile = new File(destinationPath);
                if (destinationFile.exists()) {
                    // If the file exists, delete it
                    destinationFile.delete();
                }
                // Copy the selected file to the server directory
                Files.copy(selectedFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

                // Construct the URL for the image
                String imageUrl = localhostImgUrl + formattedFileName;

                // Store the image URL in the database
                Profile profile = new Profile(userClass.getIdUser(), imageUrl);
                profileService.UpdatePictureUser(profile);

                // Display the image on the UI (if needed)
                Image originalImage = new Image(selectedFile.toURI().toString());
                ImagePattern imagePattern = new ImagePattern(originalImage);
                PictureUser.setFill(imagePattern);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userClass=userUtils.getConnectedUser();
        profile=profileService.getProfileUser(userClass.getIdUser());
        Image image = new Image(profile.getPicture());
        ImagePattern imagePattern = new ImagePattern(image);
        PictureUser.setFill(imagePattern);
        UserAddresse.setText(profile.getAddress());
        PictureUser.setStroke(null);
        UserEmail.setText(userClass.getEmail());
        UserLocation.setText(profile.getLocation());
        System.out.println(profile.getPhoneNumber());
        UserPhoneNumber.setText(String.valueOf(profile.getPhoneNumber()) );
        ServiceNote serviceNote = new ServiceNote();
        ArrayList<Note> notes = serviceNote.getAll();
        if(notes.size()!=0)
        {
        for (Note note : notes )
        {
            if(note.getNote()>=8)
            {
                Label label = new Label();
                label.setText("\n"+note.getModule()+"\n");
                label.setStyle("-fx-font-weight: bold");
                CertificationFlowPane.getChildren().add(label);
                CertifcationScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                CertifcationScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        }
        }
        ServiceDossier serviceDossier = new ServiceDossier() ;
        ArrayList<Dossier> dossiers = serviceDossier.getDossierByUser(UserUtils.ConnectedUser.getIdUser());
        if(dossiers.size()!=0) {


            for (int i = dossiers.size() - 1; i > dossiers.size() - 4; i--) {

                Label label = new Label();
                label.setText("\n Vous avez participé dans l'offre " + dossiers.get(i).getOffre().getDesc());
                label.setStyle("-fx-font-weight: bold");
                EventFlowPane.getChildren().add(label);
                EventScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                EventScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        }
    }
}
