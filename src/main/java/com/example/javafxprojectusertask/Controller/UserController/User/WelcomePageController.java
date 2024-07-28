package com.example.javafxprojectusertask.Controller.UserController.User;

import com.almasb.fxgl.app.GameApplication;
import com.example.javafxprojectusertask.Controller.LoginController;
import com.example.javafxprojectusertask.Controller.UserController.Bibliotheque.BibliothequeManagementController;
import com.example.javafxprojectusertask.Controller.UserController.Cours.CourDetailsController;
import com.example.javafxprojectusertask.Controller.UserController.Cours.CourPageController;
import com.example.javafxprojectusertask.Controller.UserController.Magasin.CatalogueController;
import com.example.javafxprojectusertask.Controller.UserController.Quiz.ExamenPageController;
import com.example.javafxprojectusertask.Entities.*;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Simulation.SimulationApp;
import com.example.javafxprojectusertask.Test.Application;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class WelcomePageController implements Initializable {

    int recivedData=0;
    @FXML
    AnchorPane DropDownMenuNavBar;
    @FXML
    AnchorPane NavBarUserAnchorPane;
    @FXML
    AnchorPane WelcomPageContentAnchorPane;
    @FXML
    AnchorPane SettingsAnchorPane;
    @FXML
    AnchorPane ProfileAnchorPane;
    @FXML
    Circle PictureUser;

    @FXML
    private Label coursLabel;

    @FXML
    private Line coursLine;

    @FXML
    private Label evenementLabel;

    @FXML
    private Line evenementLine;

    @FXML
    private Label examensLabel;

    @FXML
    private Line examensLine;

    @FXML
    private Line homeLine;
    @FXML
    private Line BibLine;
    @FXML
    private Label BibLabel;
    @FXML
    private Label homepageLabel;

    @FXML
    private Label magasinLabel;
    @FXML
    private Line MagasinLine;

    @FXML
    private Label profileLabel;

    @FXML
    private Line profileLine;
    @FXML
            private  AnchorPane MagasinAnchorPane;
    int login =0;

    Stage stage;
    Scene scene;

    UserClass user=new UserClass();
    UserUtils userUtils=new UserUtils();
    UserService userService=new UserService();
    Profile profile = new Profile();
    ProfileService profileService=new ProfileService();

    public void setDataUser(int data){

        recivedData = data;
        System.out.println("id passed is:"+data);
        profile=profileService.getProfileUser(data);
        Image image = new Image(profile.getPicture());
        ImagePattern imagePattern = new ImagePattern(image);
        PictureUser.setFill(imagePattern);
        PictureUser.setStroke(null);

    }
    @FXML
    public void Settings(){
        try {
            WelcomPageContentAnchorPane.setVisible(false);
            DropDownMenuNavBar.setVisible(false);
            NavBarUserAnchorPane.setVisible(true);
            SettingsAnchorPane.setVisible(true);
            ProfileAnchorPane.setVisible(false);
            NavBarUserAnchorPane.toFront();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/SettingsPage.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/UserTemplate/User/SettingsPage.fxml"));
          //  SettingsAnchorPane.setVisible(true);
            loader.load();
            SettingsController settingsController = loader.getController();
            System.out.println("Settings id Recived"+recivedData);

            SettingsAnchorPane.getChildren().setAll(pane);
            settingsController.setData(recivedData);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML void logout(MouseEvent event) throws IOException, BackingStoreException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginAdmin/loginPage.fxml"));
        Parent root = loader.load();
        LoginController loginController=loader.getController();
        Preferences preferences = Preferences.userNodeForPackage(Application.class);
        UserUtils userUtils1=new UserUtils();

        UserUtils.ConnectedUser= userUtils1.clearUser();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();
        login=0;


    }
    @FXML
    public void SwitchToExamen(MouseEvent event) throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        ProfileAnchorPane.setVisible(true);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Examen/ExamenPage.fxml"));
        AnchorPane pane = loader.load();
        ExamenPageController examenPageController = loader.getController();
        ProfileAnchorPane.getChildren().setAll(pane);
    }
    @FXML
    public void showDropDownMenu(MouseEvent event)
    {
        DropDownMenuNavBar.setVisible(true);
        DropDownMenuNavBar.toFront();
    }
    @FXML
    public void SwitchToWelcomePage(MouseEvent event)
    {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(true);
        MagasinAnchorPane.setVisible(false);
    }
    @FXML
    public void SwitchToProfile() throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.setVisible(true);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/ProfilePage.fxml"));
        AnchorPane pane = loader.load();
        ProfileUserController profileUserController = loader.getController();
        profileUserController.setData();
        ProfileAnchorPane.getChildren().setAll(pane);
        MagasinAnchorPane.setVisible(false);

    }
    @FXML
    public void SwitchToEvent(MouseEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        SimulationApp simulationApp = new SimulationApp();
        var fxglRoot = GameApplication.embeddedLaunch(simulationApp);
        BorderPane root = new BorderPane();
        root.setPrefSize(1366, 768);
        //root.setTop(new HBox(btnResize, new Button("Button2"), new Button("Button3"), new Button("Button4")));
        root.setBottom(new HBox(new Text("The status area")));
        //root.setLeft(area);
        root.setRight(new Text("RIGHT"));
        root.setCenter(fxglRoot);
        //Scene scene = (crystalApp.getScene());
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        MagasinAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        //WelcomPageContentAnchorPane.setVisible(true);
        if(login==0) {
            login=1;
            UserUtils userUtils1 = new UserUtils();
            UserClass userClass = new UserClass();
            UserService userService1 = new UserService();
            UserUtils.ConnectedUser = userUtils1.getConnectedUser();
            ServiceSuivi serviceSuivi = new ServiceSuivi();
            ArrayList<Suivi> suivis = serviceSuivi.getSuivipariduser(UserUtils.ConnectedUser.getIdUser());
            System.out.println(suivis);
            ArrayList<Module> modules = new ArrayList<>();
            ServiceModule serviceModule = new ServiceModule();
            for (Suivi suivi : suivis) {
                modules.add(serviceModule.getModulebyId(suivi.getIdModule()));
            }
            System.out.println(modules);
            ArrayList<Exam> exams = new ArrayList<>();
            ServiceExam serviceExam = new ServiceExam();
            for (Module module : modules) {
                exams.addAll(serviceExam.getExambymodule(module.getNom()));
            }
            System.out.println(exams);
            for (Exam exam : exams) {
                if (exam.getDate().isAfter(LocalDate.now().atStartOfDay())) {
                    SystemNotification.trigger("exam alert", "Vous avez un examen du " + exam.getModule());
                }
            }
        }


    }
    @FXML
    public void AnimateOnHomeLine(){
        AnimationLineOn(homeLine);
    }
    @FXML
    public void AnimateOffHomeLine(){
        AnimationLineoff(homeLine);
    }

    @FXML
    public void AnimateOnProfileLine(){
        AnimationLineOn(profileLine);
    }
    @FXML
    public void AnimateOffBibLine(){
        AnimationLineoff(BibLine);
    }

    @FXML
    public void AnimateOnBibLine(){
        AnimationLineOn(BibLine);
    }
    @FXML
    public void AnimateOffProfileLine(){
        AnimationLineoff(profileLine);
    }
    @FXML
    public void AnimateOnCoursLine(){
        AnimationLineOn(coursLine);
    }
    @FXML
    public void AnimateOffCoursLine(){
        AnimationLineoff(coursLine);
    }
    @FXML
    public void AnimateOnExamensLine(){
        AnimationLineOn(examensLine);
    }
    @FXML
    public void AnimateOffExamensLine(){
        AnimationLineoff(examensLine);
    }
    @FXML
    public void AnimateOnMagasinLine(){
        AnimationLineOn(MagasinLine);
    }
    @FXML
    public void AnimateOffMagasinLine(){
        AnimationLineoff(MagasinLine);
    }
    @FXML
    public void AnimateOnEvenementLine(){
        AnimationLineOn(evenementLine);
    }
    @FXML
    public void AnimateOffEvenementLine(){
        AnimationLineoff(evenementLine);
    }











    public void AnimationLineOn(Line line)
    {
        line.setVisible(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), line);
        scaleTransition.setToX(1);
        scaleTransition.play();
    }
    public void AnimationLineoff(Line line)
    {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), line);
        scaleTransition.setToX(0);
        scaleTransition.setOnFinished(event -> line.setVisible(false)); // Hide line after animation
        scaleTransition.play();
    }

    public void switchToWelcomePage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
        Parent root = loader.load();
        WelcomePageController welcomePageController=loader.getController();
        Preferences preferences = Preferences.userNodeForPackage(Application.class);
        welcomePageController.setDataUser(UserUtils.ConnectedUser.getIdUser());
        Scene scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToCours(MouseEvent event) throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        ProfileAnchorPane.setVisible(true);
        MagasinAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/cour.fxml"));
        AnchorPane pane = loader.load();
        CourPageController controller = loader.getController();
        ProfileAnchorPane.getChildren().setAll(pane);
    }

    public void switchToCoursDetails(MouseEvent event, Cour cour) throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        ProfileAnchorPane.setVisible(true);
        MagasinAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        //ProfileAnchorPane.getChildren().clear();
        WelcomPageContentAnchorPane.setVisible(false);

     //   WelcomPageContentAnchorPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/coursDetails.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/UserTemplate/Cours/coursDetails.fxml"));
        CourDetailsController.cour=cour;
        loader.load();

        CourDetailsController controller = loader.getController();
        System.out.println(cour);

        ProfileAnchorPane.getChildren().setAll(pane);
        //controller.initialize();
    }

    @FXML
    public void switchToBibliotheque(MouseEvent event) throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        ProfileAnchorPane.setVisible(true);
        MagasinAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        BibliothequeManagementController.b = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Bibliotheque/BibliothequePage.fxml"));
        AnchorPane pane = loader.load();
        BibliothequeManagementController controller = loader.getController();
        ProfileAnchorPane.getChildren().setAll(pane);
    }

    @FXML
    public void switchToLivres(MouseEvent event) throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        ProfileAnchorPane.setVisible(true);
        MagasinAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Bibliotheque/BibliothequePage.fxml"));
        AnchorPane pane = loader.load();
        BibliothequeManagementController controller = loader.getController();
        ProfileAnchorPane.getChildren().setAll(pane);
    }
    @FXML
    public void SwitchToMagasin(MouseEvent event) throws IOException {
        DropDownMenuNavBar.setVisible(false);
        NavBarUserAnchorPane.setVisible(true);
        SettingsAnchorPane.setVisible(false);
        ProfileAnchorPane.getChildren().clear();
        ProfileAnchorPane.setVisible(false);
        NavBarUserAnchorPane.toFront();
        WelcomPageContentAnchorPane.setVisible(false);
        MagasinAnchorPane.setVisible(true);
        ProfileAnchorPane.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/Catalogue.fxml"));
        AnchorPane pane = loader.load();
        CatalogueController controller = loader.getController();
        MagasinAnchorPane.getChildren().setAll(pane);
    }
}

