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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OffreController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    @FXML
    private TableColumn<Offre, String> date_deb_off;

    @FXML
    private TableColumn<Offre, String> date_fin_off;

    @FXML
    private TableColumn<Offre,String> des_off_col;

    @FXML
    private TableColumn<Offre, Integer> id_off_col;

    @FXML
    private TableColumn<Offre, Integer> niv_off_col;

    @FXML
    private TableColumn<Offre, Societe> nom_soc_col;

    @FXML
    private TableView<Offre> table_societe;

    ServiceOffre serviceOffre = new ServiceOffre();

    ServiceSociete serviceSociete = new ServiceSociete();

    ArrayList<Offre> offres;

    ObservableList<Offre> offres1;

    ArrayList<Societe> societes = new ArrayList<>();

    static Offre offre = null;

    public static boolean afficherActive = true;

    public static boolean updateActive = false;

    static  boolean ajoutActive = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(afficherActive)
        {
            System.out.println("I'm here");
            show();
        }
        if(ajoutActive) {
            societes = serviceSociete.getAll();
            ObservableList<Societe> societes1 = FXCollections.observableArrayList(societes);
            societe_choice.setItems(societes1);
            animateFields();
        }

    }

    @FXML
    void ajout_Offre(ActionEvent event) {
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
        serviceOffre.add(offre);

        a.show();

    }

    @FXML
    public void afficherScene(ActionEvent event) throws IOException {
        afficherActive = true;
        updateActive = false;
        ajoutActive = false;
        offre = null;
        root = FXMLLoader.load(getClass().getResource("../afficherOffre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }



    @FXML
    public void show()
    {
        offres = serviceOffre.getAll();
        System.out.println(offres);
        id_off_col.setCellValueFactory(new PropertyValueFactory<Offre,Integer>("id"));
        System.out.println(id_off_col);
        niv_off_col.setCellValueFactory(new PropertyValueFactory<Offre,Integer>("niveau"));
        //System.out.println(id_soc_col.getCellValueFactory());
        nom_soc_col.setCellValueFactory(new PropertyValueFactory<Offre,Societe>("societe"));
        des_off_col.setCellValueFactory(new PropertyValueFactory<Offre,String>("desc"));
        //date_deb_off.setCellValueFactory(new PropertyValueFactory<Offre,Date>("date_deb"));
        date_deb_off.setCellValueFactory(new PropertyValueFactory<Offre,String>("date_deb"));
        date_fin_off.setCellValueFactory(new PropertyValueFactory<Offre,String>("date_fin"));



        offres1 = FXCollections.observableArrayList(offres);

        table_societe.setItems(offres1);
    }



    @FXML
    public void ajoutScene(ActionEvent event) throws IOException {
        ajoutActive = true;
        afficherActive = false;
        updateActive = false;
        offre = null;
        root = FXMLLoader.load(getClass().getResource("../ajoutOffre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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
