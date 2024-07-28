package com.example.javafxprojectusertask.Controller.AdminController.Module;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.nio.file.Files;
import java.nio.file.Path;

public class AddModuleController {

    @FXML
    private ImageView closeArea;

    @FXML
    private ImageView closeButton;

    @FXML
    private AnchorPane AddPopUpModule;
    @FXML
    private TextArea description;

    @FXML
    private TextField nom;

    @FXML
    private TextField photoModulepath;
    public void setModuleManagementController(ModuleManagementController moduleManagementController) {
        this.moduleManagementController = moduleManagementController;

    }

    private ModuleManagementController moduleManagementController;
    public void closePopUp(MouseEvent event) throws IOException {
        AddPopUpModule.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser = userUtils.getConnectedUser();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
            dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
            dashBordController.SwitchToModule(event);        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void photoPicker(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);



        if (selectedFile != null) {
            String xamppImgPath = "C:/xampp/htdocs/img/";
            final String base = "http://127.0.0.1/img/";
            String destinationPath = xamppImgPath + selectedFile.getName();
            String destination = base + selectedFile.getName();
            Files.copy(Path.of(selectedFile.getAbsolutePath()), Path.of(destinationPath));
            photoModulepath.setText(destination);

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
    public void AjouterModule(MouseEvent event) {
        String nomText = nom.getText();
        if (nomText.isEmpty() || description.getText().isEmpty() || photoModulepath.getText().isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez saisir tous les champs.");
            return;
        }

        if (!nomText.matches("[a-zA-Z ]+")) {
            showAlert("Erreur de saisie", "Le champ nom ne doit contenir que des lettres et des espaces.");
            return;
        }

        ServiceModule serviceModule = new ServiceModule();
        Integer idModule = serviceModule.getIdModulebyNom(nomText);
        if (idModule == null) {
            // Le nom n'existe pas, vous pouvez ajouter le module
            Module m = new Module(19, nomText, description.getText(), photoModulepath.getText(), 0);
            ServiceModule sm = new ServiceModule();
            sm.add(m);
            photoModulepath.clear();
            description.clear();
            nom.clear();
        } else {
            showAlert("Erreur de saisie", "Le nom du module existe déjà.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void clearData(ActionEvent actionEvent)
    {
        photoModulepath.clear();
        description.clear();
        nom.clear();
    }




}
