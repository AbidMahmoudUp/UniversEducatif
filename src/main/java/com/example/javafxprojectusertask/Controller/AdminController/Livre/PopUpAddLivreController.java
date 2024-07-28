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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpAddLivreController {

    @FXML
    private Button ajout_Button;

    @FXML
    private ImageView backgroundBlur;

    @FXML
    private TextField type_textField;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextField titre_textField;

    @FXML
    private TextField auteur_textField;

    @FXML
    private TextField langue_textField;
    @FXML
    private AnchorPane PopUpAddLivre;

    @FXML
    void ClosepopUp(MouseEvent event) {
        PopUpAddLivre.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();

            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser = userUtils.getConnectedUser();
            dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
            dashBordController.SwitchToLivres(event);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAddButtonClick(MouseEvent event) {
        LivreService service = new LivreService();
        //Alert a = new Alert(Alert.AlertType.INFORMATION);
        // a.setContentText("Societé a été Ajouter !");


        Livre l = new Livre();
        l.setAuteur(auteur_textField.getText());
        l.setTitre(titre_textField.getText());
        l.setType(type_textField.getText());

        l.setLangue(langue_textField.getText());
        l.setIdBib(LivreManagementController.b);

        service.add(l);
        //  a.show();
        ClosepopUp(event);

    }
}
