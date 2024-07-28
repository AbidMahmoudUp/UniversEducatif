package com.example.javafxprojectusertask.Controller.UserController.Cours;

import com.example.javafxprojectusertask.Controller.UserController.Module.CardModuleControllerUser;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;

public class CourPageController {
    @FXML
    private AnchorPane CoursAnchorPane , AnchorScrollPaneModule;

    @FXML
    private FlowPane CoursFlowPane;

    @FXML
    private ScrollPane CoursScrollPane;
    @FXML
    private AnchorPane coursDetails;
    @FXML
    private FlowPane DetailsFlowPane;

    public FlowPane getDetailsFlowPane() {
        return DetailsFlowPane;
    }

    public FlowPane getCoursFlowPane() {
        return CoursFlowPane;
    }

    public AnchorPane getCoursDetails() {
        return coursDetails;
    }

    public AnchorPane getCoursAnchorPane() {
        return CoursAnchorPane;
    }

    public void setCoursFlowPane(FlowPane coursFlowPane)  {
        CoursFlowPane = coursFlowPane;

    }

    @FXML
    void switchToCours(MouseEvent event)  {CoursAnchorPane.setVisible(true);
    }
    @FXML
    public void switchTooCours()  {
        Platform.runLater(() -> {
            CoursAnchorPane.setVisible(true);
        });
    }
    public void SetDataModules() throws IOException {
        ServiceModule sm= new ServiceModule();
        CoursFlowPane.getChildren().clear();
        ArrayList<Module> modules = sm.getAll();
        System.out.println(modules);
        for (Module module : modules) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/CardModuleUser.fxml"));
            AnchorPane pane = loader.load();
            CardModuleControllerUser cardModuleControllerUser = loader.getController();
            cardModuleControllerUser.setModule(module);
            cardModuleControllerUser.initializeData(module,CoursFlowPane);
            CoursFlowPane.getChildren().add(pane);

        }
    }

    @FXML
    void initialize() {

       // DropShadow dropShadow = new DropShadow();

       // dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
       // dropShadow.setRadius(10);
       // dropShadow.setOffsetX(0);
       // dropShadow.setOffsetY(5);
       // AnchorScrollPaneModule.setEffect(dropShadow);
        System.out.println("SSSS");
        CoursScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        CoursScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        assert CoursAnchorPane != null : "fx:id=\"CoursAnchorPane\" was not injected: check your FXML file 'courPage.fxml'.";
        assert CoursFlowPane != null : "fx:id=\"CoursFlowPane\" was not injected: check your FXML file 'courPage.fxml'.";
        assert CoursScrollPane != null : "fx:id=\"CoursScrollPane\" was not injected: check your FXML file 'courPage.fxml'.";
        CoursFlowPane.setPadding(new Insets(10,10,10,10));
        CoursFlowPane.setHgap(10);
        CoursFlowPane.setVgap(10);
        try {
            this.SetDataModules();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
