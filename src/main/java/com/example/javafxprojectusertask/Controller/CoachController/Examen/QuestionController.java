package com.example.javafxprojectusertask.Controller.CoachController.Examen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.example.javafxprojectusertask.Services.ServiceQuestion;
import com.example.javafxprojectusertask.Entities.Exam;
import com.example.javafxprojectusertask.Entities.Question;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER;
import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.COURIER_BOLD;

public class QuestionController {
    @FXML
    private FlowPane questionFlowPane;

    private ServiceQuestion serviceQuestion;

    public Exam exam;
    @FXML
    private ImageView addIcon;
    @FXML
    private ImageView ASC;
    @FXML
    private ImageView Back;
    @FXML
    private ImageView DESC;
    @FXML
    private ImageView pdf;
    @FXML
    private TextField chercher;
    @FXML
    private MenuButton tripar;
    private String tri="ASC";
    private String trierpar="question";


    public QuestionController() {
        this.serviceQuestion = new ServiceQuestion();
    }
    @FXML
    public void initialize() {
        addIcon.setOnMouseClicked(this::handleAddIconClicked);
        ASC.setOnMouseClicked(this::handleASC);
        DESC.setOnMouseClicked(this::handleDESC);
        Back.setOnMouseClicked(this::BACK);
        pdf.setOnMouseClicked(this::printScreen);
        questionFlowPane.setHgap(10);
        questionFlowPane.setVgap(10);
        questionFlowPane.setPadding(new Insets(10, 10, 10, 10));

    }



    private void BACK(MouseEvent mouseEvent) {
        ExamController.dashBordController.SwitchToExamen(mouseEvent);

    }

    private void handleASC(MouseEvent event) {
        tri="ASC";
        System.out.println(tri);
        loadQuestionData();
    }
    private void handleDESC(MouseEvent event) {
        tri="DESC";
        System.out.println(tri);
        loadQuestionData();
    }
    @FXML
    private void handleMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        tripar.setText(menuItem.getText());
        trierpar=tripar.getText();
    }

    @FXML
    private void handleAddIconClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Question/ajouterQuestion.fxml"));
            Parent root = loader.load();
            AddQuestionController controller = loader.getController();
            controller.initData(exam.getIdexams());

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 406, 485));
            stage.setTitle("Add Question");
            stage.showAndWait();
            loadQuestionData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadQuestionData() {
        questionFlowPane.getChildren().clear();
        for (Question question : serviceQuestion.getQuestionsByExam(exam.getIdexams(),tri,chercher.getText(),trierpar)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CoachTemplate/Question/CardQuestion.fxml"));
                Node card = loader.load();
                QuestionCardController controller = loader.getController();
                controller.setQuestion(question);
                controller.setQc(this);
                questionFlowPane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setExam(Exam selectedExam) { exam=selectedExam;
    }

    public void initData(Exam exam) {
        setExam(exam);
        loadQuestionData();
        System.out.println(exam);
        /*TwilioUtil.SendSMS("PDF");
        Gmailer gmailer = null;
        try {
            gmailer = new Gmailer();
            gmailer.sendMail("PDF" , "PDF DONE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    public void printScreen(MouseEvent mouseEvent) {
        Thread pdfThread = new Thread(() -> {
            // Create content list
            List<String> content = new ArrayList<>();
            // Add header row to the content list
            content.add("Question    Choix1     Choix2     Choix3     ChoixCorrect     Duree");
            for (Question q : serviceQuestion.getQuestionsByExamId(exam.getIdexams())) {
                // Format question and choices to align properly
                String formattedQuestion = String.format("%-10s", q.getQuestion());
                String formattedChoice1 = String.format("%-13s", q.getChoix1());
                String formattedChoice2 = String.format("%-10s", q.getChoix2());
                String formattedChoice3 = String.format("%-10s", q.getChoix3());
                String formattedCorrectChoice = String.format("%-18s", q.getChoixCorrect());
                String formattedDuration = String.format("%-10s", q.getDuree());
                // Combine formatted strings and add to content list
                content.add(formattedQuestion + formattedChoice1 + formattedChoice2 + formattedChoice3 +
                        formattedCorrectChoice + formattedDuration);
            }

            // Create a new PDF document
            try (PDDocument document = new PDDocument()) {
                // Add a new page to the document
                PDPage page = new PDPage();
                document.addPage(page);

                // Add content to the PDF document
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Add the title
                    contentStream.beginText();
                    PDType1Font font = new PDType1Font(COURIER_BOLD);
                    contentStream.setFont(font, 36);
                    contentStream.newLineAtOffset(50, 750); // Adjusted offset for title
                    contentStream.showText("Question de l'examen " + exam.getModule());
                    contentStream.endText();

                    // Add the table data
                    contentStream.setLineWidth(0.5f); // Set border width
                    float startX = 50;
                    float startY = 500; // Adjusted startY after adding image
                    float cellWidth = 100;
                    float cellHeight = 20;
                    for (int i = 0; i < content.size(); i++) {
                        String rowData = content.get(i);
                        System.out.println(rowData);
                        String[] cellData = rowData.split("\\s+");
                        for (int j = 0; j < cellData.length; j++) {
                            // Draw cell border

                            contentStream.addRect(startX + (cellWidth * j), startY - (cellHeight * (i +2)), cellWidth, cellHeight);
                            contentStream.stroke();
                            // Write cell content
                            contentStream.beginText();
                            PDType1Font font1 = new PDType1Font(COURIER);
                            contentStream.setFont(font1, 12);
                            contentStream.newLineAtOffset(startX + (cellWidth * j) + 2, startY - (cellHeight * (i + 1)) - 12);
                            contentStream.showText(cellData[j]);
                            contentStream.endText();

                        }
                    }

                    // Add an image
                    try (InputStream in = getClass().getResourceAsStream("/Images/MahmoudAbid.png")) {
                        BufferedImage bImage = ImageIO.read(in);
                        PDImageXObject image = LosslessFactory.createFromImage(document, bImage);
                        float imageWidth = image.getWidth() / 2;
                        float imageHeight = image.getHeight() / 2;
                        float imageX = page.getMediaBox().getWidth() - 50 - imageWidth; // Adjusted for margin
                        float imageY = 50; // Adjusted for margin
                        contentStream.drawImage(image, imageX, imageY, imageWidth, imageHeight); // Adjust image position and size as needed
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                // Save the document
                document.save("Examenn.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pdfThread.setDaemon(true);
        pdfThread.start();
    }



}

