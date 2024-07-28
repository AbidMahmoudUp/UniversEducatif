package com.example.javafxprojectusertask.Simulation;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
//import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.col.ColModelImporter;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class SimulationFactory implements EntityFactory {



    @Spawns("ground")
    public Entity newGround(SpawnData data)
    {
        var mat = new PhongMaterial(Color.BROWN);
        var view = new Box(500,0.4,500);
        view.setMaterial(mat);

        return entityBuilder(data)
                .type(EntityType.GROUND)
                //.bbox(BoundingShape.box3D(500,0.4,500))
                .view("CoarseConcrete_Albedo.png")
                //.collidable()
                .build();

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

   /* private Group loadModelObj(String url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        for (MeshView view : importer.getImport()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }*/

    @Spawns("stand")
    public Entity newStand(SpawnData data)
    {

        Group model = loadModelCol("src/main/java/com/example/javafxprojectusertask/Simulation/standsoc.dae");
        model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        return entityBuilder(data)
                .type(EntityType.STAND)
                .view(model)
                .collidable()
                .bbox(BoundingShape.box3D(4,50,4))
                .build();
    }
    @Spawns("player")
    public Entity newPlatform(SpawnData data)
    {
        var mat = new PhongMaterial(Color.BLUE);
        var view = new Box(2,0.2,2);
        view.setMaterial(mat);

        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(BoundingShape.box3D(2,1,2))
                .view(view)
                .collidable()
                .build();

    }

    @Spawns("person")
    public Entity newPerson(SpawnData data)
    {

        //Group model = loadModelCol("src/main/java/com/example/testfx7002/Simulation/TEST8001.dae");
        //model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        return entityBuilder(data)
                .type(EntityType.STAND)
                //.view(model)
                .collidable()
                .bbox(BoundingShape.box3D(1,50,1))
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data)
    {
        /*var pixels = new ColoredTexture(1024, 1024, Color.RED)
                .pixels()
                .stream()
                .map(p -> p.copy(FXGLMath.randomColor()))
                .collect(Collectors.toList());

        //System.out.println(pixels);

        // can be any image, including texture cube map

        //var image = ImagesKt.fromPixels(1024, 1024, pixels);*/
        //var view = new Box(3,500,500);

        //view.set
        return entityBuilder()
                .type(EntityType.WALL)
                .bbox(BoundingShape.box3D(500,500,3.8))
                .collidable()
                .build();
    }
}
