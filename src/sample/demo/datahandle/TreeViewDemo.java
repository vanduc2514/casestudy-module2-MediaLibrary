package sample.demo.datahandle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TreeViewDemo extends Application {
    private Stage window;
    private TreeView<String> treeView;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo Tree View");

        TreeItem<String> root, games, music, book, apps, author1, author2, apps1, apps2;

        //Tạo gốc
        root = new TreeItem<>();
        root.setExpanded(true);

        //Tạo nhánh Games bằng hàm tự định nghĩa
        games = makeBranch("Games", root);
        makeBranch("GTA V", games);
        makeBranch("L4D", games);
        makeBranch("Half Life", games);

        //Tạo nhánh Music bằng hàm tự định nghĩa
        music = makeBranch("Music", root);
        makeBranch("Green day", music);
        makeBranch("Ngot", music);

        //Tạo nhánh book ở root
        book = new TreeItem<>("Book");
        root.getChildren().add(book);
        author1= new TreeItem<>("Haruki Murakami");
        author2 = new TreeItem<>("Franz Kafka");
        book.getChildren().add(author1);
        book.getChildren().add(author2);

        //Tạo nhánh apps ở games
        apps = new TreeItem<>("Apps");
        games.getChildren().add(apps);
        apps1 = new TreeItem<>("Word");
        apps.getChildren().add(apps1);
        apps2 = new TreeItem<>("Excel");
        apps.getChildren().add(apps2);

        //Tạo Tree view và truyền tham chiếu root vào
        treeView = new TreeView<>(root);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue.getValue());
            }
        });

        //Layout
        StackPane layout = new StackPane();
        layout.getChildren().add(treeView);
        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();
    }

    //Method tạo nhánh
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}
