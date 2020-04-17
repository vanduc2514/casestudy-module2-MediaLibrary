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

public class CommunicationWindow extends Application {
    private Stage window = new Stage();
    private boolean result;

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
                boolean result = ConfirmBox.display("Đây là ảnh Nude", "Ảnh nude đó đừng click vô");
                if (result) {
                    StackPane stackPane = new StackPane();
                    Scene scene = new Scene(stackPane,200,200);
                    window.setScene(scene);
                    window.show();
                }
            }
        });



        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 200, 200);
        window.setScene(scene);
        window.show();
    }

    public static class ConfirmBox {
        static boolean answer;

        public static boolean display(String title, String message) {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setMinWidth(250);
            Label label = new Label();
            label.setText(message);

            //Create two buttons
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            noButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    answer = false;
                    window.close();
                }
            });

            yesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    answer = true;
                    window.close();
                }
            });

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, noButton, yesButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();

            return answer;
        }
    }
}
