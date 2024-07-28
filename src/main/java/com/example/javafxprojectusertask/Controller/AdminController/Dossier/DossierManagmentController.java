package com.example.javafxprojectusertask.Controller.AdminController.Dossier;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Services.ServiceDossier;
import com.example.javafxprojectusertask.Utilities.UserUtils;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DossierManagmentController {

    Dossier d;

    ArrayList<Dossier> dossiers = new ArrayList<>();

    ServiceDossier serviceDossier = new ServiceDossier();

    @FXML
    TextField searchTF;

    @FXML
    ChoiceBox<String> triCB;

    @FXML
    ScrollPane ScrollPaneDossier;

    @FXML
    AnchorPane displayDossier;
    HashMap<String,String> choices = new HashMap<>();

    @FXML
    public FlowPane dossierContainer;


    int idUser = -1;

    public void setUser(int id) throws IOException {
        idUser = id;
        initialize();
    }

    @FXML
  public   void initialize() throws IOException {
        dossierContainer.setPadding(new Insets(24,24,24,20));
        choices.put("Accepté","Accepté");
        choices.put("Refusé","Refusé");
        choices.put("En attente", "En attente");
        triCB.getItems().addAll(choices.keySet());
        if(idUser == -1) {
            dossiers = serviceDossier.getAll();
            System.out.println("HERE");
        }
        else{
            System.out.println("IT WORKED");
            dossiers = serviceDossier.getDossierByUser(idUser);
        }
        System.out.println(idUser);

        int column = 0;
        int row = 1;

        for(Dossier dossier : dossiers)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Dossier/cardDossier.fxml"));
            AnchorPane cardBox = loader.load();
            //System.out.println("ttttt");
            DossierCardController cardController = loader.getController();
            System.out.println("DDDDDD");
            System.out.println(dossier);
            cardController.setData(dossier);
            System.out.println("DDDD");
            cardController.prepareCard();
            System.out.println("FGGGG");

            if(column == 4)
            {
                column = 0;
                row++;
            }
            dossierContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

        }

        triCB.setOnAction(event -> {
            try{
                filter(event);
            }catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        });

        searchTF.setOnKeyReleased(keyEvent -> {
            try
            {
                searchDossier(searchTF.getText());
            }catch (IOException e)
            {
                e.getMessage();
            }
        });

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        displayDossier.setEffect(dropShadow);
        ScrollPaneDossier.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneDossier.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void searchDossier(String input) throws IOException
    {
        ArrayList<Dossier> list;
        if(input.isEmpty() || input.isBlank() || input == null)
        {
            list = dossiers;
        }
        else
        {
            list = dossiers.stream()
                    .filter(dossier -> dossier.getStatus().toLowerCase().contains(input.toLowerCase()) ||
                            dossier.getOffre().getDesc().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        }



        int column = 0;
        int row = 1;
        dossierContainer.getChildren().clear();
        for(Dossier dossier : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Dossier/cardDossier.fxml"));
            VBox cardBox = loader.load();
            //System.out.println("ttttt");
            DossierCardController cardController = loader.getController();
            System.out.println("DDDDDD");
            System.out.println(dossier);
            cardController.setData(dossier);
            System.out.println("DDDD");
            cardController.prepareCard();
            System.out.println("FGGGG");

            if(column == 2)
            {
                column = 0;
                row++;
            }
            dossierContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

        }

    }

    public void filter(ActionEvent event) throws IOException {
        ArrayList<Dossier> list = serviceDossier.getFiltered(triCB.getValue());
        dossierContainer.getChildren().clear();
        int column = 0;
        int row = 1;

        for(Dossier dossier : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Dossier/cardDossier.fxml"));
            VBox cardBox = loader.load();
            //System.out.println("ttttt");
            DossierCardController cardController = loader.getController();
            System.out.println("DDDDDD");
            System.out.println(dossier);
            cardController.setData(dossier);
            System.out.println("DDDD");
            cardController.prepareCard();
            System.out.println("FGGGG");

            if(column == 2)
            {
                column = 0;
                row++;
            }
            dossierContainer.getChildren().add(cardBox);

            GridPane.setMargin(cardBox, new Insets(10));
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
    public void showEventManagment(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));

            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            dashBordController.SwitchToEvent(event);
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
