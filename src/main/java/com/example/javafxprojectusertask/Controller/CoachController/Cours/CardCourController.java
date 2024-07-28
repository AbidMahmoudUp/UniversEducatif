package com.example.javafxprojectusertask.Controller.CoachController.Cours;

import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.Cour;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceCour;
import com.example.javafxprojectusertask.Services.ServiceModule;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class CardCourController {

    @FXML
    private Label descriptionCour;

    @FXML
    private Label nomCour;

    @FXML
    private Label tempsC;
    @FXML
    private Hyperlink pdfPath;
    @FXML
    private ImageView updateIcon;
    @FXML
    private ImageView deleteIcon;
    private Cour cour ;
    @FXML
    private AnchorPane ParentAnchor;

    @FXML
    public static AnchorPane anchorPane;
    private CoursManagementController coursManagementController=new CoursManagementController();
    public void setCour(Cour cour) {
        this.cour = cour;
    }

    public ImageView getUpdateIcon() {
        return updateIcon;
    }

    public ImageView getDeleteIcon() {
        return deleteIcon;
    }

    @FXML
    void deleteCour(MouseEvent event) throws IOException {
        ServiceCour serviceCour = new ServiceCour();
        serviceCour.delete(this.cour);
        ServiceModule sm = new ServiceModule();
        Module module = sm.getModulebyId(this.cour.getID_Module());
        module.supprimerCour(this.cour);
        coursManagementController.SetDataCour();
    }

    @FXML
    void updateCour(MouseEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Cours/UpdateCour.fxml"));
        Parent root;
        root = loader2.load();
        UpdateCourController updateCourController = loader2.getController();
        updateCourController.setCour(this.cour);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        coursManagementController.SetDataCour();
    }

    public static void openFilee(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Le fichier n'existe pas.");
            return;
        }

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier : " + e.getMessage());
        }
    }
    @FXML
    void ouvrirPdf(MouseEvent event) throws URISyntaxException {
        System.out.println(pdfPath.getText());
        URI uri = new URI(pdfPath.getText());
        String ftext="C:/xampp/htdocs"+uri.getPath();
        System.out.println(ftext);
        openFilee(ftext);
    }

    public void initialize()
    {
        System.out.println("SDQSDSQ");
        if(UserUtils.ConnectedUser.getStatus().equalsIgnoreCase("coach"))
        {
            deleteIcon.setVisible(true);
            updateIcon.setVisible(true);
        }

    }
    public void initializeData(Cour cour) {
        if(UserUtils.ConnectedUser.getStatus().equalsIgnoreCase("coach"))
        {
            deleteIcon.setVisible(true);
            updateIcon.setVisible(true);
        }
        this.cour = cour;
        nomCour.setText(cour.getNom());
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0,0,0,0.4));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        ParentAnchor.setEffect(dropShadow);
        descriptionCour.setText(cour.getDescription());
        pdfPath.setText(cour.getFichierPath());
        int t = cour.getTempsEstimeC();
        tempsC.setText("" + t);
    }
    public void initializeData(Cour cour, CoursManagementController coursManagementController) {
        if(UserUtils.ConnectedUser.getStatus().equalsIgnoreCase("coach"))
        {
            deleteIcon.setVisible(true);
            updateIcon.setVisible(true);
        }
        this.coursManagementController=coursManagementController;
        this.cour = cour;

        nomCour.setText(cour.getNom());
        descriptionCour.setText(cour.getDescription());
        int t = cour.getTempsEstimeC();
        tempsC.setText("" + t);



    }
    @FXML
    void voirdetails(MouseEvent event) throws IOException {
        anchorPane.getChildren().clear();
        FXMLLoader coursdetails = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
        AnchorPane details =  coursdetails.load();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        WelcomePageController controller=coursdetails.getController();
        controller.setDataUser(UserUtils.ConnectedUser.getIdUser());
        controller.switchToCoursDetails(event,cour);
        //anchorPane.getChildren().setAll(details);

    }
    }

