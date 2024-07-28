package com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque;

import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpUpdateBibliothequeController {


    @FXML
    private Button ajout_Button;

    public static Bibliotheque b;
    @FXML
    private ImageView backgroundBlur;

    @FXML
    private TextField nbr_textField;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextField desc_textArea;

    @FXML
    private TextField nom_textField;
    @FXML
    private AnchorPane PopUpAddBibliotheque;

    @FXML
    void ClosepopUp(MouseEvent event) {
        PopUpAddBibliotheque.setVisible(false);
    }

    @FXML
    void updateButton(MouseEvent event) {

        BibliothequeService service = new BibliothequeService();
        //Alert a = new Alert(Alert.AlertType.INFORMATION);
        // a.setContentText("Societé a été Ajouter !");

        String nom = nom_textField.getText();
        String desc = desc_textArea.getText();

        b.setNbrLivre(Integer.parseInt(nbr_textField.getText()));
        b.setMail(desc);
        b.setNom(nom);
        System.out.println("QSDSQDQS");
        service.update(b);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
}
