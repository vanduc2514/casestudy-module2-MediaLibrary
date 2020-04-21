package main.resources.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.util.HashMap;

public class DetailStage {
    public HashMap<String, String> propertyMap = new HashMap<>();
    public boolean isFieldEdited = false;

    @FXML
    public TextField track;
    @FXML
    public TextField title;
    @FXML
    public TextField artist;
    @FXML
    public TextField album;
    @FXML
    public TextField composer;
    @FXML
    public TextField genre;
    @FXML
    public TextField year;
    @FXML
    public Label duration;
    @FXML
    public Label bitrate;
    @FXML
    public Label sampleRate;
    @FXML
    public Button okButton;

    @FXML
    public void closeStage(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateSong(ActionEvent actionEvent) {
        if (isFieldEdited) {
            propertyMap.put("track", track.getText());
            propertyMap.put("title", title.getText());
            propertyMap.put("artist", artist.getText());
            propertyMap.put("album", album.getText());
            propertyMap.put("genre", genre.getText());
            propertyMap.put("composer", composer.getText());
            propertyMap.put("year", year.getText());
        }
        closeStage(actionEvent);
    }

    public void setFieldStatus(KeyEvent keyEvent) {
        isFieldEdited = true;
    }
}
