package com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque;

import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class ModifierBibliothequeController {
    public TextField txtidBib_mod;
    public TextField txtnom_mod;
    public TextField txtmail_mod;
    public TextField txtnbLivre_mod;
    public Button button_ajout;
    public Button button_view;
    public Button buttonlivre;

    @FXML
    void GoToLivre(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/ajouterlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) buttonlivre.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void GoToView(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/View.fxml"));
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
    void GoToAjout(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/ajouter.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) button_ajout.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void modifier(ActionEvent actionEvent) {
        try {
            int idBib = Integer.parseInt(txtidBib_mod.getText());
            String nom = txtnom_mod.getText();
            String mail = txtmail_mod.getText();
            int nbrLivre = Integer.parseInt(txtnbLivre_mod.getText());


            // Ajoutez les livres récupérées à votre liste des livres

            Bibliotheque b = new Bibliotheque(idBib, nom, mail, nbrLivre);
            BibliothequeService cs = new BibliothequeService();
            cs.update(b);
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en nombre échoue
            e.printStackTrace();
        }
    }


}
