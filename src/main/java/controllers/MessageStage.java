package controllers;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MessageStage {
    @FXML
    public Label message;
    @FXML
    public TextField userInput;

    public boolean isOkButtonPressed;

    @FXML
    public void closeStage(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleOkButton(ActionEvent actionEvent) {
        isOkButtonPressed = true;
        closeStage(actionEvent);
    }
}
