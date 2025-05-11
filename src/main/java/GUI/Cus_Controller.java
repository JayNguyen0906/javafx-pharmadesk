package GUI;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BUS.Cus_BUS;
import DTO.Customer;

import java.sql.SQLException;

public class Cus_Controller {
	@FXML
	private TableView<Customer> tableView;
	@FXML
	private TableColumn<Customer, String> id;
	@FXML
	private TableColumn<Customer, String> name;
	@FXML
	private TableColumn<Customer, String> phone;
	@FXML
	private TableColumn<Customer, String> address;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button editButton;
	@FXML
	private Button searchButton;

	private Cus_BUS cusBUS;
	private ObservableList<Customer> customerList;

	@FXML
	public void initialize() throws SQLException {
		cusBUS = new Cus_BUS();

		// Gán dữ liệu vào bảng
		id.setCellValueFactory(new PropertyValueFactory<>("id_Cus"));
		name.setCellValueFactory(new PropertyValueFactory<>("name_Cus"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone_Cus"));
		address.setCellValueFactory(new PropertyValueFactory<>("address_Cus"));

		// Load data
		loadCustomerData();

		// Event handlers for buttons
		addButton.setOnAction(event -> handleAdd());
		deleteButton.setOnAction(event -> handleDelete());
		editButton.setOnAction(event -> handleEdit());
		searchButton.setOnAction(event -> handleSearch());
	}

	// Thêm khách hàng
	@FXML
	private void handleAdd() {
		Dialog<Customer> dialog = createCustomerDialog(null, false);
		dialog.showAndWait().ifPresent(newCustomer -> {
			
			if (newCustomer.getId_Cus().isEmpty() || newCustomer.getName_Cus().isEmpty() ||
		            newCustomer.getPhone_Cus().isEmpty() || newCustomer.getAddress_Cus().isEmpty()) {
		            showAlert("Please fill in all customer fields!");
		            handleAdd();
		            return;
		        }
			
			
			try {
				if (cusBUS.addCustomer(newCustomer)) {
					loadCustomerData();
					showAlert("Customer added successfully!");
				} else {
					showAlert("Customer already exists or failed to add!");
				}
			} catch (SQLException e) {
				showAlert("Database error while adding customer: " + e.getMessage());
			}
		});
	}

	// Xóa khách hàng
	@FXML
	private void handleDelete() {
		Customer selected = tableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (showConfirmation("Are you sure you want to delete the selected customer?")) {
				try {
					if (cusBUS.deleteCustomer(selected.getId_Cus())) {
						loadCustomerData();
						showAlert("Customer deleted successfully!");
					}
				} catch (SQLException e) {
					showAlert("Database error while deleting customer: " + e.getMessage());
				}
			}
		} else {
			showAlert("Please select a customer to delete.");
		}
	}

	// Sửa thông tin khách hàng
	@FXML
	private void handleEdit() {
		Customer selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null) {
			showAlert("Please select a customer to edit.");
			return;
		}

		Dialog<Customer> dialog = createCustomerDialog(selected, true);
		dialog.showAndWait().ifPresent(updatedCustomer -> {
			try {
				if (cusBUS.updateCustomer(updatedCustomer)) {
					loadCustomerData();
					showAlert("Customer updated successfully!");
				} else {
					showAlert("Failed to update customer.");
				}
			} catch (SQLException e) {
				showAlert("Database error while updating customer: " + e.getMessage());
			}
		});
	}

	// Tìm kiếm
	@FXML
	private void handleSearch() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Search Customer");
		dialog.setHeaderText("Search for a customer");
		dialog.setContentText("Enter ID, name, phone, or address:");

		dialog.showAndWait().ifPresent(keyword -> {
			String lowerKeyword = keyword.trim().toLowerCase();
			ObservableList<Customer> filtered = FXCollections.observableArrayList();

			for (Customer c : customerList) {
				if (c.getId_Cus().toLowerCase().contains(lowerKeyword)
						|| c.getName_Cus().toLowerCase().contains(lowerKeyword)
						|| c.getPhone_Cus().toLowerCase().contains(lowerKeyword)
						|| c.getAddress_Cus().toLowerCase().contains(lowerKeyword)) {
					filtered.add(c);
				}
			}

			if (filtered.isEmpty()) {
				showAlert("No matching customer found!");
			} else {
				tableView.setItems(filtered);
				tableView.refresh();
			}
		});
	}

	// Load customer data into table
	private void loadCustomerData() throws SQLException {
		customerList = FXCollections.observableArrayList(cusBUS.getAllCustomers());
		tableView.setItems(customerList);
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
	private Dialog<Customer> createCustomerDialog(Customer existing, boolean isEdit) {
		Dialog<Customer> dialog = new Dialog<>();
		dialog.setTitle(isEdit ? "Edit Customer" : "Add Customer");
		dialog.setHeaderText(isEdit ? "Edit customer information" : "Enter customer information");

		ButtonType actionType = new ButtonType(isEdit ? "Save" : "Add", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

		TextField idField = new TextField();
		TextField nameField = new TextField();
		TextField phoneField = new TextField();
		TextField addressField = new TextField();

		idField.setPromptText("ID");
		nameField.setPromptText("Name");
		phoneField.setPromptText("Phone");
		addressField.setPromptText("Address");
		
		if (isEdit && existing != null) {
			idField.setText(existing.getId_Cus());
			nameField.setText(existing.getName_Cus());
			phoneField.setText(existing.getPhone_Cus());
			addressField.setText(existing.getAddress_Cus());
			idField.setEditable(false);
		}

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.addRow(0, new Label("ID:"), idField);
		grid.addRow(1, new Label("Name:"), nameField);
		grid.addRow(2, new Label("Phone:"), phoneField);
		grid.addRow(3, new Label("Address:"), addressField);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(button -> {
			if (button == actionType) {
				String phone = phoneField.getText().trim();
				
				if (!phone.matches("0\\d{9}")) {
		            showAlert("Invalid phone number!");
		            return null;
		        }
				return new Customer(idField.getText().trim(), nameField.getText().trim(), phoneField.getText().trim(),
						addressField.getText().trim());
			}
			return null;
		});

		return dialog;
	}
}
