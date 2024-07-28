package com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit;

import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.ProduitService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UpdateProduitController {

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
    private ImageView closeArea;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextArea description;

    @FXML
    private Label id;

    @FXML
    private TextField nom;

    @FXML
    private ImageView videop;





    private int r;

    // Méthode pour initialiser les champs avec les valeurs du produit lors de l'ouverture de la vue UpdateProduit
    public void initializeFieldsForUpdate(Produit produit) {
        id.setText(String.valueOf(produit.getId_produit()));
        nom.setText(produit.getNom());
        Type.setText(produit.getType());
        description.setText(produit.getDescrip());
        Prix_init.setText(String.valueOf(produit.getPrix_init()));
        Marge.setText(String.valueOf(produit.getMarge()));
        Imagepath.setText(produit.getImage());
        Videopath.setText(produit.getVideo());
        Audiopath.setText(produit.getAudio());
    }


    // Méthode pour mettre à jour un produit
    @FXML
    void Modifier(MouseEvent mouseEvent) {
        Produit p = new Produit();

        // Mettre à jour les propriétés du produit avec les nouvelles valeurs des champs
        p.setNom(nom.getText());
        p.setType(Type.getText());
        p.setDescrip(description.getText());
        p.setId_produit(Integer.parseInt(id.getText()));
        try {
            // Parsing Prix_init
            if (!Prix_init.getText().isEmpty()) {
                p.setPrix_init(Float.parseFloat(Prix_init.getText()));
            }

            // Parsing Marge
            if (!Marge.getText().isEmpty()) {
                p.setMarge(Float.parseFloat(Marge.getText()));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        p.setVideo(Videopath.getText());
        p.setImage(Imagepath.getText());
        p.setAudio(Audiopath.getText());

        // Mettre à jour le produit dans la base de données
        ProduitService ps = new ProduitService();
        ps.updateProduit(p);

        // Afficher un message de confirmation de la modification
        System.out.println("Produit modifié : " + p.getNom());
    }

    // Méthode pour effacer les champs

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
        Imagep.setImage(null); // Effacez également l'image affichée dans ImageView
    }

    // Méthode pour sélectionner une image
    @FXML
    public void Picker(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Imagepath.setText(selectedFile.getAbsolutePath());
            // Load image
            try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
                Image image = new Image(inputStream);
                Imagep.setImage(image); // Set the loaded image to the ImageView
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("No file has been selected");
        }
    }

    // Méthode pour sélectionner une vidéo
    @FXML
    public void Picker_vid(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Videopath.setText(selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file has been selected");
        }
    }

    // Méthode pour sélectionner un fichier audio
    @FXML
    public void Picker_audio(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Audiopath.setText(selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file has been selected");
        }
    }

    // Méthode pour fermer la fenêtre de pop-up
    @FXML
    public void closePopUp(MouseEvent mouseEvent) {
        AddPopUpProduit.setVisible(false);
    }
}
