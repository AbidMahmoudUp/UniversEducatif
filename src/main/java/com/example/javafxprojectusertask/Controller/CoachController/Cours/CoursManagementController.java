package com.example.javafxprojectusertask.Controller.CoachController.Cours;

import com.example.javafxprojectusertask.Controller.AdminController.Module.ModuleManagementController;
import com.example.javafxprojectusertask.Entities.Cour;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileCoach;
import com.example.javafxprojectusertask.Services.CoachProfileService;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.ServiceCour;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoursManagementController {


    @FXML
    private ImageView addCour;
    @FXML
    private ScrollPane ScrollPaneCours;
    @FXML
    private Circle circleAdd;
    @FXML
    private TextField chercher;
    @FXML
    private AnchorPane DisplayCours;

    public static int idModule;

    public ImageView getAddCour() {
        return addCour;
    }

    public Circle getCircleAdd() {
        return circleAdd;
    }

    @FXML
    private FlowPane flowPaneCours;

    @FXML
    private AnchorPane popUpAddCours;
    @FXML
    private AnchorPane ParentAnchor;
    public  static  AnchorPane pop;

    @FXML
    void openPopUp(MouseEvent event) throws IOException {
        popUpAddCours.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/AddCour.fxml"));
        AnchorPane pane = loader.load();
        AddCourController addCourController = loader.getController();
        addCourController.setCoursManagementController(this);
        popUpAddCours.getChildren().setAll(pane);
    }

    public void SetDataCour() throws IOException {
        ServiceCour sc = new ServiceCour();
        flowPaneCours.getChildren().clear();

        ArrayList<Cour> cours = sc.getAll();
        if(cours.size()!=0)
        {
        for (Cour cour : cours) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/CardCour.fxml"));
            AnchorPane pane = loader.load();
            CardCourController cardCourController = loader.getController();
            cardCourController.initializeData(cour,this);
            flowPaneCours.getChildren().add(pane);
        }}
    }
    private void afficherCourCardbyIdModule(List<Cour> cours) throws IOException {
        flowPaneCours.getChildren().clear();
        for (Cour cour : cours) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/CardCour.fxml"));
            AnchorPane pane = loader.load();
            CardCourController cardCourController = loader.getController();
            cardCourController.initializeData(cour);
            cardCourController.getDeleteIcon().setVisible(true);
            cardCourController.getUpdateIcon().setVisible(true);
            if(UserUtils.ConnectedUser.getStatus().equals("Admin"))
            {
                cardCourController.getDeleteIcon().setVisible(false);
                cardCourController.getUpdateIcon().setVisible(false);
            }
            flowPaneCours.getChildren().add(pane);
            ScrollPaneCours.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            ScrollPaneCours.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
    }

    public void chercherCourByIdModule(int id) throws IOException {
        ServiceCour serviceCour= new ServiceCour();
        final List<Cour> cours = serviceCour.getAll();
       // System.out.println(cours.get(0).getTempsEstimeC());
            List<Cour> filteredList = cours.stream()
                    .filter(item -> item.getID_Module()==id)
                    .collect(Collectors.toList());
            this.afficherCourCardbyIdModule(filteredList);
    }
    @FXML
    void chercherCour(KeyEvent event) throws IOException {
        ServiceCour serviceCour= new ServiceCour();
        final List<Cour> cours = serviceCour.getAll();
        final String searchParam = chercher.getText();
        if(searchParam != null && !searchParam.isEmpty()) {
            List<Cour> filteredList = cours.stream()
                    .filter(item -> item.getNom().contains(searchParam) ||
                            item.getDescription().contains(searchParam) ||
                            String.valueOf(item.getTempsEstimeC()).contains(searchParam))
                    .collect(Collectors.toList());
            this.searchCourCard(filteredList);
        } else {
            this.searchCourCard(cours);
        }
    }
    private void searchCourCard(List<Cour> cours) throws IOException {
        flowPaneCours.getChildren().clear();
        for (Cour cour : cours) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/CardCour.fxml"));
            AnchorPane pane = loader.load();
            CardCourController cardCourController = loader.getController();
            cardCourController.initializeData(cour);
            flowPaneCours.getChildren().add(pane);
        }
    }
    @FXML
    void triAsc(MouseEvent event) throws IOException {
        ServiceCour serviceCour= new ServiceCour();
        flowPaneCours.getChildren().clear();
        ArrayList<Cour> cours = serviceCour.triByTempsEstimeC("ASC");
        for (Cour cour : cours) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/CardCour.fxml"));
            AnchorPane pane = loader.load();
            CardCourController cardCourController = loader.getController();
            cardCourController.initializeData(cour);
            flowPaneCours.getChildren().add(pane);
        }
    }

    @FXML
    void triDesc(MouseEvent event) throws IOException {
        ServiceCour serviceCour= new ServiceCour();
        flowPaneCours.getChildren().clear();
        ArrayList<Cour> cours = serviceCour.triByTempsEstimeC("DESC");
        for (Cour cour : cours) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/CardCour.fxml"));
            AnchorPane pane = loader.load();
            CardCourController cardCourController = loader.getController();
            cardCourController.initializeData(cour);
            flowPaneCours.getChildren().add(pane);
        }
    }





    @FXML
    void initialize() throws IOException {
        assert addCour != null : "fx:id=\"addCour\" was not injected: check your FXML file 'CoursManagement.fxml'.";
        assert circleAdd != null : "fx:id=\"circleAdd\" was not injected: check your FXML file 'CoursManagement.fxml'.";
        assert flowPaneCours != null : "fx:id=\"flowPaneCours\" was not injected: check your FXML file 'CoursManagement.fxml'.";
        assert popUpAddCours != null : "fx:id=\"popUpAddCours\" was not injected: check your FXML file 'CoursManagement.fxml'.";
        ParentAnchor.setVisible(true);
        circleAdd.toFront();
        addCour.toFront();
        popUpAddCours.setVisible(false);
        ParentAnchor.setVisible(true);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        DisplayCours.setEffect(dropShadow);
        ScrollPaneCours.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneCours.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        flowPaneCours.setPadding(new Insets(10, 10, 10, 10));
        flowPaneCours.setHgap(10);
        flowPaneCours.setVgap(10);
        UserUtils userUtils = new UserUtils();
                UserUtils.ConnectedUser=userUtils.getConnectedUser();
        System.out.println(UserUtils.ConnectedUser.getStatus());
        try {
            if(UserUtils.ConnectedUser.getStatus().equalsIgnoreCase("coach"))
            {
                getAddCour().setVisible(true);
                getCircleAdd().setVisible(true);
                ProfileService service = new ProfileService();
                Profile p = service.getProfileUser(UserUtils.ConnectedUser.getIdUser());
                CoachProfileService service1 = new CoachProfileService();
                ProfileCoach p1 = service1.getCoachProfile(p);
                this.chercherCourByIdModule(p1.getIdModule());
            }
            else
            {
                this.chercherCourByIdModule(idModule);
                getAddCour().setVisible(false);
                getCircleAdd().setVisible(false);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetIdModule()
    {
        idModule = -1;
    }

    public void replace(AnchorPane pane) {
        ModuleManagementController.ModuleManagement.getChildren().clear();
        ModuleManagementController.ModuleManagement.getChildren().setAll(pane);
    }
}
