package com.example.javafxprojectusertask.Controller.UserController.Bibliotheque;

import com.example.javafxprojectusertask.Controller.UserController.Livre.CardLivreController;
import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import com.example.javafxprojectusertask.Services.LivreService;
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
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;



public class BibliothequeManagementController {


    public static Bibliotheque b;




        @FXML
        private FlowPane BibliothequeFlowPane;

        @FXML
        private ScrollPane BibliothequeScrollPane;

        @FXML
        private AnchorPane ParentAnchor;

        private ArrayList<Bibliotheque> bibliotheques;


        @FXML
    public void initialize() throws IOException {
            if (b == null) {
                BibliothequeService service = new BibliothequeService();

                bibliotheques = service.getAll();
                for (Bibliotheque bibliotheque : bibliotheques) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/GUI/UserTemplate/Bibliotheque/cardBiliotheque.fxml"));
                    AnchorPane cardBox = loader.load();
                    CardBibliothequeController cardBibliothequeController = loader.getController();
                    cardBibliothequeController.setData(bibliotheque);
                    cardBibliothequeController.prepareCard();


                    BibliothequeFlowPane.getChildren().add(cardBox);
                }
            }

            else
            {

                LivreService service = new LivreService();
                ArrayList<Livre> livres = service.getByIdBiblio(b.getIdBib());
                BibliothequeFlowPane.getChildren().clear();
                for (Livre livre : livres) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/GUI/UserTemplate/Bibliotheque/cardLivre.fxml"));
                    AnchorPane cardBox = loader.load();
                    CardLivreController cardBibliothequeController = loader.getController();
                    cardBibliothequeController.setData(livre);
                    cardBibliothequeController.prepareCard();


                    BibliothequeFlowPane.getChildren().add(cardBox);
                }

            }
        }




}








