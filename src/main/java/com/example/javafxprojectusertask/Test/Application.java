package com.example.javafxprojectusertask.Test;

import com.example.javafxprojectusertask.Controller.CoachController.DashbordController;
import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.prefs.Preferences;



public class Application extends javafx.application.Application {


    @Override
    public void start(Stage stage) throws Exception {
      /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginAdmin/loginPage.fxml"));
     Parent   root = loader.load();
     UserUtils userUtils = new UserUtils();
     userUtils.clearUser();
        Scene scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();*/
        Preferences preferences = Preferences.userNodeForPackage(Application.class);
        UserUtils userUtils = new UserUtils();
        UserClass userClass =new UserClass();

        UserUtils.ConnectedUser = userUtils.getConnectedUser();
        Parent root;
        if (UserUtils.ConnectedUser.getIdUser() != -1) {

            if(UserUtils.ConnectedUser.getStatus().equals("Admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
                root = loader.load();
                DashBordController dashBordController = loader.getController();
                System.out.println("FirstCheck:"+UserUtils.ConnectedUser.getIdUser());
                dashBordController.setData( UserUtils.ConnectedUser.getIdUser());
                Scene scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();
            }
             if (UserUtils.ConnectedUser.getStatus().equals("User"))
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
                root = loader.load();
                WelcomePageController welcomePageController = loader.getController();
                welcomePageController.setDataUser( UserUtils.ConnectedUser.getIdUser());
                Scene scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();
            }
           else if (UserUtils.ConnectedUser.getStatus().equals("Coach"))
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Coach/DashbordCoach.fxml"));
                root = loader.load();
                DashbordController dashBordController = loader.getController();
                dashBordController.setData( UserUtils.ConnectedUser.getIdUser());
                Scene scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();
            }
        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/loginAdmin/loginPage.fxml"));
            root = loader.load();
            Scene scene = new Scene(root, 1366, 786);
            stage.setScene(scene);
            stage.show();
        }

       /* Scene scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();*/
    }


    public static void main(String[] args) {

        launch();


    }
}


