package com.example.javafxprojectusertask.Controller.AdminController.Admin;

import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.LogsService;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardLogsController implements Initializable {


    @FXML
    private Circle PictureUser;

    @FXML
    private Label action1;

    @FXML
    private Label action2;

    @FXML
    private Label date;

    @FXML
    private Label description1;

    @FXML
    private Label description2;

    @FXML
    private Label fullName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PictureUser.setStroke(null);
    }

    public void setDataCardLogs(Logs logs)
    {
        Profile profile = new Profile();
        ProfileService profileService = new ProfileService();
        UserClass userClass = new UserClass();
        UserService userService= new UserService();
        LogsService logsService = new LogsService();
        ArrayList<Logs> logsList = new ArrayList<>();
        logsList=logsService.getLogsByUser(UserUtils.ConnectedUser.getIdUser());
        profile=profileService.getProfileUser(logs.getIdUser());
        this.date.setText(logs.getDate().toString());
        Image image = new Image(profile.getPicture());
        ImagePattern imagePattern = new ImagePattern(image);
        this.PictureUser.setFill(imagePattern);
        this.fullName.setText(profile.getFirstName()+" "+profile.getLastName());
        this.description1.setText(logs.getDescription1());
        this.action1.setText(logs.getAction1());
        this.description2.setText(logs.getDescription2());
        this.action2.setText(logs.getAction2());

    }
}
