package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import com.example.javafxprojectusertask.Controller.CoachController.DashbordController;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileCoach;
import com.example.javafxprojectusertask.Services.CoachProfileService;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.ServiceModule;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceExam;
import com.example.javafxprojectusertask.Entities.Exam;

import java.io.IOException;
import java.sql.SQLException;

public class ExamController {
    @FXML
    private ImageView addIcon;
    @FXML
    private ImageView ASC;
    @FXML
    private ImageView DESC;
    private final ServiceExam serviceExam = new ServiceExam();

    @FXML
    private FlowPane examFlowPane;
    @FXML
    private TextField chercher;
    @FXML
    private MenuButton tripar;
    private String tri="ASC";
    private String trierpar="Module";

    @FXML
    private AnchorPane DisplayExamenAnchor;

    @FXML
    private ScrollPane ScrollPaneExamen;
    public static AnchorPane moduleAnchorPane;
    public static DashbordController dashBordController;
    public void replaceModuleAnchorPaneContent(AnchorPane content) {
        moduleAnchorPane.getChildren().clear();
        moduleAnchorPane.getChildren().setAll(content);
    }
    @FXML
    public void initialize() {
        addIcon.setOnMouseClicked(this::handleAddIconClicked);
        ASC.setOnMouseClicked(this::handleASC);
        DESC.setOnMouseClicked(this::handleDESC);
        examFlowPane.setHgap(10);
        examFlowPane.setVgap(10);
        examFlowPane.setPadding(new Insets(10, 10, 10, 10));
        ScrollPaneExamen.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneExamen.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        DisplayExamenAnchor.setEffect(dropShadow);
        loadExamData();
    }
    private void handleAddIconClicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/CoachTemplate/Examen/addExamen.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root,406,485));
            stage.setTitle("Examen");
            stage.showAndWait();
            loadExamData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        tripar.setText(menuItem.getText());
        trierpar=tripar.getText();
    }

    private void handleASC(MouseEvent event) {
        tri="ASC";
        loadExamData();

    }
    private void handleDESC(MouseEvent event) {
        tri="DESC";
        loadExamData();
    }
    public void loadExamData() {
        examFlowPane.getChildren().clear();
        ProfileCoach profileCoach = new ProfileCoach();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        CoachProfileService coachProfileService = new CoachProfileService();
        ProfileService profileService = new ProfileService();
        Profile profile = new Profile();
        profile=profileService.getProfileUser(UserUtils.ConnectedUser.getIdUser());
        try {
            profileCoach=coachProfileService.getCoachProfile(profile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Module module = new Module();
        ServiceModule serviceModule=new ServiceModule();
        module = serviceModule.getModulebyId(profileCoach.getIdModule());
        for (Exam exam : serviceExam.getAl(module.getNom(),tri,trierpar)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Examen/CardExamen.fxml"));
                AnchorPane card = loader.load();
                ExamCardController controller = loader.getController();
                controller.setExam(exam);
                controller.setExamController(this);
                examFlowPane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




