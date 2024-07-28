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
import java.util.Date;

public class UpdateOffreController {
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
    Offre o;

    ServiceSociete serviceSociete = new ServiceSociete();
    ObservableList<Societe> societes;

    public void setData(Offre o1)
    {
        o = o1;
    }

    public Offre getData()
    {
        return o;
    }
    public void prepareScene()
    {
        societes = FXCollections.observableArrayList(serviceSociete.getAll());
        niveau_textField.setText(Integer.toString(o.getNiveau()));
        desc_textArea.setText(o.getDesc());
        societe_choice.setItems(societes);
        animateFields();
    }


    @FXML
    public void updateOffre(ActionEvent event) throws IOException {
        ServiceOffre serviceOffre = new ServiceOffre();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Offre Mis a jour !");
        Societe s = societe_choice.getValue();
        String desc = desc_textArea.getText();
        int niveau = Integer.parseInt(niveau_textField.getText());
        LocalDate localDate = date_deb.getValue();
        Date dateDeb = java.sql.Date.valueOf(localDate);
        LocalDate localDate1 = date_fin.getValue();
        Date dateFin = java.sql.Date.valueOf(localDate1);
        o.setDesc(desc);
        o.setSociete(s);
        o.setDateDeb((java.sql.Date) dateDeb);
        o.setDateFin((java.sql.Date) dateFin);
        o.setNiveau(niveau);
        a.show();
        serviceOffre.update(o);
        afficherScene(event);
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
