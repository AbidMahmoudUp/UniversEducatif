package com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque;

import com.example.javafxprojectusertask.Entities.Bibliotheque;

import com.example.javafxprojectusertask.Services.BibliothequeService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class BibliothequeManagementController {




    private final BibliothequeService service = new BibliothequeService();


    @FXML
    AnchorPane parentContainer;

    @FXML
    TextField searchTF;
    @FXML
    AnchorPane anchorBibliotheque;

    @FXML
    ChoiceBox<String> triCB;
    @FXML
    public FlowPane bibliothequeContainer;
    
    @FXML
    public ScrollPane ScrollPaneBibliotheque;

    @FXML
    public Circle addBiblio;

    @FXML
    public ImageView addIcon;
    @FXML
    public AnchorPane AnchorPaneAddbibliotheque,DisplayBibliotheque;
    ArrayList<Bibliotheque> bibliotheques;

    HashMap<String,String> choices = new HashMap<>();

    @FXML
    public   void initialize() throws IOException {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        addBiblio.toFront();
        //addIcon.toFront();
        ScrollPaneBibliotheque.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneBibliotheque.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        anchorBibliotheque.setEffect(dropShadow);
        choices.put("Nom","nombibliotheque");
        choices.put("Description","description");

        //addbibliotheque.setFill(Color.web("#4A8292"));
        //addbibliotheque.setStroke(null);
        //AnchorPaneAddbibliotheque.setVisible(false);
        triCB.getItems().addAll(choices.keySet());
        bibliotheques = service.getAll();
        for(Bibliotheque bibliotheque : bibliotheques)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/CardBibliotheque.fxml"));
            AnchorPane cardBox = loader.load();
            CardBibliothequeController cardBibliothequeController = loader.getController();
            cardBibliothequeController.setData(bibliotheque);
            cardBibliothequeController.prepareCard();


            bibliothequeContainer.getChildren().add(cardBox);



            fadeInTransition(DisplayBibliotheque);

            triCB.setOnAction(event -> {
                try
                {
                    tribibliotheque(event);
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });

            searchTF.setOnKeyReleased(keyEvent -> {
                try
                {
                    searchbibliotheque(searchTF.getText());
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });
        }

    }


    public void searchbibliotheque(String input) throws IOException {
        ArrayList<Bibliotheque> list;
        if(input.isEmpty() || input.isBlank() || input == null)
        {
            list = bibliotheques;
        }
        else
        {
            list = bibliotheques.stream()
                    .filter(bibliotheque -> bibliotheque.getNom().toLowerCase().contains(input.toLowerCase()) ||
                            bibliotheque.getMail().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        }

        bibliothequeContainer.getChildren().clear();
        for(Bibliotheque bibliotheque : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/CardBibliotheque.fxml"));
            AnchorPane cardBox = loader.load();
            CardBibliothequeController cardBibliothequeController = loader.getController();
            cardBibliothequeController.setData(bibliotheque);
            cardBibliothequeController.prepareCard();

            bibliothequeContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

        }


    }
    public void tribibliotheque(ActionEvent event) throws IOException {
       /* String s = triCB.getValue();
        bibliotheques = service.getAllOrdered(choices.get(s),"ASC");


        bibliothequeContainer.getChildren().clear();
        for(Bibliotheque bibliotheque : bibliotheques)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/cardbibliotheque.fxml"));
            AnchorPane cardBox = loader.load();
            CardController cardController = loader.getController();
            cardController.setData(bibliotheque);
            cardController.prepareCard();


            bibliothequeContainer.getChildren().add(cardBox);


        }*/

    }
    private void fadeInTransition(AnchorPane anchorPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), anchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    @FXML
    public void showPopUpAddbibliotheque(MouseEvent event)
    {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/PopUpAddBibliotheque.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/PopUpAddBibliotheque.fxml"));
            loader.load();
            PopUpAddBibliothequeController controller = loader.getController();
            AnchorPaneAddbibliotheque.setVisible(true);
            AnchorPaneAddbibliotheque.getChildren().setAll(pane);

        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }


    }



}








