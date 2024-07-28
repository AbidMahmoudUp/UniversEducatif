package com.example.javafxprojectusertask.Controller.UserController.Quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceExam;
import com.example.javafxprojectusertask.Services.ServiceNote;
import com.example.javafxprojectusertask.Services.ServiceQuestion;
import com.example.javafxprojectusertask.Entities.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizPageController {
    @FXML
    private AnchorPane AnchorParent;

    @FXML
    private AnchorPane ap1;

    @FXML
    private AnchorPane ap2;

    @FXML
    private AnchorPane ap3;

    @FXML
    private Label numQuestion;


    @FXML
    private FlowPane reponses;

    @FXML
    private RadioButton C1;

    @FXML
    private RadioButton C2;

    @FXML
    private RadioButton C3;

    @FXML
    private Label QuestionLabel;

    @FXML
    private Label choix1;

    @FXML
    private Label choix2;

    @FXML
    private Label choix3;
    @FXML
    private Label timer;

    @FXML
    private Button nextButton;

    @FXML
    private Button Termine;

    private Thread timerThread;
    private volatile boolean running = true;
    public static int timeSeconds;

    public static int examId;

    private final ServiceExam serviceExam = new ServiceExam();
    private final ServiceQuestion serviceQuestion = new ServiceQuestion();
    private final ServiceNote serviceNote = new ServiceNote();
    private List<Question> questionList = new ArrayList<>();
    private List<String> reponse = new ArrayList<>();
    private int currentIndex = 0;
    private int note =0;
    ToggleGroup toggleGroup = new ToggleGroup();
    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {
        C1.setToggleGroup(toggleGroup);
        C2.setToggleGroup(toggleGroup);
        C3.setToggleGroup(toggleGroup);
        questionList = serviceQuestion.getQuestionsByExamId(examId);
        Collections.shuffle(questionList);
        showQuestion(currentIndex);
        nextButton.setOnAction(event -> nextQuestion());
        AnchorParent.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if ((event.getCode() == KeyCode.ALT)||(event.getCode() == KeyCode.TAB)) {
                System.out.println("You're cheating");
                cheater(event);
                event.consume(); // Consume the event to prevent it from being processed further
            }
        });



    }
    private void cheater(KeyEvent event)
    {
        reponse.clear();
        for (int i =0;i<10;i++)
        {
            reponse.add("");
        }
        afficherquestion(event);
    }
    public void afficherquestion(KeyEvent event) {
        termine();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/tn/esprit/gestionexamen/GUI/User/reponse.fxml"));
        ReponseController.examId = examId;
        Parent root;
        try {
            root = loader1.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ReponseController reponseController = loader1.getController();
        reponseController.setReponse(reponse);
        reponseController.setQuestionList(questionList);
        reponseController.loadExamData();

        // Ensure the stage is properly initialized
        if (this.stage == null) {
            this.stage = new Stage();
        }

        // Get the stage from the event source if available
        if (event != null && event.getSource() instanceof Node) {
            Node source = (Node) event.getSource();
            Scene currentScene = source.getScene();
            if (currentScene != null && currentScene.getWindow() instanceof Stage) {
                this.stage = (Stage) currentScene.getWindow();
            }
        }

        // If still null, create a new stage
        if (this.stage == null) {
            this.stage = new Stage();
        }

        // Create the scene and set it to the stage
        Scene scene = new Scene(root, 1366, 786);
        this.stage.setScene(scene);
        this.stage.show();
    }




    private void showQuestion(int index) {
        // Arrêtez le compte à rebours actuel s'il est en cours
        stopCountdown();

        // Obtenez le temps spécifique à la question
        timeSeconds = questionList.get(index).getDuree();

        // Démarrez un nouveau compte à rebours pour la nouvelle question
        startCountdown();
        Question question = questionList.get(index);
        numQuestion.setText(Integer.toString(index + 1));
        QuestionLabel.setText(question.getQuestion());
        choix1.setText(question.getChoix1());
        choix2.setText(question.getChoix2());
        choix3.setText(question.getChoix3());
        if (questionList.get(index).equals(questionList.get(0))) {
        } else {
            if (questionList.get(index).equals(questionList.get(9))) {
                nextButton.setVisible(false);
                Termine.setVisible(true);
            } else {
                nextButton.setVisible(true);
            }
        }

    }

    private void nextQuestion() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        int selectedIndex = 0;
        if (selectedRadioButton != null) {
            selectedIndex = Integer.parseInt(selectedRadioButton.getId().substring(1));
            System.out.println("Selected choice: " + (selectedIndex));
        } else {

            System.out.println("No choice selected.");
        }
        addSelectedChoiceToList(selectedIndex);
        currentIndex = (currentIndex + 1) ;
        showQuestion(currentIndex);
        System.out.println(currentIndex);
        toggleGroup.selectToggle(null);
        ap3.setStyle("-fx-background-color: #f5f5f5");ap2.setStyle("-fx-background-color: #f5f5f5");ap1.setStyle("-fx-background-color: #f5f5f5");
    }
    private void termine() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        int selectedIndex = 0;
        if (selectedRadioButton != null) {
            selectedIndex = Integer.parseInt(selectedRadioButton.getId().substring(1));
            System.out.println("Selected choice: " + (selectedIndex));
        } else {

            System.out.println("No choice selected.");
        }
        addSelectedChoiceToList(selectedIndex);
        toggleGroup.selectToggle(null);
        stopCountdown();
    }


    private void addSelectedChoiceToList(int selectedIndex) {
        String selectedChoice = "";
        switch (selectedIndex) {
            case 1:
                selectedChoice = questionList.get(currentIndex).getChoix1();
                break;
            case 2:
                selectedChoice = questionList.get(currentIndex).getChoix2();
                break;
            case 3:
                selectedChoice = questionList.get(currentIndex).getChoix3();
                break;
            default:
                break;
        }
        reponse.add(selectedChoice);
    }
    @FXML
    public void afficherquestions(MouseEvent event) {
        termine();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Examen/reponse.fxml"));
        Parent root= null;
        ReponseController.examId=examId;
        try {
            root = loader1.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ReponseController reponseController =loader1.getController();
        System.out.println(reponse);
        reponseController.setReponse(reponse);
        reponseController.setQuestionList(questionList);
        System.out.println(questionList);
        reponseController.loadExamData();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();
    }

    private void startCountdown() {
        running = true;
        timerThread = new Thread(() -> {
            try {
                while (running && timeSeconds >= 0) {
                    int seconds = timeSeconds;
                    javafx.application.Platform.runLater(() -> timer.setText(String.valueOf(seconds)));

                    Thread.sleep(1000); // Sleep for 1 second
                    timeSeconds--;
                }
                javafx.application.Platform.runLater(() -> nextQuestion());
            } catch (InterruptedException e) {
                // Le thread a été interrompu pendant le sommeil
                System.out.println("Countdown thread interrupted.");
            }
        });
        timerThread.start();
    }

    public void stopCountdown() {
        running = false;
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    @FXML
    public void checkRadioButton1(MouseEvent event){
        if(ap1.getChildren().get(3) instanceof RadioButton btn)
        {
            ap1.setStyle("-fx-background-color: green");
            ap2.setStyle("-fx-background-color: #f5f5f5");
            ap3.setStyle("-fx-background-color: #f5f5f5");
            btn.setSelected(true);
        }
    }

    @FXML
    public void checkRadioButton2(MouseEvent event){
        if(ap2.getChildren().get(3) instanceof RadioButton btn)
        {
            ap3.setStyle("-fx-background-color: #f5f5f5");
            ap2.setStyle("-fx-background-color: green");
            ap1.setStyle("-fx-background-color: #f5f5f5");
            btn.setSelected(true);
        }
    }

    @FXML
    public void checkRadioButton3(MouseEvent event){
        if(ap3.getChildren().get(3) instanceof RadioButton btn)
        {   ap3.setStyle("-fx-background-color: green");
          ap2.setStyle("-fx-background-color: #f5f5f5");
           ap1.setStyle("-fx-background-color: #f5f5f5");
            btn.setSelected(true);
        }
    }


}



