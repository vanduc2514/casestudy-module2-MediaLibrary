package sample.demo.datahandle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
* List View có dạng là 1 list với nhiều lựa chọn
*/

public class ListViewDemo extends Application {
    private Stage window;
    private ListView<String> listView;
    private Button button;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo List View");
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20,20,20,20));

        listView = new ListView<>();
        listView.getItems().addAll(
                "Terminator",
                "500 days of summer",
                "Her"
        );

        //Cài đặt list view cho phép chọn nhiều lựa chọn với phím ctrl / shift
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        button = new Button("Nhấp vô");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonClick();
            }
        });

        vbox.getChildren().addAll(listView,button);
        Scene scene = new Scene(vbox,400,400);
        window.setScene(scene);
        window.show();
    }

    private void buttonClick() {
        //Tất cả list trong javaFX là Observable List
        ObservableList<String> movies;
        movies = listView.getSelectionModel().getSelectedItems();

        for (String movie : movies) {
            System.out.println("Selected " + movie);
        }
    }
}
