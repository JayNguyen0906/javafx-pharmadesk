package GUI;
import BUS.Emp_BUS;
import DTO.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Emp_Controller {
    @FXML
    private VBox formBox;
    @FXML
    private Button btnShowForm;
    @FXML private FlowPane employeeList;
    @FXML private TextField idField, nameField, positionField, emailField, phoneField,cccdField;
    @FXML private TextField searchField;
    @FXML private Button btnSave, btnAdd;
    private final Emp_BUS bus = new Emp_BUS();


    @FXML
    public void initialize() throws SQLException {
    	idField.setDisable(true);
        for (Employee emp : bus.getAllEmp()) {
            addEmployeeCard(emp);
        }
        if (editingEmp != null) {
            idField.setText(String.valueOf(editingEmp.getID_Emp()));
            nameField.setText(editingEmp.getName_Emp());
            positionField.setText(editingEmp.getPosition_Emp());
            emailField.setText(editingEmp.getEmail_Emp());
            phoneField.setText(editingEmp.getPhone_Emp());
            cccdField.setText(editingEmp.getCCCD_Emp());

             
        }
        loadData();
    }

    @FXML
    private void handleAddEmployee() {
        String name = nameField.getText();
        String position = positionField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String cccd = cccdField.getText();
        
        if (name.isEmpty() || position.isEmpty() || email.isEmpty() || phone.isEmpty() || cccd.isEmpty()) {
            ShowDialog.showAlert("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        
        if (!phone.matches("\\d{10,11}")) {
            ShowDialog.showAlert("Số điện thoại không hợp lệ.");
            return;
        }

        if (!cccd.matches("\\d{12}")) {
            ShowDialog.showAlert("CCCD phải gồm đúng 12 chữ số.");
            return;
        }
        
        Employee newEmp = new Employee(name, position, email, phone,cccd);
        
        loadData();
        
        if (bus.addEmployee(newEmp)) {
            addEmployeeCard(newEmp);
            
            idField.clear();
            nameField.clear();
            positionField.clear();
            emailField.clear();
            phoneField.clear();
            cccdField.clear();

            btnShowForm.setText("➕ Thêm nhân viên mới"); 
            formBox.setVisible(false);
            formBox.setManaged(false);
        } else {
            ShowDialog.showAlert("Không thể thêm nhân viên. Vui lòng thử lại.");
        }
       
    }

    private void addEmployeeCard(Employee emp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Emp_Card.fxml"));
            Node card = loader.load();
            Emp_Card_Controller controller = loader.getController();
            controller.setData(emp);
            employeeList.getChildren().add(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleSearch() throws SQLException {
        String keyword = searchField.getText().toLowerCase();
        employeeList.getChildren().clear();
        for (Employee emp : bus.getAllEmp()) {
            if (emp.getName_Emp().toLowerCase().contains(keyword)) {
                addEmployeeCard(emp);
            }
        }
    }

    @FXML
    private void handleShowAll() throws SQLException {
        searchField.clear();
        employeeList.getChildren().clear();
        for (Employee emp : bus.getAllEmp()) {
            addEmployeeCard(emp);
        }
    }
    @FXML
    private void handleShowForm() {
    	editingEmp = null;
    	
        boolean isVisible = formBox.isVisible();
        formBox.setVisible(!isVisible);
        formBox.setManaged(!isVisible);
        
        nameField.clear();
        positionField.clear();
        emailField.clear();
        phoneField.clear();
        cccdField.clear();
        
        idField.setVisible(false);
        idField.setManaged(false);
        idField.setDisable(true);
        
        btnSave.setVisible(false);
        btnSave.setManaged(false);
        btnAdd.setVisible(true);
        btnAdd.setManaged(true);

        if (!isVisible) {
            btnShowForm.setText(" Ẩn form thêm");
        } else {
            btnShowForm.setText(" Thêm nhân viên mới");
        }
    }


    @FXML
    private void toggleFormVisibility() {
        if (formBox == null) {
            System.out.println("formPane Lỗi!");
            return;
        }

        boolean currentlyVisible = formBox.isVisible();
        formBox.setVisible(!currentlyVisible);
        formBox.setManaged(!currentlyVisible);
    }
    private static Employee editingEmp;

    public static void setEditingEmployee(Employee emp) {
        editingEmp = emp;
    }
    @FXML
    private void handleSaveEmployee() {
        Employee updated = new Employee(
        		Integer.parseInt(idField.getText()),
                nameField.getText(),
                positionField.getText(),
                emailField.getText(),
                phoneField.getText(),
                cccdField.getText()
        );

        boolean success = Emp_BUS.updateEmployee(updated);
        if (success) {
            ShowDialog.showAlert("Thông tin nhân viên đã được cập nhật thành công!");
            editingEmp = null; 
            formBox.setVisible(false); 
            loadData();
        } else {
            ShowDialog.showAlert("Lỗi! Không thể cập nhật thông tin.");
        }
    }
    public void loadData() {
        employeeList.getChildren().clear(); 

        List<Employee> employees = Emp_BUS.emp_dao.getAll(); 
        try {
            for (Employee emp : employees) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Emp_Card.fxml"));
                Node empCard = loader.load();

                Emp_Card_Controller cardController = loader.getController();
                cardController.setData(emp);
                cardController.setParentController(this);

                employeeList.getChildren().add(empCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showEditForm(Employee emp) {
        editingEmp = emp;
        formBox.setVisible(true);
        formBox.setManaged(true);
        btnShowForm.setText("Ẩn form thêm");
        
        btnSave.setVisible(true);
        btnSave.setManaged(true);
        btnAdd.setVisible(false);
        btnAdd.setManaged(false);
        
        idField.setText(String.valueOf(emp.getID_Emp()));
        idField.setDisable(true); 
        nameField.setText(emp.getName_Emp());
        positionField.setText(emp.getPosition_Emp());
        emailField.setText(emp.getEmail_Emp());
        phoneField.setText(emp.getPhone_Emp());
        cccdField.setText(emp.getCCCD_Emp());
        
        idField.setVisible(true);
        idField.setManaged(true);

    }
}