package GUI;

import javafx.scene.control.Button;
import java.sql.SQLException;

import BUS.Cus_BUS;
import DTO.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
	@FXML 
	private TextField idField;
	@FXML 
	private TextField nameField;
	@FXML 
	private TextField phoneField;
	@FXML 
	private TextField addressField;
	
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
	    String id = idField.getText();
	    String name = nameField.getText();
	    String phone = phoneField.getText();
	    String address = addressField.getText();

	    if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
	        System.out.println("Vui lòng nhập đầy đủ thông tin.");
	        return;
	    }

	    Customer newCustomer = new Customer(id, name, phone, address);

	    try {
	        if (cusBUS.addCustomer(newCustomer)) {
	            customerList.add(newCustomer);
	            System.out.println("Đã thêm khách hàng.");
	        } else {
	            System.out.println("Khách hàng đã tồn tại hoặc lỗi khi thêm.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void handleDelete() {
	    Customer selected = tableView.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	        try {
	            if (cusBUS.deleteCustomer(selected.getId_Cus())) {
	                customerList.remove(selected);
	                System.out.println("Xóa thành công.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Chọn khách hàng để xóa.");
	    }
	}

	@FXML
	private void handleEdit() {
	    Customer selected = tableView.getSelectionModel().getSelectedItem();
	    if (selected != null) {
	        selected.setName_Cus(nameField.getText());
	        selected.setAddress_Cus(addressField.getText());
	        selected.setPhone_Cus(phoneField.getText());

	        try {
	            cusBUS.updateCustomer(selected);
	            tableView.refresh();
	            System.out.println("Sửa thành công.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Chọn khách hàng để sửa.");
	    }
	}

	@FXML
	private void handleSearch() {
	    String keyword = nameField.getText().trim().toLowerCase();
	    if (!keyword.isEmpty()) {
	        ObservableList<Customer> filtered = FXCollections.observableArrayList();
	        for (Customer c : customerList) {
	            if (c.getName_Cus().toLowerCase().contains(keyword) ||
	                c.getAddress_Cus().toLowerCase().contains(keyword) ||
	                c.getPhone_Cus().toLowerCase().contains(keyword)) {
	                filtered.add(c);
	            }
	        }
	        tableView.setItems(filtered);
	    } else {
	        tableView.setItems(customerList);
	    }
	}

	private void loadCustomerData() throws SQLException {
	    customerList = FXCollections.observableArrayList(cusBUS.getAllCustomers());
	    tableView.setItems(customerList);
	}
}
