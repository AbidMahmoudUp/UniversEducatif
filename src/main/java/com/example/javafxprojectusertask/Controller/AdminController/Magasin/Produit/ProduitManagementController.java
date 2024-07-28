package com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit;

import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.ProduitService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProduitManagementController implements Initializable {

    @FXML
    private FlowPane flowPaneModules;

    @FXML
    private MenuButton trieur;

    @FXML
    private TextField rech;

    @FXML
    private ImageView addIcon;

    @FXML
    private AnchorPane popUpAddModule;

    @FXML
    private Circle circleAdd;

    private ProduitService produitService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        circleAdd.toFront();
        addIcon.toFront();
        flowPaneModules.setPadding(new Insets(0, 0, 0, 10));
        flowPaneModules.setHgap(10);
        flowPaneModules.setVgap(10);

        // Initialiser le service de produit
        produitService = new ProduitService();

        // Récupérer les types de produit à partir du service et les ajouter au MenuButton trieur
        ArrayList<String> typesProduits = produitService.getTypesProduits();
        typesProduits.forEach(type -> {
            MenuItem menuItem = new MenuItem(type);
            menuItem.setOnAction(event -> filterByType(type));
            trieur.getItems().add(menuItem);
        });

        // Charger les données des produits au démarrage
        try {
            SetDataProduit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SetDataProduit() throws IOException {
        ArrayList<Produit> produits = produitService.getProduitAll();
        for (Produit produit : produits) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/cards/CardProduit.fxml"));
            AnchorPane pane = loader.load();
            CardProduitController cardProduitController = loader.getController();
            cardProduitController.setProduit(produit);
            cardProduitController.initializeData(produit);
            flowPaneModules.getChildren().add(pane);
        }
    }

    @FXML
    public void openPopUp() throws IOException {
        popUpAddModule.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/AddProduit.fxml"));
        AnchorPane pane = loader.load(getClass().getResource("/GUI/DashBordAdmin/Magasin/AddProduit.fxml"));
        AddProduitController addModuleController = loader.getController();
        popUpAddModule.getChildren().setAll(pane);
    }

    private void filterByType(String selectedType) {
        flowPaneModules.getChildren().clear(); // Effacez les cartes de produit existantes

        // Utilisez le service de produit pour récupérer les produits filtrés par type
        ArrayList<Produit> produitsFiltres = produitService.getProduitsByType(selectedType);

        // Ajoutez les nouveaux produits filtrés à la FlowPane
        for (Produit produit : produitsFiltres) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/cards/CardProduit.fxml"));
            try {
                AnchorPane pane = loader.load();
                CardProduitController cardModuleController = loader.getController();
                cardModuleController.initializeData(produit);
                flowPaneModules.getChildren().add(pane);
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
            try {
                SetDataProduit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void filterProduits(String searchText) {
        flowPaneModules.getChildren().clear(); // Effacer les cartes de produit existantes

        // Utilisez le service de produit pour rechercher les produits en fonction du texte de recherche
        ArrayList<Produit> produits = produitService.searchProduits(searchText);

        // Ajouter les nouvelles cartes de produit filtrées à la FlowPane
        for (Produit produit : produits) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/cards/CardProduit.fxml"));
            try {
                AnchorPane pane = loader.load();
                CardProduitController cardModuleController = loader.getController();
                cardModuleController.initializeData(produit);
                flowPaneModules.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
