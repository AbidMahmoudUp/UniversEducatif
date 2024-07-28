package com.example.javafxprojectusertask.Controller.UserController.Cours;

import com.example.javafxprojectusertask.Entities.Cour;
import com.example.javafxprojectusertask.Services.ServiceAudio;
import com.example.javafxprojectusertask.Services.ServiceCour;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CourDetailsController {

    @FXML
    private VBox audioPlayer;


    @FXML
    private Label descriptionCour;

    @FXML
    private Label nomCour;

    @FXML
    private Hyperlink pdfCour;

    @FXML
    private VBox videoPlayer;
    @FXML
    private TextArea videoText;

    public static Cour cour;

    @FXML
    public void initialize() throws IOException {
        initializeData(this.cour);
    }

    public void initializeData(Cour cour) throws IOException {
        ServiceCour serviceCour = new ServiceCour();
        System.out.println(serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath());
        if(serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath()==null){
        FXMLLoader mediaPlayer = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/MediaPlayer.fxml"));
        MediaPlayerController.path=serviceCour.getCoursbyId(cour.getIdCour()).getAudioPath();
       // System.out.println("Test : "+ "C:\\xampp\\htdocs\\files\\ExamenPrincipal_2012_2013-Enoncé-et-Corrigé.wav".equals( MediaPlayerController.path));
        VBox media = mediaPlayer.load(getClass().getResource("/GUI/UserTemplate/Cours/MediaPlayer.fxml"));
        audioPlayer.getChildren().add(media);
        mediaPlayer.load();
        }
        else {
            FXMLLoader mediaPlayer = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/MediaPlayer.fxml"));
            MediaPlayerController.path=serviceCour.getCoursbyId(cour.getIdCour()).getAudioPath();
            // System.out.println("Test : "+ "C:\\xampp\\htdocs\\files\\ExamenPrincipal_2012_2013-Enoncé-et-Corrigé.wav".equals( MediaPlayerController.path));
            VBox media = mediaPlayer.load(getClass().getResource("/GUI/UserTemplate/Cours/MediaPlayer.fxml"));
            audioPlayer.getChildren().add(media);
            mediaPlayer.load();

        FXMLLoader videoplayer = new FXMLLoader(getClass().getResource("/GUI/UserTemplate/Cours/VideoPlayer.fxml"));
        VideoPlayerController.path=serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath();
        VBox video=videoplayer.load(getClass().getResource("/GUI/UserTemplate/Cours/VideoPlayer.fxml"));
        videoPlayer.getChildren().add(video);
        videoplayer.load();

            // Create a new thread for audio extraction
            Thread audioExtractionThread = new Thread(() -> {
                extractAudioFromVideo(serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath(),serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath().replace(".mp4",".mp3"));
                ServiceAudio serviceAudio =new ServiceAudio();
                URI uri = null;
                try {
                    uri = new URI(serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath().replace(".mp4",".mp3"));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                String ftext="C:/xampp/htdocs"+uri.getPath();
                System.out.println(ftext);
                String videoToText = serviceAudio.audioToText(ftext);

                videoText.setText(videoToText);
                System.out.println(videoToText);
                System.out.println("DESC "+videoText.getText());

                // Supprimer le fichier créé
                File outputFile = new File(serviceCour.getCoursbyId(cour.getIdCour()).getVideoPath().replace(".mp4",".mp3"));
                if (outputFile.exists()) {
                    if (outputFile.delete()) {
                        System.out.println("Fichier audio supprimé avec succès !");
                    } else {
                        System.err.println("Impossible de supprimer le fichier audio !");
                    }
                }
            });
            // Start the thread
            audioExtractionThread.start();
        }

        //   mediaPlayerController.setPath(serviceCour.getCoursbyId(cour.getIdCour()).getAudioPath());


        nomCour.setText(cour.getNom());

        descriptionCour.setText(cour.getDescription());
        pdfCour.setText(cour.getFichierPath());
    }
    public void extractAudioFromVideo(String videoFilePath, String outputAudioFilePath) {
        try {
            // Construction de la commande FFmpeg pour extraire l'audio du fichier vidéo MP4
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", videoFilePath, "-vn", "-c:a", "libmp3lame", outputAudioFilePath);

            // Redirection des entrées/sorties standard vers le flux actuel de la console
            pb.redirectErrorStream(true);
            pb.inheritIO();

            // Démarrage du processus
            Process process = pb.start();

            // Attente de la fin du processus
            process.waitFor();

            System.out.println("Extraction de l'audio terminée avec succès !");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}