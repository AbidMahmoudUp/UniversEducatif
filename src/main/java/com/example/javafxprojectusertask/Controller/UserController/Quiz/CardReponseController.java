package com.example.javafxprojectusertask.Controller.UserController.Quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import com.example.javafxprojectusertask.Entities.Question;

public class CardReponseController {
    @FXML
    private AnchorPane AnchorParent;

    @FXML
    private ImageView C1;

    @FXML
    private ImageView C2;

    @FXML
    private ImageView C3;

    @FXML
    private Label QuestionLabel;

    @FXML
    private ImageView R1;

    @FXML
    private ImageView R2;

    @FXML
    private ImageView R3;

    @FXML
    private Label choix1;
@FXML
private AnchorPane ap1;
    @FXML
    private AnchorPane ap2;
    @FXML
    private AnchorPane ap3;
    @FXML
    private Label choix2;

    @FXML
    private Label choix3;

    public static Question question = new Question();
    public static  String reponse;

    public void display()
    {
        QuestionLabel.setText(question.getQuestion());
        choix1.setText(question.getChoix1());
        choix2.setText(question.getChoix2());
        choix3.setText(question.getChoix3());
    }
    @FXML
    void initialize() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);

        AnchorParent.setEffect(dropShadow);
       display();
        System.out.println("im here");
        System.out.println(reponse);
        C1.setVisible(false);
        C2.setVisible(false);
        C3.setVisible(false);
        R1.setVisible(false);
        R2.setVisible(false);
        R3.setVisible(false);

        if (question.getChoix1().equals(question.getChoixCorrect())) {
            ap1.setStyle("-fx-background-color: #88DF77;");
            ap2.setStyle("-fx-background-color: #FDD3D3;");
            ap3.setStyle("-fx-background-color: #FDD3D3;");
            if (reponse.equals(question.getChoixCorrect())) {
                C1.setVisible(true);
                R1.setVisible(false);
            } else {
                if(reponse.equals(question.getChoix2()))
                {
                    R2.setVisible(true);
                }
                else {
                    if(reponse.equals(question.getChoix3()))
                    {
                        R3.setVisible(true);
                    }
                    else{
                        R1.setVisible(true);
                        R2.setVisible(true);
                        R3.setVisible(true);
                    }
                }
            }
        }

        if (question.getChoix2().equals(question.getChoixCorrect())) {
            ap2.setStyle("-fx-background-color: #88DF77;");
            ap1.setStyle("-fx-background-color: #FDD3D3;");
            ap3.setStyle("-fx-background-color: #FDD3D3;");
            if (reponse.equals(question.getChoixCorrect())) {
                C2.setVisible(true);
                R2.setVisible(false);
            } else {
                if(reponse.equals(question.getChoix1()))
                {
                    R1.setVisible(true);
                }
                else {
                    if(reponse.equals(question.getChoix3()))
                    {
                        R3.setVisible(true);
                    }
                    else{
                        R1.setVisible(true);
                        R2.setVisible(true);
                        R3.setVisible(true);
                    }
                }
            }
        }

        if (question.getChoix3().equals(question.getChoixCorrect())) {
            ap3.setStyle("-fx-background-color: #88DF77;");
            ap1.setStyle("-fx-background-color: #FDD3D3;");
            ap2.setStyle("-fx-background-color: #FDD3D3;");
            if (reponse.equals(question.getChoixCorrect())) {
                C3.setVisible(true);
                R3.setVisible(false);
            } else {
                if(reponse.equals(question.getChoix2()))
                {
                    R2.setVisible(true);
                }
                else {
                    if(reponse.equals(question.getChoix1()))
                    {
                        R1.setVisible(true);
                    }
                    else{
                        R1.setVisible(true);
                        R2.setVisible(true);
                        R3.setVisible(true);
                    }
                }
            }
        }
    }
}
