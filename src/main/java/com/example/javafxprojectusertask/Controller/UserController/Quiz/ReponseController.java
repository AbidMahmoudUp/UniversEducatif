package com.example.javafxprojectusertask.Controller.UserController.Quiz;

import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.Module;
import com.example.javafxprojectusertask.Services.ServiceModule;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.example.javafxprojectusertask.Services.ServiceExam;
import com.example.javafxprojectusertask.Services.ServiceNote;
import com.example.javafxprojectusertask.Entities.Exam;
import com.example.javafxprojectusertask.Entities.Note;
import com.example.javafxprojectusertask.Entities.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReponseController {
    @FXML
    private Label StatLabel;
    @FXML
    private Hyperlink certif;
    @FXML
    private FlowPane Rflowpane;
    @FXML
    private Button test;
    @FXML
    private Label note;
    @FXML
    private ImageView back;
    @FXML
    private ScrollPane ReponseScroll;

    @FXML
    public AnchorPane certifAnchorPane;

    @FXML
    private AnchorPane q1;

    @FXML
    private AnchorPane q10;

    @FXML
    private AnchorPane q2;

    @FXML
    private AnchorPane q3;

    @FXML
    private AnchorPane q4;

    @FXML
    private AnchorPane q5;

    @FXML
    private AnchorPane q6;

    @FXML
    private AnchorPane q7;

    @FXML
    private AnchorPane q8;

    @FXML
    private AnchorPane q9;

    public List<Question> getQuestionList() {
        return questionList;
    }
    private ServiceExam serviceExam=new ServiceExam();
    private Exam exam;


    public static int examId;

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public static int resultat=0;

    public List<String> getReponse() {
        return reponse;
    }
    @FXML
    public void test()
    {
        loadExamData();
    }

    public void setReponse(List<String> reponse) {
        this.reponse = reponse;
    }

    private List<Question> questionList = new ArrayList<>();
    private List<String> reponse = new ArrayList<>();
    @FXML
    public void initialize()
    {
        resultat= 0;
        certif.setVisible(false);
        setQuestionList(questionList);
        setReponse(reponse);
        exam=serviceExam.getElementByIndex(examId);
        certifAnchorPane.setVisible(false);
        ReponseScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ReponseScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ReponseScroll.toFront();


    }
    @FXML
    public void initialize1()
    {
        setQuestionList(questionList);
        setReponse(reponse);
        exam=serviceExam.getElementByIndex(examId);
        certifAnchorPane.setVisible(false);

        ReponseScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ReponseScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }
    public void loadExamData() {
        Rflowpane.getChildren().clear();
        int i=0;
        for (Question question : questionList){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Examen/CardReponse.fxml"));
                CardReponseController.question=question;
                CardReponseController.reponse=reponse.get(i);
                setColorQ(i,question,reponse.get(i));
                i++;
                AnchorPane card = loader.load();
                CardReponseController controller = loader.getController();
                Rflowpane.getChildren().add(card);
                note.setText(String.valueOf(resultat));
                if (i==10)
                    break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(resultat >= 6) {
            try {
                StatLabel.setText("Congratz");
                certif.setVisible(true);
                showCertif();
                certifAnchorPane.setOnMouseClicked((MouseEvent event) ->
                {
                    certifAnchorPane.getChildren().clear();
                    certifAnchorPane.toBack();
                    certifAnchorPane.setVisible(false);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            StatLabel.setText("sadge");
        }

    }

    public void setColorQ(int i, Question q, String r)
    {
        switch (i)
        {
            case 0:
                if(q.getChoixCorrect().equals(r))
                {
                    q1.setStyle(q1.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }

                else
                    q1.setStyle(q1.getStyle() + " -fx-background-color: #FF8181");
                break;
            case 1:
                if(q.getChoixCorrect().equals(r))
                {
                    q2.setStyle(q2.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }

                else
                    q2.setStyle(q2.getStyle() + " -fx-background-color: #FF8181");
                break;
            case 2:

                if(q.getChoixCorrect().equals(r))
                {
                    q3.setStyle(q3.getStyle() +" -fx-background-color: #6FE858");
                    resultat ++;
                }
                else
                    q3.setStyle(q3.getStyle() +" -fx-background-color: #FF8181");
                break;
            case 3:

                if(q.getChoixCorrect().equals(r)) {
                    q4.setStyle(q4.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }                else
                    q4.setStyle(q4.getStyle() + " -fx-background-color: #FF8181");
                break;
            case 4:

                if(q.getChoixCorrect().equals(r)) {
                    q5.setStyle(q5.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }                else
                    q5.setStyle(q5.getStyle() +" -fx-background-color: #FF8181");
                break;
            case 5:

                if(q.getChoixCorrect().equals(r)) {
                    q6.setStyle(q6.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }
                else
                    q6.setStyle(q6.getStyle() +" -fx-background-color: #FF8181");
                break;
            case 6:

                if(q.getChoixCorrect().equals(r)) {
                    q7.setStyle(q7.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }                else
                    q7.setStyle(q7.getStyle() +" -fx-background-color: #FF8181");
                break;
            case 7:

                if(q.getChoixCorrect().equals(r)) {
                    q8.setStyle(q8.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }                else
                    q8.setStyle(q8.getStyle() +" -fx-background-color: #FF8181");
                break;
            case 8:

                if(q.getChoixCorrect().equals(r)) {
                    q9.setStyle(q9.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }                else
                    q9.setStyle(q9.getStyle() +" -fx-background-color: #FF8181");
                break;
            case 9:

                if(q.getChoixCorrect().equals(r)) {
                    q10.setStyle(q10.getStyle() + " -fx-background-color: #6FE858");
                    resultat ++;
                }                else
                    q10.setStyle(q10.getStyle() +" -fx-background-color: #FF8181");
                break;
        }
    }

    public void backtomain(MouseEvent event) throws IOException {
        ServiceModule serviceModule = new ServiceModule();
        Module module = serviceModule.getModulebyNom(exam.getModule());
        Note note1=new Note(UserUtils.ConnectedUser.getIdUser(), exam.getIdexams(),resultat, module.getIdModule());
        ServiceNote serviceNote = new ServiceNote();
        serviceNote.add(note1);
        resultat=0;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/User/WelcomePage.fxml"));
        Parent root = loader.load();
        WelcomePageController welcomePageController=loader.getController();
        UserUtils userUtils =new UserUtils();
        UserUtils.ConnectedUser=userUtils.getConnectedUser();
        welcomePageController.setDataUser(UserUtils.ConnectedUser.getIdUser());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1366, 786);
        stage.setScene(scene);
        stage.show();
    }



    public void showCertif() throws IOException {
        ServiceModule serviceModule = new ServiceModule();
        Module module=new Module();
        module =serviceModule.getModulebyNom(exam.getModule());
        CertificationController.module=module;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Certification/certification.fxml"));
        AnchorPane pane = loader.load();

        certifAnchorPane.setVisible(true);
        certifAnchorPane.toFront();
        certifAnchorPane.getChildren().setAll(pane);
    }
    public void HideCertif() throws IOException {
      certifAnchorPane.setVisible(false);
      certifAnchorPane.toBack();


    }

    @FXML
    void Scroll1(MouseEvent event) {
        ReponseScroll.setVvalue(0); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll10(MouseEvent event) {
        ReponseScroll.setVvalue(1); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll2(MouseEvent event) {
        ReponseScroll.setVvalue(0.1); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll3(MouseEvent event) {
        ReponseScroll.setVvalue(0.2); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll4(MouseEvent event) {
        ReponseScroll.setVvalue(0.3); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll5(MouseEvent event) {
        ReponseScroll.setVvalue(0.4); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll6(MouseEvent event) {
        ReponseScroll.setVvalue(0.5); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll7(MouseEvent event) {
        ReponseScroll.setVvalue(0.6); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll8(MouseEvent event) {
        ReponseScroll.setVvalue(0.7); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }

    @FXML
    void Scroll9(MouseEvent event) {
        ReponseScroll.setVvalue(0.8); // Set vertical scroll to halfway
        ReponseScroll.setHvalue(0.5); // Set horizontal scroll to halfway
    }
}
