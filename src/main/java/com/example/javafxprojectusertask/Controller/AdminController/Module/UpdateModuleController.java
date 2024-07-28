package com.example.javafxprojectusertask.Controller.AdminController.Module;

import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateModuleController implements Initializable {


    @FXML
    private AnchorPane UpdatePopUpModule;

    @FXML
    private ImageView closeArea;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextArea description;

    @FXML
    private TextField nom;

    @FXML
     ImageView photo;

    @FXML
    private TextField photoModulepath;
    private Module module;

    public void setModule(Module module) {
        this.module = module;
        nom.setText(this.module.getNom());
        description.setText(this.module.getDescription());
        photoModulepath.setText(this.module.getModuleImgPath());
    }

    @FXML
    void ModifierModule(MouseEvent event) {
        String nomText = nom.getText();
        String descriptionText = description.getText();
        String photoModulepathText = photoModulepath.getText();

        if (nomText.isEmpty() || descriptionText.isEmpty() || photoModulepathText.isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez saisir tous les champs.");
            return;
        }

        if (!nomText.matches("[a-zA-Z ]+")) {
            showAlert("Erreur de saisie", "Le champ nom ne doit contenir que des lettres et des espaces.");
            return;
        }

        // Mise à jour du module
        ServiceModule sm = new ServiceModule();
        this.module.setNom(nomText);
        this.module.setDescription(descriptionText);
        this.module.setModuleImgPath(photoModulepathText);
        sm.update(this.module);

        nom.clear();
        description.clear();
        photoModulepath.clear();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }






    @FXML
    void clearData(ActionEvent event) {
        photoModulepath.clear();
        description.clear();
        nom.clear();

    }

    @FXML
    void closePopUp(MouseEvent event) {
        UpdatePopUpModule.setVisible(false);
    }

    @FXML
    void photoPicker(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            photoModulepath.setText(selectedFile.getAbsolutePath());

            // Load image
            try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
                Image image = new Image(inputStream);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("No file has been selected");
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
