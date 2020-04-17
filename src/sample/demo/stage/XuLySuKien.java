package sample.demo.stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class XuLySuKien implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Button chạy rồi!");
    }
}
