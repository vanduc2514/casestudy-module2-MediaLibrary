package sample.demo.datahandle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DropDownList extends Application {
    private Stage window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo Choice Box");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(30,30,30,30));

        //getItem trả về một danh sách đối tượng mà có thể thêm các giá trị vào
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Cyka");
        choiceBox.getItems().add("Blyat");
        choiceBox.getItems().add("Nyan");
        choiceBox.getItems().add("Bl4z1t");

        //Đặt giá trị mặc định = giá trị đã add
        choiceBox.setValue("Cyka");

        //Thêm Event Listener cho choiceBox
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((v, oldValue, newValue) -> System.out.println(oldValue));


        Button button = new Button("Nhấp vô");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getChoice(choiceBox);
            }
        });

        vBox.getChildren().addAll(choiceBox,button);
        Scene scene = new Scene(vBox,200,200);
        window.setScene(scene);
        window.show();
    }

    //Lấy giá trị của choice Box
    private void getChoice (ChoiceBox<String> choiceBox) {
        System.out.println(choiceBox.getValue());
    }
}
