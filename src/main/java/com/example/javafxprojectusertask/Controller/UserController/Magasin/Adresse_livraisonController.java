/**
 * Sample Skeleton for 'Adresse_livrasion.fxml' Controller Class
 */

package com.example.javafxprojectusertask.Controller.UserController.Magasin;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.Achat;
import com.example.javafxprojectusertask.Entities.Commande;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.AchatService;
import com.example.javafxprojectusertask.Services.CommandeService;
import com.example.javafxprojectusertask.Utilities.CustomCircleMarkerLayer;
import com.example.javafxprojectusertask.Utilities.GlobalListMapSingleton;
import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Adresse_livraisonController {
    private Connection cnx;
    public Adresse_livraisonController() {
        this.cnx = DataBaseConnection.getInstance().getCnx();
    }
    public String adr="soussa";
    public int idUser=22;
    public List<Map<Produit, Integer>> panierr;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="adresse_forapi"
    private ImageView adresse_forapi; // Value injected by FXMLLoader

    @FXML // fx:id="card_adresseLoad"
    private AnchorPane card_adresseLoad; // Value injected by FXMLLoader

    @FXML // fx:id="cardadrloader"
    private AnchorPane cardadrloader; // Value injected by FXMLLoader

    @FXML // fx:id="scroll_adresse"
    private ScrollPane scroll_adresse; // Value injected by FXMLLoader

    @FXML // fx:id="vers_payment"
    private ImageView vers_payment; // Value injected by FXMLLoader

    @FXML
    void choisirAdresse(MouseEvent event) {

    }

    @FXML
    void ouvrirPaiement(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert adresse_forapi != null : "fx:id=\"adresse_forapi\" was not injected: check your FXML file 'Adresse_livrasion.fxml'.";
        assert card_adresseLoad != null : "fx:id=\"card_adresseLoad\" was not injected: check your FXML file 'Adresse_livrasion.fxml'.";
        assert cardadrloader != null : "fx:id=\"cardadrloader\" was not injected: check your FXML file 'Adresse_livrasion.fxml'.";
        assert scroll_adresse != null : "fx:id=\"scroll_adresse\" was not injected: check your FXML file 'Adresse_livrasion.fxml'.";
        assert vers_payment != null : "fx:id=\"vers_payment\" was not injected: check your FXML file 'Adresse_livrasion.fxml'.";

    }

  @FXML
    void valide_cmd(MouseEvent event) throws IOException {
      GlobalListMapSingleton globalListMapSingleton = GlobalListMapSingleton.getInstance();
      this.panierr = globalListMapSingleton.getListMap();
        for (Map<Produit, Integer> article : panierr) {
            //GlobalListMapSingleton globalListMapSingleton = GlobalListMapSingleton.getInstance();
          //  this.panierr = globalListMapSingleton.getListMap();
            Produit produit = article.keySet().iterator().next();
            int quantite = article.get(produit);
            CommandeService commandeService = new CommandeService();
            AchatService achatService = new AchatService();
            Achat a = new Achat();
            Commande c = new Commande();
            c.setId_user(idUser);
            c.setDate(LocalDate.now());
            c.setAdresse(adr);
            //c.setId_cmd();
            a.setIdc(c);
            a.setId_produit(produit.getId_produit());
            a.setMont_tot((produit.getPrix_init() + produit.getMarge() * quantite));
            a.setQte(quantite);
            commandeService.addCommande(c);
            achatService.addAchat(a);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));

            Parent root = loader.load();
            WelcomePageController dashBordController = loader.getController();
            dashBordController.SwitchToWelcomePage(event);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
            UserUtils userUtils = new UserUtils();
            UserUtils.ConnectedUser=userUtils.getConnectedUser();
            dashBordController.setDataUser(UserUtils.ConnectedUser.getIdUser());
        }


    }
}
