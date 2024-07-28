package com.example.javafxprojectusertask.Controller.AdminController.Livre;

import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.LivreService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class LivreManagementController {



    public static Bibliotheque b;


    private final LivreService service = new LivreService();


    @FXML
    AnchorPane parentContainer;

    @FXML
    TextField searchTF;
    @FXML
    AnchorPane anchorBibliotheque;

    @FXML
    ChoiceBox<String> triCB;
    @FXML
    public FlowPane livreContainer;
    
    @FXML
    public ScrollPane ScrollPaneLivre;

    @FXML
    public Circle addLivre;

    @FXML
    public ImageView addIcon;
    @FXML
    public AnchorPane AnchorPaneAddLivre,DisplayLivre;
    ArrayList<Livre> livres;

    HashMap<String,String> choices = new HashMap<>();

    @FXML
    public   void initialize() throws IOException {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
         addLivre.toFront();
        //addIcon.toFront();
        ScrollPaneLivre.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneLivre.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        anchorBibliotheque.setEffect(dropShadow);
        choices.put("Nom","nombibliotheque");
        choices.put("Description","description");

        //addbibliotheque.setFill(Color.web("#4A8292"));
        //addbibliotheque.setStroke(null);
        //AnchorPaneAddLivre.setVisible(false);
        triCB.getItems().addAll(choices.keySet());
        livres = service.getByIdBiblio(b.getIdBib());
        for(Livre livre : livres)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Livre/CardLivre.fxml"));
            AnchorPane cardBox = loader.load();
            CardLivreController cardController = loader.getController();
            cardController.setData(livre);
            cardController.prepareCard();


            livreContainer.getChildren().add(cardBox);



            fadeInTransition(DisplayLivre);

            triCB.setOnAction(event -> {
                try
                {
                    triLivre(event);
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });

            searchTF.setOnKeyReleased(keyEvent -> {
                try
                {
                    searchLivre(searchTF.getText());
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });
        }

    }


    public void searchLivre(String input) throws IOException {
        ArrayList<Livre> list;
        if(input.isEmpty() || input.isBlank() || input == null)
        {
            list = livres;
        }
        else
        {
            list = livres.stream()
                    .filter(livre -> livre.getAuteur().toLowerCase().contains(input.toLowerCase()) ||
                            livre.getTitre().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        }

        livreContainer.getChildren().clear();
        for(Livre livre : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Livre/CardLivre.fxml"));
            AnchorPane cardBox = loader.load();
            CardLivreController cardLivreController = loader.getController();
            cardLivreController.setData(livre);
            cardLivreController.prepareCard();

            livreContainer.getChildren().add(cardBox);
            GridPane.setMargin(cardBox, new Insets(10));

        }


    }
    public void triLivre(ActionEvent event) throws IOException {
       /* String s = triCB.getValue();
        livres = service.getAllOrdered(choices.get(s),"ASC");


        livreContainer.getChildren().clear();
        for(Bibliotheque bibliotheque : livres)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/cardbibliotheque.fxml"));
            AnchorPane cardBox = loader.load();
            CardController cardController = loader.getController();
            cardController.setData(bibliotheque);
            cardController.prepareCard();


            livreContainer.getChildren().add(cardBox);


        }*/

    }
    private void fadeInTransition(AnchorPane anchorPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), anchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    @FXML
    public void showPopUpAddLivre(MouseEvent event)
    {
        try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Livre/PopUpAddLivre.fxml"));
            AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Livre/PopUpAddLivre.fxml"));
            loader.load();
            PopUpAddLivreController controller = loader.getController();

            AnchorPaneAddLivre.setVisible(true);
            AnchorPaneAddLivre.getChildren().setAll(pane);

        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }


    }



}








