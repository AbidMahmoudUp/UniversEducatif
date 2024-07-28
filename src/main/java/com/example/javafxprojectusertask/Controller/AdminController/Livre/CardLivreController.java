package com.example.javafxprojectusertask.Controller.AdminController.Livre;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.LivreService;
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

public class CardLivreController {



    @FXML
    private Label des_label;

    @FXML
    private Label nom_label;


    @FXML
    private AnchorPane card;

    Livre l;


    public void prepareCard()
    {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        card.setEffect(dropShadow);
        nom_label.setText(l.getAuteur());
        des_label.setText(l.getLangue());
    }
    public void setData(Livre l1)
    {
        l = l1;
    }

    public Livre getData()
    {
        return l;
    }

    @FXML
    public void updateButton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        PopUpUpdateLivreController.l = this.l;
        loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Livre/PopUpUpdateLivre.fxml"));
        Parent root = loader.load();
        PopUpUpdateLivreController controller = loader.getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        //AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/DashBord.fxml.fxml"));
        Parent root1 = loader1.load();
        DashBordController dashBordController = loader1.getController();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser=userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        dashBordController.SwitchToLivres(event);
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
        LivreService service = new LivreService();
            Optional<ButtonType> option = c.showAndWait();
            if(option.get() == ButtonType.OK) {
                service.delete(l);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Société supprimé !");
                a.show();
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
                root = loader.load();
                DashBordController controller = loader.getController();

                UserUtils userUtils = new UserUtils();
                UserUtils.ConnectedUser=userUtils.getConnectedUser();
                controller.setData(UserUtils.ConnectedUser.getIdUser());
                controller.SwitchToLivres(event);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

    }


    @FXML
    public void afficherLivres(MouseEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
                AnchorPane pane = loader.load();

                DashBordController controller = loader.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }



}
