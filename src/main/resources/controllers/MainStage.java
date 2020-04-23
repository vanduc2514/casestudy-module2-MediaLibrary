package main.resources.controllers;
/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.model.Song;
import main.java.service.facade.FacadeManager;
import main.java.service.facade.MyFacadeManager;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainStage implements Initializable {
    private ObservableList<Song> displayList;
    private FileChooser fileChooser = new FileChooser();
    FacadeManager facadeManager;
    List<Song> toDisplay;

    @FXML
    public ContextMenu contextTable;
    @FXML
    public TableView<Song> songTable;
    @FXML
    public TableColumn<Song, String> titleColumn;
    @FXML
    public TableColumn<Song, String> artistColumn;
    @FXML
    public TableColumn<Song, String> albumColumn;
    @FXML
    public TableColumn<Song, String> genreColumn;
    @FXML
    public TableColumn<Song, Duration> durationColumn;
    @FXML
    public TableColumn<Song, Integer> trackColumn;
    @FXML
    public TableColumn<Song, String> creatorColumn;
    @FXML
    public TableColumn<Song, Integer> sampleRateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facadeManager = MyFacadeManager.getInstance();
        try {
            toDisplay = facadeManager.loadDisplayList("library.dat");
        } catch (NullPointerException ex) {
            toDisplay = facadeManager.createNewList();
        }
        displayList = FXCollections.observableList(toDisplay);
        configTable();
    }

    @FXML
    public void addFiles() {
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Tệp Mp3", "*.mp3");
        fileChooser.getExtensionFilters().add(extension);
        try {
            List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
            fileChooser.setInitialDirectory(files.get(0).getParentFile());
            facadeManager.importFiles(files);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        songTable.refresh();
    }

    @FXML
    public void addFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(new Stage());
        try {
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getAbsolutePath().endsWith(".mp3");
                }
            });
            if (files != null) {
                facadeManager.importFiles(Arrays.asList(files));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        songTable.refresh();
    }

    @FXML
    public void deleteLibrary() {
        toDisplay = facadeManager.createNewList();
        displayList = FXCollections.observableList(toDisplay);
        songTable.setItems(displayList);
    }

    @FXML
    public void removeSong() {
        ObservableList<Song> selected;
        selected = songTable.getSelectionModel().getSelectedItems();
        facadeManager.removeSong(selected.get(0));
        songTable.refresh();
    }

    @FXML
    public void openDetail() {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        Song selectedSong = selected.get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/DetailStage.fxml"));
        Stage stage = new Stage();
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            DetailStage detailStage = loader.getController();
            setSongDetail(selectedSong, detailStage);
            stage.setScene(scene);
            stage.showAndWait();
            if (detailStage.isFieldEdited) {
                facadeManager.editInfo(selectedSong, detailStage.propertyMap);
            }
            songTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openLocation(ActionEvent actionEvent) throws IOException {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        Desktop desktop = Desktop.getDesktop();
        desktop.open(facadeManager.getSongFolder(selected.get(0)));
    }

    @FXML
    public void handleContextMenuTableView(ContextMenuEvent contextMenuEvent) {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        if (selected.isEmpty()) {
            songTable.getContextMenu().hide();
        }
    }

    @FXML
    public void exit() throws IOException {
        facadeManager.saveLibrary("library.dat");
        Platform.exit();
    }

    private void setSongDetail(Song selectedSong, DetailStage detailStage) {
        detailStage.track.setText(String.valueOf(selectedSong.getTrackNumber()));
        detailStage.title.setText(selectedSong.getTitle());
        detailStage.artist.setText(selectedSong.getArtist());
        detailStage.album.setText(selectedSong.getAlbum());
        detailStage.composer.setText(selectedSong.getComposer());
        detailStage.genre.setText(selectedSong.getGenre());
        detailStage.year.setText(String.valueOf(selectedSong.getYear()));
        detailStage.duration.setText(selectedSong.getDuration().toMinutes() + ":" + selectedSong.getDuration().minusMinutes(selectedSong.getDuration().toMinutes()).getSeconds());
        detailStage.bitrate.setText(selectedSong.getBitrate() + " kbps");
        detailStage.sampleRate.setText(selectedSong.getSampleRate() + " Khz");
    }

    private void configTable() {
        trackColumn.setCellValueFactory(new PropertyValueFactory<>("trackNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("composer"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellFactory(new Callback<TableColumn<Song, Duration>, TableCell<Song, Duration>>() {
            @Override
            public TableCell<Song, Duration> call(TableColumn<Song, Duration> param) {
                return new TableCell<Song, Duration>() {
                    @Override
                    protected void updateItem(Duration item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toMinutes() + ":" + item.minusMinutes(item.toMinutes()).getSeconds());
                        }
                    }
                };
            }
        });
        sampleRateColumn.setCellValueFactory(new PropertyValueFactory<>("sampleRate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        songTable.setItems(displayList);
    }
}
