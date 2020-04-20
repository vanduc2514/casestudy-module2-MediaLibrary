package main.resources.controllers;
/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
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

    private ObservableList<Song> displayList;
    private SongManger songManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add Database
        try {
            songManager = FileService.getInstance().readDB("library.dat");
            System.out.println(songManager.getSongList());
            displayList = FXCollections.observableArrayList(songManager.getSongList());
        } catch (IOException | NullPointerException ex) {
            displayList = FXCollections.observableArrayList();
            songManager = new SongManger();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        //create Table
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellFactory(new Callback<TableColumn<Song, Duration>, TableCell<Song, Duration>>() {
            @Override
            public TableCell<Song, Duration> call(TableColumn<Song, Duration> param) {
                return new TableCell<Song,Duration>() {
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
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        songTable.setItems(displayList);
    }

    public void addFiles() {
        FileChooser fileChooser = new FileChooser();
        List<File> fileList = fileChooser.showOpenMultipleDialog(new Stage());
        for (File file : fileList) {
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

    public void exit() throws IOException {
        FileService.getInstance().saveDB(songManager, new File("library.dat"));
    }

}
