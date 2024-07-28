package com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Entities.Produit;
import com.example.javafxprojectusertask.Services.ProduitService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CardProduitController {

    @FXML
    private ImageView Bdelete;
    @FXML
    private ImageView ImgModulePath;

    @FXML
    private Label descriptionModule;

    @FXML
    private Label nomModule;

    @FXML
    private Label typeProduit;

    @FXML
    private Label prixProduit;

    @FXML
    private Label margeProduit;

    @FXML
    private ImageView id_produit;

    private Produit produit; // Define a variable to store the product

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    private int idd;

    public void initializeData(Produit p) {
        nomModule.setText(p.getNom());
        descriptionModule.setText(p.getDescrip());
        typeProduit.setText(p.getType());
        prixProduit.setText(String.valueOf(p.getPrix_init()));
        margeProduit.setText(String.valueOf(p.getMarge()));
        idd = p.getId_produit();
    }

    public void redirection(MouseEvent mouseEvent) {
        Object userData = id_produit.getUserData();

        if (userData instanceof String) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Magasin/UpdateProduit.fxml"));
                AnchorPane pane = loader.load();

                UpdateProduitController updateProduitController = loader.getController();
                ProduitService ps = new ProduitService();
                Produit produit = ps.getProduitById(idd);

                updateProduitController.initializeFieldsForUpdate(produit);
                Stage stage = new Stage();
                stage.setScene(new Scene(pane));
                stage.showAndWait();

            } catch (NumberFormatException e) {
                System.err.println("La chaîne ne peut pas être convertie en un entier : " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("UserData n'est pas une chaîne.");
        }
    }

    @FXML
    private void deleteProduitt(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de la suppression");
        alert.setContentText("Voulez-vous vraiment supprimer ce produit ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {


            ProduitService ps = new ProduitService();
            if (ps.deleteProduit(this.produit)) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Failed to delete product!");
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        Parent root = loader.load();
        DashBordController dashBordController = loader.getController();
        UserUtils userUtils = new UserUtils() ;
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        dashBordController.SwitchToMagasin(event);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1366,786);
        stage.setScene(scene);
        stage.show();
    }

    public int sendId() {
        return idd;
    }
}
