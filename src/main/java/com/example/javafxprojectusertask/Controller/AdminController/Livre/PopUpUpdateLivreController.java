package com.example.javafxprojectusertask.Controller.AdminController.Livre;

import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.LivreService;
import com.twilio.rest.conversations.v1.conversation.message.DeliveryReceipt;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpUpdateLivreController {

    public static Livre l;
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
    public void updateButton(MouseEvent event)
    {
        LivreService service = new LivreService();
        //Alert a = new Alert(Alert.AlertType.INFORMATION);
        // a.setContentText("Societé a été Ajouter !");



        l.setAuteur(auteur_textField.getText());
        l.setTitre(titre_textField.getText());
        l.setType(type_textField.getText());

        l.setLangue(langue_textField.getText());
        l.setIdBib(LivreManagementController.b);

        service.update(l);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
