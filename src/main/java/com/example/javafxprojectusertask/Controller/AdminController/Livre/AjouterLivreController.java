package com.example.javafxprojectusertask.Controller.AdminController.Livre;

import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import com.example.javafxprojectusertask.Services.LivreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class AjouterLivreController {
    public Button bibliotheque_button;
    public Button ajoutlivre;
    public TextField txttype;
    public TextField txttitre;
    public TextField txtauteur;
    public TextField txtlangue;
    public TextField txtidLivre;
    public Button button_modifier;
    public Button button_view;
    public TextField textidBib;


    int idBib;

    @FXML
    void GoToBibliotheque(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/ajouter.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) bibliotheque_button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void GoToViewLivre(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/viewlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) button_view.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void GoToModifierLivre(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/modifierlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) button_modifier.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    public void ajouterlivre(ActionEvent actionEvent) {
        try {
         //  int idLivre = Integer.parseInt(txtidLivre.getText());
            String type = txttype.getText();
            String titre = txttitre.getText();
            String auteur = txtauteur.getText();
            String langue = txtlangue.getText();
            int idBib = Integer.parseInt(textidBib.getText());

            // Obtenez l'objet Centre correspondant à l'idBib
            BibliothequeService bibliothequeService = new BibliothequeService(); // Suppose que vous avez un service pour manipuler les bibliotheques
            Bibliotheque bibliotheque = bibliothequeService.getBibliothequeById(idBib);

            // Créez l'objet Livre avec l'objet Bibliotheque obtenu
            Livre l = new Livre(idBib, type, titre, auteur, langue,bibliotheque);

            // Ajoutez livre en utilisant le service LivreService
            LivreService as = new LivreService();
            as.add(l);
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en nombre échoue
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
