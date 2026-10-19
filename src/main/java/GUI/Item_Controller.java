package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import BUS.Item_BUS;
import DTO.Item;

import java.sql.SQLException;

public class Item_Controller {
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> idColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, Double> priceColumn;
    @FXML private TableColumn<Item, String> classificationColumn;
    @FXML private Button addButton, deleteButton, editButton, searchButton, refreshButton;

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
                    loadItemData();
                    ShowDialog.showAlert("Thêm sản phẩm thành công!");
                } else {
                    ShowDialog.showAlert("Sản phẩm đã tồn tại hoặc thêm thất bại!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi khi thêm sản phẩm: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleEdit() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            ShowDialog.showAlert("Vui lòng chọn sản phẩm để sửa.");
            return;
        }
        
        
        String tenmoi = selected.getName();

        Dialog<Item> dialog = createItemDialog(selected, true);
        dialog.showAndWait().ifPresent(updatedItem -> {
            try {
                if (itemBUS.updateItem(updatedItem)) {
                    loadItemData();
                    ShowDialog.showAlert("Cập nhật sản phẩm thành công!" + tenmoi);
                } else {
                    ShowDialog.showAlert("Cập nhật thất bại!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleDelete() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            ShowDialog.showAlert("Vui lòng chọn sản phẩm để xóa.");
            return;
        }

        if (ShowDialog.showConfirmation("Bạn có chắc chắn muốn xóa sản phẩm có ID: " + selected.getId() + " không?")) {
            try {
                if (itemBUS.deleteItem(selected.getId())) {
                    itemList.remove(selected);
                    ShowDialog.showAlert("Xóa sản phẩm thành công!");
                } else {
                    ShowDialog.showAlert("Không thể xóa sản phẩm!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi khi xóa sản phẩm: " + e.getMessage());
            }
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
                if (item.getName().toLowerCase().contains(lowerKeyword) ||
                    String.valueOf(item.getId()).contains(lowerKeyword)) {
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
        ComboBox<String> classificationBox = new ComboBox<>();

        idField.setPrefWidth(325);
        nameField.setPromptText("Tên");
        priceField.setPromptText("Giá");
        classificationBox.setPromptText("Chọn phân loại");
        classificationBox.getItems().addAll(
            "Thuốc kê đơn", "Thuốc không kê đơn", "Thực phẩm chức năng", "Dược mỹ phẩm",
            "Thiết bị y tế", "Vật tư y tế tiêu hao", "Thuốc đông y / Thảo dược",
            "Sản phẩm cho mẹ & bé", "Sản phẩm chăm sóc cá nhân", "Khác"
        );

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        int rowIndex = 0;

        if (isEdit && existing != null) {
            idField.setText(String.valueOf(existing.getId()));
            idField.setEditable(false);
            grid.addRow(rowIndex++, new Label("ID:"), idField);
            nameField.setText(existing.getName());
            priceField.setText(String.valueOf(existing.getPrice()));
            classificationBox.setValue(existing.getClassification());
        }

        grid.addRow(rowIndex++, new Label("Tên:"), nameField);
        grid.addRow(rowIndex++, new Label("Giá:"), priceField);
        grid.addRow(rowIndex, new Label("Phân loại:"), classificationBox);
        classificationBox.setPrefWidth(325);

        grid.setPrefWidth(400);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(450);

        Node okButton = dialog.getDialogPane().lookupButton(actionType);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String name = nameField.getText().trim();
            String priceStr = priceField.getText().trim();
            String classification = classificationBox.getValue();

            if (name.isEmpty() || priceStr.isEmpty() || classification == null || classification.trim().isEmpty()) {
                ShowDialog.showAlert("Vui lòng điền đầy đủ thông tin.");
                event.consume();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    ShowDialog.showAlert("Giá phải lớn hơn 0.");
                    event.consume();
                }
            } catch (NumberFormatException e) {
                ShowDialog.showAlert("Giá không hợp lệ. Vui lòng nhập số.");
                event.consume();
            }
        });

        dialog.setResultConverter(button -> {
            if (button == actionType) {
                return new Item(
                    isEdit ? Integer.parseInt(idField.getText().trim()) : 0, 
                    nameField.getText().trim(),
                    Double.parseDouble(priceField.getText().trim()),
                    classificationBox.getValue().trim()
                );
            }
            return null;
        });

        return dialog;
    }
    
    
}
