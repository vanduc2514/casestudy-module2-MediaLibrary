package main.resources.controllers;
/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.model.Song;
import main.java.model.SongManger;
import main.java.service.FileService;
import main.java.service.SongService;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainStage implements Initializable {
    @FXML
    public TableView<Song> songTable;
    public TableColumn<Song, String> titleColumn;
    public TableColumn<Song, String> artistColumn;
    public TableColumn<Song, String> albumColumn;
    public TableColumn<Song, String> genreColumn;
    public TableColumn<Song, Duration> durationColumn;
    public TableColumn<Song, Integer> trackColumn;
    public TableColumn<Song, String> creatorColumn;
    public TableColumn<Song, Integer> sampleRateColumn;

    private ObservableList<Song> displayList;
    private SongManger songManager;
    private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add Database
        try {
            songManager = FileService.getInstance().readDB("library.dat");
            displayList = FXCollections.observableArrayList(songManager.getSongList());
        } catch (IOException | NullPointerException ex) {
            displayList = FXCollections.observableArrayList();
            songManager = new SongManger();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        //create Table
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

    public void addFiles() {
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Tệp Mp3", "*.mp3");
        fileChooser.getExtensionFilters().add(extension);
        try {
            List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
            fileChooser.setInitialDirectory(files.get(0).getParentFile());
            importFile(files);
        } catch (NullPointerException e) {
            System.out.println("Huỷ chọn");
        }
    }

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
            System.out.println("Huỷ chọn!");
        }
    }

    private void importFile(List<File> files) {
        for (File file : files) {
            try {
                Song song = FileService.getInstance().importSong(file);
                songTable.getItems().add(song);
                SongService.getInstance().addToDB(song, songManager);
            } catch (IOException e) {
                System.out.println("File đã tồn tại");
            } catch (TikaException | SAXException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteLibrary() {
        displayList = FXCollections.observableArrayList();
        songTable.setItems(displayList);
        songManager = new SongManger();
    }

    public void deleteSong() {
        ObservableList<Song> current, selected;
        current = songTable.getItems();
        selected = songTable.getSelectionModel().getSelectedItems();
        current.removeAll(selected);
        SongService.getInstance().deleteFromDB(selected.get(0),songManager);
    }

    public void exit() throws IOException {
        FileService.getInstance().saveDB(songManager, new File("library.dat"));
    }
}
