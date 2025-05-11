package GUI;

import DTO.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Emp_Card_Controller {
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML private Label positionLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label cccdLabel;

    public void setData(Employee employee) {
        idLabel.setText("📧 " + employee.getID_Emp());
        nameLabel.setText("👤 " + employee.getName_Emp());
        positionLabel.setText("💼 " + employee.getPosition_Emp());
        emailLabel.setText("📧 " + employee.getEmail_Emp());
        phoneLabel.setText("📞 " + employee.getPhone_Emp());
        cccdLabel.setText("📧 " + employee.getCCCD_Emp());
    }
}