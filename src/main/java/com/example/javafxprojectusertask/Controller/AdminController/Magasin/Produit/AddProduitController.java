package com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit;
import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.ProduitService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
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

public class AddProduitController {
    @FXML
    private AnchorPane AddPopUpProduit;

    @FXML
    private TextField Audiopath;

    @FXML
    private ImageView Imagep;

    @FXML
    private TextField Imagepath;

    @FXML
    private TextField Marge;

    @FXML
    private TextField Prix_init;

    @FXML
    private TextField Type;

    @FXML
    private TextField Videopath;

    @FXML
    private ImageView audiop;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextArea description;

    @FXML
    private TextField nom;

    @FXML
    private ImageView videop;

    private ProduitService produitService;

    public void initialize() {
        produitService = new ProduitService();
    }

    public void AjouterProduit(MouseEvent mouseEvent) throws IOException {
        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.getText().isEmpty() || Type.getText().isEmpty() || description.getText().isEmpty() ||
                Prix_init.getText().isEmpty() || Marge.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs obligatoires.");
            return; // Sortir de la méthode si des champs obligatoires sont vides
        }

        // Vérifier l'unicité du nom du produit
        if (produitService.isProduitExists(nom.getText())) {
            afficherAlerte("Le nom du produit existe déjà. Veuillez en choisir un autre.");
            return; // Sortir de la méthode si le nom du produit existe déjà
        }


        Produit p = new Produit(1, nom.getText(), Type.getText(), description.getText(),
                Float.parseFloat(Prix_init.getText()), Float.parseFloat(Marge.getText()),
                Videopath.getText(), Imagepath.getText(), Audiopath.getText());
        p.setDescrip(description.getText());
        produitService.add(p);


        System.out.println("Le produit a été ajouté avec succès.");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        Parent root = loader.load();
        DashBordController dashBordController = loader.getController();
        UserUtils userUtils = new UserUtils() ;
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        dashBordController.SwitchToMagasin(mouseEvent);
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1366,786);
        stage.setScene(scene);
        stage.show();
    }

    public void Picker(MouseEvent event) {
        String selectedPath = selectFilePath();
        if (selectedPath != null) {
            Imagepath.setText(selectedPath);
            loadImage(selectedPath, Imagep);
        }
    }

    public void Picker_vid(MouseEvent event) {
        String selectedPath = selectFilePath();
        if (selectedPath != null) {
            Videopath.setText(selectedPath);
            loadImage(selectedPath, videop);
        }
    }

    public void Picker_audio(MouseEvent event) {
        String selectedPath = selectFilePath();
        if (selectedPath != null) {
            Audiopath.setText(selectedPath);
            loadImage(selectedPath, audiop);
        }
    }

    private String selectFilePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile != null ? selectedFile.getAbsolutePath() : null;
    }

    private void loadImage(String path, ImageView imageView) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            Image image = new Image(inputStream);
            imageView.setImage(image);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void closePopUp(MouseEvent mouseEvent) throws IOException {
        AddPopUpProduit.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        Parent root = loader.load();
        DashBordController dashBordController = loader.getController();
        UserUtils userUtils = new UserUtils() ;
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        dashBordController.SwitchToMagasin(mouseEvent);
        Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1366,786);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void effacer(MouseEvent mouseEvent) {
        nom.clear();
        Type.clear();
        description.clear();
        Prix_init.clear();
        Marge.clear();
        Imagepath.clear();
        Videopath.clear();
        Audiopath.clear();
        Imagep.setImage(null);
        videop.setImage(null);
        audiop.setImage(null);
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
