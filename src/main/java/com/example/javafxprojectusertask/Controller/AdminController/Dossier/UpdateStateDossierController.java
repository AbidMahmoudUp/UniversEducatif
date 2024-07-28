package com.example.javafxprojectusertask.Controller.AdminController.Dossier;

import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Services.Gmailer;
import com.example.javafxprojectusertask.Services.ServiceDossier;
import com.example.javafxprojectusertask.Services.UserService;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateStateDossierController {

    public static Dossier d;


    @FXML
    private ChoiceBox<String> StateDossier;

    @FXML
    public void initialize()
    {
        StateDossier.getItems().addAll("Refusé", "Accepté");

    }

    @FXML
    public void updateButton(MouseEvent event)
    {
        ServiceDossier serviceDossier = new ServiceDossier();
        d.setStatus(StateDossier.getValue());
        serviceDossier.update(d);
        Gmailer gmailer = null;
        try {
            gmailer = new Gmailer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UserClass user = new UserClass();
        UserService service = new UserService();
        try {
            user = service.getUserById(d.getIdUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(d.getStatus().equalsIgnoreCase("Accepté"))
        {
            try {
                gmailer.sendMail("Reponse de votre dossier","Félicitations pour avoir été accepté au sein de notre société ! Ton admission témoigne de ton talent exceptionnel et de ton engagement envers l'excellence. En tant que nouvel employé, tu fais maintenant partie intégrante de notre équipe dynamique et innovante. C'est une opportunité passionnante de contribuer au succès de notre société et de participer à des projets stimulants. Nous croyons fermement que tes compétences et ton enthousiasme vont enrichir notre environnement de travail. N'hésite pas à explorer les différentes ressources et à t'impliquer dans les activités de l'entreprise. Nous encourageons la collaboration et l'échange d'idées, car c'est ensemble que nous atteindrons de nouveaux sommets. Bienvenue dans notre équipe, nous sommes impatients de voir les grandes choses que nous accomplirons ensemble !",user.getEmail());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            try {
                gmailer.sendMail("Reponse de votre dossier","Nous comprenons que recevoir une notification de refus peut être décevant, mais nous tenons à te remercier sincèrement pour l'intérêt que tu as manifesté envers notre société. La sélection est toujours un processus difficile et compétitif, et la qualité des candidats était exceptionnelle. Bien que ta candidature n'ait pas été retenue pour le moment, cela ne doit pas être interprété comme un reflet de tes compétences et de ta valeur. Nous encourageons vivement à continuer à développer tes compétences et à explorer d'autres opportunités. Les chemins professionnels sont variés, et nous espérons que tu trouveras le cadre idéal pour mettre en valeur tes talents. Nous te remercions encore une fois pour ton intérêt et te souhaitons beaucoup de succès dans tes futures démarches professionnelles.",user.getEmail());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
