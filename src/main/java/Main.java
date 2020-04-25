package main.java;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.resources.controllers.MainStage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Media Library 1.0");
        primaryStage.getIcons().add(new Image(new File("src/main/resources/images/application-icon.png").toURI().toString()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/MainStage.fxml"));
        Parent root;
        try {
            root = loader.load();
            File file = new File("src/main/resources/css/style.css");
            root.getStylesheets().add(file.toURI().toString());
            MainStage controller = loader.getController();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    controller.exit();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
