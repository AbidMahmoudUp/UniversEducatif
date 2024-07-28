package com.example.javafxprojectusertask.Controller.AdminController.Offre;

import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import com.example.javafxprojectusertask.Services.ServiceSociete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PopUpUpdateOffreController {

    @FXML
    private DatePicker date_deb;


    @FXML
    private DatePicker date_fin;


    @FXML
    private TextField desc_text;

    Stage stage;

    @FXML
    private TextField niveau_textField;

    @FXML
    private ChoiceBox<Societe> societe_choice;


    ArrayList<Societe> societes = new ArrayList<>();


    public static Offre o;
    @FXML
    public AnchorPane PopUpAddOffre;

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
    @FXML
    public void initialize() {
        prepareScene();
    }

    @FXML
    void ClosepopUp(MouseEvent event) {
        PopUpAddOffre.setVisible(false);
    }


    public Offre getData()
    {
        return o;
    }
    public void prepareScene()
    {
        ServiceSociete serviceSociete = new ServiceSociete();
        ObservableList<Societe> societes1 = FXCollections.observableArrayList(serviceSociete.getAll());
        niveau_textField.setText(Integer.toString(o.getNiveau()));
        desc_text.setText(o.getDesc());
        date_deb.setValue(java.sql.Date.valueOf(o.getDateDeb().toString()).toLocalDate());
        date_fin.setValue(java.sql.Date.valueOf(o.getDateFin().toString()).toLocalDate());
        societe_choice.setItems(societes1);
    }


    @FXML
    void updateOffre(MouseEvent event) throws IOException {

        int niveau = 0 ;
        try
        {
            niveau= Integer.parseInt(niveau_textField.getText());
        }catch(NumberFormatException e)
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Erreur");
            a.setContentText("Niveau requis un entier !!!");
            a.show();
            return;
        }
        String des = desc_text.getText();
        LocalDate localDate = date_deb.getValue();
        Date dateDeb = java.sql.Date.valueOf(localDate);
        LocalDate localDate1 = date_fin.getValue();
        Date dateFin = java.sql.Date.valueOf(localDate1);
        Societe societe = societe_choice.getValue();

        if(dateDeb.before(java.sql.Date.valueOf(LocalDate.now())))
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Erreur");
            a.setContentText("Date invalide");
            a.show();
            return;
        }
        if(dateFin.before(dateDeb))
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Erreur");
            a.setContentText("Date invalide");
            a.show();
            return;
        }

        o.setNiveau(niveau);
        o.setDesc(des);
        o.setDateDeb(dateDeb);
        o.setDateFin(dateFin);
        o.setSociete(societe);
        ServiceOffre serviceOffre = new ServiceOffre();
        serviceOffre.update(o);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();


    }
}
