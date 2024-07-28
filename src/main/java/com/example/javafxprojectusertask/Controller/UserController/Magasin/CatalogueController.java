package com.example.javafxprojectusertask.Controller.UserController.Magasin;

import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.ProduitService;
import com.example.javafxprojectusertask.Utilities.GlobalListMapSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CatalogueController implements Initializable {
    @FXML
    private FlowPane FlowPaneproduit;

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane aa;

    @FXML
    private ImageView cat_flech;

    @FXML
    private ImageView chercher_b;

    @FXML
    private ImageView filter_fleche;

    @FXML
    private MenuButton menucat;

    @FXML
    private MenuButton menufilter;

    @FXML
    private MenuButton menuuniver;

    @FXML
    private Label nbr_produit_panier;

    @FXML
    private TextField rech;

    @FXML
    private ImageView univers_fleche;

    @FXML
    private ScrollPane vers;

    @FXML
    private ImageView vers_panier;


    @FXML
    private MenuButton trieur;


    public ProduitService produitService;

    public List<Map<Produit, Integer>> panierr;

    public void addToList(Map<Produit, Integer> item) {
        if (item != null) {
            int sum=0;
            if (panierr == null) panierr = new ArrayList<>();
            Produit produit = item.keySet().iterator().next();

            for (Map<Produit, Integer> map : panierr) {

                System.out.println(sum+"sum");
                nbr_produit_panier.setText(String.valueOf(sum));



            }

            nbr_produit_panier.setText(String.valueOf(panierr.size()));
            GlobalListMapSingleton globalListMapSingleton = GlobalListMapSingleton.getInstance();
            globalListMapSingleton.setListMap(panierr);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FlowPaneproduit.setPadding(new Insets(0, 0, 0, 10));
        FlowPaneproduit.setHgap(10);
        FlowPaneproduit.setVgap(10);

        // Initialiser le service de produit
        produitService = new ProduitService();

        // Récupérer les types de produit à partir du service et les ajouter au MenuButton trieur
        ArrayList<String> typesProduits = produitService.getTypesProduits();
        typesProduits.forEach(type -> {
            MenuItem menuItem = new MenuItem(type);
            menuItem.setOnAction(event -> filterByType(type));
            if (trieur != null)
                trieur.getItems().add(menuItem);
        });

        // Charger les données des produits au démarrage
        setDataProduit();
        updateNbrProduitPanier();
    }
    private void updateNbrProduitPanier() {
        List<Map<Produit, Integer>> panier = GlobalListMapSingleton.getInstance().getListMap();
        int totalProduits = panier.stream().mapToInt(map -> map.values().stream().mapToInt(Integer::intValue).sum()).sum();
        nbr_produit_panier.setText(String.valueOf(totalProduits));
    }
    public void setDataProduit() {
        ArrayList<Produit> produits = produitService.getProduitAll();

        for (Produit produit : produits) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/cards/CardProduitClient.fxml"));
            try {
                AnchorPane pane = loader.load();
                CardProduitClientController cardProduitClientController = loader.getController();
                cardProduitClientController.initializeData(produit);
                FlowPaneproduit.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterByType(String selectedType) {
        FlowPaneproduit.getChildren().clear(); // Effacez les cartes de produit existantes

        // Utilisez le service de produit pour récupérer les produits filtrés par type
        ArrayList<Produit> produitsFiltres = produitService.getProduitsByType(selectedType);

        // Ajoutez les nouveaux produits filtrés à la FlowPane
        for (Produit produit : produitsFiltres) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/cards/CardProduitClient.fxml"));
            try {
                AnchorPane pane = loader.load();
                CardProduitClientController cardProduitClientController = loader.getController();
                cardProduitClientController.initializeData(produit);
                FlowPaneproduit.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void searchProducts() {
        String searchText = rech.getText();
        if (!searchText.isEmpty()) {
            filterProduits(searchText);
        } else {
            // Si le champ de recherche est vide, afficher tous les produits
            setDataProduit();
        }
    }

    private void filterProduits(String searchText) {
        FlowPaneproduit.getChildren().clear(); // Effacer les cartes de produit existantes

        // Utilisez le service de produit pour rechercher les produits en fonction du texte de recherche
        ArrayList<Produit> produits = produitService.searchProduits(searchText);

        // Ajouter les nouvelles cartes de produit filtrées à la FlowPane
        for (Produit produit : produits) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/cards/CardProduitClient.fxml"));
            try {
                AnchorPane pane = loader.load();
                CardProduitClientController cardProduitClientController = loader.getController();
                cardProduitClientController.initializeData(produit);
                FlowPaneproduit.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML

    void vers_panierr(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/Panier.fxml"));
            AnchorPane panierPane = loader.load();

            // Supposons que vous avez une référence au AnchorPane principal où vous voulez charger le panier
            mainAnchorPane.getChildren().setAll(panierPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
