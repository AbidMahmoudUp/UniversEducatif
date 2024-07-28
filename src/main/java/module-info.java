module com.example.javafxprojectusertask {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;

    requires mysql.connector.java;
    requires java.sql;
    requires java.prefs;
    requires java.base;
    exports com.example.javafxprojectusertask.Controller;
    opens com.example.javafxprojectusertask.Controller to javafx.fxml;
    exports com.example.javafxprojectusertask.Utilities;
    opens com.example.javafxprojectusertask.Utilities to javafx.fxml;
    exports com.example.javafxprojectusertask.Test;
    opens com.example.javafxprojectusertask.Test to javafx.fxml;

    exports com.example.javafxprojectusertask.Controller.CoachController;
    opens com.example.javafxprojectusertask.Controller.CoachController to javafx.fxml;

    exports com.example.javafxprojectusertask.Controller.AdminController.Dossier;
    opens com.example.javafxprojectusertask.Controller.AdminController.Dossier to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.AdminController.Societe;
    opens com.example.javafxprojectusertask.Controller.AdminController.Societe to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.AdminController.Offre;
    opens com.example.javafxprojectusertask.Controller.AdminController.Offre to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Offre;
    opens com.example.javafxprojectusertask.Controller.UserController.Offre to javafx.fxml;
    exports com.example.javafxprojectusertask.Simulation;
    opens com.example.javafxprojectusertask.Simulation to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.CoachController.Examen;
    opens com.example.javafxprojectusertask.Controller.CoachController.Examen to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Quiz;
    opens com.example.javafxprojectusertask.Controller.UserController.Quiz to javafx.fxml;

    exports com.example.javafxprojectusertask.Controller.AdminController.Module;
    opens com.example.javafxprojectusertask.Controller.AdminController.Module to javafx.fxml;

    exports com.example.javafxprojectusertask.Controller.CoachController.Cours;
    opens com.example.javafxprojectusertask.Controller.CoachController.Cours to javafx.fxml;

    exports com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque;
    opens com.example.javafxprojectusertask.Controller.AdminController.Bibliotheque to javafx.fxml;

    exports com.example.javafxprojectusertask.Controller.AdminController.Livre;
    opens com.example.javafxprojectusertask.Controller.AdminController.Livre to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit;
    opens com.example.javafxprojectusertask.Controller.AdminController.Magasin.Produit to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.AdminController.Magasin.Commande;
    opens com.example.javafxprojectusertask.Controller.AdminController.Magasin.Commande to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Cours;
    opens com.example.javafxprojectusertask.Controller.UserController.Cours to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Module;
    opens com.example.javafxprojectusertask.Controller.UserController.Module to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.AdminController.Admin;
    opens com.example.javafxprojectusertask.Controller.AdminController.Admin to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.User;
    opens com.example.javafxprojectusertask.Controller.UserController.User to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Bibliotheque;
    opens com.example.javafxprojectusertask.Controller.UserController.Bibliotheque to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Livre;
    opens com.example.javafxprojectusertask.Controller.UserController.Livre to javafx.fxml;
    exports com.example.javafxprojectusertask.Controller.UserController.Magasin;
    opens com.example.javafxprojectusertask.Controller.UserController.Magasin to javafx.fxml;
    //  exports com.example.javafxprojectusertask.Controller.AdminController;
  //  opens com.example.javafxprojectusertask.Controller.AdminController to javafx.fxml;


    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires mail;
    requires jdk.httpserver;



    requires bcrypt;
    requires com.google.api.client.json.jackson2;
    requires java.desktop;
    requires google.api.services.oauth2.v2.rev157;
   // requires google.oauth.client;
    //requires google.http.client;

    requires activation;
    requires com.almasb.fxgl.all;
    requires org.apache.pdfbox;
    //requires jimObjModelImporterJFX;
    requires javafx.swing;
    requires core;
    requires twilio;
    requires leopard.java;
    requires freetts;
    requires commons.cli;
    requires itextpdf;
    requires com.google.gson;
    requires org.apache.commons.io;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.json;
    requires javafx.web;
    requires javafx.media;
    //requires jimColModelImporterJFX;
    //requires jimColModelImporterJFX;
    requires jimColModelImporterJFX;
    //requires jimColModelImporterJFX;
    //requires twilio;

}