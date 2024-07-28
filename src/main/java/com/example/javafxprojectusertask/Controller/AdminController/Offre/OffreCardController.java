package com.example.javafxprojectusertask.Controller.AdminController.Offre;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Services.ServiceDossier;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class OffreCardController {

    @FXML
    private AnchorPane card;

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
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        card.setEffect(dropShadow);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            root = loader.load();
            DashBordController controller = loader.getController();
            controller.SwitchToOffre();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void updateButton(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        PopUpUpdateOffreController.o = this.o;
        loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/PopUpUpdateOffre.fxml"));
        Parent root = loader.load();
        PopUpUpdateOffreController controller = loader.getController();
        controller.setStage((Stage)((Node) event.getSource()).getScene().getWindow());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        Parent root1 = loader1.load();
        DashBordController dashBordController = loader1.getController();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser=userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        dashBordController.SwitchToOffre();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1, 1366, 786);

        stage.setScene(scene);
        stage.show();
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
