package sample.demo.css;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmbeddingCss extends Application {
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

        Label userLabel = new Label("User Name: ");
        //Cài ID cho CSS
        userLabel.setId("user-label");
        GridPane.setConstraints(userLabel, 1, 2);

        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 1, 3);

        TextField userInput = new TextField();
        userInput.setPromptText("username");
        GridPane.setConstraints(userInput,2,2);

        TextField passwordInput = new TextField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput,2,3);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setUserAgentStylesheet(STYLESHEET_MODENA);
            }
        });
        GridPane.setConstraints(loginButton,2,4);

        Button signUpButton = new Button("Sign up");
        //Cài classCSS cho button để dùng cho css
        signUpButton.getStyleClass().add("button-signup");
        GridPane.setConstraints(signUpButton,3,4);

        gridPane.getChildren().addAll(userLabel,userInput,passwordLabel,passwordInput,loginButton,signUpButton);

        Scene scene = new Scene(gridPane,200,300);
        //Nhúng css vào file java
        scene.getStylesheets().add("/sample/demo/css/style.css");
        window.setScene(scene);

        window.show();
    }
}
