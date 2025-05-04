package main.java.GUI;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.BUS.Warehouse_BUS;
import main.java.DTO.Warehouse;

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
        refreshButton.setOnAction(envent -> handleRefresh());
    }

    private void loadWarehouseData() throws SQLException {
        warehouseList = FXCollections.observableArrayList(warehouseBUS.getAllWarehouses());
        tableView.setItems(warehouseList);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    @FXML
    private void handleAdd() {
        Dialog<Warehouse> dialog = createWarehouseDialog(null, false);
        dialog.showAndWait().ifPresent(newWarehouse -> {
            try {
                if (warehouseBUS.addWarehouse(newWarehouse)) {
                    loadWarehouseData();
                    showAlert("Warehouse added successfully!");
                } else {
                    showAlert("Failed to add warehouse (possibly duplicate)!");
                }
            } catch (SQLException e) {
                showAlert("Error adding warehouse: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleDelete() {
        Warehouse selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (showConfirmation("Are you sure you want to delete this warehouse item?")) {
                try {
                    if (warehouseBUS.deleteWarehouse(selected.getItemName())) {
                        loadWarehouseData();
                        showAlert("Warehouse deleted successfully!");
                    } else {
                        showAlert("Failed to delete warehouse.");
                    }
                } catch (SQLException e) {
                    showAlert("Error deleting warehouse: " + e.getMessage());
                }
            }
        } else {
            showAlert("Please select a warehouse item to delete.");
        }
    }

    @FXML
    private void handleEdit() {
        Warehouse selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a warehouse item to edit.");
            return;
        }

        Dialog<Warehouse> dialog = createWarehouseDialog(selected, true);
        dialog.showAndWait().ifPresent(updatedWarehouse -> {
            try {
                if (warehouseBUS.updateWarehouse(updatedWarehouse)) {
                    loadWarehouseData();
                    showAlert("Warehouse updated successfully!");
                } else {
                    showAlert("Failed to update warehouse.");
                }
            } catch (SQLException e) {
                showAlert("Error updating warehouse: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleSearch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Warehouse");
        dialog.setHeaderText("Search for a Warehouse Item");
        dialog.setContentText("Enter item name or supplier name:");

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
                showAlert("No matching warehouse item found.");
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
			showAlert("Warehouse list refreshed.");
		} catch (SQLException e) {
			showAlert("Failed to refresh warehouse list: " + e.getMessage());
		}
	}
    
    private Dialog<Warehouse> createWarehouseDialog(Warehouse existing, boolean isEdit) {
        Dialog<Warehouse> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Edit Warehouse" : "Add Warehouse");
        dialog.setHeaderText(isEdit ? "Edit warehouse information" : "Enter warehouse information");

        ButtonType actionType = new ButtonType(isEdit ? "Save" : "Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        TextField itemNameField = new TextField();
        TextField supplierField = new TextField();
        TextField quantityField = new TextField();
        DatePicker expiryPicker = new DatePicker();
        TextField primeCostField = new TextField();

        itemNameField.setPromptText("Item Name");
        supplierField.setPromptText("Supplier Name");
        quantityField.setPromptText("Quantity");
        primeCostField.setPromptText("Prime Cost");

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
        grid.addRow(0, new Label("Item Name:"), itemNameField);
        grid.addRow(1, new Label("Supplier Name:"), supplierField);
        grid.addRow(2, new Label("Quantity:"), quantityField);
        grid.addRow(3, new Label("Expiry Date:"), expiryPicker);
        grid.addRow(4, new Label("Prime Cost:"), primeCostField);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == actionType) {
                try {
                    String itemName = itemNameField.getText().trim();
                    String supplier = supplierField.getText().trim();
                    int quantity = Integer.parseInt(quantityField.getText().trim());
                    LocalDate expiryDate = expiryPicker.getValue();
                    double primeCost = Double.parseDouble(primeCostField.getText().trim());

                    if (itemName.isEmpty() || supplier.isEmpty() || expiryDate == null) {
                        showAlert("Please fill in all required fields!");
                        return null;
                    }

                    return new Warehouse(itemName, supplier, quantity, Date.valueOf(expiryDate), primeCost);
                } catch (NumberFormatException e) {
                    showAlert("Invalid number format!");
                    return null;
                }
            }
            return null;
        });

        return dialog;
    }
}
