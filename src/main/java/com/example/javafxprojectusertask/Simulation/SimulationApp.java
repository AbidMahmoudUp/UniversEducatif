package com.example.javafxprojectusertask.Simulation;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Camera3D;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.example.javafxprojectusertask.Controller.UserController.User.WelcomePageController;
import com.example.javafxprojectusertask.Entities.Dossier;
import com.example.javafxprojectusertask.Entities.Offre;
import com.example.javafxprojectusertask.Entities.Societe;
import com.example.javafxprojectusertask.Services.ServiceDossier;
import com.example.javafxprojectusertask.Services.ServiceOffre;
import com.example.javafxprojectusertask.Services.ServiceSociete;
import com.example.javafxprojectusertask.Utilities.UserUtils;
import com.interactivemesh.jfx.importer.col.ColModelImporter;
import javafx.collections.FXCollections;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.example.javafxprojectusertask.Simulation.EntityType.*;

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class SimulationApp extends GameApplication // implements Runnable
{


    //FXGLPane fxglPane = new FXGLPane(1366,768);;


    private Entity player;

    ArrayList<Dossier> dossierArrayList = new ArrayList<>();
    Text[] dossierText = null;

    double lastStandPos;
    private Entity[] stands;

    private Entity[] persons;
    private Camera3D camera;

    ArrayList<Societe> societeArrayList = new ArrayList<>();

    ArrayList<Offre> offres = new ArrayList<>();

    ChoiceBox<Offre> test;
    private Entity wall1;


    Entity stand = null;

    int userId = UserUtils.ConnectedUser.getIdUser();

    private ArrayList<Offre> arrayList = new ArrayList<>();

    ServiceOffre serviceOffre = new ServiceOffre();

    ServiceDossier serviceDossier = new ServiceDossier();

    ServiceSociete serviceSociete = new ServiceSociete();

    @Override
    protected void initSettings(GameSettings settings) {
        //fxglPane = new FXGLPane(1366,768);
        settings.set3D(true);
        settings.setWidth(1366);
        settings.setHeight(768);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.Z,() -> {
                camera.getTransform().translateZ(0.05 * (1 * Math.cos(Math.toRadians(camera.getTransform().getRotationY()))));
                camera.getTransform().translateX(0.05 * (1 * Math.sin(Math.toRadians(camera.getTransform().getRotationY()))));
                player.setPosition3D(camera.getTransform().getPosition3D());

        });
        /*onKey(KeyCode.S,() -> {
            if(!blockBackward)
            {//player.translateZ(-0.5);
                camera.getTransform().translateZ(-0.05 * (1 * Math.cos(Math.toRadians(camera.getTransform().getRotationY()))));
                camera.getTransform().translateX(-0.05 * (1 * Math.sin(Math.toRadians(camera.getTransform().getRotationY()))));
                player.setPosition3D(camera.getTransform().getPosition3D());
                forward = false;
            }

        });*/
        onKey(KeyCode.Q,() -> {
            //player.translateX(-0.5);
            //camera.getTransform().translateX(-0.5);
            camera.getTransform().setRotationY(camera.getTransform().getRotationY() - 2);
            player.setPosition3D(camera.getTransform().getPosition3D());
        });
        onKey(KeyCode.D,() -> {
            //camera.getTransform().translateX(0.5);
            camera.getTransform().setRotationY(camera.getTransform().getRotationY() + 2);
            player.setPosition3D(camera.getTransform().getPosition3D());
        });

        onKey(KeyCode.F,() -> {
            if(player.isColliding(stand))
            {
                /*var graph = getAssetLoader().loadDialogueGraph("test1.json");
                getCutsceneService().startDialogueScene(graph);*/
                lastStandPos = stand.getPosition().getX() + 25;
                arrayList.clear();
                dossierArrayList = serviceDossier.getDossierByUser(userId);
                offres = dossierArrayList.stream().map(Dossier::getOffre).collect(Collectors.toCollection(ArrayList::new));
                int index = (int) (lastStandPos / 10);
                arrayList = serviceOffre.getBySociete(societeArrayList.get(index));
                System.out.println(arrayList);
                arrayList.removeAll(offres);
                System.out.println(arrayList);
                List<String> lines;
                lines = new ArrayList<>();
                if(arrayList.isEmpty())
                {
                    lines.add("1.name = "+societeArrayList.get(index).getNom());
                    lines.add("1:Bonjour, Malheureusement il n y'a pas d'offres pour le moment !");
                }
                else
                {
                    lines.add("1.name = "+societeArrayList.get(index).getNom());
                    lines.add("1: Bonjour voici notre offres de travail !");
                }

                var cutscene = new Cutscene(lines);
                getCutsceneService().startCutscene(cutscene);
                //if(available)
                    //System.out.println(test.getItems());

                //available = true;
                updateUI(lastStandPos);

            }
        });

        onKey(KeyCode.R,() -> updateDossier());

        onKey(KeyCode.C,() -> {
            List<String> lines;
            if(test.getValue() == null)
            {
                lines = new ArrayList<>();
                lines.add("1.name = Systeme");
                lines.add("1: Choisir une offre !");
            }
            else
            {
                lines = new ArrayList<>();
                lines.add("1.name = Systeme\n");
                lines.add("1: Merci pour appliquer l'offre !");
                lines.add("1: Veuillez attender jusqu'a tu as reçu un email !");
                Dossier d = new Dossier();
                d.setStatus("En attente");
                d.setIdUser(userId);
                d.setOffre(test.getValue());
                serviceDossier.add(d);
                dossierArrayList = serviceDossier.getDossierByUser(userId);
                offres = dossierArrayList.stream().map(Dossier::getOffre).collect(Collectors.toCollection(ArrayList::new));
                updateUI(lastStandPos);
                updateDossier();
            }
            var cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
        });

        onKey(KeyCode.P,() -> {
            System.out.println("SHUT");
            WelcomePageController controller = new WelcomePageController();
            Stage stage = new Stage();
            stage = (Stage) getGameScene().getRoot().getScene().getWindow();
            //System.out.println(stage.toString());
            try {
                controller.switchToWelcomePage((Stage) getGameScene().getRoot().getScene().getWindow());
            } catch (Exception e) {
                //throw new Exception(e);
            }
            embeddedShutdown();
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("tuto",0);
    }


   private Group loadModelCol(String url) {
        Group modelRoot = new Group();

        ColModelImporter importer = new ColModelImporter();
        importer.read(url);

        for (Node view : importer.getImport()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }
    @Override
 public void initGame()
 {

     camera = getGameScene().getCamera3D();
     camera.getTransform().translateY(-1);
     camera.getTransform().translateX(2);
     camera.getTransform().translateZ(0);
     getGameScene().setFPSCamera(true);
     FXGL.getGameWorld().addEntityFactory(new SimulationFactory());
     spawn("ground",0,0,0);

     player = spawn("player",5,5,0);
     //camera.getTransform().lookAt(player.getPosition3D());
     //platform = spawn("platform",0,1,0);
     societeArrayList = serviceSociete.getAll();
     dossierArrayList = serviceDossier.getDossierByUser(userId);
     offres = dossierArrayList.stream().map(Dossier::getOffre).collect(Collectors.toCollection(ArrayList::new));
     stands = new Entity[societeArrayList.size()];
     persons = new Entity[societeArrayList.size()];

     Collections.shuffle(societeArrayList);

     int size = 0;
     if(societeArrayList.size() < 5)
         size = societeArrayList.size();
     else
         size = 5;

     int finalSize = size;
     getExecutor().startAsyncFX(() -> {

         int x = -25;
         for(int i = 0; i < finalSize; i++)
         {
             stands[i] = spawn("stand",x,0,0);


            Group model = loadModelCol("src/main/java/com/example/javafxprojectusertask/Simulation/TEST8001.dae");
             Entity person = entityBuilder()
                     .view(model)
                     .buildAndAttach();

             person.setUpdateEnabled(false);
             person.setEverUpdated(false);
             person.setPosition3D(new Point3D(x,0,-1));
             //model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));



             System.out.println(person);

             x = x + 10;


             //addUINode(label,x,1);
         }

         Image image = new Image("C:\\Users\\Wicked\\Desktop\\JavaFxProject\\JavaFxProjectUserTask_Integration\\JavaFxProjectUserTask\\src\\main\\resources\\assets\\textures\\CoarseConcrete_Albedo.jpg");
            Texture texture = new Texture(image);
            Image imageSky = new Image("C:\\Users\\Wicked\\Desktop\\JavaFxProject\\JavaFxProjectUserTask_Integration\\JavaFxProjectUserTask\\src\\main\\resources\\assets\\textures\\Front.png");
         Image imageSky1 = new Image("C:\\Users\\Wicked\\Desktop\\JavaFxProject\\JavaFxProjectUserTask_Integration\\JavaFxProjectUserTask\\src\\main\\resources\\assets\\textures\\test.png");
            Texture textureSky1 = new Texture(imageSky);
         Texture textureSky2 = new Texture(imageSky);
         Texture textureSky3 = new Texture(imageSky);
         Texture textureSky4 = new Texture(imageSky1);
         Texture textureSky5 = new Texture(imageSky);
         var ground = entityBuilder()
                 //.bbox(BoundingShape.box3D(500,0.4,500))
                 .view(texture)
                 //.collidable()
                 .buildAndAttach();

         ground.setPosition3D(new Point3D(-512,0,-512));
         ground.rotationXProperty().set(90);

         var sky = entityBuilder()
                 //.type(EntityType.WALL)
                 .view(textureSky1)
                 /*.bbox(BoundingShape.box3D(500,500,0.1))
                 .collidable()*/
                 .buildAndAttach();
         sky.setPosition3D(new Point3D(-512,-500,500));
         //wall.setVisible(false);
         sky.rotationYProperty().set(0);
         sky = entityBuilder()
                 //.type(EntityType.WALL)
                 .view(textureSky2)
                 /*.bbox(BoundingShape.box3D(500,500,0.1))
                 .collidable()*/
                 .buildAndAttach();
         sky.setPosition3D(new Point3D(-512,-500,512));
         sky.rotationYProperty().set(90);
         //sky.rotationZProperty().set(180);
//
//
         sky =  entityBuilder()
                 //.type(EntityType.WALL)
                 .view(textureSky3)
                 /*.bbox(BoundingShape.box3D(500,500,0.1))
                 .collidable()*/
                 .buildAndAttach();

         sky.setPosition3D(new Point3D(500,-500,512));
         sky.rotationYProperty().set(90);
//
//
//         //sky.rotationZProperty().set(180);
//
         sky = entityBuilder()
                 //.type(EntityType.WALL)
                 .view(textureSky5)
                 /*.bbox(BoundingShape.box3D(500,500,0.1))
                 .collidable()*/
                 .buildAndAttach();
         sky.setPosition3D(new Point3D(-512,-500,-500));
         //sky.rotationYProperty().set(0);
//
//
         sky = entityBuilder()
                 //.type(EntityType.WALL)
                 .view(textureSky4)
                 /*.bbox(BoundingShape.box3D(500,500,0.1))
                 .collidable()*/
                 .buildAndAttach();

         sky.setPosition3D(new Point3D(-512,-500,-500));
         sky.rotationXProperty().set(90);
//        //skyBox();

         //getExecutor().startAsyncFX(() -> getGameController().exit());
     });

     runOnce(() -> {
         spawn("wall");
     }, Duration.seconds(1));



     /*wall2 = spawn("wall",-1,0,0);
     wall2.setPosition3D(new Point3D(0,0,-0.2));
     //wall2.setVisible(false);
     //wall2.rotationYProperty().set(90);
     double lastStandPosZ = stands[stands.length - 1].getPosition3D().getZ();
     wall3 = spawn("wall",0,0,lastStandPosZ);
     wall3.setPosition3D(new Point3D(0,0,-0.2));
     //wall3.setVisible(false);
     //wall3.rotationYProperty().set(90);
     wall4 = spawn("wall",0,0,-5);
     wall4.setPosition3D(new Point3D(0,0,-0.2));
     //wall4.setVisible(false);
     wall4.rotationYProperty().set(90);*/



 }

    @Override
    protected void initPhysics() {

        onCollisionBegin(PLAYER,STAND,(player,stand1) -> {
            stand = stand1;
            System.out.println(stand.getPosition());

        });

        onCollisionBegin(PLAYER,WALL,(player,wall1) -> {
            System.out.println("Collision WALL");

                camera.getTransform().translateZ(-0.2 * (1 * Math.cos(Math.toRadians(camera.getTransform().getRotationY()))));


        });
        /*onCollisionBegin(PLAYER,WALL,(player,wall2) -> {
            System.out.println("Collision WALL");

                camera.getTransform().translateZ(-0.2 * (1 * Math.cos(Math.toRadians(camera.getTransform().getRotationY()))));


        });onCollisionBegin(PLAYER,WALL,(player,wall3) -> {
            System.out.println("Collision WALL");

                camera.getTransform().translateZ(-0.2 * (1 * Math.cos(Math.toRadians(camera.getTransform().getRotationY()))));


        });onCollisionBegin(PLAYER,WALL,(player,wall4) -> {
            System.out.println("Collision WALL");

                camera.getTransform().translateZ(-0.2 * (1 * Math.cos(Math.toRadians(camera.getTransform().getRotationY()))));

        });*/

       /* onCollisionEnd(STAND,PLAYER,(stand,player) -> {
            System.out.println("TEST");
            blockBackward = false;
            blockForward = false;
        });*/
    }

    void updateUI(double x)
    {
        arrayList.clear();
        int index = (int) (x / 10);
        System.out.println(index);
        arrayList = serviceOffre.getBySociete(societeArrayList.get(index));
        arrayList.removeAll(offres);
        /*arrayList.add("DEV ");
        arrayList.add("ASES");
        arrayList.add("MANAGER");
        arrayList.add("KASKAS");*/
        removeUINode(test);
        test = getUIFactoryService().newChoiceBox(FXCollections.observableList(arrayList));
        addUINode(test,50,75);
    }


    private void clearDossierNode()
    {
        //removeUINode();
    }

    private void removeOldText()
    {
        if(dossierText != null)
        {
            for(int i = 0; i < dossierText.length; i++)
            {
                removeUINode(dossierText[i]);
            }
        }
    }
    private void updateDossier()
    {
        int x = 875;
        int y = 50;
        removeOldText();
        dossierArrayList = serviceDossier.getDossierByUser(userId);
        dossierText = new Text[dossierArrayList.size()];
        System.out.println(dossierArrayList.size());
        for(int i = 0; i < dossierArrayList.size(); i++)
        {
            System.out.println(dossierArrayList.get(i).getStatus().toLowerCase());
            if(dossierArrayList.get(i).getStatus().equalsIgnoreCase("en attente")) {
                dossierText[i] = getUIFactoryService().newText(dossierArrayList.get(i).getOffre().getDesc()+" "+dossierArrayList.get(i).getStatus(),Color.YELLOW,20.0);
                //getUIFactoryService().newText(dossierArrayList.get(i).getOffre().getDesc()+" "+dossierArrayList.get(i).getStatus(),Color.YELLOW,20.0);
                addUINode(dossierText[i],x,y);
                y = y + 25;
            }
            if(dossierArrayList.get(i).getStatus().equalsIgnoreCase("accepté")) {
                dossierText[i] = (getUIFactoryService().newText(dossierArrayList.get(i).getOffre().getDesc()+" "+dossierArrayList.get(i).getStatus(),Color.GREEN,20.0));
                //getUIFactoryService().newText(dossierArrayList.get(i).getOffre().getDesc()+" "+dossierArrayList.get(i).getStatus(),Color.YELLOW,20.0);
                addUINode(dossierText[i],x,y);
                y = y + 25;
            }
            if(dossierArrayList.get(i).getStatus().equalsIgnoreCase("refusé")) {
                dossierText[i] = (getUIFactoryService().newText(dossierArrayList.get(i).getOffre().getDesc()+" "+dossierArrayList.get(i).getStatus(),Color.RED,20.0));
                //getUIFactoryService().newText(dossierArrayList.get(i).getOffre().getDesc()+" "+dossierArrayList.get(i).getStatus(),Color.YELLOW,20.0);
                addUINode(dossierText[i],x,y);
                y = y + 25;
            }
        }
    }
    @Override
    protected void initUI() {
        //arrayList.add("FFFFFF");
        //arrayList.add("GGGGGGGG");

        var scoreText = getUIFactoryService().newText("", Color.DARKGRAY,15.0);
        var dossierText = getUIFactoryService().newText("Vos dossier",Color.DARKGRAY,20.0);
        var offreText = getUIFactoryService().newText("Les Offres : ",Color.DARKGRAY,20.0);
        addUINode(dossierText,1000,25);
        addUINode(offreText,50,50);
        updateDossier();

            //var offreText = getUIFactoryService().newButton("");
        test = getUIFactoryService().newChoiceBox(FXCollections.observableList(arrayList));
        test.autosize();
        //var button = getUIFactoryService().newButton("apply");
            //offreText.textProperty().bind(getip("score").asString("OFFRE 1"));
        scoreText.textProperty().bind(getip("tuto").asString("Toucher Z,Q,D\n pour déplacer\n Quand tu approche un stand appuyer sur F\n Choisir de la liste l'offre que tu veux appliquer\n puis appuyer C pour confirmer l'envoi de dossier\nAppuyer R pour refrechir vos dossiers"));
        addUINode(scoreText,50,450);
            //addUINode(offreText,50,100);
        addUINode(test, 50, 75);



    }


    /*@Override
    public void run() {

        try {
            fxglPane.setRenderFill(Color.TRANSPARENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("TEST");
        GameApplication.embeddedLaunch(new CrystalApp());
        fxglPane.getScene();
        //MainThread.gameStage.setScene(fxglPane.getScene());
        //MainThread.gameStage.show();
        Platform.runLater(() -> {

        });
    }*/
}
