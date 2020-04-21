package main.resources.controllers;
/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import com.sun.istack.internal.NotNull;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import libs.mp3agic.InvalidDataException;
import libs.mp3agic.UnsupportedTagException;
import main.java.model.Song;
import main.java.service.FileService;
import main.java.service.Mp3Handler;
import main.java.service.LinkedListManager;
import main.java.service.SongManager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainStage implements Initializable {
    private ObservableList<Song> displayList;
    private FileChooser fileChooser = new FileChooser();
    private FileService mp3Handler;
    private SongManager songManager;

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
        mp3Handler = new Mp3Handler();
        songManager = LinkedListManager.getInstance();
        try {
            mp3Handler.readList(new File("library.dat"), songManager);
            displayList = FXCollections.observableArrayList(songManager.getSongList());
        } catch (IOException | NullPointerException ex) {
            displayList = FXCollections.observableArrayList();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        configTable();
    }

    @FXML
    public void addFiles() {
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Tệp Mp3", "*.mp3");
        fileChooser.getExtensionFilters().add(extension);
        try {
            List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
            fileChooser.setInitialDirectory(files.get(0).getParentFile());
            importFile(files);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
                importFile(Arrays.asList(files));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteLibrary() {
        displayList = FXCollections.observableArrayList();
        songTable.setItems(displayList);
        songManager.setSongList(new LinkedList<>());
    }

    @FXML
    public void deleteSong() {
        ObservableList<Song> current, selected;
        current = songTable.getItems();
        selected = songTable.getSelectionModel().getSelectedItems();
        current.removeAll(selected);
        songManager.removeFromList(selected.get(0));
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
            songTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() throws IOException {
        mp3Handler.saveList(new File("library.dat"), songManager);
        Platform.exit();
    }

    private void setSongDetail(Song selectedSong, DetailStage detailStage) {
        detailStage.setSong(selectedSong);
        detailStage.track.setText(String.valueOf(selectedSong.getTrackNumber()));
        detailStage.title.setText(selectedSong.getTitle());
        detailStage.artist.setText(selectedSong.getArtist());
        detailStage.album.setText(selectedSong.getAlbum());
        detailStage.creator.setText(selectedSong.getCreator());
        detailStage.genre.setText(selectedSong.getGenre());
        detailStage.year.setText(String.valueOf(selectedSong.getYear()));
        detailStage.duration.setText(selectedSong.getDuration().toMinutes() + ":" + selectedSong.getDuration().minusMinutes(selectedSong.getDuration().toMinutes()).getSeconds());
        detailStage.bitrate.setText(selectedSong.getBitrate() + "kbps");
        detailStage.sampleRate.setText(selectedSong.getSampleRate() + "Khz");
    }

    private void importFile(@NotNull List<File> files) {
        for (File file : files) {
            try {
                mp3Handler.importSong(file, songManager);
            } catch (IOException e) {
                System.out.println("File đã tồn tại");
            } catch (InvalidDataException | UnsupportedTagException e) {
                e.printStackTrace();
            }
        }
        displayList = FXCollections.observableArrayList(songManager.getSongList());
        songTable.setItems(displayList);
    }

    private void configTable() {
        trackColumn.setCellValueFactory(new PropertyValueFactory<>("trackNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
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
