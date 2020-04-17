package sample.demo.stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CloseOnPrompt extends Application {
    private Stage window;
    private Button button;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Đây là chương trình thử nghiệm nhé");

        button = new Button("Đóng ứng dụng");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               window.close();
            }
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button);
        Scene scene = new Scene(stackPane,200,200);
        window.setScene(scene);
        window.show();

        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                boolean result = CommunicationWindow.ConfirmBox.display("Đóng ứng dụng","Có chắc muốn đóng không ?");
                if (result) {
                    window.close();
                }
            }
        });
    }
}
