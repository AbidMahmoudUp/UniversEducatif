package com.example.javafxprojectusertask.Controller.CoachController.Cours;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.CoachController.DashbordController;
import com.example.javafxprojectusertask.Entities.*;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import com.twilio.rest.chat.v1.service.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddCourController {

    @FXML
    private AnchorPane AddPopUpCour;

    @FXML
    private TextField FichierCourpath;

    @FXML
    private ImageView closeArea;

    @FXML
    private ImageView closeButton;

    @FXML
    private MenuButton Module;

    @FXML
    private TextArea description;

    @FXML
    private ImageView fichier;

    @FXML
    private TextField nom;

    @FXML
    private TextField tempsEstimeC;

    @FXML
    private TextField audioCourPath;
    @FXML
    private TextField videoCourPath;
    private CoursManagementController coursManagementController=new CoursManagementController();

    public void setCoursManagementController(CoursManagementController coursManagementController) {
        this.coursManagementController = coursManagementController;
    }



    @FXML
    void AjouterCour(MouseEvent event) throws SQLException, IOException {
        String nomText = nom.getText();
        String descriptionText = description.getText();
        String fichierCourPathText = FichierCourpath.getText();

        String tempsEstimeCText = tempsEstimeC.getText();
        String audioPath = audioCourPath.getText();
        String videoPath = videoCourPath.getText();

        if (nomText.isEmpty() || descriptionText.isEmpty() || fichierCourPathText.isEmpty()  || tempsEstimeCText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        if (!nomText.matches("[a-zA-Z ]+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le champ nom ne doit contenir que des lettres et des espaces.");
            return;
        }


        int tempsEstimeCInt;
        try {
            tempsEstimeCInt = Integer.parseInt(tempsEstimeCText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le champ temps estimé doit être un entier.");
            return;
        }

        ServiceCour serviceCour = new ServiceCour();
        Integer courId = serviceCour.getIdCourbyNom(nomText);
        if (courId != null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Un cours avec ce nom existe déjà.");
            return;
        }
        ServiceModule sm = new ServiceModule();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        UserService userService = new UserService();
        UserClass userClass = new UserClass();
        Profile profile = new Profile();
        ProfileService profileService = new ProfileService();
        ProfileCoach profileCoach = new ProfileCoach();
        CoachProfileService coachProfileService = new CoachProfileService();
        profile = profileService.getProfileUser(UserUtils.ConnectedUser.getIdUser());
        profileCoach = coachProfileService.getCoachProfile(profile);
        Module module = sm.getModulebyId(profileCoach.getIdModule());
        Cour c = new Cour(nomText, descriptionText, fichierCourPathText, profileCoach.getIdModule(), tempsEstimeCInt, audioPath,videoPath);
        module.ajouterCour(c);

        closePopUp(event);
        clearData();
    }

    @FXML
    void clearData() {
        FichierCourpath.clear();
        description.clear();
        nom.clear();
        tempsEstimeC.clear();
        videoCourPath.clear();
    }

    @FXML
    void closePopUp(MouseEvent event) throws IOException {
        AddPopUpCour.setVisible(false);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Coach/DashbordCoach.fxml"));
        Parent root = loader.load();
        DashbordController dashBordController = loader.getController();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        dashBordController.SwitchToGestionCours(event);
        stage.show();
    }

    @FXML
    void fichierPicker(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            final String xamppImgPath = "C:/xampp/htdocs/files/";
            final String base = "http://127.0.0.1/files/";
            String destination = base + selectedFile.getName();
            ServiceAudio serviceAudio = new ServiceAudio();
            String destinationPath = xamppImgPath + selectedFile.getName();
            Files.copy(Path.of(selectedFile.getAbsolutePath()), Path.of(destinationPath));
            final int lastDotIndex = selectedFile.getName().lastIndexOf('.');
            final String fileName = selectedFile.getName().substring(0, lastDotIndex);

            Thread thread = new Thread(() -> {
                try {
                    PDDocument document = Loader.loadPDF(selectedFile);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    String text = pdfStripper.getText(document);
                    serviceAudio.textToAudio(text, fileName.replace(" ",""));
                    System.out.println("Content : \n" + text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            FichierCourpath.setText(destination);
            audioCourPath.setText(base + fileName.replace(" ","")+".wav");

            try {
                String content = Files.readString(selectedFile.toPath());
                System.out.println("Contenu du fichier : " + content);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }
    @FXML
    void videoPicker(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video File", "*.mp4"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);



        if (selectedFile != null) {
            String xamppImgPath = "C:/xampp/htdocs/files/";
            final String base = "http://127.0.0.1/files/";
            String destinationPath = xamppImgPath + selectedFile.getName().replace(" ","");
            String destination = base + selectedFile.getName().replace(" ","");
            Files.copy(Path.of(selectedFile.getAbsolutePath()), Path.of(destinationPath));
            videoCourPath.setText(destination);

            // Load image
            try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
                Image image = new Image(inputStream);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("No file has been selected");
        }

    }

    @FXML
    void initialize() {
        assert AddPopUpCour != null : "fx:id=\"AddPopUpCour\" was not injected: check your FXML file 'AddCour.fxml'.";
        assert FichierCourpath != null : "fx:id=\"FichierCourpath\" was not injected: check your FXML file 'AddCour.fxml'.";
        assert closeArea != null : "fx:id=\"closeArea\" was not injected: check your FXML file 'AddCour.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'AddCour.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'AddCour.fxml'.";
        assert fichier != null : "fx:id=\"fichier\" was not injected: check your FXML file 'AddCour.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'AddCour.fxml'.";

    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
