package main.resources.controllers;
/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import main.java.model.*;
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
import java.util.*;
import java.util.List;

public class MainStage implements Initializable {

    private ObservableList<SongDao> displayList;
    private List<SongDao> toDisplay;
    private FacadeUtil facadeUtil;
    private Sorter sorter;
    private FileChooser fileChooser = new FileChooser();
    public Stage stage;
    public Scene scene;
    public Parent root;
    public FXMLLoader fxmlLoader;
    public TabPane tabPane;
    public Tab tab;
    public ListView<SongDao> songList;
    public TableView<SongDao> filterTable;
    public SongDao selectedSong;
    public ObservableList<SongDao> selectedList;

    @FXML
    public BorderPane borderPane;
    @FXML
    public TextField searchBar;
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
    public ContextMenu contextMenu;
    @FXML
    public TableView<SongDao> songTable;
    @FXML
    public TableColumn<SongDao, String> titleColumn;
    @FXML
    public TableColumn<SongDao, ArtistDao> artistColumn;
    @FXML
    public TableColumn<SongDao, AlbumDao> albumColumn;
    @FXML
    public TableColumn<SongDao, GenreDao> genreColumn;
    @FXML
    public TableColumn<SongDao, Duration> durationColumn;
    @FXML
    public TableColumn<SongDao, Integer> trackColumn;
    @FXML
    public TableColumn<SongDao, String> creatorColumn;
    @FXML
    public TableColumn<SongDao, Integer> bitRateColumn;

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
        if (toDisplay.size() > 0) {
            if (!deleteLibrary()) {
                return;
            }
        }
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("../view/MessageStage.fxml"));
        try {
            root = fxmlLoader.load();
            MessageStage messageStage = fxmlLoader.getController();
            messageStage.message.setText("Nhập tên của thư viện");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            if (messageStage.isOkButtonPressed) {
                libraryName.setText(libraryName.getText().replace("Admin", messageStage.userInput.getText()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public boolean deleteLibrary() {
        AlertStage alertStage = getAlertStage("Thư viện cũ sẽ bị xoá vĩnh viễn", "Bạn có chắc chắn ?");
        assert alertStage != null;
        if (alertStage.confirm) {
            toDisplay = facadeUtil.createNewList();
            songTable.getItems().clear();
            clearSummary();
            return true;
        }
        return false;
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
        displayList.addAll(toDisplay);
        if (order.getSelectedToggle() == ascendingOrder) {
            ascendingSort();
        } else if (order.getSelectedToggle() == descendingOrder) {
            descendingSort();
        }
        songTable.setItems(displayList);
    }

    @FXML
    public void handleContextMenuTableView(ContextMenuEvent contextMenuEvent) {
        if (contextMenuEvent.getSource() == songTable) {
            selectedList = songTable.getSelectionModel().getSelectedItems();
            if (selectedList.isEmpty()) {
                songTable.getContextMenu().hide();
            }
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedList = selectedContent.getSelectionModel().getSelectedItems();
            if (selectedList.isEmpty()) {
                selectedContent.getContextMenu().hide();
            }
        }
    }

    @FXML
    public void openFileLocation(ActionEvent actionEvent) throws IOException {
        if (borderPane.getCenter() == songTable) {
            selectedSong = songTable.getSelectionModel().getSelectedItem();
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedSong = selectedContent.getSelectionModel().getSelectedItem();
        }
        Desktop desktop = Desktop.getDesktop();
        desktop.open(facadeUtil.getSongFolder(selectedSong));
    }

    @FXML
    public void deleteSong() {
        if (borderPane.getCenter() == songTable) {
            selectedSong = songTable.getSelectionModel().getSelectedItem();
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedSong = selectedContent.getSelectionModel().getSelectedItem();
        }
        AlertStage alertStage = getAlertStage("Bài hát sẽ bị xoá vĩnh viễn", "Bạn có chắc chắn ?");
        assert alertStage != null;
        if (alertStage.confirm) {
            facadeUtil.deleteSong(selectedSong);
        }
        clearSummary();
    }

    @FXML
    public void removeSong() {
        if (borderPane.getCenter() == songTable) {
            selectedSong = songTable.getSelectionModel().getSelectedItem();
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedSong = selectedContent.getSelectionModel().getSelectedItem();
        }
        facadeUtil.removeSong(selectedSong);
        songTable.refresh();
        clearSummary();
    }

    @FXML
    public void showSongDetail(ActionEvent event) {
        if (borderPane.getCenter() == songTable) {
            selectedSong = songTable.getSelectionModel().getSelectedItem();
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedSong = selectedContent.getSelectionModel().getSelectedItem();
        }
        fxmlLoader = new FXMLLoader(getClass().getResource("../view/DetailStage.fxml"));
        stage = new Stage();
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            DetailStage detailStage = fxmlLoader.getController();
            setSongDetail(selectedSong, detailStage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            if (detailStage.isFieldEdited) {
                facadeUtil.editInfo(selectedSong, detailStage.propertyMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSongDetail(SongDao selectedSong, DetailStage detailStage) {
        detailStage.track.setText(String.valueOf(selectedSong.getTrackNumber()));
        detailStage.title.setText(selectedSong.getTitle());
        detailStage.artist.setText(selectedSong.getArtistDao().getTitle());
        detailStage.album.setText(selectedSong.getAlbumDao().getTitle());
        detailStage.composer.setText(selectedSong.getComposer());
        detailStage.genre.setText(selectedSong.getGenreDao().getTitle());
        detailStage.year.setText(String.valueOf(selectedSong.getYear()));
        detailStage.duration.setText(selectedSong.getDuration().toMinutes() + ":" + selectedSong.getDuration().minusMinutes(selectedSong.getDuration().toMinutes()).getSeconds());
        detailStage.bitrate.setText(selectedSong.getBitrate() + " kbps");
        detailStage.sampleRate.setText(selectedSong.getSampleRate() + " Khz");
        detailStage.filePath.setText(selectedSong.getPath());
        Image albumArt = facadeUtil.getAlbumArt(selectedSong);
        Rectangle2D rectangle2D = new Rectangle2D(0, 0, albumArt.getWidth(), albumArt.getHeight());
        detailStage.albumArt.setViewport(rectangle2D);
        detailStage.albumArt.setImage(albumArt);
    }

    @FXML
    public void showSongSummary(MouseEvent mouseEvent) {
        TableRow<SongDao> tableRow = (TableRow<SongDao>) mouseEvent.getSource();
        if (tableRow.getTableView() == songTable) {
            selectedSong = songTable.getSelectionModel().getSelectedItem();
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedSong = selectedContent.getSelectionModel().getSelectedItem();
        }
        Image image;
        image = facadeUtil.getAlbumArt(selectedSong);
        Rectangle2D rectangle2D = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
        albumArt.setViewport(rectangle2D);
        titleLabel.setVisible(true);
        titleLabel.setText(selectedSong.getTitle());
        artistLabel.setVisible(true);
        artistLabel.setText(selectedSong.getArtistDao().getTitle());
        albumLabel.setVisible(true);
        albumLabel.setText(selectedSong.getAlbumDao().getTitle());
        genreLabel.setVisible(true);
        genreLabel.setText(selectedSong.getGenreDao().getTitle());
        albumArt.setVisible(true);
        albumArt.setImage(image);
    }

    @FXML
    public void showSongTable(ActionEvent actionEvent) {
        borderPane.setCenter(songTable);
    }

    @FXML
    public void showArtistPane(ActionEvent actionEvent) {
        LibraryUtil util = (LibraryUtil) facadeUtil;
        List<SongData> listArtist = util.getArtistDaoList();
        tabPane = createSongTablePane(listArtist);
        borderPane.setCenter(tabPane);
    }

    @FXML
    public void showAlbumPane(ActionEvent actionEvent) {
        LibraryUtil util = (LibraryUtil) facadeUtil;
        List<SongData> listAlbum = util.getAlbumDaoList();
        tabPane = createSongTablePane(listAlbum);
        borderPane.setCenter(tabPane);
    }

    @FXML
    public void showGenrePane(ActionEvent actionEvent) {
        LibraryUtil util = (LibraryUtil) facadeUtil;
        List<SongData> listGenre = util.getGenreDaoList();
        tabPane = createSongTablePane(listGenre);
        borderPane.setCenter(tabPane);
    }

    private TabPane createSongTablePane(List<SongData> list) {
        tabPane = new TabPane();
        for (SongData data : list) {
            ObservableList<SongDao> filterList = FXCollections.observableList(data.getSongDaoList());
            tab = new Tab(data.getTitle());
            filterTable = new TableView<>();
            TableColumn<SongDao, String> tableColumn = new TableColumn<>("Tựa đề");
            tableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            filterTable.getColumns().add(tableColumn);
            filterTable.setItems(filterList);
            filterTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    handleContextMenuTableView(event);
                }
            });
            filterTable.setRowFactory(new Callback<TableView<SongDao>, TableRow<SongDao>>() {
                @Override
                public TableRow<SongDao> call(TableView<SongDao> param) {
                    TableRow<SongDao> row = new TableRow<>();
                    row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() == 1 && !row.isEmpty()) {
                                showSongSummary(event);
                            }
                        }
                    });
                    return row;
                }
            });
            filterTable.setContextMenu(songTable.getContextMenu());
            tab.setContent(filterTable);
            tabPane.getTabs().add(tab);
        }
        return tabPane;
    }

    @FXML
    public void searchSong(KeyEvent keyEvent) {
    }

    @FXML
    public void sortItem() {
        if (order.getSelectedToggle() == ascendingOrder) {
            ascendingSort();
        }
        if (order.getSelectedToggle() == descendingOrder) {
            descendingSort();
        }
    }

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

    private void configTable() {
        trackColumn.setCellValueFactory(new PropertyValueFactory<>("trackNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistDao"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumDao"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("composer"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genreDao"));
        durationColumn.setCellFactory(new Callback<TableColumn<SongDao, Duration>, TableCell<SongDao, Duration>>() {
            @Override
            public TableCell<SongDao, Duration> call(TableColumn<SongDao, Duration> param) {
                return new TableCell<SongDao, Duration>() {
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
        songTable.setRowFactory(new Callback<TableView<SongDao>, TableRow<SongDao>>() {
            @Override
            public TableRow<SongDao> call(TableView<SongDao> param) {
                TableRow<SongDao> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 1 && !row.isEmpty()) {
                            showSongSummary(event);
                        }
                    }
                });
                return row;
            }
        });
        songTable.setItems(displayList);
    }

    private AlertStage getAlertStage(String message, String detail) {
        fxmlLoader = new FXMLLoader(getClass().getResource("../view/AlertStage.fxml"));
        stage = new Stage();
        try {
            root = fxmlLoader.load();
            AlertStage alertStage = fxmlLoader.getController();
            alertStage.message.setText(message);
            alertStage.detail.setText(detail);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            return alertStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearSummary() {
        titleLabel.setVisible(false);
        artistLabel.setVisible(false);
        albumLabel.setVisible(false);
        genreLabel.setVisible(false);
        albumArt.setVisible(false);
    }
}