package sample.demo.stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SwitchScene extends Application {
    private Stage window = new Stage();
    private Scene scene1, scene2;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        //Tạo Button 1
        Label label1 = new Label("Đây là scene 1");
        Button button1 = new Button("Ấn để chuyển scene");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(scene2);
            }
        });

        //layout 1 là vertical Column
        VBox layout1 = new VBox(20);
        layout1.getChildren().add(label1);
        layout1.getChildren().add(button1);
        scene1 = new Scene(layout1,200,200);

        //Tạo Button 2
        Label label2 = new Label("Đây là scene 2");
        label2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setScene(scene1);
            }
        });
        Button button2 = new Button();
        button2.setText("Ấn để chuyển scene ");
        button2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setScene(scene1);
            }
        });

        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        layout2.getChildren().add(label2);
        scene2 = new Scene(layout2,300,300);

        window.setScene(scene1);
        window.setTitle("Đây là demo Switch Scene");
        window.show();
    }
}
