package sample.demo.datahandle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckBoxDemo extends Application {
    private Stage window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo ");
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene;

        //Check box
        CheckBox checkBox = new CheckBox("Toang đến nơi rồi!");
        CheckBox checkBox1 = new CheckBox("Sắp phải học lại rồi!");
        checkBox.setSelected(true);

        Button button = new Button("Nhấp vô");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(handleOption(checkBox, checkBox1));
            }
        });

        vBox.getChildren().addAll(checkBox, checkBox1, button);
        scene = new Scene(vBox, 200, 200);
        window.setScene(scene);
        window.show();
    }

    //Handle checkbox option
    private String handleOption(CheckBox box1, CheckBox box2) {
        String isBox1Check = box1.isSelected() ? "checked" : "not checked";
        String isBox2Check = box2.isSelected() ? "checked" : "not checked";
        return "Toang rồi: " + isBox1Check + "\n" + "Sắp học lại: " + isBox2Check;
    }
}
