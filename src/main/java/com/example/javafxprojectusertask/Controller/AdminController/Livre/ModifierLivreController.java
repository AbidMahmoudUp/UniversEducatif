package com.example.javafxprojectusertask.Controller.AdminController.Livre;

import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import com.example.javafxprojectusertask.Services.LivreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class ModifierLivreController {
    public TextField txt_idLivre;
    public TextField texttype;
    public TextField texttitre;
    public TextField textauteur;
    public TextField textlangue;
    public Button button_livre_mod;
    public Button button_bibliotheque;
    public Button view_button;
    public Button ajouter_button;
    public TextField txtidBib;

    @FXML
    void GoToBibliotheque(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/ajouter.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) button_bibliotheque.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void GoToAjouterLivre(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/ajouterlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) view_button.getScene().getWindow();
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
            Stage stage = (Stage) ajouter_button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void modifierlivre(ActionEvent actionEvent) {
        try {
            int idLivre = Integer.parseInt(txt_idLivre.getText());
            String type = texttype.getText();
            String titre = texttitre.getText();
            String auteur = textauteur.getText();
            String langue = textlangue.getText();
            int idBib = Integer.parseInt(txtidBib.getText());
            BibliothequeService bibliothequeService = new BibliothequeService(); // Suppose que vous avez un service pour manipuler les bibliotheques
            Bibliotheque bibliotheque = bibliothequeService.getBibliothequeById(idBib);

            // Créez l'objet Activite avec l'objet Centre obtenu
            Livre l = new Livre(idLivre, type, titre, auteur,langue, bibliotheque);

            // Ajoutez livre en utilisant le service LivreService
            LivreService as = new LivreService();
            as.update(l);
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en nombre échoue
            e.printStackTrace();
        }
    }

}


