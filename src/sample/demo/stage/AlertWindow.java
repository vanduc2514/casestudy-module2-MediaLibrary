package sample.demo.stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertWindow extends Application {
    private Stage window = new Stage();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Hello");

        Button button = new Button("Ấn vào đây");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AlertBox alertBox = new AlertBox();
                alertBox.display("Đây là window mới","Ấn vào nút đi");
            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout,200,200);
        window.setScene(scene);
        window.show();
    }

    public static class AlertBox{
        public static void display(String title, String message) {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setMinWidth(250);

            Label label = new Label();
            label.setText(message);
            Button closeButton = new Button("CLose the window");
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    window.close();
                }
            });

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label,closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.show();
        }
    }
}
