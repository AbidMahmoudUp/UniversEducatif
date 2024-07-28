package com.example.javafxprojectusertask.Controller.AdminController.Admin;

import com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque.BibliothequeManagementController;
import com.example.javafxprojectusertask.Controller.AdminController.Dossier.DossierManagmentController;
import com.example.javafxprojectusertask.Controller.AdminController.Livre.LivreManagementController;
import com.example.javafxprojectusertask.Controller.AdminController.Magasin.Commande.CommandeManagementController;
import com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit.ProduitManagementController;
import com.example.javafxprojectusertask.Controller.AdminController.Module.ModuleManagementController;
import com.example.javafxprojectusertask.Controller.AdminController.Offre.OffreManagmentController;
import com.example.javafxprojectusertask.Controller.AdminController.Societe.EventManagmentController;
import com.example.javafxprojectusertask.Controller.LoginController;
import com.example.javafxprojectusertask.Entities.*;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Test.Application;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DashBordController implements Initializable {


    //-----------------------------------------------------------------------------------------
    @FXML
    private Label idUser,GestionEventLabelDashBord ;
    @FXML
    private Label profileLabelDashBord ;
    @FXML
    private Label GestionUtilisateurLabelDashBord ;
    @FXML
    private AnchorPane selectedOption;
    @FXML
    private Label GestionCoursLabelDashBord;
    @FXML
    private AnchorPane lineairgradientdashbord;

    @FXML
    private Label GestionBibliothequeLabel,GestionMagasin,GestionCommande;

    @FXML
    private AnchorPane BibliothequeManagement;
    @FXML
    private FlowPane LogsFlowPane;
    @FXML
    private Rectangle pictureNavBarUser;

    @FXML
    private ScrollPane LogsScrollPane;

    @FXML
    private AnchorPane dashBordAnimation,CommandeManagement;
    @FXML
    private  AnchorPane DropDownMenuNavBar,EventManagment,MagasinManagement;

    @FXML
    private Circle circle;

    @FXML
    private  AnchorPane SettingsAnchorPane , ProfileContent ;

    @FXML
    private AnchorPane ModuleManagement;
    @FXML
     public AnchorPane UserManagement;
    @FXML
    ImageView pictureAdmin;
    @FXML
    Label nameAdmin;
    int receivedData;
    boolean StatusofDropDownMenu = false;

    Stage stage;

    Scene scene;

    AdminProfileService profileAdminController = new AdminProfileService();
    ProfileAdmin profileAdmin=new ProfileAdmin();
    ProfileService profileService = new ProfileService();
    UserUtils userUtils = new UserUtils();
    UserClass userClass = new UserClass();
    Profile profile = new Profile();
    public void setData(int data) throws IOException {
        this.receivedData=data;
        System.out.println("the data Recived is "+data);
        this.idUser.setText(String.valueOf(data));
        userClass = userUtils.getConnectedUser();
        profile =profileService.getProfileUser(data);
        profileAdmin=profileAdminController.getAdminData(profile);


        Image pictureAdmin1=new Image(profile.getPicture());

        this.nameAdmin.setText(profile.getFirstName()+" "+profile.getLastName());

        Image originalImage = new Image(profile.getPicture());
        ImagePattern imagePattern = new ImagePattern(originalImage);
        circle.setFill(imagePattern);
        pictureNavBarUser.setFill(imagePattern);
        LogsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        LogsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        LogsService logsService = new LogsService();
        ArrayList<Logs> logsList = new ArrayList<>();
        logsList=logsService.getLogsByUser(data);

        LogsFlowPane.getChildren().clear();
        for (int i = 0 ;i<logsList.size();i++)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/CardLogs.fxml"));
            AnchorPane pane = loader.load();
            CardLogsController cardUserController = loader.getController();
            cardUserController.setDataCardLogs(logsList.get(i));
            System.out.println(logsList.get(i).getDescription2());
            LogsFlowPane.getChildren().add(pane);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropDownMenuNavBar.setVisible(false);
        DropDownMenuNavBar.toFront();
        pictureNavBarUser.setStroke(null);

        CommandeManagement.setVisible(false);

    }

// This function is supposed to pass on it  a label so you can make the animation !
    public void SwitchDashBordOption(Label option ){
        Timeline timeline = new Timeline();
        dashBordAnimation.setPrefWidth(0);
        dashBordAnimation.setLayoutX(-10);
        lineairgradientdashbord.setPrefWidth(0);
        Timeline secondAnimation = new Timeline();
        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO,
                new KeyValue(dashBordAnimation.layoutXProperty(), -10),
                new KeyValue(dashBordAnimation.layoutYProperty(), option.getLayoutY()),
                new KeyValue(lineairgradientdashbord.prefWidthProperty(), 0),
                new KeyValue(lineairgradientdashbord.opacityProperty(),0.2),
                new KeyValue(selectedOption.prefWidthProperty(), 0));

        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.1),
                new KeyValue(dashBordAnimation.prefWidthProperty(),256),
                new KeyValue(dashBordAnimation.layoutXProperty(), -5),
                new KeyValue(dashBordAnimation.layoutYProperty(), option.getLayoutY()),
                new KeyValue(lineairgradientdashbord.prefWidthProperty(), 256),
                new KeyValue(lineairgradientdashbord.opacityProperty(),0.05),
                new KeyValue(selectedOption.prefWidthProperty(), 8)

        );
        secondAnimation.getKeyFrames().addAll(keyFrame1, keyFrame2);

        secondAnimation.play();
       // timeline.play();


    }
    @FXML
    public void SwitchToGestionUtilsateur(MouseEvent eventt){

     SwitchDashBordOption(GestionUtilisateurLabelDashBord);

        try {
            ProfileContent.setVisible(false);
            SettingsAnchorPane.setVisible(false);
            UserManagement.setVisible(true);
            EventManagment.setVisible(false);
            ModuleManagement.setVisible(false);
            BibliothequeManagement.setVisible(false);
            MagasinManagement.setVisible(false);
            CommandeManagement.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/UserManagment.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Admin/UserManagment.fxml"));
            loader.load();

            UserManagementController userManagementController = loader.getController();
            UserManagement.getChildren().setAll(pane);
             userManagementController.setData();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void SwitchToProfile(MouseEvent event)
    {
        SwitchDashBordOption(profileLabelDashBord);
        ProfileContent.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        ModuleManagement.setVisible(false);
        MagasinManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        CommandeManagement.setVisible(false);


    }
    @FXML
    public void SwitchToModule(MouseEvent event) throws IOException {
        SwitchDashBordOption(GestionCoursLabelDashBord);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        ModuleManagement.setVisible(true);
        MagasinManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/ModuleManagement.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Module/ModuleManagement.fxml"));
        loader.load();

        ModuleManagementController controller = loader.getController();
        ModuleManagement.getChildren().setAll(pane);
    }

    @FXML
    public void SwitchToEvent(MouseEvent event) throws IOException {
        SwitchDashBordOption(GestionEventLabelDashBord);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(true);
        ModuleManagement.setVisible(false);
        MagasinManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Societe/E-eventManagment.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Societe/E-eventManagment.fxml"));
        loader.load();

        EventManagmentController eventManagment = loader.getController();
        EventManagment.getChildren().setAll(pane);
        eventManagment.initialize();
    }

    public void SwitchToOffre() throws IOException {

        SwitchDashBordOption(GestionEventLabelDashBord);
        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(true);
        ModuleManagement.setVisible(false);
        MagasinManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Offre/OffreManagment.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Offre/OffreManagment.fxml"));
        loader.load();
        EventManagment.getChildren().clear();
        OffreManagmentController offreManagment = loader.getController();
        EventManagment.getChildren().setAll(pane);
        offreManagment.initialize();
    }

    @FXML
    public void ActivateDropDownMenuNavBar(MouseEvent event){
        if(!StatusofDropDownMenu)
        {DropDownMenuNavBar.setVisible(false);
        StatusofDropDownMenu=true;}
       else if(StatusofDropDownMenu) {
            DropDownMenuNavBar.setVisible(true);
            StatusofDropDownMenu=false;
       }

    }
    @FXML
    public void logout(MouseEvent event) throws IOException, BackingStoreException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginAdmin/loginPage.fxml"));
        Parent root = loader.load();
        LoginController loginController=loader.getController();
        Preferences preferences = Preferences.userNodeForPackage(Application.class);
        UserUtils userUtils1 = new UserUtils();
        UserUtils.ConnectedUser= userUtils1.clearUser();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    private void pickImage(javafx.scene.input.MouseEvent event) {
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
                circle.setFill(imagePattern);
                LogsService logsService = new LogsService();
                ArrayList<Logs> logsList = new ArrayList<>();
                logsList=logsService.getLogsByUser(UserUtils.ConnectedUser.getIdUser());
                LogsFlowPane.getChildren().clear();
                for (int i = 0 ;i<logsList.size();i++)
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/CardLogs.fxml"));
                    AnchorPane pane = loader.load();
                    CardLogsController cardUserController = loader.getController();
                    cardUserController.setDataCardLogs(logsList.get(i));
                    System.out.println(logsList.get(i).getDescription2());
                    LogsFlowPane.getChildren().add(pane);
                }
                pictureNavBarUser.setFill(imagePattern);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private Image resizeImage(Image image, double width, double height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        ImagePattern imagePattern=new ImagePattern(image);
        circle.setFill(imagePattern);
        imageView.setStyle("-fx-background-radius:10 10 10 10 ; -fx-border-radius: 10 10 10 10;");
        imageView.setPreserveRatio(true); // Maintain aspect ratio
        return imageView.snapshot(null, null);
    }
    @FXML
    public void Settings(MouseEvent event) throws IOException{
        try {
            ProfileContent.setVisible(false);
            MagasinManagement.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Settings.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Admin/Settings.fxml"));
            SettingsAnchorPane.setVisible(true);
                loader.load();
            SettingsController settingsController = loader.getController();


            SettingsAnchorPane.getChildren().setAll(pane);
            settingsController.setData(receivedData);

      } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void SwitchToDossier() throws IOException {

        SwitchDashBordOption(GestionEventLabelDashBord);
        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(true);
        MagasinManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        ModuleManagement.setVisible(false);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Dossier/DossierManagement.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Dossier/DossierManagement.fxml"));
        loader.load();
        EventManagment.getChildren().clear();
        DossierManagmentController controller = loader.getController();
        EventManagment.getChildren().setAll(pane);
        controller.initialize();
    }


    public void switchToCours() throws IOException {
        SwitchDashBordOption(GestionCoursLabelDashBord);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        BibliothequeManagement.setVisible(false);
        ModuleManagement.setVisible(true);
        MagasinManagement.setVisible(false);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Cour/CoursManagement.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Cour/CoursManagement.fxml"));
        loader.load();

        ModuleManagement.getChildren().clear();
        ModuleManagement.getChildren().setAll(pane);


    }

    @FXML
    public void SwitchToBibliotheque(MouseEvent event) throws IOException {
        SwitchDashBordOption(GestionBibliothequeLabel);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        ModuleManagement.setVisible(false);
        BibliothequeManagement.setVisible(true);
        MagasinManagement.setVisible(false);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/BibliothequeManagement.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/BibliothequeManagement.fxml"));
        loader.load();

        System.out.println("test bib");
        BibliothequeManagementController bibliothequeManagement = loader.getController();
        BibliothequeManagement.getChildren().setAll(pane);
        //bibliothequeManagement.initialize();
    }


    @FXML
    public void SwitchToLivres(MouseEvent event) throws IOException {
        SwitchDashBordOption(GestionBibliothequeLabel);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        ModuleManagement.setVisible(false);
        MagasinManagement.setVisible(false);
        BibliothequeManagement.setVisible(true);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Livre/LivreManagement.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Livre/LivreManagement.fxml"));
        loader.load();

        System.out.println("test bib");
        LivreManagementController livreManagement = loader.getController();
        BibliothequeManagement.getChildren().setAll(pane);
        livreManagement.initialize();
    }

    @FXML
    public void SwitchToMagasin(MouseEvent event) throws IOException {
        SwitchDashBordOption(GestionMagasin);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        ModuleManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        MagasinManagement.setVisible(true);
        CommandeManagement.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/ProduitManagement.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Magasin/ProduitManagement.fxml"));
        loader.load();

        System.out.println("test bib");
        ProduitManagementController bibliothequeManagement = loader.getController();
        MagasinManagement.getChildren().setAll(pane);
        //bibliothequeManagement.initialize();
    }
    @FXML
    public void SwitchToCommande(MouseEvent event) throws IOException {
        SwitchDashBordOption(GestionCommande);

        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        EventManagment.setVisible(false);
        ModuleManagement.setVisible(false);
        BibliothequeManagement.setVisible(false);
        MagasinManagement.setVisible(false);
        CommandeManagement.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/CommandeManagment.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Magasin/CommandeManagment.fxml"));
        loader.load();

        System.out.println("test bib");
        CommandeManagementController commandeManagementController = loader.getController();
        CommandeManagement.getChildren().setAll(pane);
        //bibliothequeManagement.initialize();
    }


}
