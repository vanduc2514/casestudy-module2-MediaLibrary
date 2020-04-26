package controllers;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertStage {

    @FXML
    public Label message;
    @FXML
    public Label detail;
    @FXML
    public Button okButton;
    @FXML
    public Button cancelButton;

    public boolean confirm;

    @FXML
    public void closeStage(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleButton(ActionEvent actionEvent) {
        confirm = true;
        closeStage(actionEvent);
    }
}
