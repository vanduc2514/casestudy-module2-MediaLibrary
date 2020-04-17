package sample.demo.menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuDemo extends Application {
    private Stage window;
    private BorderPane layout;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo Menu");

        //Tạo một menu có tên là File
        Menu fileMenu = new Menu("_File"); //Đặt tên có gạch chân sẽ dùng đc shortcut bàn phím khi ấn alt

        //Tạo các mục trong menu File
        //Quy ước: Các mục có 3 tên đi kèm dấu chấm sẽ mở ra cửa sổ mới
        //Quy ước: Các mục không có tên sẽ thực hiện lệnh mà không mở ra cửa sổ mới
        //Quy ước: Các mục có tên và có dấu mũi tên sẽ mở ra 1 menu phụ
        MenuItem newFile = new MenuItem("New...");
        fileMenu.getItems().add(newFile);
        MenuItem openFile = new MenuItem("Open...");
        fileMenu.getItems().add(openFile);
        MenuItem saveFile = new MenuItem("Save...");
        fileMenu.getItems().add(saveFile);
        fileMenu.getItems().add(new SeparatorMenuItem()); //Tạo một đường ngăn cách giữa các menu item
        MenuItem settings = new MenuItem("settings...");
        fileMenu.getItems().add(settings);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().add(exit);

        //Tạo các listener cho từng mục cho menu
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Create new File");
            }
        });

        //Tạo các menu khác để thêm vào menubar
        Menu editMenu = new Menu("E_dit");
        MenuItem cut = new MenuItem("cut");
        editMenu.getItems().add(cut);
        cut.setDisable(true);

        //Tạo menu có biểu tượng đánh dấu khi toggle
        Menu helpMenu = new Menu("Help");
        CheckMenuItem showLines = new CheckMenuItem("Show Line Numbers");
        showLines.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (showLines.isSelected()) {
                    System.out.println("Program will now display a line numbers");
                } else {
                    System.out.println("Hiding Line number");
                }
            }
        });
        helpMenu.getItems().add(showLines);
        CheckMenuItem autoSave = new CheckMenuItem("Enable Autosave");
        autoSave.setSelected(true);
        helpMenu.getItems().add(autoSave);

        //Tạo Menu multichoice (RadioMenuItem)
        Menu radioChoice = new Menu("Single Choice");
        ToggleGroup choice = new ToggleGroup();
        RadioMenuItem easy = new RadioMenuItem("easy");
        RadioMenuItem medium = new RadioMenuItem("medium");
        RadioMenuItem hard = new RadioMenuItem("hard");
        //Đặt các mục không cho chọn cả 3.
        easy.setToggleGroup(choice);
        medium.setToggleGroup(choice);
        hard.setToggleGroup(choice);
        radioChoice.getItems().addAll(easy, medium, hard);


        //Tạo Menu Bar, thêm menu File tạo ở trên vào Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu, radioChoice);

        layout = new BorderPane();
        layout.setTop(menuBar);
        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.show();
    }
}
