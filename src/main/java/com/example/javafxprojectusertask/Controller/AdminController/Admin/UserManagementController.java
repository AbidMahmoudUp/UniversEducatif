package com.example.javafxprojectusertask.Controller.AdminController.Admin;

import com.example.javafxprojectusertask.Entities.Profile;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.ProfileService;
import com.example.javafxprojectusertask.Services.UserService;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {

@FXML
    FlowPane flowpaneUserDisplay;
@FXML
AnchorPane useranchor,anchorPaneDisplay , PopUpAddCoach;
@FXML
    ImageView addIcon , ascImg,descImg;
@FXML
ScrollPane ScrollPaneUserManagement;
@FXML
    TextField searchBarUserManagment;
@FXML
Circle addCoach;
@FXML
Line lineAll,lineUsers,lineCoaches,lineAdmin;
@FXML
Label
        AllLabel , UsersLabel,CoachsLabel,AdminLabels,TypeDisplayed;
@FXML
AnchorPane OrderAnchorPane;
@FXML
        Label optionLabel,nom,email,role;
boolean isOpened = false;
boolean state = false;

    private ScaleTransition scaleTransition;
    int index = -1;
ProfileService profileService = new ProfileService();
public void setData() throws IOException {
     ArrayList <UserClass>  ListUsers = new ArrayList<>();
    UserService userService = new UserService();
    flowpaneUserDisplay.getChildren().clear();
    System.out.println("test");
    try {
        ListUsers= userService.getAllUsers();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    for(int i = 0 ;i<ListUsers.size();i++)
    {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
         AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
         loader.load();
         CardUserController controller = loader.getController();
        Profile profile = new Profile();
        profile= profileService.getProfileUser(ListUsers.get(i).getIdUser());


        if(pane.getChildren().get(0) instanceof Circle circle)
        {
            String pictureUrl = profile.getPicture();
            if (pictureUrl != null) {

                Image originalImage = new Image(pictureUrl);
                originalImage.errorProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        // Handle error loading image
                        System.out.println("Error loading image from URL: " + pictureUrl);
                    }
                });
                ImagePattern imagePattern = new ImagePattern(originalImage);
                circle.setFill(imagePattern);
            } else {
            }
        }
        if(pane.getChildren().get(1) instanceof Label label)
        {
            label.setText(profile.getFirstName()+" "+profile.getLastName());
        }
        if( pane.getChildren().get(2) instanceof Label label){
            label.setText(ListUsers.get(i).getEmail());
        }
        if( pane.getChildren().get(3) instanceof Label label){
            label.setText(ListUsers.get(i).getStatus());
        }
        if(pane.getChildren().get(4) instanceof Label label)
        {
            label.setText(String.valueOf(profile.getIdUser()));
        }
        if(pane.getChildren().get(5) instanceof ImageView image)
        {
            if(ListUsers.get(i).getState().equals("Active"))
            {
                Image active =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/activeIcon.png");
                image.setImage(active);
            }
            if(ListUsers.get(i).getState().equals("Banned"))
            {
                Image banned =new Image("file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/Banned.png");
                image.setImage(banned);
            }
        }
      //  pane.setOnMouseEntered(mouseDragEvent ->{ System.out.println("test Card Interactivity");pane.setScaleX(pane.getScaleX()+0.1);});

      //  pane.setOnMouseExited(mouseDragEvent -> pane.setScaleX(pane.getScaleX()-0.1));
        flowpaneUserDisplay.getChildren().add(pane);
        fadeInTransition(pane);
    }
}
    private void fadeInTransition(AnchorPane anchorPane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), anchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderAnchorPane.setVisible(false);
        addCoach.toFront();
        addIcon.toFront();

        addCoach.setFill(Color.web("#4A8292"));
        addCoach.setStroke(null);

        ScrollPaneUserManagement.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneUserManagement.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);

        anchorPaneDisplay.setEffect(dropShadow);
        try {
            flowpaneUserDisplay.setPadding(new Insets(24,24,24,24));
            flowpaneUserDisplay.setVgap(12);
            flowpaneUserDisplay.setHgap(40);

           this.setData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
   }
    @FXML
    public void SetAllUsers() throws SQLException, IOException {
        lineAdmin.setVisible(false);
        lineUsers.setVisible(false);
        lineAll.setVisible(true);
        lineAll.toFront();
        lineCoaches.setVisible(false);
        this.flowpaneUserDisplay.getChildren().clear();
        ArrayList<UserClass> ListUsers= new ArrayList<>();
        UserService userService = new UserService();
        ListUsers=userService.getAllUsers();

        for (int i = 0 ; i<ListUsers.size();i++) {

                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
            AnchorPane pane = loader.load();
            CardUserController cardUserController = loader.getController();
            cardUserController.setData(ListUsers.get(i));

            flowpaneUserDisplay.getChildren().add(pane);}


    }
    @FXML
    public void setOnlyUsers() throws SQLException, IOException {
        lineAdmin.setVisible(false);
        lineUsers.setVisible(true);
        lineAll.setVisible(false);
        lineCoaches.setVisible(false);
        TypeDisplayed.setText("Gestion Utilisateur");
        this.flowpaneUserDisplay.getChildren().clear();
        ArrayList<UserClass> ListUsers= new ArrayList<>();
        UserService userService = new UserService();
        ListUsers=userService.getAllUsers();

        for (int i = 0 ; i<ListUsers.size();i++) {
            if(ListUsers.get(i).getStatus().equals("User"))
            {            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
                AnchorPane pane = loader.load();
                CardUserController cardUserController = loader.getController();
                cardUserController.setData(ListUsers.get(i));

                flowpaneUserDisplay.getChildren().add(pane);}
        }


    }
    @FXML
    public void setOnlyCoachs() throws SQLException, IOException {
        TypeDisplayed.setText("Gestion Coach");
        lineAdmin.setVisible(false);
        lineUsers.setVisible(false);
        lineAll.setVisible(false);
        lineCoaches.setVisible(true);
        this.flowpaneUserDisplay.getChildren().clear();
        ArrayList<UserClass> ListUsers= new ArrayList<>();
        UserService userService = new UserService();
        ListUsers=userService.getAllUsers();

        for (int i = 0 ; i<ListUsers.size();i++) {
            if(ListUsers.get(i).getStatus().equals("Coach"))
            {            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
                AnchorPane pane = loader.load();
                CardUserController cardUserController = loader.getController();
                cardUserController.setData(ListUsers.get(i));

                flowpaneUserDisplay.getChildren().add(pane);}
        }


    }
@FXML
public void setOnlyAdmins() throws SQLException, IOException {
    TypeDisplayed.setText("Gestion Admin");
    lineAdmin.setVisible(true);
    lineUsers.setVisible(false);
    lineAll.setVisible(false);
    lineCoaches.setVisible(false);
    this.flowpaneUserDisplay.getChildren().clear();
    ArrayList<UserClass> ListUsers= new ArrayList<>();
    UserService userService = new UserService();
    ListUsers=userService.getAllUsers();

    for (int i = 0 ; i<ListUsers.size();i++) {
        if(ListUsers.get(i).getStatus().equals("Admin"))
        {            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
            AnchorPane pane = loader.load();
            CardUserController cardUserController = loader.getController();
            cardUserController.setData(ListUsers.get(i));

            flowpaneUserDisplay.getChildren().add(pane);}
    }
}




    @FXML
    public void AnimateOnAllLine(MouseEvent event){
       lineAll.setVisible(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineAll);
        scaleTransition.setToX(1);
        scaleTransition.play();
    }

    @FXML
    public void AnimateOffAllLine(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineAll);
        scaleTransition.setToX(0);
        scaleTransition.setOnFinished(event -> lineAll.setVisible(false)); // Hide line after animation
        scaleTransition.play();
    }
    @FXML
    public void AnimateOnUsersLine(){
        lineUsers.setVisible(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineUsers);
        scaleTransition.setToX(1);
        scaleTransition.play();
    }
    @FXML
    public void AnimateOffUsersLine(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineUsers);
        scaleTransition.setToX(0);
        scaleTransition.setOnFinished(event -> lineUsers.setVisible(false)); // Hide line after animation
        scaleTransition.play();
    }
    @FXML
    public void AnimateOnCoachLine(){
        lineCoaches.setVisible(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineCoaches);
        scaleTransition.setToX(1);
        scaleTransition.play();
    }
    @FXML
    public void AnimateOffCoachLine(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineCoaches);
        scaleTransition.setToX(0);
        scaleTransition.setOnFinished(event -> lineCoaches.setVisible(false)); // Hide line after animation
        scaleTransition.play();
    }
    @FXML
    public void AnimateOnAdminLine(){
        lineAdmin.setVisible(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineAdmin);
        scaleTransition.setToX(1);
        scaleTransition.play();
    }
    @FXML
    public void AnimateOffAdminLine(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), lineAdmin);
        scaleTransition.setToX(0);
        scaleTransition.setOnFinished(event -> lineAdmin.setVisible(false)); // Hide line after animation
        scaleTransition.play();
    }
    @FXML
    void ShowPopUpCoach(MouseEvent event)
    {

       try{ FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/PopUpAddCoach.fxml"));
        AnchorPane pane= FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/Admin/PopUpAddCoach.fxml"));
        loader.load();
        PopUpAddCoachController controller = loader.getController();
        PopUpAddCoach.getChildren().setAll(pane);
           PopUpAddCoach.setVisible(true);
       }
       catch (IOException exception){
           System.out.println(exception.getMessage());
       }


    }
    private void searchUserCard(List<UserClass> ListUsers , List<Profile>profileList) throws IOException {
        this.flowpaneUserDisplay.getChildren().clear();
        for (int i = 0 ; i<ListUsers.size();i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/Admin/Card.fxml"));
            AnchorPane pane = loader.load();
            CardUserController cardUserController = loader.getController();
            cardUserController.setData(ListUsers.get(i));

            flowpaneUserDisplay.getChildren().add(pane);
        }


    }
    @FXML
    public void searchBarUserManagment(KeyEvent keyEvent) throws SQLException, IOException {
    ProfileService profileService = new ProfileService();
    UserService userService = new UserService();
        final List<UserClass> users = userService.getAllUsers();
        final List<Profile>profiles= profileService.getAllProfiles();
        final String searchParam = searchBarUserManagment.getText();
        if(searchParam != null && !searchParam.isEmpty()) {

            List<UserClass> newlist = new ArrayList<>();
            List<Profile>profileList=new ArrayList<>();

            for(int i = 0 ;i<users.size();i++)
            {
                if(users.get(i).getUserName().contains(searchParam)||users.get(i).getEmail().contains(searchParam)||String.valueOf(profiles.get(i).getPhoneNumber()).contains(searchParam)||profiles.get(i).getFirstName().contains(searchParam)||profiles.get(i).getLastName().contains(searchParam))
                {
                    newlist.add(users.get(i));
                    profileList.add(profiles.get(i));
                }
            }
            this.searchUserCard(newlist,profileList);
        } else {
            this.searchUserCard(users,profiles);
        }

    }


    public void OpenAndCloseTrieFiled(MouseEvent event){
        if(!isOpened)
        {
            OrderAnchorPane.setVisible(true);
            isOpened=true;
        }
        else{
            OrderAnchorPane.setVisible(false);
            isOpened=false;
        }
    }
    public void SwitchOption(MouseEvent event) throws SQLException, IOException {
        if(!state)
        {
            flowpaneUserDisplay.getChildren().clear();

        UserService userService = new UserService();
        ProfileService profileService1 = new ProfileService();
        List<UserClass> listUsers=new ArrayList<>();
        List<Profile> profiles=new ArrayList<>();
        String option =optionLabel.getText();
        String tryy="idUser";
        listUsers= userService.getAllUsersByName(tryy,"DESC");
        profiles=profileService.getAllProfiles();
        searchUserCard(listUsers,profiles);
        state=true;
    }
        else {
            flowpaneUserDisplay.getChildren().clear();

            UserService userService = new UserService();
            ProfileService profileService1 = new ProfileService();
            List<UserClass> listUsers=new ArrayList<>();
            List<Profile> profiles=new ArrayList<>();
            String option =optionLabel.getText();
            String tryy="idUser";
            listUsers= userService.getAllUsersByName(tryy,"ASC");
            profiles=profileService.getAllProfilesFiltred(tryy,"ASC");
            searchUserCard(listUsers,profiles);
            state=false;
        }
    }
    @FXML
    void choisirEmail(MouseEvent event) {
    optionLabel.setText(email.getText());
    OrderAnchorPane.setVisible(false);
    }

    @FXML
    void choisirNom(MouseEvent event) {
    optionLabel.setText(nom.getText());
    OrderAnchorPane.setVisible(false);

    }

    @FXML
    void choisirRole(MouseEvent event) {
    optionLabel.setText(role.getText());
    OrderAnchorPane.setVisible(false);
    }

    @FXML
    void triAsc(MouseEvent event) throws SQLException, IOException {
        flowpaneUserDisplay.getChildren().clear();
        UserService userService = new UserService();
        ProfileService profileService1 = new ProfileService();
        List<UserClass> listUsers=new ArrayList<>();
        List<Profile> profiles=new ArrayList<>();
        String option =optionLabel.getText();
        if(option.equals("Nom")){
        listUsers= userService.getAllUsersByNameProfile("ASC");
        profiles=profileService.getAllProfiles();
        searchUserCard(listUsers,profiles);}
        else if (option.equals("Email")) {
        listUsers= userService.getAllUsersByName("Email","ASC");
        profiles=profileService.getAllProfiles();
        searchUserCard(listUsers,profiles);
        }
        else if (option.equals("Role")) {
        listUsers= userService.getAllUsersByName("Status","ASC");
        profiles=profileService.getAllProfiles();
        searchUserCard(listUsers,profiles);
        }

    }

    @FXML
    void triDesc(MouseEvent event) throws SQLException, IOException {
        flowpaneUserDisplay.getChildren().clear();
        UserService userService = new UserService();
        ProfileService profileService1 = new ProfileService();
        List<UserClass> listUsers=new ArrayList<>();
        List<Profile> profiles=new ArrayList<>();
        String option =optionLabel.getText();
        if(option.equals("Nom")){
            listUsers= userService.getAllUsersByNameProfile("DESC");
            profiles=profileService.getAllProfiles();
            searchUserCard(listUsers,profiles);}
        else if (option.equals("Email")) {
            listUsers= userService.getAllUsersByName("Email","DESC");
            profiles=profileService.getAllProfiles();
            searchUserCard(listUsers,profiles);
        }
        else if (option.equals("Role")) {
            listUsers= userService.getAllUsersByName("Status","DESC");
            profiles=profileService.getAllProfiles();
            searchUserCard(listUsers,profiles);
        }

    }
}

