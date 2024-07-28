package com.example.javafxprojectusertask.Controller.AdminController.Module;

import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModuleManagementController  implements Initializable {
    @FXML
    private ScrollPane ScrollPaneModule;

    @FXML
    private FlowPane flowPaneModules;
    @FXML
    private ImageView addIcon  ;

    @FXML
    private AnchorPane popUpAddModule;

    @FXML
    private Circle circleAdd;

    @FXML
    private AnchorPane ParentAnchor;
    @FXML
    private TextField chercher;
    @FXML
    public static AnchorPane ModuleManagement;

    @FXML
    void chercherModule(KeyEvent event) throws IOException {
        ServiceModule sm= new ServiceModule();
        final List<Module> modules = sm.getAll();
        final String searchParam = chercher.getText();
        if(searchParam != null && !searchParam.isEmpty()) {
            List<Module> filteredList = modules.stream()
                    .filter(item -> item.getNom().contains(searchParam) ||
                            item.getDescription().contains(searchParam) ||
                            String.valueOf(item.getTempsEstimeM()).contains(searchParam))
                    .collect(Collectors.toList());
            this.searchModuleCard(filteredList);
        } else {
            this.searchModuleCard(modules);
        }
    }


    public void SetDataModules() throws IOException {
        ServiceModule sm= new ServiceModule();
        flowPaneModules.getChildren().clear();
        ArrayList<Module> modules = sm.getAll();
        for (Module module : modules) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/CardModule.fxml"));
            AnchorPane pane = loader.load();
            CardModuleController cardModuleController = loader.getController();

            cardModuleController.initializeData(module,this);
            flowPaneModules.getChildren().add(pane);
        }
        }

    public void searchModuleCard(List<Module> modules) throws IOException {
        flowPaneModules.getChildren().clear();
        for (Module module : modules) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/CardModule.fxml"));
            AnchorPane pane = loader.load();
            CardModuleController cardModuleController = loader.getController();
          //  cardModuleController.setModuleManagementController(this);
            cardModuleController.initializeData(module);
            flowPaneModules.getChildren().add(pane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        circleAdd.toFront();
        addIcon.toFront();
        popUpAddModule.setVisible(false);
        ParentAnchor.setVisible(true);
        flowPaneModules.setPadding(new Insets(0,0,0,10));
        flowPaneModules.setHgap(10);
        flowPaneModules.setVgap(10);
        chercher.setOnKeyReleased(event -> {
            try {
                chercherModule(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            this.SetDataModules();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    public void openPopUp(MouseEvent event) throws IOException {
        popUpAddModule.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/AddModule.fxml"));
        AnchorPane pane = loader.load();
        AddModuleController addModuleController = loader.getController();
        addModuleController.setModuleManagementController(this);
        popUpAddModule.getChildren().setAll(pane);

    }

    public void updateModule () throws IOException {
        popUpAddModule.setVisible(true);
        popUpAddModule.toFront();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/UpdateModule.fxml"));
        AnchorPane pane = loader.load();
        popUpAddModule.getChildren().setAll(pane);
    }
    @FXML
    public void openPopUpUpdate()throws  IOException{
        popUpAddModule.setVisible(true);
        popUpAddModule.toFront();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/UpdateModule.fxml"));
        Parent pane = loader.load();
        UpdateModuleController updateModuleController = loader.getController();
        popUpAddModule.getChildren().setAll(pane);

    }
    @FXML
    void triAsc(MouseEvent event) throws IOException {
        ServiceModule sm= new ServiceModule();
        flowPaneModules.getChildren().clear();
        ArrayList<Module> modules = sm.triByTempsEstimeM("ASC");
        for (Module module : modules) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/CardModule.fxml"));
            AnchorPane pane = loader.load();
            CardModuleController cardModuleController = loader.getController();
            cardModuleController.initializeData(module);
            flowPaneModules.getChildren().add(pane);
        }

    }

    @FXML
    void triDesc(MouseEvent event) throws IOException {
        ServiceModule sm= new ServiceModule();
        flowPaneModules.getChildren().clear();
        ArrayList<Module> modules = sm.triByTempsEstimeM("DESC");
        for (Module module : modules) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Module/CardModule.fxml"));
            AnchorPane pane = loader.load();
            CardModuleController cardModuleController = loader.getController();
            cardModuleController.initializeData(module);
            flowPaneModules.getChildren().add(pane);
        }
    }
    public AnchorPane getAnchorPanePopUp(){
        return popUpAddModule;
    }

    public AnchorPane getParentAnchor(){return ParentAnchor;}
    public  FlowPane getFlowPaneModules(){ return  this.flowPaneModules;}
    public ScrollPane getScrollPaneModule(){return this.ScrollPaneModule;}

    public void replace(AnchorPane pane) {

    }

    public void initializeModule(Parent parent) throws IOException {
        ModuleManagement = new AnchorPane();
        ModuleManagement = FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Module/ModuleManagement.fxml"));
        ModuleManagement.getChildren().add(parent);
    }
}
