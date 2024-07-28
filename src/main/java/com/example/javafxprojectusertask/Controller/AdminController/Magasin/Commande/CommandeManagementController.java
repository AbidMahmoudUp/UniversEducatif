package com.example.javafxprojectusertask.Controller.AdminController.Magasin.Commande;

import com.example.javafxprojectusertask.Entities.Commande;
import com.example.javafxprojectusertask.Services.CommandeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class CommandeManagementController {

    @FXML
    private AnchorPane ModuleAnchorPane;

    @FXML
    private FlowPane flowPaneCommandes;

    @FXML
    private AnchorPane popUpAddModule;

    public void initialize() {
        try {
            setDataCommande();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setDataCommande() throws IOException {
        CommandeService commandeService = new CommandeService();
        ArrayList<Commande> commandes = commandeService.getAllCommandes();

        for (Commande commande : commandes) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/cards/CardCommande.fxml"));
            AnchorPane pane = loader.load();
            CardCommandeController controller = loader.getController();
            controller.initializeData(commande);
            flowPaneCommandes.getChildren().add(pane);
        }
    }

    public void openPopUp() throws IOException {
        popUpAddModule.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/AddCommande.fxml"));
        AnchorPane pane = loader.load();
        popUpAddModule.getChildren().setAll(pane);
    }
}
