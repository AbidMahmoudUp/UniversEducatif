package com.example.javafxprojectusertask.Controller.UserController.Module;

import com.example.javafxprojectusertask.Controller.UserController.Cours.CardCourController;
import com.example.javafxprojectusertask.Controller.AdminController.Module.ModuleManagementController;
import com.example.javafxprojectusertask.Controller.UserController.Cours.CourDetailsController;
import com.example.javafxprojectusertask.Entities.Cour;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Entities.Suivi;
import com.example.javafxprojectusertask.Services.ServiceCour;
import com.example.javafxprojectusertask.Services.ServiceSuivi;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CardModuleControllerUser {
        @FXML
        private ImageView ImgModulePath;

        @FXML
        private Label descriptionModule;

        @FXML
        private Label nomModule;
        @FXML
        private AnchorPane ParentAnchorCard;
    public static AnchorPane anchorPane;



    @FXML
        private Label tempsM;
    private FlowPane coursFlowPane;
    private Module module;

        Stage stage;

        Scene scene;
        private ModuleManagementController moduleManagementController = new ModuleManagementController();
        public void setModule(Module module) {
            this.module = module;
        }

        public Module getModule() {
            return module;
        }

        public void initializeData(Module m, FlowPane coursFlowPane){

            DropShadow dropShadow = new DropShadow();

            dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
            dropShadow.setRadius(10);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(5);
            ParentAnchorCard.setEffect(dropShadow);
            this.coursFlowPane=coursFlowPane;
        nomModule.setText(m.getNom());
            descriptionModule.setText(m.getDescription());
            int t = m.getTempsEstimeM();
            tempsM.setText("" + t);
            String IP = m.getModuleImgPath();

            File f = new File(IP);
            if (f.exists()) {
                Image img = new Image(f.toURI().toString());
                ImgModulePath.setImage(img);
            } else {
                System.out.println("L'image n'existe pas : " + IP);
            }
        }
    private void afficherCourCardbyIdModule(List<Cour> cours) throws IOException {
      //  FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/User/courPage.fxml"));
      //  loader1.load();
      //  WelcomePageController welcomePageController = loader1.getController();
        coursFlowPane.getChildren().clear();
        CardCourController.anchorPane=anchorPane;
        for (Cour cour : cours) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/CardCour.fxml"));
            AnchorPane pane = loader.load();
            CardCourController cardCourController = loader.getController();
            cardCourController.initializeData(cour);
            FXMLLoader loaderDetails = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/coursDetails.fxml"));
           CourDetailsController.cour=cour;
           // loaderDetails.load();
            CourDetailsController courDetailsController=loaderDetails.getController();


            coursFlowPane.getChildren().add(pane);
           // welcomePageController.getDetailsFlowPane().setVisible(true);
           // welcomePageController.getDetailsFlowPane().getChildren().add(pane);
        }
    }

        @FXML
        void voirCours(MouseEvent event) {
            try {
                ServiceCour serviceCour= new ServiceCour();
                final List<Cour> cours = serviceCour.getAll();
                List<Cour> filteredList = cours.stream()
                        .filter(item -> item.getID_Module()==module.getIdModule())
                        .collect(Collectors.toList());
                this.afficherCourCardbyIdModule(filteredList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LocalDate dateSysteme = LocalDate.now();
            Date dateSql = Date.valueOf(dateSysteme);
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser = userUtils.getConnectedUser();
            Suivi suivi =new Suivi(UserUtils.ConnectedUser.getIdUser(), this.module.getIdModule(),dateSql);
            ServiceSuivi serviceSuivi =new ServiceSuivi();
            if (serviceSuivi.aDejaConsulteModule(suivi.getIdUser(), suivi.getIdModule())){
                serviceSuivi.update(suivi);
            }
            else {
            serviceSuivi.add(suivi);}
        }
    @FXML
   public void voirCours() {
        Platform.runLater(() -> {
            try {
                ServiceCour serviceCour= new ServiceCour();
                final List<Cour> cours = serviceCour.getAll();
                List<Cour> filteredList = cours.stream()
                        .filter(item -> item.getID_Module()==module.getIdModule())
                        .collect(Collectors.toList());
                this.afficherCourCardbyIdModule(filteredList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LocalDate dateSysteme = LocalDate.now();
            Date dateSql = Date.valueOf(dateSysteme);
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser = userUtils.getConnectedUser();
            Suivi suivi =new Suivi(UserUtils.ConnectedUser.getIdUser(), this.module.getIdModule(),dateSql);
            ServiceSuivi serviceSuivi =new ServiceSuivi();
            if (serviceSuivi.aDejaConsulteModule(suivi.getIdUser(), suivi.getIdModule())){
                serviceSuivi.update(suivi);
            }
            else {
                serviceSuivi.add(suivi);}
                }

        );}}




