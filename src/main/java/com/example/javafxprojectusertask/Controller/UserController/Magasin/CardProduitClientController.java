package com.example.javafxprojectusertask.Controller.UserController.Magasin;

import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.ProduitService;
import com.example.javafxprojectusertask.Utilities.GlobalListMapSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CardProduitClientController {
    @FXML
    Label id;
    @FXML
    private ImageView ajoutp;
    @FXML
    private AnchorPane ParentAnchorCard;

    @FXML
    private ImageView dec;

    @FXML
    private Label i;

    @FXML
    private ImageView image_produit;

    @FXML
    private ImageView inc;

    @FXML
    private Label nomProduit;

    @FXML
    private Label prix;

    private Produit produit;

    @FXML
    int decrimenter(MouseEvent event) {
        int value = Integer.parseInt(i.getText());
        if (value > 0) {
            i.setText(String.valueOf(value - 1));
        }
        return value;
    }

    @FXML
    int incrimenter(MouseEvent event) {
        int value = Integer.parseInt(i.getText());
        i.setText(String.valueOf(value + 1));
        return value;
    }

    @FXML
    void initialize() {
        // Initialisation du contrôleur
    }

    @FXML
    void loadRatePanier(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/cards/Rate+aupanier .fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void retour(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/cards/CardProduitClient.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeData(Produit p) {
        nomProduit.setText(p.getNom());
        prix.setText(String.valueOf(p.getPrix_init() + p.getMarge()) + "   DT");
        id.setText(String.valueOf(p.getId_produit()));
        int idd = p.getId_produit();
    }

    @FXML
    void ajout_au_panier(MouseEvent event) {
        ProduitService produitService = new ProduitService();
        Produit p = produitService.getProduitById(Integer.parseInt(id.getText()));
        // p.setId_produit(id);
        int n = 0;
        Map<Produit, Integer> map = new HashMap<>();
        if (p != null && i.getText() != null && !i.getText().isEmpty()) {

            map.put(p, Integer.parseInt(i.getText()));
            afficherMap(map);
            GlobalListMapSingleton.getInstance().addToListMap(map);


        } else {
            // Gérer le cas où produit ou le texte de i est nul ou vide
        }
    }


    private void afficherMap(Map<Produit, Integer> map) {
        for (Map.Entry<Produit, Integer> entry : map.entrySet()) {
            Produit produit = entry.getKey();
            Integer quantite = entry.getValue();
            System.out.println("Produit : " + produit + ", Quantité : " + quantite);
        }
    }
}


