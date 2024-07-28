package com.example.javafxprojectusertask.Controller.UserController.Offre;


import com.example.javafxprojectusertask.Controller.AdminController.Dossier.DossierManagmentController;
import com.example.javafxprojectusertask.Controller.AdminController.Societe.EventManagmentController;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class AfficherOffreController {

    Stage stage;

    Scene scene;

    ArrayList<Offre> offres;

    public static Societe s;
    ServiceOffre serviceOffre = new ServiceOffre();

    @FXML
    TextField searchTF;

    @FXML
    ChoiceBox<String> triCB;

    HashMap<String,String> choices = new HashMap<>();

    @FXML
    public GridPane offreContainer;


    public void setSociete(Societe societe) throws IOException {
        s = societe;
        initialize();
    }
    @FXML
    void initialize() throws IOException {

        choices.put("Niveau","niveau");
        choices.put("Description","description");
        choices.put("Date de debut","dateDebut");
        choices.put("Date Fin","dateFin");
        triCB.getItems().addAll(choices.keySet());
        if(s == null) {
            offres = serviceOffre.getAll();
            System.out.println("HERE");
        }
        else{
            System.out.println("IT WORKED");
            offres = serviceOffre.getBySociete(s);
        }
        System.out.println(s);

        int column = 0;
        int row = 1;

        for(Offre offre : offres)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/cardOffre.fxml"));
            VBox cardBox = loader.load();
            OffreCardController cardController = loader.getController();
            cardController.setData(offre);
            cardController.prepareCard();

            if(column == 2)
            {
                column = 0;
                row++;
            }
            offreContainer.add(cardBox,column++,row);
            GridPane.setMargin(cardBox, new Insets(10));


            triCB.setOnAction(event -> {
                try
                {
                    triSociete(event);
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });

            searchTF.setOnKeyReleased(keyEvent -> {
                try
                {
                    searchSociete(searchTF.getText());
                }catch (IOException e)
                {
                    e.getMessage();
                }
            });

        }
    }

    public void searchSociete(String input) throws IOException{
        ArrayList<Offre> list;
        if(input.isEmpty() || input.isBlank() || input == null)
        {
            list = offres;
        }
        else
        {
            list = offres.stream()
                    .filter(offre -> offre.getDateDeb().toString().toLowerCase().contains(input.toLowerCase()) ||
                            offre.getDesc().toLowerCase().contains(input.toLowerCase()) ||
                            Integer.toString(offre.getNiveau()).contains(input)||
                            offre.getDateFin().toString().toLowerCase().contains(input.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        }

        int column = 0;
        int row = 1;

        offreContainer.getChildren().clear();
        for(Offre offre : list)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/cardOffre.fxml"));
            VBox cardBox = loader.load();
            OffreCardController cardController = loader.getController();
            cardController.setData(offre);
            cardController.prepareCard();

            if(column == 2)
            {
                column = 0;
                row++;
            }
            offreContainer.add(cardBox,column++,row);
            GridPane.setMargin(cardBox, new Insets(10));

        }
    }

    public void triSociete(ActionEvent event) throws IOException {
        String s = triCB.getValue();
        offres = serviceOffre.getAllOrdered(choices.get(s),"ASC");
        int column = 0;
        int row = 1;

        offreContainer.getChildren().clear();
        for(Offre offre : offres)
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/DashBordAdmin/Offre/cardOffre.fxml"));
            VBox cardBox = loader.load();
            OffreCardController cardController = loader.getController();
            cardController.setData(offre);
            cardController.prepareCard();

            if(column == 2)
            {
                column = 0;
                row++;
            }
            offreContainer.add(cardBox,column++,row);
            GridPane.setMargin(cardBox, new Insets(10));

        }
    }

    @FXML
    public void ajoutScene(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/DashBordAdmin/ajoutOffre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void afficherScene(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/DashBordAdmin/afficherSociete.fxml"));
        Parent root = loader.load();
        EventManagmentController eventManagmentController =loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void afficherDossier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testfx7002/Dossier/afficherDossier.fxml"));
        Parent root = loader.load();
        DossierManagmentController afficherController=loader.getController();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   /* @FXML
    void showPdf(MouseEvent event) {



        // Create content list
        List<String> content = new ArrayList<>();
// Add header row to the content list
        content.add("Societe    niveau     Description     ");
        content.add( offres.get(0).getSociete()+"                   "+offres.get(0).getNiveau()+"         "+offres.get(0).getDesc());
        // Create a new PDF document
        PDDocument document = new PDDocument();

// Add a new page to the document
        PDPage page = new PDPage();
        document.addPage(page);

// Create a new font for the header
        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.COURIER) ;

// Add content to the PDF document
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Add the title
            contentStream.beginText();
            contentStream.setFont(font, 36);
            contentStream.newLineAtOffset(50, 650);
            contentStream.showText("Offre");
            contentStream.endText();

            // Add the table data
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(50, 600);

            for (int i = 0; i < content.size(); i++) {
                contentStream.showText(content.get(i));
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();
            contentStream.close();
        }catch(Exception e) {
            e.printStackTrace();
        }

// Save the PDF document to a file or stream


        try {
            document.save("C:\\Users\\midow\\Desktop\\offre.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

}
