package GUI;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BUS.Sup_BUS;
import DTO.Supplier;

import java.sql.SQLException;

public class Sup_Controller {
	@FXML
	private TableView<Supplier> tableView;
	@FXML
	private TableColumn<Supplier, String> idColumn;
	@FXML
	private TableColumn<Supplier, String> nameColumn;
	@FXML
	private TableColumn<Supplier, String> phoneColumn;
	@FXML
	private TableColumn<Supplier, String> emailColumn;
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

	private Sup_BUS supBUS;
	private ObservableList<Supplier> SupplierList = FXCollections.observableArrayList();
	

	@FXML
	public void initialize() throws SQLException {
		supBUS = new Sup_BUS();

		// Gán dữ liệu vào bảng
		idColumn.setCellValueFactory(new PropertyValueFactory<>("ID_Sup"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name_Sup"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone_Sup"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email_Sup"));
		
		// Load data
		loadSupplierData();

		// Event handlers for buttons
		addButton.setOnAction(event -> handleAdd());
		deleteButton.setOnAction(event -> handleDelete());
		editButton.setOnAction(event -> handleEdit());
		searchButton.setOnAction(event -> handleSearch());
		refreshButton.setOnAction(event -> handleRefresh());
	}

	// Thêm
	@FXML
	private void handleAdd() {
		Dialog<Supplier> dialog = createSupplierDialog(null, false);
		dialog.showAndWait().ifPresent(newSupplier -> {
			
			if (newSupplier.getName_Sup().isEmpty() ||
		            newSupplier.getPhone_Sup().isEmpty() || newSupplier.getEmail_Sup().isEmpty()) {
		            showAlert("Please fill in all Supplier fields!");
		            handleAdd();
		            return;
		        }
			
			
			try {
				if (supBUS.addSupplier(newSupplier)) {
					tableView.refresh();
					showAlert("Supplier added successfully!");
				} else {
					showAlert("Supplier already exists or failed to add!");
				}
			} catch (SQLException e) {
				showAlert("Database error while adding Supplier: " + e.getMessage());
			}
		});
	}

	// Xóa
	@FXML
	private void handleDelete() {
		Supplier selected = tableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (showConfirmation("Are you sure you want to delete the selected Supplier?")) {
				try {
					if (supBUS.deleteSupplier(selected.getID_Sup())) {
						loadSupplierData();
						showAlert("Supplier deleted successfully!");
					}
				} catch (SQLException e) {
					showAlert("Database error while deleting Supplier: " + e.getMessage());
				}
			}
		} else {
			showAlert("Please select a Supplier to delete.");
		}
	}

	// Sửa
	@FXML
	private void handleEdit() {
		Supplier selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null) {
			showAlert("Please select a Supplier to edit.");
			return;
		}

		Dialog<Supplier> dialog = createSupplierDialog(selected, true);
		dialog.showAndWait().ifPresent(updatedSupplier -> {
			try {
				if (supBUS.updateSupplier(updatedSupplier)) {
					loadSupplierData();
					showAlert("Supplier updated successfully!");
				} else {
					showAlert("Failed to update Supplier.");
				}
			} catch (SQLException e) {
				showAlert("Database error while updating Supplier: " + e.getMessage());
			}
		});
	}

	// Tìm kiếm
	@FXML
	private void handleSearch() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search Supplier");
		dialog.setHeaderText("Search for a Supplier");
		dialog.setContentText("Enter ID, name, phone, or email:");

		dialog.showAndWait().ifPresent(keyword -> {
			String lowerKeyword = keyword.trim().toLowerCase();
			ObservableList<Supplier> filtered = FXCollections.observableArrayList();

			for (Supplier c : SupplierList) {
				if ( c.getName_Sup().toLowerCase().contains(lowerKeyword)
						|| c.getPhone_Sup().toLowerCase().contains(lowerKeyword)
						|| c.getEmail_Sup().toLowerCase().contains(lowerKeyword)) {
					filtered.add(c);
				}
			}

			if (filtered.isEmpty()) {
				showAlert("No matching Supplier found!");
			} else {
				tableView.setItems(filtered);
				tableView.refresh();
			}
		});
	}
	
	@FXML
	private void handleRefresh() {
		try {
			loadSupplierData();
			showAlert("Supplier list refreshed.");
		} catch (SQLException e) {
			showAlert("Failed to refresh supplier list: " + e.getMessage());
		}
	}

	// Load Supplier data into table
	private void loadSupplierData() throws SQLException {
		SupplierList = FXCollections.observableArrayList(supBUS.getAllSuppliers());
		tableView.setItems(SupplierList);
	}

	// Show information alert
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// Show confirmation alert (for delete operation)
	private boolean showConfirmation(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(message);

		return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
	}

	// Tạo dialog chung cho cả thêm và sửa
	private Dialog<Supplier> createSupplierDialog(Supplier existing, boolean isEdit) {
		Dialog<Supplier> dialog = new Dialog<>();
		dialog.setTitle(isEdit ? "Edit Supplier" : "Add Supplier");
		dialog.setHeaderText(isEdit ? "Edit Supplier information" : "Enter Supplier information");

		ButtonType actionType = new ButtonType(isEdit ? "Save" : "Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

		TextField idField = new TextField();
		TextField nameField = new TextField();
		TextField phoneField = new TextField();
		TextField emailField = new TextField();

		idField.setPromptText("ID");
		nameField.setPromptText("Name");
		phoneField.setPromptText("Phone");
		emailField.setPromptText("Email");
		
		if (isEdit && existing != null) {
			idField.setText(String.valueOf(existing.getID_Sup()));
			nameField.setText(existing.getName_Sup());
			phoneField.setText(existing.getPhone_Sup());
			emailField.setText(existing.getEmail_Sup());
			idField.setEditable(false);
		}

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.addRow(0, new Label("ID:"), idField);
		grid.addRow(1, new Label("Name:"), nameField);
		grid.addRow(2, new Label("Phone:"), phoneField);
		grid.addRow(3, new Label("Email:"), emailField);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(button -> {
			if (button == actionType) {
				String phone = phoneField.getText().trim();
				
				if (!phone.matches("0\\d{9}")) {
		            showAlert("Invalid phone number!");
		            return null;
		        }
				return new Supplier(Integer.parseInt(idField.getText().trim()),  nameField.getText().trim(), phoneField.getText().trim(),
						emailField.getText().trim());
			}
			return null;
		});

		return dialog;
	}
}
