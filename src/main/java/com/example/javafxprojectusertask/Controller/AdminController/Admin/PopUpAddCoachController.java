package com.example.javafxprojectusertask.Controller.AdminController.Admin;

import com.example.javafxprojectusertask.Entities.Logs;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.ProfileCoach;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import at.favre.lib.crypto.bcrypt.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PopUpAddCoachController implements Initializable {


    @FXML
    private AnchorPane PopUpAddCoach;

    @FXML
    private Button addCoach;

    @FXML
    private ImageView backgroundBlur;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView closeButton;

    @FXML
    private TextField AddressTextFiled;

    @FXML
    private TextField EmailTextFiled;

    @FXML
    private TextField FirstNameTextFiled;

    @FXML
    private TextField LastNameTextFiled;

    @FXML
    private TextField LocationTextFiled;

    @FXML
    private PasswordField PasswordTextFiled;

    @FXML
    private TextField PhoneTextFiled;

    @FXML
    private Label LastNameErrorLabel;
    @FXML
    private Label firstNameErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label phoneNumberErrorLabel;
    @FXML
    private Label addresseErrorLabel;
    @FXML
    private Label LocationErrorLabel;

    @FXML
    private ChoiceBox Module;
    Stage stage;
    Scene scene;

    ArrayList<Module> modules;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceModule serviceModule = new ServiceModule();
        modules = serviceModule.getAll();
        Module.getItems().addAll(modules);
    }
    @FXML
    public void ClosepopUp(MouseEvent event) throws IOException {
            PopUpAddCoach.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
        Parent root = loader.load();
        DashBordController dashBordController = loader.getController();
        UserUtils userUtils = new UserUtils();
        UserUtils.ConnectedUser=userUtils.getConnectedUser();
        dashBordController.setData(UserUtils.ConnectedUser.getIdUser());
        dashBordController.SwitchToGestionUtilsateur(event);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();




    }

    @FXML
    void focusOnAddressFiled(ActionEvent event) {
        AddressTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateMaj(AddressTextFiled.getText(),addresseErrorLabel);
            }
        });
    }

    @FXML
    void focusOnEmailFiled(ActionEvent event) {
        EmailTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateEmail(EmailTextFiled.getText(),emailErrorLabel);
            }
        });
    }

    @FXML
    void focusOnFirstNameFiled(ActionEvent event) {
        FirstNameTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateMaj(FirstNameTextFiled.getText(),firstNameErrorLabel);
            }
        });
    }

    @FXML
    void focusOnLastNameFiled(ActionEvent event) {
        LastNameTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateMaj(LastNameTextFiled.getText(),LastNameErrorLabel);
            }
        });
    }

    @FXML
    void focusOnLocationFiled(ActionEvent event) {
        LocationTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateMaj(LocationTextFiled.getText(),LocationErrorLabel);
            }
        });
    }

    @FXML
    void focusOnPasswordFiled(ActionEvent event) {
        PasswordTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword(PasswordTextFiled.getText(),passwordErrorLabel);
            }
        });
    }

    @FXML
    void focusOnPhoneNumberFiled(ActionEvent event) {
        PhoneTextFiled.requestFocus();
        PhoneTextFiled.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePhoneNumber(PhoneTextFiled.getText(),phoneNumberErrorLabel);
            }
        });
    }
    @FXML
    void addCoach(MouseEvent event) throws SQLException, IOException {
        String firstName=  FirstNameTextFiled.getText();
        String lastName= LastNameTextFiled.getText();
        String location= LocationTextFiled.getText();
        String password=  PasswordTextFiled.getText();
        String phoneNumbertext=PhoneTextFiled.getText();

        String email= EmailTextFiled.getText();
        String address = AddressTextFiled.getText() ;
        boolean isValid=true;
        if (!validatePhoneNumber(PhoneTextFiled.getText(),phoneNumberErrorLabel))
        {
            isValid=false;
        }
        if (!validatePassword(PasswordTextFiled.getText(),passwordErrorLabel))
        {
            isValid=false;
        }
        if (!validateEmail(EmailTextFiled.getText(),emailErrorLabel))
        {
            isValid=false;
        }
        if (!validateMaj(LocationTextFiled.getText(),LocationErrorLabel))
        {
            isValid=false;
        }
        if (!validateMaj(AddressTextFiled.getText(),addresseErrorLabel))
        {
            isValid=false;
        }
        if (!validateMaj(FirstNameTextFiled.getText(),firstNameErrorLabel))
        {
            isValid=false;
        }
        if (!validateMaj(LastNameTextFiled.getText(),LastNameErrorLabel))
        {
            isValid=false;
        }
        if (isValid)
        {
            int phoneNumber = Integer.parseInt(phoneNumbertext);
            Module m = (com.example.javafxprojectusertask.Entities.Module) Module.getValue();
        Profile profile = new Profile(0,0,firstName,lastName,"http://127.0.0.1/img/userDefaultIcon.png",address,location,phoneNumber);
       Profile profile1 = new Profile();
       String cryptedPassword = encryptPassword(password);
        UserClass userClass = new UserClass(0,firstName,cryptedPassword,"Coach",email);
        ProfileCoach profileCoach = new ProfileCoach(0,0,firstName,lastName,"",address,location,phoneNumber,m.getIdModule());
        ProfileService profileService = new ProfileService();
        UserClass userClass1 = new UserClass();
        CoachProfileService coachProfileService = new CoachProfileService();
        UserService userService = new UserService();
         userService.addUser(userClass);
        userClass1 = userService.getUserBynameAndEmail(firstName,email);
        profileService.AddProfileUser(profile,userClass1.getIdUser());
        profileService.getProfileUser(userClass1.getIdUser());
        profile1 = profileService.getProfileUser(userClass1.getIdUser());
        coachProfileService.addCoachProfile(profileCoach,profile1.getIdProfile());
          FirstNameTextFiled.clear();
         LastNameTextFiled.clear();
         LocationTextFiled.clear();
          PasswordTextFiled.clear();
           PhoneTextFiled.clear();
           EmailTextFiled.clear();
         AddressTextFiled.clear() ;
        System.out.println("Coach add sucessfly ");
        PopUpAddCoach.setVisible(false);
            Date date = Timestamp.valueOf(LocalDateTime.now());
            Logs log =new Logs(UserUtils.ConnectedUser.getIdUser(), "a ajouté",date,firstName+" "+lastName,"Coach","en tand que nouvel");
            LogsService logsService =new LogsService();
            logsService.addLog(log);
            System.out.println(logsService.getLogsByUser(67));
            ClosepopUp(event);

        }
        else {
            showErrorAndAnimate(LastNameErrorLabel);
            showErrorAndAnimate(firstNameErrorLabel);
            showErrorAndAnimate(addresseErrorLabel);
            showErrorAndAnimate(LocationErrorLabel);
            showErrorAndAnimate(emailErrorLabel);
            showErrorAndAnimate(passwordErrorLabel);
            showErrorAndAnimate(phoneNumberErrorLabel);
        }

    }

    //------------------ValidationMethodesAddCoach----------------------------------------------



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
    private boolean validateMaj(String username, Label errorLabel) {
        Pattern pattern = Pattern.compile("^[A-Z].{3,}$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Il faut Commencer par Une Letter Majusclue");
            errorLabel.setLayoutX(32);
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();
    }
    private boolean validatePhoneNumber(String phoneNumber, Label errorLabel) {
        Pattern pattern = Pattern.compile("^\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Le numéro de téléphone doit être composé de 8 chiffres.");
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
    private void showErrorAndAnimate(Label label) {

        TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), label);
        transition1.setFromX(-10);
        transition1.setToX(10);
        transition1.setCycleCount(4);
        transition1.setAutoReverse(true);
        transition1.play();

    }
    public String encryptPassword(String password) {

        return String.valueOf(BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(10, password.toCharArray()));
    }
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(plainPassword.toCharArray(), hashedPassword.toCharArray());

        return result.verified;

    }
}
