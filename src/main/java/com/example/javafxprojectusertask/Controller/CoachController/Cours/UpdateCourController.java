package com.example.javafxprojectusertask.Controller.CoachController.Cours;

import com.example.javafxprojectusertask.Entities.Cour;
import com.example.javafxprojectusertask.Services.ServiceCour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpdateCourController {
    @FXML
    private AnchorPane AddPopUpCour;

    @FXML
    private TextField FichierCourpath;

    @FXML
    private ImageView closeArea;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextArea description;

    @FXML
    private ImageView fichier;

    @FXML
    private TextField nom;

    @FXML
    private TextField tempsEstimeC;
    private Cour cour ;
    public void setCour(Cour cour) {
        this.cour = cour;
        nom.setText(cour.getNom());
        tempsEstimeC.setText(String.valueOf(cour.getTempsEstimeC()));
        description.setText(cour.getDescription());
        FichierCourpath.setText(cour.getFichierPath());
    }

    public void closePopUp(MouseEvent event) {
        AddPopUpCour.setVisible(false);
    }

    public void clearData(ActionEvent actionEvent) {
        FichierCourpath.clear();
        description.clear();
        nom.clear();
        tempsEstimeC.clear();
    }

    public void fichierPicker(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        Stage stage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String xamppImgPath = "C:/xampp/htdocs/files/";


            String destinationPath = xamppImgPath + selectedFile.getName();
            Files.copy(Path.of(selectedFile.getAbsolutePath()), Path.of(destinationPath));
            Thread thread = new Thread(() -> {

                try {
                    PDDocument document = Loader.loadPDF(selectedFile);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    String text = pdfStripper.getText(document);
                    System.out.println("Content : \n" + text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            FichierCourpath.setText(destinationPath);

            try {
                String content = Files.readString(selectedFile.toPath());
                System.out.println("Contenu du fichier : " + content);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("Aucun fichier sélectionné");
        }

    }

    @FXML
    void ModifierCour(MouseEvent event) {
        String nomText = nom.getText();
        String descriptionText = description.getText();
        String fichierCourPathText = FichierCourpath.getText();
        String tempsEstimeCText = tempsEstimeC.getText();

        if (nomText.isEmpty() || descriptionText.isEmpty() || fichierCourPathText.isEmpty() || tempsEstimeCText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Tous les champs doivent être remplis.");
            return;
        }

        if (!nomText.matches("[a-zA-Z ]+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le champ nom ne doit contenir que des lettres et des espaces.");
            return;
        }

        int tempsEstimeCInt;
        try {
            tempsEstimeCInt = Integer.parseInt(tempsEstimeCText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le champ temps estimé doit être un entier.");
            return;
        }

        ServiceCour serviceCour = new ServiceCour();
        Integer courId = serviceCour.getIdCourbyNom(nomText);
        if (courId != null && !courId.equals(cour.getIdCour())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Un cours avec ce nom existe déjà.");
            return;
        }

        this.cour.setNom(nomText);
        this.cour.setDescription(descriptionText);
        this.cour.setFichierPath(fichierCourPathText);
        this.cour.setTempsEstimeC(Integer.parseInt(tempsEstimeCText));
        serviceCour.update(this.cour);
        nom.clear();
        description.clear();
        FichierCourpath.clear();
        tempsEstimeC.clear();
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
