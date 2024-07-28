package com.example.javafxprojectusertask.Controller.AdminController.Offre;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import com.example.javafxprojectusertask.Services.ServiceSociete;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PopUpAddOffreController {

    @FXML
    private DatePicker date_deb;


    @FXML
    private DatePicker date_fin;


    @FXML
    private TextField desc_text;


    @FXML
    private TextField niveau_textField;

    @FXML
    private ChoiceBox<Societe> societe_choice;


    ArrayList<Societe> societes = new ArrayList<>();


    @FXML
    public AnchorPane PopUpAddOffre;

    @FXML
    public void initialize() {
        ServiceSociete serviceSociete = new ServiceSociete();
        societes = serviceSociete.getAll();
        ObservableList<Societe> societes1 = FXCollections.observableArrayList(societes);
        societe_choice.setItems(societes1);
    }

    @FXML
    void ClosepopUp(MouseEvent event) {
        PopUpAddOffre.setVisible(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser = userUtils.getConnectedUser();
            dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
            dashBordController.SwitchToOffre();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajoutOffre(MouseEvent event) {

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

        Offre offre = new Offre();
        offre.setNiveau(niveau);
        offre.setDesc(des);
        offre.setDateDeb(dateDeb);
        offre.setDateFin(dateFin);
        offre.setSociete(societe);
        ServiceOffre serviceOffre = new ServiceOffre();
        serviceOffre.add(offre);
        ClosepopUp(event);

    }

}
