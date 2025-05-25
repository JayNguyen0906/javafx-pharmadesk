package GUI;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BUS.Warehouse_BUS;
import DTO.Warehouse;

import java.sql.Date;
import java.time.LocalDate;
import java.sql.SQLException;

public class Warehouse_Controller {

    @FXML
    private TableView<Warehouse> tableView;
    @FXML
    private TableColumn<Warehouse, String> itemColumn;
    @FXML
    private TableColumn<Warehouse, String> supplierColumn;
    @FXML
    private TableColumn<Warehouse, Integer> currentquantityColumn;
    @FXML
    private TableColumn<Warehouse, Date> expiryColumn;
    @FXML
    private TableColumn<Warehouse, Double> primecostColumn;
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

    private Warehouse_BUS warehouseBUS;
    private ObservableList<Warehouse> warehouseList;

    @FXML
    public void initialize() throws SQLException {
        warehouseBUS = new Warehouse_BUS();

        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        currentquantityColumn.setCellValueFactory(new PropertyValueFactory<>("currentQuantity"));
        expiryColumn.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        primecostColumn.setCellValueFactory(new PropertyValueFactory<>("primeCost"));

        loadWarehouseData();

        addButton.setOnAction(event -> handleAdd());
        deleteButton.setOnAction(event -> handleDelete());
        editButton.setOnAction(event -> handleEdit());
        searchButton.setOnAction(event -> handleSearch());
        refreshButton.setOnAction(event -> handleRefresh());
    }

    private void loadWarehouseData() throws SQLException {
        warehouseList = FXCollections.observableArrayList(warehouseBUS.getAllWarehouses());
        tableView.setItems(warehouseList);
    }

    @FXML
    private void handleAdd() {
        Dialog<Warehouse> dialog = createWarehouseDialog(null, false);
        dialog.showAndWait().ifPresent(newWarehouse -> {
            try {
                if (warehouseBUS.addWarehouse(newWarehouse)) {
                    loadWarehouseData();
                    ShowDialog.showAlert("Thêm hàng hóa vào kho thành công!");
                } else {
                    ShowDialog.showAlert("Thêm thất bại (có thể đã tồn tại)!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi khi thêm hàng hóa: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleDelete() {
        Warehouse selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (ShowDialog.showConfirmation("Bạn có chắc muốn xóa hàng hóa này khỏi kho?")) {
                try {
                    if (warehouseBUS.deleteWarehouse(selected.getItemName())) {
                        loadWarehouseData();
                        ShowDialog.showAlert("Xóa hàng hóa thành công!");
                    } else {
                        ShowDialog.showAlert("Xóa thất bại!");
                    }
                } catch (SQLException e) {
                    ShowDialog.showAlert("Lỗi khi xóa hàng hóa: " + e.getMessage());
                }
            }
        } else {
            ShowDialog.showAlert("Vui lòng chọn hàng hóa để xóa.");
        }
    }

    @FXML
    private void handleEdit() {
        Warehouse selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            ShowDialog.showAlert("Vui lòng chọn hàng hóa để chỉnh sửa.");
            return;
        }

        Dialog<Warehouse> dialog = createWarehouseDialog(selected, true);
        dialog.showAndWait().ifPresent(updatedWarehouse -> {
            try {
                if (warehouseBUS.updateWarehouse(updatedWarehouse)) {
                    loadWarehouseData();
                    ShowDialog.showAlert("Cập nhật hàng hóa thành công!");
                } else {
                    ShowDialog.showAlert("Cập nhật thất bại.");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi khi cập nhật hàng hóa: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleSearch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm hàng hóa");
        dialog.setHeaderText("Tìm kiếm theo tên hàng hoặc nhà cung cấp");
        dialog.setContentText("Nhập từ khóa:");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/img/search_icon.png").toExternalForm()));
        
        dialog.showAndWait().ifPresent(keyword -> {
            String lowerKeyword = keyword.trim().toLowerCase();
            ObservableList<Warehouse> filtered = FXCollections.observableArrayList();

            for (Warehouse w : warehouseList) {
                if (w.getItemName().toLowerCase().contains(lowerKeyword)
                        || w.getSupplierName().toLowerCase().contains(lowerKeyword)) {
                    filtered.add(w);
                }
            }

            if (filtered.isEmpty()) {
                ShowDialog.showAlert("Không tìm thấy hàng hóa phù hợp.");
            } else {
                tableView.setItems(filtered);
                tableView.refresh();
            }
        });
    }

    @FXML
    private void handleRefresh() {
        try {
            loadWarehouseData();
            ShowDialog.showAlert("Đã làm mới danh sách hàng hóa.");
        } catch (SQLException e) {
            ShowDialog.showAlert("Lỗi khi làm mới danh sách: " + e.getMessage());
        }
    }

    private Dialog<Warehouse> createWarehouseDialog(Warehouse existing, boolean isEdit) {
        Dialog<Warehouse> dialog = new Dialog<>();

        String iconPath = isEdit ? "/img/edit_icon.png" : "/img/add_icon.png";
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource(iconPath).toExternalForm()));

        dialog.setTitle(isEdit ? "Chỉnh sửa hàng hóa" : "Thêm hàng hóa");
        dialog.setHeaderText(isEdit ? "Chỉnh sửa thông tin hàng hóa" : "Nhập thông tin hàng hóa");

        ButtonType actionType = new ButtonType(isEdit ? "Lưu" : "Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        TextField itemNameField = new TextField();
        TextField supplierField = new TextField();
        TextField quantityField = new TextField();
        DatePicker expiryPicker = new DatePicker();
        TextField primeCostField = new TextField();

        itemNameField.setPrefWidth(325);
        itemNameField.setPromptText("Tên hàng hóa");
        supplierField.setPromptText("Nhà cung cấp");
        quantityField.setPromptText("Số lượng");
        primeCostField.setPromptText("Giá nhập");

        if (isEdit && existing != null) {
            itemNameField.setText(existing.getItemName());
            itemNameField.setEditable(false);
            supplierField.setText(existing.getSupplierName());
            quantityField.setText(String.valueOf(existing.getCurrentQuantity()));
            expiryPicker.setValue(existing.getExpiry().toLocalDate());
            primeCostField.setText(String.valueOf(existing.getPrimeCost()));
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Tên hàng hóa:"), itemNameField);
        grid.addRow(1, new Label("Nhà cung cấp:"), supplierField);
        grid.addRow(2, new Label("Số lượng:"), quantityField);
        grid.addRow(3, new Label("Hạn sử dụng:"), expiryPicker);
        grid.addRow(4, new Label("Giá nhập:"), primeCostField);

        grid.setPrefWidth(400);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(450);

        Node okButton = dialog.getDialogPane().lookupButton(actionType);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String itemName = itemNameField.getText().trim();
            String supplier = supplierField.getText().trim();
            String quantityStr = quantityField.getText().trim();
            String primeCostStr = primeCostField.getText().trim();
            LocalDate expiryDate = expiryPicker.getValue();

            if (itemName.isEmpty() || supplier.isEmpty() || quantityStr.isEmpty() ||
                primeCostStr.isEmpty() || expiryDate == null) {
                ShowDialog.showAlert("Vui lòng nhập đầy đủ thông tin!");
                event.consume();
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    ShowDialog.showAlert("Số lượng phải lớn hơn 0.");
                    event.consume();
                    return;
                }
            } catch (NumberFormatException e) {
                ShowDialog.showAlert("Số lượng không hợp lệ. Vui lòng nhập số nguyên.");
                event.consume();
                return;
            }

            try {
                double primeCost = Double.parseDouble(primeCostStr);
                if (primeCost <= 0) {
                    ShowDialog.showAlert("Giá nhập phải lớn hơn 0.");
                    event.consume();
                }
            } catch (NumberFormatException e) {
                ShowDialog.showAlert("Giá nhập không hợp lệ. Vui lòng nhập số.");
                event.consume();
            }
        });

        dialog.setResultConverter(button -> {
            if (button == actionType) {
                return new Warehouse(
                    itemNameField.getText().trim(),
                    supplierField.getText().trim(),
                    Integer.parseInt(quantityField.getText().trim()),
                    Date.valueOf(expiryPicker.getValue()),
                    Double.parseDouble(primeCostField.getText().trim())
                );
            }
            return null;
        });

        return dialog;
    }
}