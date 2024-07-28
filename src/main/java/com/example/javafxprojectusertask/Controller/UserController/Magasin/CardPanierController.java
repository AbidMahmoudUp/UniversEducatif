package com.example.javafxprojectusertask.Controller.UserController.Magasin; /**
 * Sample Skeleton for 'CardPanier.fxml' Controller Class
 */

import com.example.javafxprojectusertask.Controller.UserController.Magasin.PanierController;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Utilities.GlobalListMapSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CardPanierController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="down"
    private ImageView down; // Value injected by FXMLLoader

    @FXML // fx:id="image_produit"
    private ImageView image_produit; // Value injected by FXMLLoader

    @FXML // fx:id="nom_produit"
    private Label nom_produit; // Value injected by FXMLLoader
@FXML
private Label iddd;
    @FXML
    private Button pdel;

    @FXML // fx:id="prix_marge"
    private Label prix_marge; // Value injected by FXMLLoader

    @FXML // fx:id="prix_total"
    private Label prix_total; // Value injected by FXMLLoader

    @FXML // fx:id="qte"
    private TextField qte; // Value injected by FXMLLoader

    @FXML // fx:id="up"
    private ImageView up; // Value injected by FXMLLoader

    @FXML
    private Label id_produit;
    public List<Map<Produit, Integer>> panierr;
    public void definirDonnees(Produit produit, Integer quantite) {
        // En supposant que la classe Produit a des méthodes getName, getPriceInit, et getMarge
        nom_produit.setText(produit.getNom());
        prix_marge.setText(String.format("%.2f", produit.getPrix_init() + produit.getMarge()));
        qte.setText(quantite.toString());
        prix_total.setText(String.format("%.2f", quantite * (produit.getPrix_init() + produit.getMarge())));
        id_produit.setText(String.valueOf(produit.getId_produit()));
        // Mettre à jour l'action du bouton de suppression
        //pdel.setOnMouseClicked(event -> supprimerCarte());
    }

    private void supprimerCarte() {
        // Supprimer cette carte de l'UI, nécessite une référence au conteneur parent
        // Cette partie nécessite un ajustement en fonction de votre structure spécifique
    }



    @FXML
    int decrimenter(MouseEvent event) {
        int value = Integer.parseInt(qte.getText());
              if (value > 0) {
            qte.setText(String.valueOf(value - 1));
            double prix=(Double.parseDouble(prix_marge.getText().replace(',','.')));
            double prixT=prix*(value - 1);

            prix_total.setText(String.valueOf(prixT));
                  updateQuantiteDansListe(value);

        }
        return value;
    }

    @FXML
    int incrimenter(MouseEvent event) {
        int value = Integer.parseInt(qte.getText());
        qte.setText(String.valueOf(value + 1));
        System.out.println(prix_marge.getText());
        double prix=(Double.parseDouble(prix_marge.getText().replace(',','.')));
        System.out.println(prix);
        double prixT=prix*(value - 1);
        System.out.println(prixT);
        prix_total.setText(String.valueOf(prixT));
        updateQuantiteDansListe(value);
        return value;
    }
    private void updateQuantiteDansListe(int nouvelleQuantite) {
        int produitId = Integer.parseInt(id_produit.getText());
        List<Map<Produit, Integer>> listMap = GlobalListMapSingleton.getInstance().getListMap();

        for (Map<Produit, Integer> map : listMap) {
            Produit produit = map.keySet().iterator().next();
            if (produit.getId_produit() == produitId) {
                map.put(produit, nouvelleQuantite); // Mettre à jour la quantité
                break;
            }
        }
    }



 // This method is called by the FXMLLoader when initialization is complete


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert down != null : "fx:id=\"down\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert iddd != null : "fx:id=\"iddd\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert image_produit != null : "fx:id=\"image_produit\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert nom_produit != null : "fx:id=\"nom_produit\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert prix_marge != null : "fx:id=\"prix_marge\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert prix_total != null : "fx:id=\"prix_total\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert qte != null : "fx:id=\"qte\" was not injected: check your FXML file 'CardPanier.fxml'.";
        assert up != null : "fx:id=\"up\" was not injected: check your FXML file 'CardPanier.fxml'.";

    }
   @FXML
    public void remove(MouseEvent event) throws IOException {
        final List<Map<Produit, Integer>> list = GlobalListMapSingleton.getInstance().getListMap();
        final int produit_id = Integer.parseInt( this.id_produit.getText()) ;
        for(Map<Produit,Integer> item : list) {
            if(produit_id==item.keySet().iterator().next().getId_produit()) {
                list.remove(item);
            }
        }
        GlobalListMapSingleton.getInstance().setListMap(list);
       this.panierr=GlobalListMapSingleton.getInstance().getListMap();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Magasin/Panier.fxml"));
        loader.load();
        PanierController panierController=loader.getController();
        panierController.panierr=GlobalListMapSingleton.getInstance().getListMap();


    }



    private PanierController panierController;

    public void setPanierController(PanierController panierController) {
        this.panierController = panierController;
    }

}
