package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import BUS.Item_BUS;
import DTO.Item;

import java.sql.SQLException;

public class Item_Controller {
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn;
    @FXML
    private TableColumn<Item, String> classificationColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;

    private Item_BUS itemBUS;
    private ObservableList<Item> itemList;

    @FXML
    public void initialize() throws SQLException {
        itemBUS = new Item_BUS();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));

        loadItemData();

        addButton.setOnAction(e -> handleAdd());
        deleteButton.setOnAction(e -> handleDelete());
        editButton.setOnAction(e -> handleEdit());
        searchButton.setOnAction(e -> handleSearch());
        refreshButton.setOnAction(e -> handleRefresh());
    }

    private void loadItemData() throws SQLException {
        itemList = FXCollections.observableArrayList(itemBUS.getAllItem());
        tableView.setItems(itemList);
    }

    @FXML
    private void handleAdd() {
        Dialog<Item> dialog = createItemDialog(null, false);
        dialog.showAndWait().ifPresent(newItem -> {
            try {
                if (itemBUS.addItem(newItem)) {
                    itemList.add(newItem);
                    ShowDialog.  showAlert("Thêm sản phẩm thành công!");
                } else {
                	ShowDialog.  showAlert("Sản phẩm đã tồn tại hoặc thêm thất bại!");
                }
            } catch (SQLException e) {
            	ShowDialog. showAlert("Lỗi khi thêm sản phẩm: " + e.getMessage());
            }
        });
    }
    
    @FXML
    private void handleEdit() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
        	ShowDialog. showAlert("Vui lòng chọn sản phẩm để sửa.");
            return;
        }

        Dialog<Item> dialog = createItemDialog(selected, true);
        dialog.showAndWait().ifPresent(updatedItem -> {
            try {
                if (itemBUS.updateItem(updatedItem)) {
                    loadItemData();
                    ShowDialog. showAlert("Cập nhật sản phẩm thành công!");
                } else {
                	ShowDialog.showAlert("Cập nhật thất bại!");
                }
            } catch (SQLException e) {
            	ShowDialog. showAlert("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleDelete() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Xác nhận");
            confirm.setHeaderText("Bạn có chắc chắn muốn xóa sản phẩm?");
            confirm.setContentText("ID: " + selected.getId());

            confirm.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    try {
                        if (itemBUS.deleteItem(selected.getId())) {
                            itemList.remove(selected);
                            ShowDialog. showAlert("Xóa sản phẩm thành công!");
                        }
                    } catch (SQLException e) {
                    	ShowDialog.showAlert("Lỗi khi xóa sản phẩm: " + e.getMessage());
                    }
                }
            });
        } else {
        	ShowDialog.showAlert("Vui lòng chọn sản phẩm để xóa.");
        }
    }
    
    @FXML
    private void handleSearch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm sản phẩm");
        dialog.setHeaderText("Tìm kiếm sản phẩm theo tên hoặc ID");
        dialog.setContentText("Nhập từ khóa:");
        
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/img/search_icon.png").toExternalForm()));

        dialog.showAndWait().ifPresent(keyword -> {
            String lowerKeyword = keyword.trim().toLowerCase();
            ObservableList<Item> filtered = FXCollections.observableArrayList();

            for (Item item : itemList) {
                if (item.getName().toLowerCase().contains(lowerKeyword)
                        || item.getId().toLowerCase().contains(lowerKeyword)) {
                    filtered.add(item);
                }
            }

            if (filtered.isEmpty()) {
            	ShowDialog.showAlert("Không tìm thấy sản phẩm phù hợp!");
            } else {
                tableView.setItems(filtered);
                tableView.refresh();
            }
        });
    }

    @FXML
    private void handleRefresh() {
        try {
            loadItemData();
            ShowDialog.showAlert("Đã làm mới danh sách sản phẩm.");
        } catch (SQLException e) {
        	ShowDialog.showAlert("Lỗi khi làm mới: " + e.getMessage());
        }
    }

    private Dialog<Item> createItemDialog(Item existing, boolean isEdit) {
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Sửa sản phẩm" : "Thêm sản phẩm");
        dialog.setHeaderText(isEdit ? "Sửa thông tin sản phẩm" : "Nhập thông tin sản phẩm");

        String iconPath = isEdit ? "/img/edit_icon.png" : "/img/add_icon.png";
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource(iconPath).toExternalForm()));

        ButtonType actionType = new ButtonType(isEdit ? "Lưu" : "Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField classificationField = new TextField();

        idField.setPrefWidth(325);
        
        idField.setPromptText("ID");
        nameField.setPromptText("Tên");
        priceField.setPromptText("Giá");
        classificationField.setPromptText("Phân loại");

        if (isEdit && existing != null) {
            idField.setText(existing.getId());
            nameField.setText(existing.getName());
            priceField.setText(String.valueOf(existing.getPrice()));
            classificationField.setText(existing.getClassification());
            idField.setEditable(false);
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("ID:"), idField);
        grid.addRow(1, new Label("Tên:"), nameField);
        grid.addRow(2, new Label("Giá:"), priceField);
        grid.addRow(3, new Label("Phân loại:"), classificationField);

        grid.setPrefWidth(400);
		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().setPrefWidth(450);

        dialog.setResultConverter(button -> {
            if (button == actionType) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String priceStr = priceField.getText().trim();
                String classification = classificationField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || classification.isEmpty() || priceStr.isEmpty()) {
                    ShowDialog.showAlert("Vui lòng điền đầy đủ thông tin.");
                    return null;
                }

                try {
                    double price = Double.parseDouble(priceStr);
                    if (price <= 0) {
                    	ShowDialog.showAlert("Giá phải lớn hơn 0.");
                        return null;
                    }
                    return new Item(id, name, price, classification);
                } catch (NumberFormatException e) {
                	ShowDialog.showAlert("Giá không hợp lệ.");
                    return null;
                }
            }
            return null;
        });

        return dialog;
    }

}
