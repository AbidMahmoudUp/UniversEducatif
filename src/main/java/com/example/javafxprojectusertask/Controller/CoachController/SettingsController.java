package com.example.javafxprojectusertask.Controller.CoachController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Test.Application;
import com.example.javafxprojectusertask.Services.AdminProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Entities.ProfileAdmin;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsController  implements Initializable {

    //All the FXML Declaration are overhere i make it a long line :) !!

    @FXML
   private Label idUser;
    @FXML
   private Label profileInformations;
    @FXML
    private Label warnningLabel1;
    @FXML
    private Label titleLabel;
    @FXML
    private Label EmailLabel;
    @FXML
    private Label titlePageLabel;
    @FXML
    private Label responsabiltyLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label adressLabel;
    @FXML
    private Label PhoneNumberLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel,confirmePasswordErrorLabel,newPasswordErrorLabel,oldpasswordErrorLabel;
    @FXML
    private Label warnningLabel2 , EmailErrorLabel , phoneNumberErrorLabel;

    @FXML
    private  Line decorationLane;

    @FXML
    private TextField firstNameTextFiled;
    @FXML
    private TextField lastnameTextFiled;
    @FXML
    private TextField PhoneNumberTextFiled;
    @FXML
    private TextField AdressTextFiled;
    @FXML
    private TextField titleTextFiled;
    @FXML
    private TextField locationTextFiled;
    @FXML
     TextField EmailTextFiled;
    @FXML
    private TextField ResponsabiltyTextFiled;
    @FXML
    private Button UpdateButton , CancelButton;
    @FXML
    private AnchorPane PasswordUpdateAnchorPane , informationProfileAnchorPane;
    @FXML
    private PasswordField oldPasswordFiled , newPasswordFiled , confirmePasswordFiled ;
    @FXML
    private TextField TestFiledOldPassword,TestFiledNewPassword,TestFiledConfrimePassword;
    @FXML
    private ImageView visibilityOffConfirmePassword
            ,visibiltyOnOldPassword,
            visibiltyOffOldPassword,
            visibilityOnNewPassword,
            visibiltyOffNewPassword,
            VisibiltyOnConfirmePassword;
    public static int recivedData;

    UserClass user = new UserClass();
    UserService userService = new UserService();
  //  user = userController.

    UserUtils userUtils = new UserUtils();
    UserClass userClass = new UserClass();
    ProfileAdmin profileAdmin = new ProfileAdmin();
    ProfileService profileService = new ProfileService();
    Profile profile = new Profile();

    public void setData(int data){
        System.out.println("before:"+recivedData);
        this.recivedData = data;
        System.out.println("after:"+recivedData);
        System.out.println("the data passed in parameter is: " + data);
        this.idUser.setText(String.valueOf(data));
        System.out.println("hi im in the Settings now: " + this.idUser.getText());
        System.out.println("location text :"+locationTextFiled.getText());

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AdminProfileService adminProfileService =new AdminProfileService();

        userClass=userUtils.getConnectedUser();
        profile=profileService.getProfileUser(userClass.getIdUser());
        profileAdmin= adminProfileService.getAdminData(profile);
        System.out.println(userClass.getIdUser());
        this.EmailTextFiled.setText(userClass.getEmail());
        this.PhoneNumberTextFiled.setText(String.valueOf(profile.getPhoneNumber()));

        System.out.println(profileAdmin);
        this.locationTextFiled.setText(profile.getLocation());
        this.ResponsabiltyTextFiled.setText(profileAdmin.getReponsabilty());
        this.titleTextFiled.setText(profileAdmin.getTitle());
        this.firstNameTextFiled.setText(profile.getFirstName());
        this.lastnameTextFiled.setText(profile.getLastName());
        this.AdressTextFiled.setText(profile.getAddress());



    }

    @FXML
    public void UpdateProfileAdmin(ActionEvent event) throws SQLException {
        try {
            boolean isValid = true;
            if(!validateEmail(EmailTextFiled.getText(),EmailErrorLabel))
            {
                isValid=false;
            }
            if(!validatePhoneNumber(PhoneNumberTextFiled.getText(),phoneNumberErrorLabel))
            {
             isValid=false;
            }
            if(isValid) {
                int phoneNumber = Integer.parseInt(PhoneNumberTextFiled.getText());
                System.out.println("trying the update" + recivedData);
                AdminProfileService adminProfileService = new AdminProfileService();
                Profile profile1 = new Profile(recivedData, recivedData, firstNameTextFiled.getText(), lastnameTextFiled.getText(), "", AdressTextFiled.getText(), locationTextFiled.getText(), phoneNumber);
                ProfileAdmin profileAdmin = new ProfileAdmin(recivedData, recivedData, firstNameTextFiled.getText(), lastnameTextFiled.getText(), "", AdressTextFiled.getText(), locationTextFiled.getText(), phoneNumber, ResponsabiltyTextFiled.getText(), titleTextFiled.getText());
                profileService.UpdateProfileUser(profile1);
                adminProfileService.updateProfileAdmin(profileAdmin);
            }
            else
            {
                showErrorAndAnimate(phoneNumberErrorLabel);
                showErrorAndAnimate(EmailErrorLabel);
            }
    } catch (NumberFormatException e) {

        System.err.println("Error parsing integer value: " + e.getMessage());

    }

    }
    @FXML
    public void GoToProfileInformations(MouseEvent event)
    {
        informationProfileAnchorPane.setVisible(true);
        PasswordUpdateAnchorPane.setVisible(false);
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
    public void SwitchToUpdatePassword(MouseEvent event)
    {
        informationProfileAnchorPane.setVisible(false);
        PasswordUpdateAnchorPane.setVisible(true);
    }
    @FXML
    public void UpdatePassword(ActionEvent event)
    {    boolean isValid = true;
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
    public void phoneNumberOnFocus(ActionEvent event){
        PhoneNumberTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePhoneNumber(PhoneNumberTextFiled.getText(),phoneNumberErrorLabel);
            }
        });
    }
    @FXML
    public void EmailOnFocus(ActionEvent event ){
        EmailTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateEmail(EmailTextFiled.getText(),EmailErrorLabel);
            }
        });
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

                if(email.equals(UserUtils.ConnectedUser.getEmail()))
                {
                    errorLabel.setText("");
                    return  true;
                }
                else{errorLabel.setVisible(true);
                errorLabel.setText("Vous avez déja un compte avec cet email !");
                return false; // Return false if the email exists in the database
            }
        }}
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
