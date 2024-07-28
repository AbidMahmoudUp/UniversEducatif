package com.example.javafxprojectusertask.Controller.UserController.Magasin;

import com.example.javafxprojectusertask.Controller.UserController.Magasin.CardPanierController;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Utilities.GlobalListMapSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PanierController {

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private FlowPane anchor;
    @FXML
    private Label Prix_livraison;

    @FXML
    private Label SommeArticle_Paniers;

    @FXML
    private ScrollPane card_panierLoad;

    @FXML
    private Label nbr_article;

    @FXML
    private Label vers_commande;


    public List<Map<Produit, Integer>> panierr;
    @FXML
    void initialize() {
        assert Prix_livraison != null : "fx:id=\"Prix_livraison\" was not injected: check your FXML file 'Panier.fxml'.";
        assert SommeArticle_Paniers != null : "fx:id=\"SommeArticle_Paniers\" was not injected: check your FXML file 'Panier.fxml'.";
        assert card_panierLoad != null : "fx:id=\"card_panierLoad\" was not injected: check your FXML file 'Panier.fxml'.";
        assert nbr_article != null : "fx:id=\"nbr_article\" was not injected: check your FXML file 'Panier.fxml'.";
        assert vers_commande != null : "fx:id=\"vers_commande\" was not injected: check your FXML file 'Panier.fxml'.";

        // Charge les cartes du panier depuis la base de données
        GlobalListMapSingleton globalListMapSingleton = GlobalListMapSingleton.getInstance();
        this.panierr =  globalListMapSingleton.getListMap();
        loadCartesPanier();

    }
    // Dans PanierController.java
    public void updatePanierView() {
        anchor.getChildren().clear(); // Efface tous les éléments de l'interface
        loadCartesPanier(); // Recharge l'affichage à partir de la liste mise à jour
    }


    public void loadCartesPanier() {
        if (panierr == null) {
            System.out.println("La liste panierr est null !");
            return;
        }
        int nbr_total_art=0;
       double prixTot=0;
        for (Map<Produit, Integer> article : panierr) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/cards/CardPanier.fxml"));
                AnchorPane carte = loader.load();
                CardPanierController controleur = loader.getController();

                Produit produit = article.keySet().iterator().next();
                int quantite = article.get(produit);

                controleur.definirDonnees(produit, quantite);


                anchor.getChildren().add(carte);
                card_panierLoad.setContent(anchor);

             nbr_total_art=nbr_total_art+quantite;
                prixTot=prixTot+(produit.getPrix_init()+produit.getMarge()*quantite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        nbr_article.setText(String.valueOf(nbr_total_art));
        SommeArticle_Paniers.setText(String.valueOf(prixTot));
        card_panierLoad.setContent(anchor);
    }
    @FXML
    void vers_liv(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/Adresse_livrasion.fxml"));
            AnchorPane panierPane = loader.load();

            // Supposons que vous avez une référence au AnchorPane principal où vous voulez charger le panier
            mainAnchorPane.getChildren().setAll(panierPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  /*  void loadAdresseLivraison() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Adresse_livraison.fxml"));
            AnchorPane adresseLivraisonPane = loader.load();
            card_panierLoad.setContent(adresseLivraisonPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
