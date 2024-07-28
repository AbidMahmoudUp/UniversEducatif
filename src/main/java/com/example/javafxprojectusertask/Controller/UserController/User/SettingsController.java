package com.example.javafxprojectusertask.Controller.UserController.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.javafxprojectusertask.Controller.LoginController;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileAdmin;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.AdminProfileService;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Test.Application;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsController implements Initializable {

    UserClass user = new UserClass();
    UserService userService = new UserService();
    //  user = userController.

    UserUtils userUtils = new UserUtils();
    UserClass userClass = new UserClass();
    int recivedData=0;
    @FXML
    TextField firstNameTextFiled;
    @FXML
    TextField lastnameTextFiled;
    @FXML
    TextField PhoneNumberTextFiled;
    @FXML
    TextField EmailTextFiled;
    @FXML
    TextField locationTextFiled;
    @FXML
    TextField AdressTextFiled;
    @FXML
    private Label lastNameLabel,confirmePasswordErrorLabel,newPasswordErrorLabel,oldpasswordErrorLabel;
    @FXML
    PasswordField oldPasswordFiled;
    @FXML
    PasswordField confirmePasswordFiled;
    @FXML
    PasswordField newPasswordFiled;
    @FXML
    AnchorPane PasswordUpdateAnchorPane;
    @FXML
    AnchorPane informationProfileAnchorPane,deleteAnchorPane,OptionsAnchorPane;
    @FXML
    private TextField TestFiledOldPassword,TestFiledNewPassword,TestFiledConfrimePassword;
   @FXML
    private ImageView visibilityOffConfirmePassword
            ,visibiltyOnOldPassword,
            visibiltyOffOldPassword,
            visibilityOnNewPassword,
            visibiltyOffNewPassword,
            VisibiltyOnConfirmePassword;

    @FXML
    Label idUser;
    Stage stage;
    Scene scene;

    ProfileService profileService = new ProfileService();
    Profile profile = new Profile();
    public void setData(int data){
        System.out.println("before:"+recivedData);
        this.recivedData = data;
        System.out.println("after:"+recivedData);
        System.out.println("the data passed in parameter is: " + data);
        this.idUser.setText(String.valueOf(data));
        System.out.println("hi im in the Settings now: " + this.idUser.getText());


        try {
            user = userService.getUserById(data);
            System.out.println("User object: " + user);
            if (user != null) {
                String email = user.getEmail();
                EmailTextFiled.getParent().requestFocus();
                System.out.println("Email retrieved from UserClass: " + email);
                this.EmailTextFiled.setText("aaaaaaaaaaa" + email);
                System.out.println("EmailTextField text set successfully");
            } else {
                System.out.println("User object is null");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void oldPasswordTextFiled(ActionEvent event)
    {
        oldPasswordFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword(oldPasswordFiled.getText(),oldpasswordErrorLabel);
            }
        });
    }
    @FXML
    public void NewPasswordTextFiled(){
        newPasswordFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword(newPasswordFiled.getText(),newPasswordErrorLabel);
            }
        });
    }
    @FXML
    public void confirmePasswordTextFiled()
    {
        confirmePasswordFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword(confirmePasswordFiled.getText(),confirmePasswordErrorLabel);
            }
        });
    }
    @FXML
    public  void UpdatePassword(ActionEvent event){
        boolean isValid = true;
        if(!validatePassword(oldPasswordFiled.getText(),oldpasswordErrorLabel))
        {
            isValid=false;
        }
        if(!validatePassword(newPasswordFiled.getText(),newPasswordErrorLabel))
        {
            isValid=false;
        }
        if(!validatePassword((confirmePasswordFiled.getText()),confirmePasswordErrorLabel))
        {
            isValid=false;
        }
        if (isValid)
        {
            UserUtils userUtils = new UserUtils();
            user = userUtils.getConnectedUser();
            UserService userService1 = new UserService();
            System.out.println("idUser is: "+user.getIdUser());
            System.out.println("Password old filed is:"+oldPasswordFiled.getText());
            if(verifyPassword(oldPasswordFiled.getText(),user.getPassword()))
            {
                if(newPasswordFiled.getText().equals(confirmePasswordFiled.getText()))
                {
                    try {
                        String cryptedPassword = encryptPassword(newPasswordFiled.getText());
                        userService1.updatePassword(user , cryptedPassword );
                        Preferences preferences = Preferences.userNodeForPackage(Application.class);
                        user= userService1.getUserById(user.getIdUser());
                        userUtils.saveObjectToPreferences(preferences,user);
                        System.out.println("password is: "+user.getPassword());


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(user.getPassword());
                }
                else {
                    showErrorAndAnimate(confirmePasswordErrorLabel);
                }
            }else {
                oldpasswordErrorLabel.setText("le anicien mot de passe est incorrect !");
                showErrorAndAnimate(oldpasswordErrorLabel);
            }}else {
            showErrorAndAnimate(oldpasswordErrorLabel);
            showErrorAndAnimate(newPasswordErrorLabel);
            showErrorAndAnimate(confirmePasswordErrorLabel);
        }
    }
    @FXML
    public void visibilityOffOldPassword(MouseEvent event){
        TestFiledOldPassword.setText(oldPasswordFiled.getText());
        oldPasswordFiled.setVisible(false);
        TestFiledOldPassword.setVisible(true);
        visibiltyOffOldPassword.setVisible(false);
        visibiltyOnOldPassword.setVisible(true);
        TestFiledOldPassword.requestFocus();
    }
    @FXML
    public void visibilityOnOldPassword(MouseEvent event){
        oldPasswordFiled.setText(TestFiledOldPassword.getText());
        oldPasswordFiled.setVisible(true);
        visibiltyOffOldPassword.setVisible(true);
        visibiltyOnOldPassword.setVisible(false);
        TestFiledOldPassword.setVisible(false);
        oldPasswordFiled.requestFocus();
    }
    private void showErrorAndAnimate(Label label) {

        TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), label);
        transition1.setFromX(-10);
        transition1.setToX(10);
        transition1.setCycleCount(4);
        transition1.setAutoReverse(true);
        transition1.play();

    }
    @FXML
    public void visibilityOffNewPassword(MouseEvent event)
    {
        TestFiledNewPassword.setText(newPasswordFiled.getText());
        newPasswordFiled.setVisible(false);
        TestFiledNewPassword.setVisible(true);
        visibilityOnNewPassword.setVisible(true);
        visibiltyOffNewPassword.setVisible(false);
        TestFiledNewPassword.requestFocus();
    }
    @FXML
    public void visibilityOnNewPassword(MouseEvent event)
    {
        newPasswordFiled.setText(TestFiledNewPassword.getText());
        newPasswordFiled.setVisible(true);
        visibiltyOffNewPassword.setVisible(true);
        visibilityOnNewPassword.setVisible(false);
        TestFiledNewPassword.setVisible(false);
        newPasswordFiled.requestFocus();
    }

    @FXML
    public void visibilityOffConfirmePassword(MouseEvent event)
    {
        TestFiledConfrimePassword.setText(confirmePasswordFiled.getText());
        confirmePasswordFiled.setVisible(false);
        TestFiledConfrimePassword.setVisible(true);
        visibilityOffConfirmePassword.setVisible(false);
        VisibiltyOnConfirmePassword.setVisible(true);
        TestFiledConfrimePassword.requestFocus();
    }
    public void visibilityOnConfirmePassword(MouseEvent event)
    {
        confirmePasswordFiled.setText(TestFiledConfrimePassword.getText());
        confirmePasswordFiled.setVisible(true);
        visibilityOffConfirmePassword.setVisible(true);
        VisibiltyOnConfirmePassword.setVisible(false);
        TestFiledConfrimePassword.setVisible(false);
        confirmePasswordFiled.requestFocus();
    }
    @FXML
    public  void UpdateProfileUser(){
        try {
            user = userUtils.getConnectedUser();
            int phoneNumber = Integer.parseInt(PhoneNumberTextFiled.getText());
            System.out.println("trying the update" + user.getIdUser());
            Profile profile1 = new Profile(user.getIdUser(),user.getIdUser(),firstNameTextFiled.getText(),lastnameTextFiled.getText(),"",AdressTextFiled.getText(),locationTextFiled.getText(),phoneNumber);
            profileService.UpdateProfileUser(profile1);


        } catch (NumberFormatException e) {

            System.err.println("Error parsing integer value: " + e.getMessage());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void SwitchToUpdatePassword(MouseEvent event){
        informationProfileAnchorPane.setVisible(false);
        PasswordUpdateAnchorPane.setVisible(true);
        deleteAnchorPane.setVisible(true);
    }
    @FXML
    public void switchToProfileInformation(){
        informationProfileAnchorPane.setVisible(true);
        PasswordUpdateAnchorPane.setVisible(false);
        deleteAnchorPane.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        PasswordUpdateAnchorPane.setEffect(dropShadow);
        informationProfileAnchorPane.setEffect(dropShadow);
        deleteAnchorPane.setEffect(dropShadow);
        OptionsAnchorPane.setEffect(dropShadow);
        deleteAnchorPane.setVisible(false);
        userClass=userUtils.getConnectedUser();
        profile=profileService.getProfileUser(userClass.getIdUser());
        System.out.println(userClass.getIdUser());
        this.EmailTextFiled.setText(userClass.getEmail());
        this.PhoneNumberTextFiled.setText(String.valueOf(profile.getPhoneNumber()));


        this.locationTextFiled.setText(profile.getLocation());

        this.firstNameTextFiled.setText(profile.getFirstName());
        this.lastnameTextFiled.setText(profile.getLastName());
        this.AdressTextFiled.setText(profile.getAddress());
    }
    public String encryptPassword(String password) {

        return String.valueOf(BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(10, password.toCharArray()));
    }
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(plainPassword.toCharArray(), hashedPassword.toCharArray());

        return result.verified;

    }

    private boolean validateEmail(String email, Label errorLabel) {
        UserService userService = new UserService();
        Pattern pattern = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Email invalide");
            return false; // Return false if the email format is invalid
        } else {
            errorLabel.setText("");
            // Check if the email exists in the database
            if (userService.doesUserExist(email)) {
                errorLabel.setVisible(true);
                errorLabel.setText("Vous avez déja un compte avec cet email !");
                return false; // Return false if the email exists in the database
            }
        }
        return true; // Return true if the email format is valid and doesn't exist in the database
    }
    private boolean validatePhoneNumber(String phoneNumber, Label errorLabel) {
        Pattern pattern = Pattern.compile("^\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            errorLabel.setVisible(true);
            errorLabel.setText(" 8 chiffres demandée!.");
            errorLabel.setLayoutX(32);
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();
    }
    private boolean validatePassword(String password, Label errorLabel) {
        Pattern pattern = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Mot de passe invalide");
            errorLabel.setLayoutX(32);
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();
    }

}
