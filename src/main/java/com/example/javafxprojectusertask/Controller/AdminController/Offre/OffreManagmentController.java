package com.example.javafxprojectusertask.Controller.AdminController.Offre;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OffreManagmentController {


    ArrayList<Offre> offres;

    public static Societe s;
    ServiceOffre serviceOffre = new ServiceOffre();

    @FXML
    TextField searchTF;

    @FXML
    ChoiceBox<String> triCB;

    HashMap<String,String> choices = new HashMap<>();

    @FXML
    AnchorPane AnchorPaneAddOffre;

    @FXML
    public FlowPane offreContainer;

    @FXML
    public Circle addOffre;
    @FXML
    public ImageView addIcon;
    @FXML
    public AnchorPane displayOffre;
    @FXML
    public ScrollPane ScrollPaneOffre;

    @FXML
    public void initialize() throws IOException {
        tryLoad();
        addOffre.setFill(Color.web("#4A8292"));
        addOffre.setStroke(null);
        addOffre.toFront();
        addIcon.toFront();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        displayOffre.setEffect(dropShadow);
        ScrollPaneOffre.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneOffre.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        offreContainer.setPadding(new Insets(10,17,10,17));

        offreContainer.setStyle("-fx-background-color: white");
        ScrollPaneOffre.setStyle("-fx-background-color: white");

    }

    public void showPopUpAddOffre(MouseEvent event)
    {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Offre/PopUpAddOffre.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Offre/PopUpAddOffre.fxml"));
            loader.load();
            PopUpAddOffreController controller = loader.getController();
            AnchorPaneAddOffre.getChildren().setAll(pane);
            AnchorPaneAddOffre.setVisible(true);
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }

    }

    public void tryLoad() throws IOException {
        choices.put("Niveau","niveau");
        choices.put("Description","description");
        choices.put("Date de debut","dateDebut");
        choices.put("Date Fin","dateFin");
        triCB.setItems(FXCollections.observableArrayList(choices.keySet()));
        if(s == null) {
            offres = serviceOffre.getAll();
            System.out.println("HERE");
        }
        else{
            System.out.println("IT WORKED");
            offres = serviceOffre.getBySociete(s);
        }
        System.out.println(s);

        int column = 0;
        int row = 1;

        for(Offre offre : offres)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/cardOffre.fxml"));
            AnchorPane cardBox = loader.load();
            OffreCardController cardController = loader.getController();
            cardController.setData(offre);
            cardController.prepareCard();

            if(column == 2)
            {
                column = 0;
                row++;
            }
            offreContainer.getChildren().add(cardBox);



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

    public void searchSociete(String input) throws IOException{
        ArrayList<Offre> list;
        if(input.isEmpty() || input.isBlank() || input == null)
        {
            list = offres;
        }
        else
        {
            list = offres.stream()
                    .filter(offre -> offre.getDateDeb().toString().toLowerCase().contains(input.toLowerCase()) ||
                            offre.getDesc().toLowerCase().contains(input.toLowerCase()) ||
                            Integer.toString(offre.getNiveau()).contains(input)||
                            offre.getDateFin().toString().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        }

        int column = 0;
        int row = 1;

        offreContainer.getChildren().clear();
        for(Offre offre : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/cardOffre.fxml"));
            AnchorPane cardBox = loader.load();
            OffreCardController cardController = loader.getController();
            cardController.setData(offre);
            cardController.prepareCard();

            if(column == 2)
            {
                column = 0;
                row++;
            }
            offreContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

        }
    }

    public void triSociete(ActionEvent event) throws IOException {
        String s = triCB.getValue();
        offres = serviceOffre.getAllOrdered(choices.get(s),"ASC");
        int column = 0;
        int row = 1;

        offreContainer.getChildren().clear();
        for(Offre offre : offres)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/cardOffre.fxml"));
            AnchorPane cardBox = loader.load();
            OffreCardController cardController = loader.getController();
            cardController.setData(offre);
            cardController.prepareCard();

            if(column == 2)
            {
                column = 0;
                row++;
            }
            offreContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

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
