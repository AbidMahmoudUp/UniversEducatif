package com.example.javafxprojectusertask.Controller.UserController.Bibliotheque;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque.PopUpUpdateBibliothequeController;
import com.example.javafxprojectusertask.Controller.AdminController.Livre.LivreManagementController;
import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.Bibliotheque;
import com.example.javafxprojectusertask.Services.BibliothequeService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private Button SeeBooks;

    @FXML
    private Label bibliothequeName;

    @FXML
    private AnchorPane cardAnchorPane;

    @FXML
    private Label emailBibliotheque;

    Bibliotheque b;


    public void prepareCard()
    {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        cardAnchorPane.setEffect(dropShadow);
        bibliothequeName.setText(b.getNom());
        emailBibliotheque.setText(b.getMail());
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
    public void switchToLivres(MouseEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
        root = loader.load();
        BibliothequeManagementController.b = this.b;
        WelcomePageController controller = loader.getController();
        UserUtils userUtils = new UserUtils() ;
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        controller.setDataUser(UserUtils.ConnectedUser.getIdUser());
        controller.switchToLivres(event);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
