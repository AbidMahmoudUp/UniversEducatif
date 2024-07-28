package com.example.javafxprojectusertask.Controller.CoachController;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.CardLogsController;
import com.example.javafxprojectusertask.Controller.CoachController.Cours.CoursManagementController;
import com.example.javafxprojectusertask.Controller.CoachController.Examen.ExamController;
import com.example.javafxprojectusertask.Controller.LoginController;
import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.LogsService;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class DashbordController implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane ProfileContent;
    @FXML
    private AnchorPane SettingsAnchorPane;
    @FXML
    private AnchorPane dashBordAnimation;
    @FXML
    private AnchorPane lineairgradientdashbord;
    @FXML
    private AnchorPane selectedOption;
    @FXML
    private Label idUser;
    @FXML
    private Label nameAdmin;
    @FXML
    private Rectangle pictureNavBarUser;
    @FXML
    private Label GestionCoursLabelDashBord;
    @FXML
    private Label GestionExamenLabelDashBord;
    @FXML
    private  Label GestionUtilisateurLabelDashBord;
    @FXML
    private AnchorPane UserManagement;
    @FXML
    private AnchorPane CoursManagement;
    @FXML
    private AnchorPane DropDownMenuNavBar;
    @FXML
            private  Label profileLabelDashBord;
    boolean StatusofDropDownMenu ;

    private Stage stage;
    private Scene scene;
    private UserClass userClass = new UserClass();
    private Profile profile = new Profile();
    private ProfileService profileService=new ProfileService();
    private UserService userService = new UserService();

    private  UserUtils userUtils = new UserUtils();

    int receivedData;

    @FXML
    ScrollPane LogsScrollPane;

    @FXML
    FlowPane LogsFlowPane;

    public void setData(int data) throws IOException {
        this.receivedData=data;
        this.receivedData=data;
        System.out.println("the data Recived is "+data);
        this.idUser.setText(String.valueOf(data));
        userClass = userUtils.getConnectedUser();
        profile =profileService.getProfileUser(data);
       // profile=ProfileService.getAdminData(profile);


        Image pictureAdmin1=new Image(profile.getPicture());

        this.nameAdmin.setText(profile.getFirstName()+" "+profile.getLastName());

        Image originalImage = new Image(profile.getPicture());
        ImagePattern imagePattern = new ImagePattern(originalImage);
        circle.setFill(imagePattern);
        pictureNavBarUser.setFill(imagePattern);
        pictureNavBarUser.setStroke(null);
        LogsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        LogsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        LogsService logsService = new LogsService();
        ArrayList<Logs> logsList = new ArrayList<>();
        logsList=logsService.getLogsByUser(data);

        LogsFlowPane.getChildren().clear();
        System.out.println(logsList.size());
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


    @FXML
    public void SwitchToGestionCours(MouseEvent event){

        SwitchDashBordOption(GestionUtilisateurLabelDashBord);

        try {
            ProfileContent.setVisible(false);
            SettingsAnchorPane.setVisible(false);
            UserManagement.setVisible(false);
            CoursManagement.setVisible(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/CoursManagement.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/CoachTemplate/Cours/CoursManagement.fxml"));
            loader.load();

            CoursManagementController controller = loader.getController();
            CoursManagement.getChildren().setAll(pane);
            controller.SetDataCour();

        } catch (IOException e) {
            throw new RuntimeException(e);
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
                String destinationPath = serverImgPath + selectedFile.getName();
                File destinationFile = new File(destinationPath);
                if (destinationFile.exists()) {
                    // If the file exists, delete it
                    destinationFile.delete();
                }
                // Copy the selected file to the server directory
                Files.copy(selectedFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

                // Construct the URL for the image
                String imageUrl = localhostImgUrl + selectedFile.getName();

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
    @FXML
    public void Settings(MouseEvent event) throws IOException{
        try {
            ProfileContent.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Coach/Settings.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/CoachTemplate/Coach/Settings.fxml"));
            SettingsAnchorPane.setVisible(true);
            CoursManagement.setVisible(false);
            loader.load();
            SettingsController settingsController = loader.getController();


            SettingsAnchorPane.getChildren().setAll(pane);
            settingsController.setData(receivedData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
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
    public void SwitchToProfile(MouseEvent event) throws IOException {
        SwitchDashBordOption(profileLabelDashBord);
        setData(UserUtils.ConnectedUser.getIdUser());
        ProfileContent.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        UserManagement.setVisible(false);
        CoursManagement.setVisible(false);


    }

    @FXML
    public void SwitchToExamen(MouseEvent event){
        ProfileContent.setVisible(false);
        SettingsAnchorPane.setVisible(false);
        CoursManagement.setVisible(false);
        SwitchDashBordOption(GestionExamenLabelDashBord);
        UserManagement.getChildren().clear();
        try {
            ProfileContent.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Examen/examenManagement.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/CoachTemplate/Examen/examenManagement.fxml"));
            UserManagement.setVisible(true);
            loader.load();
            // ExamController controller = loader.getController();


            UserManagement.getChildren().setAll(pane);
            ExamController.dashBordController = this;
            ExamController.moduleAnchorPane=UserManagement;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        event.getTarget();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropDownMenuNavBar.setVisible(false);
        DropDownMenuNavBar.toFront();
    }
}
