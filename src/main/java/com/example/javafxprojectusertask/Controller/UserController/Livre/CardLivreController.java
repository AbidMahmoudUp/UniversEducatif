package com.example.javafxprojectusertask.Controller.UserController.Livre;

import com.example.javafxprojectusertask.Controller.AdminController.Admin.DashBordController;
import com.example.javafxprojectusertask.Controller.AdminController.Livre.PopUpUpdateLivreController;
import com.example.javafxprojectusertask.Entities.Livre;
import com.example.javafxprojectusertask.Services.LivreService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CardLivreController {




    @FXML
    private Label AuthorBook;

    @FXML
    private Button SeeBook;

    @FXML
    private Label TitleBook;

    @FXML
    private AnchorPane cardAnchorPane;
    Livre l;


    public void prepareCard()
    {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.2));
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(5);
        cardAnchorPane.setEffect(dropShadow);
        AuthorBook.setText(l.getAuteur());
        TitleBook.setText(l.getTitre());
    }
    public void setData(Livre l1)
    {
        l = l1;
    }

    public Livre getData()
    {
        return l;
    }


}
