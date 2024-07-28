package com.example.javafxprojectusertask.Controller.AdminController.Admin;

import com.example.javafxprojectusertask.Entities.GoogleOauth;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.GoogleService;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardUserController implements Initializable {

    @FXML
    Label FullNameUser;
    @FXML
    Circle PictureUser;
    @FXML
    Button buttontry;
    @FXML
    AnchorPane CardAnchorPane , DetailsAnchorPane;

    @FXML
    ImageView DetailsCardUser;
    @FXML
    public Label EmailLabelUser;
    @FXML
    public Label idUser;
    @FXML
    public Label StatusLabel;
    @FXML
            public ImageView stateImage;
    boolean statusDetails = false;
    boolean state=false;
    Stage stage;
            Scene scene;
    public void setData(UserClass user ) throws IOException {
        ProfileService profileService = new ProfileService();
        this.EmailLabelUser.setText(user.getEmail());
        Profile newprofile = new Profile();
        newprofile= profileService.getProfileUser(user.getIdUser());
        this.EmailLabelUser.setText(user.getEmail());
        this.idUser.setText(String.valueOf( newprofile.getIdUser()));
        this.FullNameUser.setText(newprofile.getFirstName()+" "+newprofile.getLastName());
        this.StatusLabel.setText(user.getStatus());
        String pictureUrl =newprofile.getPicture();
        System.out.println(newprofile.getPicture());
        if (pictureUrl != null) {
            Image originalImage = new Image(pictureUrl);
            ImagePattern imagePattern = new ImagePattern(originalImage);
            PictureUser.setFill(imagePattern);
        } else {
            // Handle the case when the picture URL is null
        }
        /*Image originalImage = new Image(newprofile.getPicture());
        ImagePattern imagePattern = new ImagePattern(originalImage);
        PictureUser.setFill(imagePattern);*/
        if(user.getState().equals("Active"))
        {
            Image banned =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/activeIcon.png");
            stateImage.setImage(banned);
            state=true;
        }
        if(user.getState().equals("Banned"))
        {
            Image banned =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/Banned.png");
            stateImage.setImage(banned);
            state=false;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        CardAnchorPane.setEffect(dropShadow);
    }
    @FXML
    public void DetailsUserCard(MouseEvent event)
    {
        if(!statusDetails){
            DetailsAnchorPane.setVisible(true);
            DetailsAnchorPane.toFront();
            statusDetails=true;
        }
       else if (statusDetails){
            DetailsAnchorPane.setVisible(false);
            statusDetails=false;
        }

    }
    @FXML
    public void SwitchState(MouseEvent event) throws SQLException {
        UserClass  userClass= new UserClass();
        UserService userservice = new UserService();
        userClass= userservice.getUserById(Integer.parseInt(idUser.getText()));
        if (!state){
            userClass.setState("Banned");
            userservice.updateStateUser(userClass);
            Image banned =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/Banned.png");
            stateImage.setImage(banned);
            state=true;
        }
        else{
            userClass.setState("Active");
            userservice.updateStateUser(userClass);
            Image imageDESC =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/activeIcon.png");
            stateImage.setImage(imageDESC);
            state=false;
        }
    }

    @FXML
    void DeleteUser(MouseEvent event) throws SQLException, IOException {
        Profile profile = new Profile();
        UserClass userClass =new UserClass();
        UserService userService = new UserService();
        ProfileService profileService = new ProfileService();
        GoogleService googleService = new GoogleService();
        profile=profileService.getProfileUser(Integer.parseInt(idUser.getText()));
        userClass = userService.getUserById(Integer.parseInt(idUser.getText()));
        GoogleOauth googleOauth = googleService.getUserByEmail(userClass.getEmail());
        userService.deleteUser(userClass);
        profileService.DeleteProfileUser(profile);
        if(googleOauth!=null)
        {
            googleService.deleteUser(googleOauth);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        Parent root = loader.load();
        DashBordController dashBordController = loader.getController();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser=userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();
        System.out.println("ggggg");
        dashBordController.SwitchToGestionUtilsateur(event);
        System.out.println("ggggggggggggggggggg");



    }
    @FXML
    public void Interactvity(){

    }
}
