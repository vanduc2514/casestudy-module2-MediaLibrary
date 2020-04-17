package sample.demo.datahandle.table;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Tạo bảng bằng Java
 * Chuyển các Object thành Object Observable List để hiển thị
 * Bảng này có chức năng tự động sắp xếp các dữ liệu và hỗ trợ sắp xếp các cột
 */

public class TableViewDemo extends Application {
    private Stage window;
    private TableView<Product> myTable;
    private TextField nameInput, priceInput, quantityInput;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Demo Table View");

        //Tạo cột trong bảng (tham số của PropertyValueFactory phải trùng tên với thuộc tính của Product)
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        //Sử dụng thuộc tính name cho cột
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Tạo cột trong bảng (tham số của PropertyValueFactory phải trùng tên với thuộc tính của Product)
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Item Price");
        priceColumn.setMinWidth(200);
        //Sử dụng thuộc tính price cho cột
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Tạo cột trong bảng (tham số của PropertyValueFactory phải trùng tên với thuộc tính của Product)
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(200);
        //Sử dụng thuộc tính quantity cho cột
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //Khởi tạo đối tượng TableView, truyền cho nó một ObservableList (Ở đây dùng hàm tự định nghĩa để sinh ObservableList)
        myTable = new TableView<>();
        myTable.setItems(getProduct());
        myTable.getColumns().addAll(nameColumn,priceColumn,quantityColumn);

        //Tạo các trưởng để nhập dữ liệu cho bảng
        nameInput = new TextField();
        nameInput.setPromptText("Enter Name");
        nameInput.setMinWidth(100);
        priceInput = new TextField();
        priceInput.setPromptText("Enter price");
        quantityInput = new TextField();
        quantityInput.setPromptText("Enter quantity");

        //Tạo các nút thao tác
        Button buttonAdd = new Button("Add");
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addProduct();
            }
        });
        Button buttonDelete = new Button("Delete");
        buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteProduct();
            }
        });

        //Tạo 1 Hbox để chứa nút và các trường text vì Vbox sẽ xếp các nút và trường bên dưới bảng
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput,priceInput,quantityInput,buttonAdd,buttonDelete);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(myTable,hBox);
        Scene scene = new Scene(vBox, 400, 400);
        window.setScene(scene);
        window.show();
    }

    //Hàm khởi tạo danh sách đối tượng hiển thị
    public ObservableList<Product> getProduct() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("Iphone 7", 500.35, 3));
        products.add(new Product("MacBook", 1000, 2));
        products.add(new Product("Plasma TV", 700, 4));
        products.add(new Product("Kettle", 100, 10));
        products.add(new Product("Fan", 50, 100));
        products.add(new Product("Remote TV", 100, 300));
        return products;
    }

    public void addProduct() {
        //Tạo các product để thêm
        Product product = new Product();
        product.setName(nameInput.getText());
        product.setPrice(Double.parseDouble(priceInput.getText()));
        product.setQuantity(Integer.parseInt(quantityInput.getText()));
        //Trả vể 1 ObservableList và thêm product vào ObservableList
        myTable.getItems().add(product);
        //Xoá các trường sau khi nhập
        nameInput.clear();
        priceInput.clear();
        quantityInput.clear();
    }

    public void deleteProduct() {
        ObservableList<Product> productsSelected, productsInTable;
        productsInTable = myTable.getItems();
        productsSelected = myTable.getSelectionModel().getSelectedItems();
        productsInTable.removeAll(productsSelected);
    }
}
