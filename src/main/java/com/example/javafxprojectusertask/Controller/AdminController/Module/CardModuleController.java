package com.example.javafxprojectusertask.Controller.AdminController.Module;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.CoachController.Cours.CoursManagementController;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CardModuleController {

    @FXML
    private ImageView ImgModulePath;

    @FXML
    private Label descriptionModule;

    @FXML
    private Label nomModule;


    @FXML
    private Label tempsM;
    @FXML
    private ImageView deleteIcon;
    @FXML
    private ImageView updateIcon;

    public ImageView getDeleteIcon() {
        return deleteIcon;
    }

    public ImageView getUpdateIcon() {
        return updateIcon;
    }

    private Module module;
    private ModuleManagementController moduleManagementController = new ModuleManagementController();



    public void setModule(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public void initializeData(Module m) {
        System.out.println(m);
        this.module = m;
        nomModule.setText(m.getNom());
        descriptionModule.setText(m.getDescription());
        int t = m.getTempsEstimeM();
        tempsM.setText("" + t);
        System.out.println(t);
        String IP = m.getModuleImgPath();
            Image img = new Image(IP);
            ImgModulePath.setImage(img);

    }
    public void initializeData(Module m,ModuleManagementController moduleManagementController) {
        this.moduleManagementController=moduleManagementController;
        this.module = m;
        System.out.println(m);
        nomModule.setText(m.getNom());
        descriptionModule.setText(m.getDescription());
        int t = m.getTempsEstimeM();
        tempsM.setText("" + t);
        System.out.println(t);
        String IP = m.getModuleImgPath();


            Image img = new Image(IP);
            ImgModulePath.setImage(img);

    }


    public ImageView getImgModulePath() {
        return ImgModulePath;
    }

    public Label getDescriptionModule() {
        return descriptionModule;
    }

    public Label getNomModule() {
        return nomModule;
    }


    @FXML
    public void updateModule(MouseEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/UpdateModule.fxml"));
        Parent root;
        root = loader2.load();
        UpdateModuleController updateModuleController = loader2.getController();
        updateModuleController.setModule(this.module);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        moduleManagementController.SetDataModules();



    }


    public void deleteModule(MouseEvent event) throws IOException {
        ServiceModule sm = new ServiceModule();
        sm.delete(this.module);
        moduleManagementController.SetDataModules();
    }

    @FXML
    void SwitchToModule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Module/ModuleManagement.fxml"));
            Parent root = loader.load();
            DashBordController dashBordController = loader.getController();
            /*Stage stage = dashBordController.getStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();*/


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   @FXML
    void voirCours(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            Parent root = loader.load();
            DashBordController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
            CoursManagementController.idModule = this.module.getIdModule();
            controller.switchToCours();
            controller.setData(UserUtils.ConnectedUser.getIdUser());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }



}
