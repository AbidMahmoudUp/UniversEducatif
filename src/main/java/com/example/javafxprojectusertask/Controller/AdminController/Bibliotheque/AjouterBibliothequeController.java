package com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque;


import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class AjouterBibliothequeController {
    public Button button_mod;
    public Button livre_button;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtmail;

    @FXML
    private TextField txtnbrLivre;
    @FXML
    private Button AjouterButton;
    @FXML
    void GoToLivre(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/livre/ajouterlivre.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) livre_button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void GoToAjoutU(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/View.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) AjouterButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void GoToMod(ActionEvent event) {
        try {
            // Load the Event.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bibliotheque/modifier.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the AfficherUser.fxml scene
            Stage stage = (Stage) button_mod.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void ajouterbibliotheque(ActionEvent actionEvent) {
        try {
          int idBib = 0;
            String nom = txtnom.getText();
            String mail = txtmail.getText();
            int nbrLivre = Integer.parseInt(txtnbrLivre.getText());


            // Vérifier si le nom de bibliotheque existe déjà dans la base de données
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            // Vérification de l'email
            if (mail.matches(regex)) {
                Bibliotheque b = new Bibliotheque(idBib, nom,mail,  nbrLivre);
                BibliothequeService cs = new BibliothequeService();
                cs.add(b);
            } else {
                // Email invalide
                showAlert("Adresse email invalide !","Adresse email invalide !","Adresse email invalide !");
            }
            // Vérifier si le nom de bibliotheque existe déjà dans la base de données
           BibliothequeService bibliothequeService = new BibliothequeService();
            if (bibliothequeService.checkIfBibliothequeExists(nom)) {
                // Afficher une fenêtre d'alerte si le nom de bibliotheque existe déjà
                showAlert("Erreur", "Le nom de bibliotheque existe déjà.", "Veuillez saisir un nom de bibliotheque différent.");
            } else {
                Bibliotheque b = new Bibliotheque(idBib, nom,  mail, nbrLivre);
                BibliothequeService cs = new BibliothequeService();
                cs.add(b);
            }


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
