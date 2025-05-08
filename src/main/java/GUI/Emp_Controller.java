package GUI;
import BUS.Emp_BUS;
import DTO.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Emp_Controller {

    @FXML private VBox employeeList;
    @FXML private TextField idField, nameField, positionField, emailField, phoneField,cccdField;

    private final Emp_BUS bus = new Emp_BUS();

    @FXML
    public void initialize() throws SQLException {
        for (Employee emp : bus.getAllEmp()) {
            addEmployeeCard(emp);
        }
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
}
