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

		id.setCellValueFactory(new PropertyValueFactory<>("id_Cus"));
		name.setCellValueFactory(new PropertyValueFactory<>("name_Cus"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone_Cus"));
		address.setCellValueFactory(new PropertyValueFactory<>("address_Cus"));

		loadCustomerData();

		addButton.setOnAction(event -> handleAdd());
		deleteButton.setOnAction(event -> handleDelete());
		editButton.setOnAction(event -> handleEdit());
		searchButton.setOnAction(event -> handleSearch());
	}

	@FXML
	private void handleAdd() {
		Dialog<Customer> dialog = createCustomerDialog(null, false);
		dialog.showAndWait().ifPresent(newCustomer -> {
			if (newCustomer.getId_Cus().isEmpty() || newCustomer.getName_Cus().isEmpty() ||
				newCustomer.getPhone_Cus().isEmpty() || newCustomer.getAddress_Cus().isEmpty()) {
				showAlert("Vui lòng nhập đầy đủ thông tin khách hàng!");
				handleAdd();
				return;
			}

			try {
				if (cusBUS.addCustomer(newCustomer)) {
					loadCustomerData();
					showAlert("Thêm khách hàng thành công!");
				} else {
					showAlert("Khách hàng đã tồn tại hoặc thêm thất bại!");
				}
			} catch (SQLException e) {
				showAlert("Lỗi cơ sở dữ liệu khi thêm khách hàng: " + e.getMessage());
			}
		});
	}

	@FXML
	private void handleDelete() {
		Customer selected = tableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (showConfirmation("Bạn có chắc muốn xóa khách hàng đã chọn không?")) {
				try {
					if (cusBUS.deleteCustomer(selected.getId_Cus())) {
						loadCustomerData();
						showAlert("Xóa khách hàng thành công!");
					}
				} catch (SQLException e) {
					showAlert("Lỗi cơ sở dữ liệu khi xóa khách hàng: " + e.getMessage());
				}
			}
		} else {
			showAlert("Vui lòng chọn khách hàng để xóa.");
		}
	}

	@FXML
	private void handleEdit() {
		Customer selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null) {
			showAlert("Vui lòng chọn khách hàng để sửa.");
			return;
		}

		Dialog<Customer> dialog = createCustomerDialog(selected, true);
		dialog.showAndWait().ifPresent(updatedCustomer -> {
			try {
				if (cusBUS.updateCustomer(updatedCustomer)) {
					loadCustomerData();
					showAlert("Cập nhật thông tin khách hàng thành công!");
				} else {
					showAlert("Cập nhật thông tin thất bại.");
				}
			} catch (SQLException e) {
				showAlert("Lỗi cơ sở dữ liệu khi cập nhật khách hàng: " + e.getMessage());
			}
		});
	}

	@FXML
	private void handleSearch() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Tìm kiếm khách hàng");
		dialog.setHeaderText("Tìm kiếm khách hàng theo thông tin");
		dialog.setContentText("Nhập ID, tên, số điện thoại hoặc địa chỉ:");

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
				showAlert("Không tìm thấy khách hàng phù hợp!");
			} else {
				tableView.setItems(filtered);
				tableView.refresh();
			}
		});
	}

	private void loadCustomerData() throws SQLException {
		customerList = FXCollections.observableArrayList(cusBUS.getAllCustomers());
		tableView.setItems(customerList);
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean showConfirmation(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Xác nhận");
		alert.setHeaderText(null);
		alert.setContentText(message);

		return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
	}

	private Dialog<Customer> createCustomerDialog(Customer existing, boolean isEdit) {
		Dialog<Customer> dialog = new Dialog<>();
		dialog.setTitle(isEdit ? "Sửa khách hàng" : "Thêm khách hàng");
		dialog.setHeaderText(isEdit ? "Sửa thông tin khách hàng" : "Nhập thông tin khách hàng");

		ButtonType actionType = new ButtonType(isEdit ? "Lưu" : "Thêm", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

		TextField idField = new TextField();
		TextField nameField = new TextField();
		TextField phoneField = new TextField();
		TextField addressField = new TextField();

		idField.setPromptText("ID");
		nameField.setPromptText("Tên");
		phoneField.setPromptText("Số điện thoại");
		addressField.setPromptText("Địa chỉ");

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
		grid.addRow(1, new Label("Tên:"), nameField);
		grid.addRow(2, new Label("Số điện thoại:"), phoneField);
		grid.addRow(3, new Label("Địa chỉ:"), addressField);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(button -> {
			if (button == actionType) {
				String phone = phoneField.getText().trim();

				if (!phone.matches("0\\d{9}")) {
					showAlert("Số điện thoại không hợp lệ!");
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
