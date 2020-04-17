package sample.demo.datahandle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
* Combo Box là 1 dạng dropDown List nhưng có thể thêm chỉnh sửa lựa chọn và cài được
* Event listener cho từng lựa chọn
*/

public class ComboBoxDemo extends Application {
    private Stage window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo Combo Box");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(30,30,30,30));

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "Blade Runner 2049",
                "Star Wars",
                "ALien"
        );

        //Chỉnh giá trị mặc định cho combo box (không cần phải lấy giá trị đã khai báo, có thể lấy giá trị bất kì)
        comboBox.setPromptText("Phim mày thích là gì ?");

        //Cài eventListener cho từng lựa chọn cho Combo Box
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Người dùng đã lựa chọn: " + comboBox.getValue());
            }
        });

        //Thêm tuỳ chọn chỉnh sửa giá trị cho ComboBox
        comboBox.setEditable(true);

        Button button = new Button("Nhấp vô");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printMovie(comboBox);
            }
        });

        vBox.getChildren().addAll(comboBox,button);
        Scene scene = new Scene(vBox,600,600);
        window.setScene(scene);
        window.show();
    }

    private void printMovie(ComboBox<String> comboBox) {
        System.out.println(comboBox.getValue());
    }
}
