package GUI;
import BUS.Emp_BUS;
import DTO.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    @FXML private Button btnSave;
    private final Emp_BUS bus = new Emp_BUS();


    @FXML
    public void initialize() throws SQLException {
        for (Employee emp : bus.getAllEmp()) {
            addEmployeeCard(emp);
        }
        if (editingEmp != null) {
            idField.setText(editingEmp.getID_Emp());
            nameField.setText(editingEmp.getName_Emp());
            positionField.setText(editingEmp.getPosition_Emp());
            emailField.setText(editingEmp.getEmail_Emp());
            phoneField.setText(editingEmp.getPhone_Emp());
            cccdField.setText(editingEmp.getCCCD_Emp());

            idField.setDisable(true); // Không cho sửa ID
        }
        loadData();
    }

    @FXML
    private void handleAddEmployee() {
        String id = idField.getText();
        String name = nameField.getText();
        String position = positionField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String cccd = cccdField.getText();

        Employee newEmp = new Employee(id,name, position, email, phone,cccd);
        if (bus.addEmployee(newEmp)) {
            addEmployeeCard(newEmp);
            nameField.clear(); positionField.clear(); emailField.clear(); phoneField.clear();idField.clear();cccdField.clear();
        }
        if (bus.addEmployee(newEmp)) {
            addEmployeeCard(newEmp);
            idField.clear();
            nameField.clear();
            positionField.clear();
            emailField.clear();
            phoneField.clear();
            cccdField.clear();

            // Ẩn form sau khi thêm
            formBox.setVisible(false);
            formBox.setManaged(false);
            btnShowForm.setText("➕ Thêm nhân viên mới");
        }
        formBox.setVisible(false);
       formBox.setManaged(false);
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
    private  void  handleBack(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainGUI.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Pharma DeskApp - Main");
            stage.show();
        } catch (IOException e) {
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
        boolean isVisible = formBox.isVisible();
        formBox.setVisible(!isVisible);
        formBox.setManaged(!isVisible);
        if (!isVisible) {
            btnShowForm.setText(" Ẩn form thêm");
        } else {
            btnShowForm.setText(" Thêm nhân viên mới");
        }
    }


    @FXML
    private void toggleFormVisibility() {
        if (formBox == null) {
            System.out.println("formPane is null!");
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
                idField.getText(),
                nameField.getText(),
                positionField.getText(),
                emailField.getText(),
                phoneField.getText(),
                cccdField.getText()
        );

        boolean success = Emp_BUS.updateEmployee(updated);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Cập nhật thành công");
            alert.setContentText("Thông tin nhân viên đã được cập nhật.");
            alert.showAndWait();
            editingEmp = null; // reset
            formBox.setVisible(false); // ẩn form sau khi sửa
            loadData(); // tải lại dữ liệu
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Lỗi cập nhật");
            alert.setContentText("Không thể cập nhật thông tin.");
            alert.showAndWait();
        }
    }
    public void loadData() {
        employeeList.getChildren().clear(); // Đúng rồi

        List<Employee> employees = Emp_BUS.emp_dao.getAll(); // ✅ Đổi tên biến này
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
        idField.setText(emp.getID_Emp());
        idField.setDisable(true); // ID không cho sửa
        nameField.setText(emp.getName_Emp());
        positionField.setText(emp.getPosition_Emp());
        emailField.setText(emp.getEmail_Emp());
        phoneField.setText(emp.getPhone_Emp());
        cccdField.setText(emp.getCCCD_Emp());
    }
}