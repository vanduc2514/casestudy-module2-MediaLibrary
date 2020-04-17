package sample.demo.layout;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GridLayout extends Application {
    private Stage window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        //Username label
        Label userLabel = new Label("UserName: ");
        GridPane.setConstraints(userLabel, 1, 2);

        //Password Label
        Label passwordLabel = new Label("UserName: ");
        GridPane.setConstraints(passwordLabel, 1, 3);

        //UserName input
        TextField userInput = new TextField();
        userInput.setPromptText("username");
        GridPane.setConstraints(userInput,2,2);

        //Password Input
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput,2,3);

        //Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton,2,4);

        gridPane.getChildren().addAll(userLabel,userInput,passwordLabel,passwordInput,loginButton);

        Scene scene = new Scene(gridPane,200,300);
        window.setScene(scene);

        window.show();
    }
}
