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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.model.Song;
import main.java.service.facade.FacadeUtil;
import main.java.service.facade.LibraryUtil;
import main.resources.controllers.sorter.Sorter;
import main.resources.controllers.sorter.SorterUseComparator;

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
    private List<Song> toDisplay;
    private FacadeUtil facadeUtil;
    private Sorter sorter;
    private FileChooser fileChooser = new FileChooser();
    public Stage stage;
    public Scene scene;
    public Parent root;
    public FXMLLoader fxmlLoader;

    @FXML
    public ToggleGroup sortBy;
    @FXML
    public RadioMenuItem trackSort;
    @FXML
    public RadioMenuItem titleSort;
    @FXML
    public RadioMenuItem artistSort;
    @FXML
    public RadioMenuItem albumSort;
    @FXML
    public RadioMenuItem genreSort;
    @FXML
    public RadioMenuItem composerSort;
    @FXML
    public RadioMenuItem yearSort;
    @FXML
    public RadioMenuItem durationSort;
    @FXML
    public ToggleGroup order;
    @FXML
    public RadioMenuItem ascendingOrder;
    @FXML
    public RadioMenuItem descendingOrder;
    @FXML
    public Label titleLabel;
    @FXML
    public Label artistLabel;
    @FXML
    public Label albumLabel;
    @FXML
    public Label genreLabel;
    @FXML
    public ImageView albumArt;
    @FXML
    public Label libraryName;
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
    public TableColumn<Song, Integer> bitRateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facadeUtil = LibraryUtil.getInstance();
        try {
            toDisplay = facadeUtil.loadDisplayList("library.dat");
        } catch (NullPointerException ex) {
            toDisplay = facadeUtil.createNewList();
        } finally {
            displayList = FXCollections.observableList(toDisplay);
            configTable();
            sorter = new SorterUseComparator(displayList);
        }
    }

    @FXML
    public void createNewLibrary(ActionEvent actionEvent) {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("../view/MessageStage.fxml"));
        try {
            root = fxmlLoader.load();
            MessageStage messageStage = fxmlLoader.getController();
            messageStage.message.setText("Nhập tên của thư viện");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            if (messageStage.isOkButtonPressed) {
                libraryName.setText(libraryName.getText().replace("Admin", messageStage.userInput.getText()));
            }
            deleteLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteLibrary() {
        toDisplay = facadeUtil.createNewList();
        displayList = FXCollections.observableList(toDisplay);
        songTable.setItems(displayList);
    }

    @FXML
    public void addFiles() {
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Tệp Mp3", "*.mp3");
        fileChooser.getExtensionFilters().add(extension);
        try {
            List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
            fileChooser.setInitialDirectory(files.get(0).getParentFile());
            facadeUtil.importFiles(files);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        displayList = FXCollections.observableList(toDisplay);
        if (order.getSelectedToggle() == ascendingOrder) {
            ascendingSort();
        } else if (order.getSelectedToggle() == descendingOrder) {
            descendingSort();
        }
        songTable.setItems(displayList);

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
                facadeUtil.importFiles(Arrays.asList(files));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        displayList = FXCollections.observableList(toDisplay);
        if (order.getSelectedToggle() == ascendingOrder) {
            ascendingSort();
        } else if (order.getSelectedToggle() == descendingOrder) {
            descendingSort();
        }
        songTable.setItems(displayList);
    }

    @FXML
    public void openFileLocation(ActionEvent actionEvent) throws IOException {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        Desktop desktop = Desktop.getDesktop();
        desktop.open(facadeUtil.getSongFolder(selected.get(0)));
    }

    @FXML
    public void removeSong() {
        ObservableList<Song> selected;
        selected = songTable.getSelectionModel().getSelectedItems();
        facadeUtil.removeSong(selected.get(0));
        songTable.refresh();
    }

    @FXML
    public void showSongDetail() {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        Song selectedSong = selected.get(0);
        fxmlLoader = new FXMLLoader(getClass().getResource("../view/DetailStage.fxml"));
        stage = new Stage();
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            DetailStage detailStage = fxmlLoader.getController();
            setSongDetail(selectedSong, detailStage);
            stage.setScene(scene);
            stage.showAndWait();
            if (detailStage.isFieldEdited) {
                facadeUtil.editInfo(selectedSong, detailStage.propertyMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showSongSummary(MouseEvent mouseEvent) {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        Song song = selected.get(0);
        Image image;
        image = facadeUtil.getAlbumArt(song);
        Rectangle2D rectangle2D = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
        albumArt.setViewport(rectangle2D);
        titleLabel.setVisible(true);
        titleLabel.setText(song.getTitle());
        artistLabel.setVisible(true);
        artistLabel.setText(song.getArtist());
        albumLabel.setVisible(true);
        albumLabel.setText(song.getAlbum());
        genreLabel.setVisible(true);
        genreLabel.setText(song.getGenre());
        albumArt.setImage(image);
    }

    @FXML
    public void handleContextMenuTableView(ContextMenuEvent contextMenuEvent) {
        ObservableList<Song> selected = songTable.getSelectionModel().getSelectedItems();
        if (selected.isEmpty()) {
            songTable.getContextMenu().hide();
        }
    }

    @FXML
    public void sortByTrack() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByTrackNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByTrackReverse();
    }

    @FXML
    public void sortByTitle() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByTitleNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByTitleNatural();
    }

    @FXML
    public void sortByArtist() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByArtistNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByArtistReverse();
    }

    @FXML
    public void sortByAlbum() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByAlbumNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByAlbumReverse();
    }

    @FXML
    public void sortByGenre() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByGenreNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByGenreReverse();
    }

    @FXML
    public void sortByYear() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByYearNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByYearReverse();
    }

    @FXML
    public void sortByComposer() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByComposerNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByComposerReverse();

    }

    @FXML
    public void sortByDuration() {
        if (order.getSelectedToggle() == ascendingOrder) {
            sorter.sortByDurationNatural();
        } else if (order.getSelectedToggle() == descendingOrder)
            sorter.sortByDurationReverse();
    }


    @FXML
    public void ascendingSort() {
        if (sortBy.getSelectedToggle() == trackSort) {
            sorter.sortByTrackNatural();
        } else if (sortBy.getSelectedToggle() == titleSort) {
            sorter.sortByTitleNatural();
        } else if (sortBy.getSelectedToggle() == artistSort) {
            sorter.sortByArtistNatural();
        } else if (sortBy.getSelectedToggle() == albumSort) {
            sorter.sortByAlbumNatural();
        } else if (sortBy.getSelectedToggle() == genreSort) {
            sorter.sortByGenreNatural();
        } else if (sortBy.getSelectedToggle() == composerSort) {
            sorter.sortByComposerNatural();
        } else if (sortBy.getSelectedToggle() == yearSort) {
            sorter.sortByYearNatural();
        } else sorter.sortByDurationNatural();
    }

    @FXML
    public void descendingSort() {
        if (sortBy.getSelectedToggle() == trackSort) {
            sorter.sortByTrackReverse();
        } else if (sortBy.getSelectedToggle() == titleSort) {
            sorter.sortByTitleReverse();
        } else if (sortBy.getSelectedToggle() == artistSort) {
            sorter.sortByArtistReverse();
        } else if (sortBy.getSelectedToggle() == albumSort) {
            sorter.sortByAlbumReverse();
        } else if (sortBy.getSelectedToggle() == genreSort) {
            sorter.sortByGenreReverse();
        } else if (sortBy.getSelectedToggle() == composerSort) {
            sorter.sortByComposerReverse();
        } else if (sortBy.getSelectedToggle() == yearSort) {
            sorter.sortByYearReverse();
        } else sorter.sortByDurationReverse();

    }

    @FXML
    public void exit() {
        facadeUtil.saveLibrary("library.dat");
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
        bitRateColumn.setCellValueFactory(new PropertyValueFactory<>("bitrate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        songTable.setItems(displayList);
    }
}