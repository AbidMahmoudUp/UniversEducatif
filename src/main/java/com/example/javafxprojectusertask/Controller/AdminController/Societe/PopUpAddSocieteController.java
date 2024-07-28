package com.example.javafxprojectusertask.Controller.AdminController.Societe;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceSociete;
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

public class PopUpAddSocieteController {

    @FXML
    private Button ajout_Button;

    @FXML
    private ImageView backgroundBlur;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextField desc_textArea;

    @FXML
    private TextField nom_textField;
    @FXML
    private AnchorPane PopUpAddSociete;

    @FXML
    void ClosepopUp(MouseEvent event) {
        PopUpAddSociete.setVisible(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser = userUtils.getConnectedUser();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
            dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
            dashBordController.SwitchToEvent(event);        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAddButtonClick(MouseEvent event) {
        ServiceSociete serviceSociete = new ServiceSociete();
        //Alert a = new Alert(Alert.AlertType.INFORMATION);
       // a.setContentText("Societé a été Ajouter !");

        String nom = nom_textField.getText();
        String desc = desc_textArea.getText();

        Societe s = new Societe();

        s.setDesc(desc);
        s.setNom(nom);


        serviceSociete.add(s);
      //  a.show();


    }
    @FXML
    public void ClosepopUp(){

    }
}
