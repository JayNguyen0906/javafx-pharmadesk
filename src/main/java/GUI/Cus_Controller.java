package GUI;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BUS.Cus_BUS;
import DTO.Customer;

import java.sql.SQLException;

public class Cus_Controller {
	@FXML
	private TableView<Customer> tableView;
	@FXML
	private TableColumn<Customer, Integer> id, birth;
	@FXML
	private TableColumn<Customer, String> name, gender, address, phone, bhyt;
	
	@FXML
	private Button addButton, deleteButton, editButton, refreshButton, searchButton;
	
	private Cus_BUS cusBUS;
	private ObservableList<Customer> customerList;

	@FXML
	public void initialize() throws SQLException {
		cusBUS = new Cus_BUS();

		id.setCellValueFactory(new PropertyValueFactory<>("id_Cus"));
		name.setCellValueFactory(new PropertyValueFactory<>("name_Cus"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		birth.setCellValueFactory(new PropertyValueFactory<>("birthYear"));
		bhyt.setCellValueFactory(new PropertyValueFactory<>("healthInsuranceNumber"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone_Cus"));
		address.setCellValueFactory(new PropertyValueFactory<>("address_Cus"));

		loadCustomerData();

		addButton.setOnAction(event -> handleAdd());
		deleteButton.setOnAction(event -> handleDelete());
		editButton.setOnAction(event -> handleEdit());
		searchButton.setOnAction(event -> handleSearch());
		refreshButton.setOnAction(event -> handleRefresh());
	}

	@FXML
	private void handleAdd() {
		Dialog<Customer> dialog = createCustomerDialog(null, false);
		dialog.showAndWait().ifPresent(newCustomer -> {
			if (newCustomer.getName_Cus().isEmpty() ||
				newCustomer.getPhone_Cus().isEmpty() || newCustomer.getAddress_Cus().isEmpty()) {
				ShowDialog.showAlert("Vui lòng nhập đầy đủ thông tin khách hàng!");
				return;
			}

			try {
				if (cusBUS.addCustomer(newCustomer)) {
					loadCustomerData();
					ShowDialog.showAlert("Thêm khách hàng thành công!");
				} else {
					ShowDialog.showAlert("Khách hàng đã tồn tại hoặc thêm thất bại!");
				}
			} catch (SQLException e) {
				ShowDialog.showAlert("Lỗi cơ sở dữ liệu khi thêm khách hàng: " + e.getMessage());
			}
		});
	}

	@FXML
	private void handleDelete() {
		Customer selected = tableView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (ShowDialog.showConfirmation("Bạn có chắc muốn xóa khách hàng đã chọn không?")) {
				try {
					if (cusBUS.deleteCustomer(selected.getId_Cus())) {
						// loadCustomerData();
						customerList.remove(selected);
						ShowDialog.showAlert("Xóa khách hàng thành công!");
					}
				} catch (SQLException e) {
					ShowDialog.showAlert("Lỗi cơ sở dữ liệu khi xóa khách hàng: " + e.getMessage());
				}
			}
		} else {
			ShowDialog.showAlert("Vui lòng chọn khách hàng để xóa.");
		}
	}

	@FXML
	private void handleEdit() {
		Customer selected = tableView.getSelectionModel().getSelectedItem();
		if (selected == null) {
			ShowDialog.showAlert("Vui lòng chọn khách hàng để sửa.");
			return;
		}

		Dialog<Customer> dialog = createCustomerDialog(selected, true);
		dialog.showAndWait().ifPresent(updatedCustomer -> {
			try {
				if (cusBUS.updateCustomer(updatedCustomer)) {
					loadCustomerData();
					ShowDialog.showAlert("Cập nhật thông tin khách hàng thành công!");
				} else {
					ShowDialog.showAlert("Cập nhật thông tin thất bại.");
				}
			} catch (SQLException e) {
				ShowDialog.showAlert("Lỗi cơ sở dữ liệu khi cập nhật khách hàng: " + e.getMessage());
			}
		});
	}
	@FXML
	private void handleSearch() {
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Tìm kiếm khách hàng");
	    dialog.setHeaderText("Tìm kiếm khách hàng theo thông tin");
	    dialog.setContentText("Nhập ID, tên, số điện thoại hoặc địa chỉ:");

	    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	    stage.getIcons().add(new Image(getClass().getResource("/img/search_icon.png").toExternalForm()));

	    dialog.showAndWait().ifPresent(keyword -> {
	        String lowerKeyword = keyword.trim().toLowerCase();

	        if (lowerKeyword.isEmpty()) {
	            tableView.setItems(customerList);
	            tableView.refresh();
	            return;
	        }

	        ObservableList<Customer> filtered = FXCollections.observableArrayList();

	        for (Customer c : customerList) {
	            boolean match = false;

	            if (isNumeric(lowerKeyword)) {
	                match = String.valueOf(c.getId_Cus()).equals(lowerKeyword);
	            }

	            if (!match) {
	                match = c.getName_Cus().toLowerCase().contains(lowerKeyword)
	                        || c.getPhone_Cus().toLowerCase().contains(lowerKeyword)
	                        || c.getAddress_Cus().toLowerCase().contains(lowerKeyword);
	            }

	            if (match) {
	                filtered.add(c);
	            }
	        }

	        if (filtered.isEmpty()) {
	            ShowDialog.showAlert("Không tìm thấy khách hàng phù hợp!");
	        } else {
	            tableView.setItems(filtered);
	            tableView.refresh();
	        }
	    });
	}

	private boolean isNumeric(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	@FXML 
	private void handleRefresh() {
		try {
            loadCustomerData();
            ShowDialog.showAlert("Đã làm mới danh sách khách hàng.");
        } catch (SQLException e) {
            ShowDialog.showAlert("Lỗi khi làm mới: " + e.getMessage());
        }
		
	}

	private void loadCustomerData() throws SQLException {
		customerList = FXCollections.observableArrayList(cusBUS.getAllCustomers());
		tableView.setItems(customerList);
	}

	

	private Dialog<Customer> createCustomerDialog(Customer existing, boolean isEdit) {
		Dialog<Customer> dialog = new Dialog<>();

		String iconPath = isEdit ? "/img/edit_icon.png" : "/img/add_icon.png";
		Platform.runLater(() -> {
		    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		    stage.getIcons().add(new Image(getClass().getResource(iconPath).toExternalForm()));
		});


		dialog.setTitle(isEdit ? "Sửa khách hàng" : "Thêm khách hàng");
		dialog.setHeaderText(isEdit ? "Sửa thông tin khách hàng" : "Nhập thông tin khách hàng");

		ButtonType actionType = new ButtonType(isEdit ? "Lưu" : "Thêm", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

		TextField idField = new TextField();
		idField.setEditable(false);
		TextField nameField = new TextField();
		TextField birthYearField = new TextField();
		ComboBox<String> genderCombo = new ComboBox<>();
		genderCombo.getItems().addAll("Nam", "Nữ", "Khác");
		TextField phoneField = new TextField();
		TextField addressField = new TextField();
		TextField insuranceField = new TextField();

		nameField.setPromptText("Tên");
		birthYearField.setPromptText("Năm sinh");
		genderCombo.setPromptText("Giới tính");
		phoneField.setPromptText("Số điện thoại");
		addressField.setPromptText("Địa chỉ");
		insuranceField.setPromptText("Số BHYT");

		if (isEdit && existing != null) {
			idField.setText(String.valueOf(existing.getId_Cus()));
			nameField.setText(existing.getName_Cus());
			birthYearField.setText(String.valueOf(existing.getBirthYear()));
			genderCombo.setValue(existing.getGender());
			phoneField.setText(existing.getPhone_Cus());
			addressField.setText(existing.getAddress_Cus());
			insuranceField.setText(existing.getHealthInsuranceNumber());
		}

		Node okButton = dialog.getDialogPane().lookupButton(actionType);
		okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
			String name = nameField.getText().trim();
			String birthYearStr = birthYearField.getText().trim();
			String gender = genderCombo.getValue();
			String phone = phoneField.getText().trim();
			String address = addressField.getText().trim();
			String insurance = insuranceField.getText().trim();

			if (name.isEmpty() || birthYearStr.isEmpty() || gender == null ||
			    phone.isEmpty() || address.isEmpty() || insurance.isEmpty()) {
				ShowDialog.showAlert("Vui lòng nhập đầy đủ thông tin khách hàng!");
				event.consume();
				return;
			}

			if (!phone.matches("0\\d{9}")) {
				ShowDialog.showAlert("Số điện thoại không hợp lệ! Phải bắt đầu bằng 0 và đủ 10 chữ số.");
				event.consume();
				return;
			}

			try {
				int birthYear = Integer.parseInt(birthYearStr);
				if (birthYear < 1900 || birthYear > java.time.Year.now().getValue()) {
					ShowDialog.showAlert("Năm sinh không hợp lệ!");
					event.consume();
				}
			} catch (NumberFormatException e) {
				ShowDialog.showAlert("Năm sinh phải là số!");
				event.consume();
			}
		});

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		int row = 0;

		if (isEdit) {
			grid.addRow(row++, new Label("ID:"), idField);
		}
		grid.addRow(row++, new Label("Tên:"), nameField);
		grid.addRow(row++, new Label("Năm sinh:"), birthYearField);
		grid.addRow(row++, new Label("Giới tính:"), genderCombo);
		grid.addRow(row++, new Label("Số điện thoại:"), phoneField);
		grid.addRow(row++, new Label("Địa chỉ:"), addressField);
		grid.addRow(row, new Label("Số BHYT:"), insuranceField);

		grid.setPrefWidth(400);
		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().setPrefWidth(450);

		dialog.setResultConverter(button -> {
		    if (button == actionType) {
		        int birthYear = Integer.parseInt(birthYearField.getText().trim());

		        if (isEdit) {
		            int id = Integer.parseInt(idField.getText().trim());
		            return new Customer(
		                id,
		                nameField.getText().trim(),
		                birthYear,
		                genderCombo.getValue(),
		                phoneField.getText().trim(),
		                addressField.getText().trim(),
		                insuranceField.getText().trim()
		            );
		        } else {
		            return new Customer(
		                nameField.getText().trim(),
		                birthYear,
		                genderCombo.getValue(),
		                phoneField.getText().trim(),
		                addressField.getText().trim(),
		                insuranceField.getText().trim()
		            );
		        }
		    }
		    return null;
		});


		return dialog;
	}

}
