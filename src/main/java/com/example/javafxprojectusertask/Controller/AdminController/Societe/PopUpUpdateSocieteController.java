package com.example.javafxprojectusertask.Controller.AdminController.Societe;

import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceSociete;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpUpdateSocieteController {

    @FXML
    private Button ajout_Button;

    @FXML
    private ImageView backgroundBlur;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextField desc_textArea;

    @FXML
    private TextField nom_textField;
    @FXML
    private AnchorPane PopUpAddSociete;

    public static Societe s;

    @FXML
    void ClosepopUp(MouseEvent event) {
        PopUpAddSociete.setVisible(false);
    }

    @FXML
    public void initialize()
    {
        nom_textField.setText(s.getNom());
        desc_textArea.setText(s.getDesc());
    }

    @FXML
    void updateButton(MouseEvent event) {
        ServiceSociete serviceSociete = new ServiceSociete();
        //Alert a = new Alert(Alert.AlertType.INFORMATION);
        // a.setContentText("Societé a été Ajouter !");

        String nom = nom_textField.getText();
        String desc = desc_textArea.getText();

        s.setDesc(desc);
        s.setNom(nom);


        serviceSociete.update(s);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
    @FXML
    public void ClosepopUp(){

    }
}
