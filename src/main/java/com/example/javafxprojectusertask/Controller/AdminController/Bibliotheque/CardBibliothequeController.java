package com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.AdminController.Livre.LivreManagementController;
import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Services.BibliothequeService;
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

public class CardBibliothequeController {


    @FXML
    private Label des_label;

    @FXML
    private Label nom_label;


    @FXML
    private AnchorPane card;

    Bibliotheque b;


    public void prepareCard()
    {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        card.setEffect(dropShadow);
        nom_label.setText(b.getNom());
        des_label.setText(Integer.toString(b.getNbrLivre()));
    }
    public void setData(Bibliotheque b1)
    {
        b = b1;
    }

    public Bibliotheque getData()
    {
        return b;
    }

    @FXML
    public void updateButton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        PopUpUpdateBibliothequeController.b = this.b;
        loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Bibliotheque/PopUpUpdateBibliotheque.fxml"));
        Parent root = loader.load();
        PopUpUpdateBibliothequeController controller = loader.getController();
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
        dashBordController.SwitchToBibliotheque(event);
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
        BibliothequeService service = new BibliothequeService();
            Optional<ButtonType> option = c.showAndWait();
            if(option.get() == ButtonType.OK) {
                service.delete(b);
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
                controller.SwitchToBibliotheque(event);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

    }

    @FXML
    public void switchToLivres(MouseEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        root = loader.load();
        LivreManagementController.b = this.b;
        DashBordController controller = loader.getController();
        UserUtils userUtils = new UserUtils() ;
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        controller.setData(UserUtils.ConnectedUser.getIdUser());
        controller.SwitchToLivres(event);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
