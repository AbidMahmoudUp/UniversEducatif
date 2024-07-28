package com.example.javafxprojectusertask.Controller.AdminController.Societe;


import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceSociete;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class EventManagmentController {

    Stage stage;

    Scene scene;

    @FXML
            private ScrollPane ScrollPaneSociete;

    ArrayList<Societe> societes;

    ServiceSociete serviceSociete = new ServiceSociete();


    @FXML
    AnchorPane parentContainer;

    @FXML
    TextField searchTF;
    @FXML
    AnchorPane anchorSociete;

    @FXML
    ChoiceBox<String> triCB;
    @FXML
    public FlowPane societeContainer;

    @FXML
    public Circle addSociete;

    @FXML
    public ImageView addIcon;
    @FXML
            public AnchorPane AnchorPaneAddSociete,DisplaySociete;

    HashMap<String,String> choices = new HashMap<>();

    @FXML
  public   void initialize() throws IOException {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        addSociete.toFront();
        addIcon.toFront();
        ScrollPaneSociete.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneSociete.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        anchorSociete.setEffect(dropShadow);
        choices.put("Nom","nomSociete");
        choices.put("Description","description");

        addSociete.setFill(Color.web("#4A8292"));
        addSociete.setStroke(null);
        AnchorPaneAddSociete.setVisible(false);
        triCB.getItems().addAll(choices.keySet());
        societes = serviceSociete.getAll();
        for(Societe societe : societes)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Societe/cardSociete.fxml"));
            AnchorPane cardBox = loader.load();
            CardController cardController = loader.getController();
            cardController.setData(societe);
            cardController.prepareCard();


            societeContainer.getChildren().add(cardBox);



            fadeInTransition(DisplaySociete);

            triCB.setOnAction(event -> {
                try
                {
                    triSociete(event);
                }catch (IOException e)
                {
                    e.getMessage();
                }
                });

            searchTF.setOnKeyReleased(keyEvent -> {
                try
                {
                    searchSociete(searchTF.getText());
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });
        }

    }


    public void searchSociete(String input) throws IOException {
        ArrayList<Societe> list;
        if(input.isEmpty() || input.isBlank() || input == null)
        {
            list = societes;
        }
        else
        {
            list = societes.stream()
                    .filter(societe -> societe.getNom().toLowerCase().contains(input.toLowerCase()) ||
                    societe.getDesc().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        }

        societeContainer.getChildren().clear();
        for(Societe societe : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Societe/cardSociete.fxml"));
            AnchorPane cardBox = loader.load();
            CardController cardController = loader.getController();
            cardController.setData(societe);
            cardController.prepareCard();

            societeContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

        }


    }
    public void triSociete(ActionEvent event) throws IOException {
        String s = triCB.getValue();
        societes = serviceSociete.getAllOrdered(choices.get(s),"ASC");


        societeContainer.getChildren().clear();
        for(Societe societe : societes)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Societe/cardSociete.fxml"));
            AnchorPane cardBox = loader.load();
            CardController cardController = loader.getController();
            cardController.setData(societe);
            cardController.prepareCard();


            societeContainer.getChildren().add(cardBox);


        }

    }
    private void fadeInTransition(AnchorPane anchorPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), anchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    @FXML
    public void showPopUpAddSociete(MouseEvent event)
    {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Societe/PopUpAddSociete.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Societe/PopUpAddSociete.fxml"));
            loader.load();
            PopUpAddSocieteController controller = loader.getController();
            AnchorPaneAddSociete.getChildren().setAll(pane);
            AnchorPaneAddSociete.setVisible(true);
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    public void showOffreManagment(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));

            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            dashBordController.SwitchToOffre();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser=userUtils.getConnectedUser();
            dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void showDossierManagment(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));

            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            dashBordController.SwitchToDossier();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser=userUtils.getConnectedUser();
            dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
