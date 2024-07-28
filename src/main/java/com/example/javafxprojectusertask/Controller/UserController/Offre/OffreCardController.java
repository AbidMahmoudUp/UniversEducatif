package com.example.javafxprojectusertask.Controller.UserController.Offre;

import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Services.ServiceDossier;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class OffreCardController {

    @FXML
    private VBox card;

    @FXML
    private Label date_deb_label;

    @FXML
    private Label date_fin_label;

    @FXML
    private Label des_label;

    @FXML
    private Label niv_label;

    @FXML
    private Label nom_label;



    Offre o = new Offre();

    public void prepareCard()
    {
        nom_label.setText(nom_label.getText() + o.getSociete().getNom());
        des_label.setText(des_label.getText() + o.getDesc());
        niv_label.setText(niv_label.getText() + o.getNiveau());
        date_deb_label.setText(date_deb_label.getText() + o.getDateDeb().toString());
        date_fin_label.setText(date_fin_label.getText() + o.getDateFin().toString());

    }

    public void setData(Offre o1)
    {
        o = o1;
    }

    public Offre getData()
    {
        return o;
    }


    @FXML
    public void supprimerButton(MouseEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        Alert c = new Alert(Alert.AlertType.CONFIRMATION);
        c.setContentText("Supprimer ?");
        ServiceOffre serviceOffre= new ServiceOffre();
        Optional<ButtonType> option = c.showAndWait();
        if(option.get() == ButtonType.OK) {
            serviceOffre.delete(o);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Société supprimé !");
            a.show();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testfx7002/Offre/afficherOffre.fxml"));
            root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void updateButton(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/testfx7002/Offre/updateOffre.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        UpdateOffreController updateController = loader.getController();
        updateController.setData(o);
        updateController.prepareScene();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showPopUpUpdateOffre(MouseEvent event)
    {

    }

    @FXML
    void appliquerButton(MouseEvent event) throws IOException {

        Alert a = new Alert(Alert.AlertType.NONE);
        Alert c = new Alert(Alert.AlertType.CONFIRMATION);
        c.setContentText("Appliquer ?");
        ServiceDossier serviceDossier= new ServiceDossier();
        Optional<ButtonType> option = c.showAndWait();
        if(option.get() == ButtonType.OK) {
            Dossier d = new Dossier();
            d.setOffre(o);
            d.setIdUser(1);
            d.setStatus("En attente");
            if(serviceDossier.getDossierByID(d.getIdUser(),o.getId()) == null)
            {
                serviceDossier.add(d);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Dossier Envoyé !!");
                a.show();
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testfx7002/Offre/afficherOffre.fxml"));
                root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Dossier déja envoyé !");
                a.show();
            }
        }
    }

}
