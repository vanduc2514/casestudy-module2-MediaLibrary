package main.resources.controllers;
/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
    public ObservableList<SongDao> selectedList;
    public ObservableList<SongDao> filteredList;
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
    public TableView<SongDao> filterTable;
    public SongDao selectedSong;

    @FXML
    public VBox vBoxLeft;
    @FXML
    public Button songButton;
    @FXML
    public Button artistButton;
    @FXML
    public Button albumButton;
    @FXML
    public Button genreButton;
    @FXML
    public BorderPane borderPane;
    @FXML
    public Label searchLabel;
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
            filteredList = FXCollections.observableArrayList();
            displayList = FXCollections.observableArrayList(toDisplay);
            filteredList.addAll(displayList);
            displayList.addListener(new ListChangeListener<SongDao>() {
                @Override
                public void onChanged(Change<? extends SongDao> c) {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            updateFilteredList();
                        } else if (c.wasRemoved()) {
                            facadeUtil.removeSong(c.getRemoved().get(0));
                            updateFilteredList();
                        }
                    }
                }
            });
            searchBar.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    updateFilteredList();
                }
            });
            configTableView();
            sorter = new SorterUseComparator(displayList);
        }
    }

    private void updateFilteredList() {
        filteredList.clear();
        for (SongDao songDao : displayList) {
            if (isMatch(songDao)) {
                filteredList.add(songDao);
            }
        }
        songTable.setItems(filteredList);
    }

    private boolean isMatch(SongDao songDao) {
        String input = searchBar.getText().toLowerCase();
        if (input.isEmpty()) {
            return true;
        } else if (songDao.getArtistDao().getTitle().toLowerCase().contains(input)) {
            return true;
        } else return songDao.getTitle().toLowerCase().contains(input);
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
            songTable.refresh();
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
        displayList.setAll(toDisplay);
        if (order.getSelectedToggle() == ascendingOrder) {
            ascendingSort();
        } else if (order.getSelectedToggle() == descendingOrder) {
            descendingSort();
        }
        songTable.setItems(filteredList);
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
    public void openFileLocation(ActionEvent actionEvent) {
        if (borderPane.getCenter() == songTable) {
            selectedSong = songTable.getSelectionModel().getSelectedItem();
        } else {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            TableView<SongDao> selectedContent = (TableView<SongDao>) selectedTab.getContent();
            selectedSong = selectedContent.getSelectionModel().getSelectedItem();
        }
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(facadeUtil.getSongFolder(selectedSong));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            displayList.remove(selectedSong);
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
        displayList.remove(selectedSong);
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
    public void handleButton(ActionEvent actionEvent) {
        LibraryUtil util = (LibraryUtil) facadeUtil;
        List<SongData> dataList;
        if (actionEvent.getSource() == songButton) {
            borderPane.setCenter(songTable);
            searchBar.setDisable(false);
        } else if (actionEvent.getSource() == artistButton) {
            dataList = util.getArtistDaoList();
            tabPane = configTabPane(dataList);
            borderPane.setCenter(tabPane);
            searchBar.setDisable(true);
        } else if (actionEvent.getSource() == albumButton) {
            dataList = util.getAlbumDaoList();
            tabPane = configTabPane(dataList);
            borderPane.setCenter(tabPane);
            searchBar.setDisable(true);
        } else if (actionEvent.getSource() == genreButton) {
            dataList = util.getGenreDaoList();
            tabPane = configTabPane(dataList);
            borderPane.setCenter(tabPane);
            searchBar.setDisable(true);
        }
    }

    private TabPane configTabPane(List<SongData> list) {
        tabPane = new TabPane();
        for (SongData data : list) {
            ObservableList<SongDao> filterList = FXCollections.observableList(data.getSongDaoList());
            tab = new Tab(data.getTitle());
            filterTable = new TableView<>();
            TableColumn<SongDao, String> filterTitleColumn = new TableColumn<>("Tựa đề");
            TableColumn<SongDao, Duration> filterDurationColumn = new TableColumn<>("Thời Lượng");
            TableColumn<SongDao, Integer> filterBitrateColumn = new TableColumn<>("Bitrate");
            filterTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            filterTitleColumn.getStyleClass().add("table-title");
            filterTitleColumn.setMaxWidth(35000);
            filterDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            filterDurationColumn.getStyleClass().add("table-duration");
            filterDurationColumn.setMaxWidth(5000);
            filterBitrateColumn.setCellValueFactory(new PropertyValueFactory<>("bitrate"));
            filterBitrateColumn.setId("filter-bitrate");
            filterBitrateColumn.setMaxWidth(5000);
            filterTable.getColumns().addAll(filterTitleColumn, filterDurationColumn, filterBitrateColumn);
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
            filterDurationColumn.setCellFactory(new Callback<TableColumn<SongDao, Duration>, TableCell<SongDao, Duration>>() {
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
            filterBitrateColumn.setCellFactory(new Callback<TableColumn<SongDao, Integer>, TableCell<SongDao, Integer>>() {
                @Override
                public TableCell<SongDao, Integer> call(TableColumn<SongDao, Integer> param) {
                    return new TableCell<SongDao, Integer>() {
                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(item + " kbps");
                            }
                        }
                    };
                }
            });
            filterTable.setEditable(false);
            filterTable.autosize();
            filterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            filterTable.setItems(filterList);
            filterTable.setContextMenu(songTable.getContextMenu());
            tab.setContent(filterTable);
            tabPane.getTabs().add(tab);
        }
        return tabPane;
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
    public void toggleLeftPane(ActionEvent actionEvent) {
        CheckMenuItem menuItem = (CheckMenuItem) actionEvent.getSource();
        if (menuItem.isSelected()) {
            borderPane.getChildren().remove(vBoxLeft);
        } else {
            borderPane.setLeft(vBoxLeft);
        }
    }

    @FXML
    public void exit() {
        facadeUtil.saveLibrary("library.dat");
        Platform.exit();
    }

    private void configTableView() {
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
        bitRateColumn.setCellFactory(new Callback<TableColumn<SongDao, Integer>, TableCell<SongDao, Integer>>() {
            @Override
            public TableCell<SongDao, Integer> call(TableColumn<SongDao, Integer> param) {
                return new TableCell<SongDao, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item + " kbps");
                        }
                    }
                };
            }
        });
        songTable.setItems(filteredList);
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