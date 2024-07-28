package com.example.javafxprojectusertask.Controller.UserController.Offre;

import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import com.example.javafxprojectusertask.Services.ServiceSociete;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class AjoutOffreController {
    @FXML
    private Button ajout_Button;

    @FXML
    private DatePicker date_deb;

    @FXML
    private Label date_deb_label;

    @FXML
    private DatePicker date_fin;

    @FXML
    private Label date_fin_label;

    @FXML
    private Label des_label;

    @FXML
    private TextArea desc_textArea;

    @FXML
    private Label niveau_label;

    @FXML
    private TextField niveau_textField;

    @FXML
    private ChoiceBox<Societe> societe_choice;

    @FXML
    private Label societe_label;

    ArrayList<Societe> societes = new ArrayList<>();

    @FXML
    public void initialize() {
        ServiceSociete serviceSociete = new ServiceSociete();
        societes = serviceSociete.getAll();
        ObservableList<Societe> societes1 = FXCollections.observableArrayList(societes);
        societe_choice.setItems(societes1);
        animateFields();
    }

    public void animateFields()
    {
        animate(niveau_textField,1000,1050,1);
        animate(desc_textArea, 1000, 1050, 1);
        animate(date_deb,1000,1050,1);
        animate(date_fin,1000,1050,1);
        animate(societe_choice,1000,1050,1);
        animate(niveau_label, 1500, 1050, 1);
        animate(des_label, 1500, 1050, 1);
        animate(date_deb_label,1500,1050,1);
        animate(date_fin_label,1500,1050,1);
        animate(societe_label,1500,1050,1);
        animate(ajout_Button, 2000, 500, -2);
    }

    @FXML
    void ajoutOffre(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Societé a été Ajouter !");

        int niveau = Integer.parseInt(niveau_textField.getText());
        String des = desc_textArea.getText();
        LocalDate localDate = date_deb.getValue();
        Date dateDeb = java.sql.Date.valueOf(localDate);
        LocalDate localDate1 = date_fin.getValue();
        Date dateFin = java.sql.Date.valueOf(localDate1);
        Societe societe = societe_choice.getValue();

        Offre offre = new Offre();
        offre.setNiveau(niveau);
        offre.setDesc(des);
        offre.setDateDeb(dateDeb);
        offre.setDateFin(dateFin);
        offre.setSociete(societe);
        ServiceOffre serviceOffre = new ServiceOffre();
        serviceOffre.add(offre);

        a.show();

    }


    @FXML
    public void afficherScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testfx7002/Offre/afficherOffre.fxml"));
        Parent root = loader.load();
        AfficherOffreController afficherController=loader.getController();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void animate(Node element, double duration, double distance, int direction)
    {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(element);

        if(direction % 2 == 0)
        {
            direction = direction / 2;
            transition.setByY(direction * distance);
        }
        else
        {
            transition.setByX(direction * distance);
        }

        transition.setDuration(Duration.millis(duration));
        transition.play();
    }
}
