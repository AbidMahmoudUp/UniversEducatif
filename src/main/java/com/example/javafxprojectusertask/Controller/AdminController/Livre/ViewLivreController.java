package com.example.javafxprojectusertask.Controller.AdminController.Livre;


import com.example.javafxprojectusertask.Entities.Livre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.LivreService;


import java.io.IOException;
import java.util.List;

public class ViewLivreController {
    @FXML
    public TableColumn colidLivre;
    @FXML
    public TableColumn coltype;
    @FXML
    public TableColumn coltitre;
    @FXML
    public TableColumn colauteur;
    @FXML
    public TableColumn collangue;
    @FXML
    public TableView tableview;
    private final LivreService LivreService = new LivreService();
    public TextField textsupp_livre;
    public Button button_supp_livre;
    public Button buttonbibliotheque;
    public Button buttonlivre;
    public Button butttonview;
    public Button buttonajouter;
    public Button buttonmodifier;
    public TableColumn colidBib1;
    //public ChoiceBox nomChoiceBox;
    public ChoiceBox<String> nomChoiceBox;

    @FXML
    void GoToBibliotheque(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/ajouter.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) buttonbibliotheque.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void GoTomodifier(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/modifierlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) buttonmodifier.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void GoToAjouter(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/ajouterlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) buttonajouter.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        // Initialise les colonnes du TableView
        colidLivre.setCellValueFactory(new PropertyValueFactory<>("idLivre"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coltitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colauteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        collangue.setCellValueFactory(new PropertyValueFactory<>("langue"));
        colidBib1.setCellValueFactory(new PropertyValueFactory<>("idBib"));
        // Charge les données dans le TableView à partir du service
        loadLivre();
        ObservableList<String> langueList = FXCollections.observableArrayList("francais", "arabe", "anglais", "espagnol", "allemand","italien");
        nomChoiceBox.setItems(langueList);
        nomChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterByLangue(newValue);
            }
        });
    }
    public void loadLivre() {
        List<Livre> livres = LivreService.getAll();
        ObservableList<Livre> observableList = FXCollections.observableArrayList(livres);
        tableview.setItems(observableList);
    }
    @FXML
    public void supprimerlivre(ActionEvent actionEvent) {
        try {
            int idLivre = Integer.parseInt(textsupp_livre.getText());
            LivreService as = new LivreService();
            Livre l = as.readById(idLivre);
            if (l != null) {
                as.delete(l);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/viewlivre.fxml"));
                    javafx.scene.Parent root = loader.load();

                    Stage stage = (Stage) button_supp_livre.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("le livre avec l'ID " + idLivre + " n'existe pas.");
            }
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en nombre échoue
            e.printStackTrace();
        }
    }
    private void filterByLangue(String langue) {
        ObservableList<Livre> livres = tableview.getItems();
        ObservableList<Livre> livresFiltres = FXCollections.observableArrayList();
        for (Livre livre : livres) {
            if (livre.getLangue().equals(langue)) {
                livresFiltres.add(livre);
            }
        }
        tableview.setItems(livresFiltres);
    }



}