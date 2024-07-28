package com.example.javafxprojectusertask.Controller.AdminController.Dossier;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.AdminController.Offre.PopUpUpdateOffreController;
import com.example.javafxprojectusertask.Controller.UserController.Offre.AfficherOffreController;
import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.ServiceDossier;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import com.google.api.services.gmail.Gmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class DossierCardController {


    @FXML
    private AnchorPane card;

    @FXML
    private Label offre_label;

    @FXML
    private Label status_label;

    @FXML
    Label nom_label;


    @FXML
    private ImageView dossierImage;


    Dossier d = new Dossier();

    public void prepareCard()
    {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        card.setEffect(dropShadow);
        offre_label.setText(d.getOffre().getDesc());
        ProfileService service = new ProfileService();
        Profile p = service.getProfileUser(d.getIdUser());
        nom_label.setText(p.getFirstName() + " " + p.getLastName());
        status_label.setText(d.getStatus());
        System.out.println("FFFF");
        if(d.getStatus().equalsIgnoreCase("en attente"))
        {
            card.setStyle("-fx-background-color:  #FDE18F; -fx-border-width: 1;-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color:  #FDC628 ");
            status_label.setStyle("-fx-text-fill: #686632");
        }
        if(d.getStatus().equalsIgnoreCase("accepté"))
        {
            card.setStyle("-fx-background-color:  #88DF77;-fx-background-radius: 8; -fx-border-width: 1; -fx-border-radius: 8; -fx-border-color:  #02AD68 ");
            status_label.setStyle("-fx-text-fill: #008851");
        }
        if(d.getStatus().equalsIgnoreCase("refusé"))
        {
            card.setStyle("-fx-background-color:  #FDD3D3;-fx-background-radius: 8; -fx-border-width: 1; -fx-border-radius: 8; -fx-border-color:  #A61832 ");
            status_label.setStyle("-fx-text-fill: #B54156");
        }
    }

    public void setData(Dossier d1)
    {
        d = d1;
    }

    public Dossier getData()
    {
        return d;
    }


    @FXML
    void updateButton(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        UpdateStateDossierController.d = d;
        loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Dossier/UpdateStateDossier.fxml"));
        Parent root = loader.load();
        UpdateStateDossierController controller = loader.getController();
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
        dashBordController.SwitchToDossier();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1, 1366, 786);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void supprimerButton(MouseEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        Alert c = new Alert(Alert.AlertType.CONFIRMATION);
        c.setContentText("Supprimer ?");
        ServiceDossier serviceDossier= new ServiceDossier();
        Optional<ButtonType> option = c.showAndWait();
        if(option.get() == ButtonType.OK) {
            serviceDossier.delete(d);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Dossier supprimé !");
            a.show();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            root = loader.load();
            DashBordController controller = loader.getController();
            UserUtils userUtils =new UserUtils();
            UserUtils.ConnectedUser=userUtils.getConnectedUser();
            controller.setData(UserUtils.ConnectedUser.getIdUser());
            controller.SwitchToDossier();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    public void afficherOffre(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testfx7002/Offre/afficherOffre.fxml"));
        Parent root = loader.load();
        AfficherOffreController afficherController=loader.getController();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
