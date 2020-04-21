package main.resources.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.model.Song;

public class DetailStage {
    public TextField track;
    public TextField title;
    public TextField artist;
    public TextField album;
    public TextField creator;
    public TextField genre;
    public TextField year;
    public Label duration;
    public Label bitrate;
    public Label sampleRate;
    private Song song;

    public void setSong(Song song) {
        this.song = song;
    }

    public void closeStage(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void updateSong(ActionEvent actionEvent) {
        song.setTrackNumber(Integer.parseInt(track.getText()));
        song.setTitle(title.getText());
        song.setAlbum(album.getText());
        song.setArtist(artist.getText());
        song.setGenre(genre.getText());
        closeStage(actionEvent);
    }
}
