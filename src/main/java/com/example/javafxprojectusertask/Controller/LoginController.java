package com.example.javafxprojectusertask.Controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.CoachController.DashbordController;
import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.GoogleOauth;
import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Services.*;
import com.example.javafxprojectusertask.Test.Application;
import com.example.javafxprojectusertask.Entities.UserClass;
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
import javafx.scene.image.Image;
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

public class LoginController implements Initializable {


    UserService userService = new UserService();
    AdminProfileService adminProfileService =new AdminProfileService();
    ProfileService profileService = new ProfileService();

    UserUtils userUtils = new UserUtils();

    UserClass user=new UserClass();


//--------------------------------------------------------------------------------------------
@FXML
private Hyperlink inscrivez_vous_login;
    @FXML
    private Label se_connecter;
    @FXML
    private Label email_label_login;
    @FXML
    private Label password_label_login;
    @FXML
    private Label dont_have_account_label_login;
    @FXML
    private TextField email_input_login;
    @FXML
    private PasswordField password_input_login;
    @FXML
    private ImageView email_icon_login;
    @FXML
    private ImageView invisible_password_login;

    @FXML
    private Label Sinscrire;
    @FXML
    private Label username_label_signup;
    @FXML
    private Label email_label_signup;
    @FXML
    private Label password_label_signup;
    @FXML
    private Label confirmepassword_label_signup;
    @FXML
    private Label have_account_Label;

    //-------------ErrorLabelsDeclaration-----------------------------
    @FXML
    private Label UserNameErrorLabel;
    @FXML
    private Label EmailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label confirmePasswordErrorLabel;
    @FXML
    private Label ValidationLogin;
    @FXML
    private Label emailForgetPasswordErrorLabel;
    @FXML
    private Label verificationCodeErrorLabel;
    @FXML
    private Label ResetPasswordErrorLabel;

    @FXML
    private TextField username_input_signup;
    @FXML
    private TextField email_input_signup;
    @FXML
    private TextField VisiblePasswordFeild;

    @FXML
    private ImageView visibilityIconVisible;

    @FXML
    private TextField visibilityStateValue;

    @FXML
    private PasswordField password_input_signup;
    @FXML
    private PasswordField confirmepassword_input_signup;
    @FXML
    private ImageView user_icon_sign_up;
    @FXML
    private ImageView email_icon_sign_up;
    @FXML
    private ImageView visibilty_signup;
    @FXML
    private ImageView confirm_password_visibilty_signup;
    @FXML
    private Button signup_button_signup_page;
    @FXML
    private Button Sign_in_login;
    @FXML
    private Hyperlink sign_in_sign_up_page;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane signin_form;
    @FXML
    private AnchorPane forgetPasswordFormAnchor;
    @FXML
    private AnchorPane verificationFormAnchorPane;
    @FXML
    private AnchorPane resetPasswordFormAnchorPane;
    @FXML
    private Hyperlink forget_password_login;
    @FXML
    private TextField email_input_forgetPassword;
    @FXML
    private TextField verificationCodeInput;
    @FXML
    private PasswordField PasswordInputRest;
    @FXML
    private PasswordField confirmePasswordRest;
    @FXML
    private TextField VisiblePasswordFeildConfirmePassword;
    @FXML
    private ImageView visibilityIconVisibleConfirmePassword;
    @FXML
    private TextField VisiblePasswordFeildLogin;
    @FXML
    private ImageView visibilityIconVisibleLogin;

    @FXML
    private AnchorPane verificationFormSignUpAnchorPane;
    @FXML
    private TextField ConfirmationCodeInput;
    @FXML
    private AnchorPane verificationFormGoogleAnchorPane;
@FXML
private Label verificationCodeErrorLabel1;
    private Stage stage;

    @FXML
    private TextField GoogleCode;
    String  verificationCode;
private Scene scene;
private Parent root;
    private boolean passwordVisible=false;

    GoogleAuth googleAuth = new GoogleAuth();
    public void handleSignInButton(MouseEvent event) {
        verificationFormGoogleAnchorPane.setVisible(true);
        verificationFormSignUpAnchorPane.setVisible(false);
        forgetPasswordFormAnchor.setVisible(false);
        signup_form.setVisible(false);
        signin_form.setVisible(false);
        forgetPasswordFormAnchor.setVisible(false);
        verificationFormAnchorPane.setVisible(false);
       googleAuth.signInWithGoogle();


    }

    @FXML
    public void try2(ActionEvent event) throws SQLException, IOException {
       GoogleOauth googleOauth = new GoogleOauth();
       GoogleService googleService = new GoogleService();
       String googleCode = GoogleCode.getText();

       googleOauth= googleAuth.handleAuthorizationCode(googleCode);
       UserClass userClass = new UserClass();
       UserService userService1 = new UserService();
       userClass = userService1.getUserByEmail(googleOauth.getEmail());
       GoogleOauth googleOauth1 = googleService.getUserById(googleOauth.getId());

       if(userClass!=null)
       {
           System.out.println("i m already existing in the database");
           UserClass userDatabase = userService1.getUserByEmail(googleOauth.getEmail());
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
           Parent root = loader.load();
           WelcomePageController welcomePageController=loader.getController();
           Preferences preferences = Preferences.userNodeForPackage(Application.class);
           System.out.println("The Id that i will send is:"+userDatabase.getIdUser());
           welcomePageController.setDataUser(userDatabase.getIdUser());
           userUtils.saveObjectToPreferences(preferences, userDatabase);
           stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           scene = new Scene(root, 1366, 786);
           stage.setScene(scene);
           stage.show();
       }
       else{
           System.out.println("i m not in the database");
           googleService.addUser(googleOauth);

          // UserUtils userUtils1 = new UserUtils();

           String password = encryptPassword(googleOauth.getName()) ;
           UserClass userClass1 = new UserClass(0,googleOauth.getName(),password,"User",googleOauth.getEmail(),"Active");
           userService.addUser(userClass1);
           UserClass userDatabase = userService1.getUserByEmail(googleOauth.getEmail());
           Profile profile = new Profile(0,userDatabase.getIdUser(),googleOauth.getGiven_name(),googleOauth.getFamily_name(),googleOauth.getPicture(),"",googleOauth.getLocale(),0);
           ProfileService profileService1 = new ProfileService();
           profileService1.AddProfileUser(profile,userDatabase.getIdUser());
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
           Parent root = loader.load();
           WelcomePageController welcomePageController=loader.getController();
           Preferences preferences = Preferences.userNodeForPackage(Application.class);
           System.out.println("The Id that i will send is:"+userDatabase.getIdUser());
           welcomePageController.setDataUser(userDatabase.getIdUser());
           userUtils.saveObjectToPreferences(preferences, userDatabase);
           stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           scene = new Scene(root, 1366, 786);
           stage.setScene(scene);
           stage.show();


       }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            signin_form.setVisible(true);
            signup_form.setVisible(false);
            verificationFormAnchorPane.setVisible(false);
            resetPasswordFormAnchorPane.setVisible(false);
            forgetPasswordFormAnchor.setVisible(false);
            ValidationLogin.setVisible(false);
            VisiblePasswordFeild.setVisible(false);
            VisiblePasswordFeildConfirmePassword.setVisible(false);
            visibilityIconVisibleConfirmePassword.setVisible(false);
            VisiblePasswordFeildLogin.setVisible(false);
            verificationFormSignUpAnchorPane.setVisible(false);
        verificationFormGoogleAnchorPane.setVisible(false);

    }

    @FXML
    public void navigationToSignUp(ActionEvent event)
    {
        signin_form.setVisible(false);
        signup_form.setVisible(true);
        verificationFormAnchorPane.setVisible(false);
        resetPasswordFormAnchorPane.setVisible(false);
        forgetPasswordFormAnchor.setVisible(false);
        verificationFormGoogleAnchorPane.setVisible(false);
    }

    @FXML void forget_password_login(ActionEvent event)
    {
        signin_form.setVisible(false);
        signup_form.setVisible(false);
        verificationFormAnchorPane.setVisible(false);
        resetPasswordFormAnchorPane.setVisible(false);
        forgetPasswordFormAnchor.setVisible(true);
        verificationFormGoogleAnchorPane.setVisible(false);
        email_input_forgetPassword.clear();
        verificationCodeInput.clear();

    }

    @FXML
    void emailVerificationOnFocus(){

        email_input_forgetPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateEmailPasswordForget(email_input_forgetPassword.getText(),emailForgetPasswordErrorLabel);
            }});
    }
    @FXML void GoToVerification(ActionEvent event) throws Exception {

        boolean isValid = false;
        if(validateEmailPasswordForget(email_input_forgetPassword.getText(),emailForgetPasswordErrorLabel))
        {
            isValid=true;
        }
        if (isValid){
        String email = email_input_forgetPassword.getText();
        UserClass userClass = new UserClass();
        UserService userService1 = new UserService();
        userClass = userService1.getUserByEmail(email);
        String imagePath="C:/xampp/htdocs/img/Signiature.png";
        if(userClass!=null)
        {
            Gmailer gmailer = new Gmailer();
            verificationCode= generateVerificationCode();
            String SubjectMail = "Verification Code for UniversEducatif";
            String messageEmail = "Cher "+ userClass.getUserName()+",\n\n"
                    + "Vous avez demandé à vérifier votre adresse e-mail pour UniversEducatif."
                    + "Votre code de vérification est le suivant : " + verificationCode + "\n\n"
                    + "Veuillez utiliser ce code pour finaliser le processus de vérification.\n\n"
                    + "Cordialement,\n"
                    + "L'équipe de BroGrammers";

            gmailer.sendMail(SubjectMail,messageEmail,email);
            signin_form.setVisible(false);
            signup_form.setVisible(false);
            verificationFormAnchorPane.setVisible(true);
            resetPasswordFormAnchorPane.setVisible(false);
            forgetPasswordFormAnchor.setVisible(false);
        }}
        else
        {
            showErrorAndAnimate(emailForgetPasswordErrorLabel);
        }

    }
    @FXML
   public void CodeVerificationOnFocus(){
        verificationCodeInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateVerificationCode(verificationCodeInput.getText(),verificationCode,verificationCodeErrorLabel);
            }});
    }
    @FXML void goToResetPassword(ActionEvent event)
    {
        boolean isValid = false;
        if(validateVerificationCode(verificationCodeInput.getText(),verificationCode,verificationCodeErrorLabel))
        {
            isValid=true;
        }
        if(isValid){
        String verifCode = verificationCodeInput.getText();
        if(verifCode.equals(verificationCode))
        {
            signin_form.setVisible(false);
            signup_form.setVisible(false);
            verificationFormAnchorPane.setVisible(false);
            forgetPasswordFormAnchor.setVisible(false);
            resetPasswordFormAnchorPane.setVisible(true);

        }}
        else {
            showErrorAndAnimate(verificationCodeErrorLabel);
        }

    }
    @FXML
    public void PasswordResetOnFocus()
    {
        PasswordInputRest.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword(PasswordInputRest.getText(),passwordErrorLabel);
            }});
    }
    @FXML
    public void ConfirmePasswordResetOnFocus()
    {
        confirmePasswordRest.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateConfirmPassword(PasswordInputRest.getText(),confirmePasswordRest.getText(),confirmePasswordErrorLabel);
            }});
    }
    @FXML void ResetPassword(ActionEvent event) throws SQLException {
        boolean isValid = false;
        if(validatePassword(PasswordInputRest.getText(),passwordErrorLabel)&&validateConfirmPassword(PasswordInputRest.getText(),confirmePasswordRest.getText(),confirmePasswordErrorLabel))
        {
            isValid=true;
        }
        if (isValid)
        {
        String password = PasswordInputRest.getText();
        String confirmePassword=confirmePasswordRest.getText();
        if(password.equals(confirmePassword))
        {
            String email = email_input_forgetPassword.getText();
            UserClass userClass = new UserClass();
            UserService userService1 = new UserService();
            userClass = userService1.getUserByEmail(email);
            String cryptedPassword = encryptPassword(password);
            userService1.updatePassword(userClass,cryptedPassword);
            PasswordInputRest.clear();
            confirmePasswordRest.clear();
            signin_form.setVisible(true);
            signup_form.setVisible(false);
            verificationFormAnchorPane.setVisible(false);
            forgetPasswordFormAnchor.setVisible(false);
            resetPasswordFormAnchorPane.setVisible(false);
            verificationFormSignUpAnchorPane.setVisible(false);
        }}
        else {
            showErrorAndAnimate(ResetPasswordErrorLabel);
        }

    }
    @FXML
    public void navigationToSignIn(ActionEvent event)
    {
        signin_form.setVisible(true);
        signup_form.setVisible(false);
        verificationFormAnchorPane.setVisible(false);
        forgetPasswordFormAnchor.setVisible(false);
        resetPasswordFormAnchorPane.setVisible(false);
        verificationFormSignUpAnchorPane.setVisible(false);
        verificationFormGoogleAnchorPane.setVisible(false);
    }
    @FXML void navigationToSignInFromReset(ActionEvent event){
        signin_form.setVisible(true);
        signup_form.setVisible(false);
        verificationFormAnchorPane.setVisible(false);
        forgetPasswordFormAnchor.setVisible(false);
        resetPasswordFormAnchorPane.setVisible(false);
    }
    @FXML
    public void focusOnUsernameFiled()
    {
        Image imageUserName =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/user_Used.png");
        Image imageEmail =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/email_Unused.png");
        Image imagePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Unused.png");
        Image imageConfirmePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Unused.png");

        user_icon_sign_up.setImage(imageUserName);
        email_icon_sign_up.setImage(imageEmail);
        visibilty_signup.setImage(imagePassword);
        confirm_password_visibilty_signup.setImage(imageConfirmePassword);
        username_input_signup.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateUsername(username_input_signup.getText(), UserNameErrorLabel);
            }
        });

    }
    @FXML
    public void focusOnEmailFiled()
    {

        Image imageUserName =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/user_Unused.png");
        Image imageEmail =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/email_Used.png");
        Image imagePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Unused.png");
        Image imageConfirmePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Unused.png");
        user_icon_sign_up.setImage(imageUserName);
        email_icon_sign_up.setImage(imageEmail);
        visibilty_signup.setImage(imagePassword);
        confirm_password_visibilty_signup.setImage(imageConfirmePassword);
        email_input_signup.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateEmail(email_input_signup.getText(), EmailErrorLabel);
            }
        });

    }
    @FXML
    public void focusOnPassword()
    {

        Image imageUserName =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/user_Unused.png");
        Image imageEmail =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/email_Unused.png");
        Image imagePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Used.png");
        Image imageConfirmePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Unused.png");

        user_icon_sign_up.setImage(imageUserName);
        email_icon_sign_up.setImage(imageEmail);
        password_input_signup.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword(password_input_signup.getText(), passwordErrorLabel);
            }
        });


    }
    @FXML
    public void focusOnConfirmePassword()
    {

        Image imageUserName =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/user_Unused.png");
        Image imageEmail =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/email_Unused.png");
        Image imagePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Unused.png");
        Image imageConfirmePassword =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/visibility_off_Used.png");

        user_icon_sign_up.setImage(imageUserName);
        email_icon_sign_up.setImage(imageEmail);
        visibilty_signup.setImage(imagePassword);
        confirm_password_visibilty_signup.setImage(imageConfirmePassword);
        confirmepassword_input_signup.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateConfirmPassword(password_input_signup.getText(),confirmepassword_input_signup.getText() ,confirmePasswordErrorLabel);
            }
        });

    }

    public void changeVisibilty(TextField visibleTextFiled,PasswordField passwordField , ImageView visibilityIconOff,ImageView visibilityIconOn)
    {
        visibleTextFiled.setText(passwordField.getText());
    }

    @FXML
    public void changePwdVisibilityOffLogin(MouseEvent event)
    {
        password_input_login.setText(VisiblePasswordFeildLogin.getText());
        VisiblePasswordFeildLogin.setVisible(false);
        password_input_login.setVisible(true);
        visibilityIconVisibleLogin.setVisible(false);
        invisible_password_login.setVisible(true);
        password_input_login.requestFocus();
    }
    @FXML
    public void changePwdVisibilityOnLogin(MouseEvent event)
    {
        VisiblePasswordFeildLogin.setText(password_input_login.getText());
        visibilityIconVisibleLogin.setVisible(true);
        invisible_password_login.setVisible(false);
        VisiblePasswordFeildLogin.setVisible(true);
        password_input_login.setVisible(false);
        VisiblePasswordFeildLogin.requestFocus();
    }
    @FXML
    public void changePwdVisibility(MouseEvent Event)
    {
            VisiblePasswordFeild.setText(password_input_signup.getText());
            visibilityIconVisible.setVisible(true);
            visibilty_signup.setVisible(false);
            VisiblePasswordFeild.setVisible(true);
            password_input_signup.setVisible(false);
            VisiblePasswordFeild.requestFocus();

    }
    @FXML
    public void changePwdVisibilityOnConfirmePassword(MouseEvent event)
    {
        VisiblePasswordFeildConfirmePassword.setText(confirmepassword_input_signup.getText());
        visibilityIconVisibleConfirmePassword.setVisible(true);
        confirm_password_visibilty_signup.setVisible(false);
        VisiblePasswordFeildConfirmePassword.setVisible(true);
        confirmepassword_input_signup.setVisible(false);
        VisiblePasswordFeild.requestFocus();
    }
    @FXML
    public void changePwdVisibilityOffConfirmePassword(MouseEvent event)
    {
        confirmepassword_input_signup.setText(VisiblePasswordFeildConfirmePassword.getText());
        VisiblePasswordFeildConfirmePassword.setVisible(false);
        confirmepassword_input_signup.setVisible(true);
        visibilityIconVisibleConfirmePassword.setVisible(false);
        confirm_password_visibilty_signup.setVisible(true);
        confirmepassword_input_signup.requestFocus();
    }
    public void changePwdVisibilityOff(MouseEvent Event){
            password_input_signup.setText(VisiblePasswordFeild.getText());
            VisiblePasswordFeild.setVisible(false);
            password_input_signup.setVisible(true);
            visibilityIconVisible.setVisible(false);
            visibilty_signup.setVisible(true);
            password_input_signup.requestFocus();
    }

    @FXML
    public void SignUp(ActionEvent event)
    {
                try
                {
                    boolean isValid = true;
                    String emailTextField = email_input_signup.getText();
                    String passwordTextField = password_input_signup.getText();
                    String usernameTextField = username_input_signup.getText();
                    String confirmPasswordTextField = confirmepassword_input_signup.getText();
                    if(!validateEmail(emailTextField,EmailErrorLabel)){
                        isValid=false;
                    }
                    if(!validateUsername(usernameTextField,UserNameErrorLabel)){
                        isValid=false;
                    }
                    if(!validatePassword(passwordTextField,passwordErrorLabel)){
                        isValid=false;
                    }
                    if(!validateConfirmPassword(confirmPasswordTextField,passwordTextField,confirmePasswordErrorLabel)){
                        isValid=false;
                    }
                    if (isValid)//will be replaced by the verfication for all the text fileds
                    {   signin_form.setVisible(false);
                        verificationCode= generateVerificationCode();
                        verificationFormSignUpAnchorPane.setVisible(true);
                        verificationFormGoogleAnchorPane.setVisible(false);
                        Gmailer gmailer = new Gmailer();
                        String message = "Pour garantir la sécurité de votre compte et protéger vos informations personnelles, veuillez entrer le code de confirmation que nous avons envoyé à votre adresse e-mail. \n\n" +
                                "Le code de confirmtion est :"+verificationCode+"\n\n"+
                                "Ce code est une étape cruciale du processus d'inscription, conçue pour vérifier votre identité et assurer que seul vous avez accès à votre compte. \n\n" +
                                "En entrant ce code, vous confirmez que vous êtes bien le propriétaire de l'adresse e-mail fournie lors de votre inscription. \n\n" +
                                "Si vous ne recevez pas le code dans les prochaines minutes, veuillez vérifier votre dossier de courriers indésirables ou demander un nouvel envoi. \n\n" +
                                "Merci de votre collaboration pour garantir la sécurité de votre compte.";
                        gmailer.sendMail("Confirmation Registre",message,emailTextField);
                    } else
                    {
                        showErrorAndAnimate(UserNameErrorLabel);
                        showErrorAndAnimate(EmailErrorLabel);
                        showErrorAndAnimate(passwordErrorLabel);
                        showErrorAndAnimate(confirmePasswordErrorLabel);
                        System.out.println("Verify your password");
                    }
                } catch (Exception exception) {
                    System.out.println("Error: " + exception.getMessage());
                }
    }

    public void saveObjectToPreferences(Preferences preferences , UserClass user)
    {
        preferences.putInt("idConnectedUser", user.getIdUser());
        preferences.put("emailConnectedUser", user.getEmail());
        preferences.put("passwordConnectedUser", user.getPassword());
        preferences.put("StatusConnectedUser", user.getStatus());
        preferences.put("UsernameConnectedUser", user.getUserName());
    }
    @FXML
    public void SignIn(ActionEvent event) throws IOException, SQLException {
        String emailTextField = email_input_login.getText();
        String passwordTextField = password_input_login.getText();
        UserClass user1 = new UserClass();
        // Step 1: Retrieve the user from the database using their email.
         user1 = userService.getUserByEmail(emailTextField);

        ValidationLogin.setVisible(true);
        verificationFormGoogleAnchorPane.setVisible(false);

        // Step 2: Verify if the user exists.
        if(user1 == null) {
            ValidationLogin.setText("Adresse e-mail non trouvée");
            showErrorAndAnimate(ValidationLogin);
            return; // Exit the method if the user doesn't exist.
        }

        // Step 3: Retrieve the hashed password from the user object.
        String hashedPasswordFromDB = user1.getPassword();

        // Step 4: Compare the hashed password from the database with the input password.
        if (!verifyPassword(passwordTextField, hashedPasswordFromDB)) {
            ValidationLogin.setText("Mot de passe incorrect. Veuillez réessayer.");
            showErrorAndAnimate(ValidationLogin);
            return; // Exit the method if the password is incorrect.
        }

        if ("User".equals(user1.getStatus())) {
            if(user1.getState().equals("Banned"))
            {
                ValidationLogin.setText("Votre Compte est blockée Merci de contacter notre Administration");
                showErrorAndAnimate(ValidationLogin);
                return;
            }
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
                Parent root = loader.load();
                WelcomePageController welcomePageController=loader.getController();
                Preferences preferences = Preferences.userNodeForPackage(Application.class);
                System.out.println("The Id that i will send is:"+user1.getIdUser());
                welcomePageController.setDataUser(user1.getIdUser());

                userUtils.saveObjectToPreferences(preferences, user1);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();

            }

            catch (IOException e) {
                e.printStackTrace();

            }

        }


        if ("Admin".equals(user1.getStatus())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
                Parent root = loader.load();
                DashBordController dashBord=loader.getController();
                Preferences preferences = Preferences.userNodeForPackage(Application.class);
                System.out.println("The Id that i will send is:"+user1.getIdUser());
                saveObjectToPreferences(preferences, user1);
                dashBord.setData(user1.getIdUser());


                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();

            }catch (IOException e) {
                e.printStackTrace();
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
       else if ("Coach".equals(user1.getStatus())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Coach/DashbordCoach.fxml"));
                Parent root = loader.load();
                DashbordController dashBord=loader.getController();
                Preferences preferences = Preferences.userNodeForPackage(Application.class);
                System.out.println("The Id that i will send is:"+user1.getIdUser());
                dashBord.setData(user1.getIdUser());

                saveObjectToPreferences(preferences, user1);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1366, 786);
                stage.setScene(scene);
                stage.show();

            }catch (IOException e) {
                e.printStackTrace();
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }
    public static String generateVerificationCode() {
        int code = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }

    public void switchToSceneDashBord(ActionEvent event) throws IOException {
        try {
            //  root = FXMLLoader.load(getClass().getResource("/FXML/login/login.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/DashBord.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            DashBordController dashBord=loader.getController();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 800);

            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    @FXML
    public void SignUpConfirmation(ActionEvent event) throws SQLException {
        String emailTextField = email_input_signup.getText();
        String passwordTextField = password_input_signup.getText();
        String usernameTextField = username_input_signup.getText();
        String confirmPasswordTextField = confirmepassword_input_signup.getText();

       if(ConfirmationCodeInput.getText().equals(verificationCode))
       {
           UserClass DatabaseUser = new UserClass();
           String cryptedPassword = encryptPassword(passwordTextField);


           UserClass newUser = new UserClass(0, usernameTextField, cryptedPassword, "User", emailTextField);
           userService.addUser(newUser);
           DatabaseUser=  userService.getUserBynameAndEmail(usernameTextField , emailTextField);
           Profile profileUser =new Profile(0,DatabaseUser.getIdUser(),newUser.getUserName(),"","http://127.0.0.1/img/userDefaultIcon.png","","",0);
           profileService.AddProfileUser(profileUser, DatabaseUser.getIdUser());
           navigationToSignIn(event);
           System.out.println("User added successfully!"+DatabaseUser.getIdUser());
           email_input_signup.clear();
           password_input_signup.clear();
           username_input_signup.clear();
           confirmepassword_input_signup.clear();
       }
       else {
           showErrorAndAnimate(verificationCodeErrorLabel1);
       }
    }

    //-----------------------ValidationMethodes--------------------------------------
    private boolean validateEmail(String email, Label errorLabel) {
        Pattern pattern = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            errorLabel.setText("Email invalide");
            return false; // Return false if the email format is invalid
        } else {
            errorLabel.setText("");
            // Check if the email exists in the database
            if (userService.doesUserExist(email)) {
                errorLabel.setText("Vous avez déja un compte avec cet email !");
                return false; // Return false if the email exists in the database
            }
        }
        return true; // Return true if the email format is valid and doesn't exist in the database
    }
    private boolean validateEmailPasswordForget(String email, Label errorLabel) {
        Pattern pattern = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            errorLabel.setText("Email invalide");
            return false; // Return false if the email format is invalid
        } else {
            errorLabel.setText("");
            // Check if the email exists in the database

        }
        return true; // Return true if the email format is valid and doesn't exist in the database
    }

    // Fonction de validation du nom d'utilisateur
    private boolean validateUsername(String username, Label errorLabel) {
        Pattern pattern = Pattern.compile("^[A-Z].{3,}$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            errorLabel.setText("Nom d'utilisateur invalide");
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();
    }

    // Fonction de validation du mot de passe
    private boolean validatePassword(String password, Label errorLabel) {
        Pattern pattern = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            errorLabel.setText("Mot de passe invalide");
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();
    }
    private boolean validateConfirmPassword(String password, String confirmPassword, Label errorLabel) {
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Les deux mots de passe ne correspondent pas.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }
    private boolean validateVerificationCode(String code, String OriginalNumber, Label errorLabel) {
        if (!code.equals(OriginalNumber)) {
            errorLabel.setText("Le Code est incorrect.");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }
    private void showErrorAndAnimate(Label label) {

        TranslateTransition transition1 = new TranslateTransition(Duration.millis(100), label);
        transition1.setFromX(-10);
        transition1.setToX(10);
        transition1.setCycleCount(4);
        transition1.setAutoReverse(true);
        transition1.play();

    }

//---------------Crypting Password Methode------------------
public String encryptPassword(String password) {

        return String.valueOf(BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(10, password.toCharArray()));
}

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(plainPassword.toCharArray(), hashedPassword.toCharArray());

        return result.verified;

    }
}



