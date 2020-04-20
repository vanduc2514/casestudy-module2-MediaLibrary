package sample.demo.fxml;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button button1;

    public void handleClick() {
        System.out.println("Hello");
        button1.setText("Uh");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading....");
    }
}
