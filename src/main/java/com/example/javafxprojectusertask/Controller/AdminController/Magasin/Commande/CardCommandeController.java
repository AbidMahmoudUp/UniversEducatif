package com.example.javafxprojectusertask.Controller.AdminController.Magasin.Commande;

import com.example.javafxprojectusertask.Entities.Commande;
import com.example.javafxprojectusertask.Services.CommandeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CardCommandeController {
    @FXML
    private Label etat;

    @FXML
    private ImageView Bdelete;

    @FXML
    private ImageView id_produit;

    @FXML
    private Label nomModule;

    @FXML
    private Label typeProduit;

    @FXML
    private Label prixProduit;

    @FXML
    private Label margeProduit;

    @FXML
    private Label descriptionModule;

    private int idd;

    public void initializeData(Commande commande) {
        idd = commande.getId_cmd();
      //  displayCommandeDetails(commande);
    }

  /*  private void displayCommandeDetails(Commande commande) {
        nomModule.setText("ID de la commande: " + commande.getId_cmd());
        // Vérifiez si le panier associé à la commande est null avant d'accéder à son ID
        if (commande.getPanier() != null) {
            typeProduit.setText("ID du panier: " + commande.getPanier().getId_panier());
            nomModule.setText("date"+commande.getPanier().getDate());

           etat.setText("etat non traitée");
           etat.setText("etat en livraison");
        } else {
            typeProduit.setText("Panier non spécifié");
        }
        prixProduit.setText("Prixinit+Marge");
        margeProduit.setText("Prixinit+Marge");
        //descriptionModule.setText("Description du produit");
    }*/

    @FXML
    private void deleteCommande(MouseEvent event) {
        // Ajoutez ici le code pour supprimer la commande en fonction de l'ID de la commande (idd)

        // Utilisez le service CommandeService pour effectuer cette action.
        CommandeService commandeService = new CommandeService();
        Commande commande = commandeService.getCommandeById(idd);
        if (commande != null) {
            boolean deleted = commandeService.deleteCommande(commande);
            if (deleted) {
                System.out.println("La commande a été supprimée avec succès.");
            } else {
                System.out.println("La suppression de la commande a échoué.");
            }
        } else {
            System.out.println("La commande avec l'ID spécifié n'existe pas.");
        }
    }

    @FXML
    private void redirection(MouseEvent mouseEvent) {
        // Ajoutez ici le code pour gérer la redirection lorsque l'utilisateur clique sur l'image ou une autre zone de la commande.
        // Cette méthode sera appelée lorsqu'un événement de clic est détecté sur l'élément avec l'ID id_produit.
        // Vous pouvez mettre ici la logique de redirection vers une autre vue ou effectuer d'autres actions nécessaires.
        System.out.println("Redirection vers une autre vue ou action spécifique.");
    }
}
