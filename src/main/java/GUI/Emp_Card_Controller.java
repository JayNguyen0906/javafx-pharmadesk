package GUI;

import BUS.Emp_BUS;
import DAO.Emp_DAO;
import DTO.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Emp_Card_Controller {
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML private Label positionLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label cccdLabel;

    @FXML
    private Button attendanceButton;

    private Employee currentEmp;
    private Emp_Controller parentController;

    public void setParentController(Emp_Controller controller) {
        this.parentController = controller;
    }


    public void setData(Employee employee) {
        this.currentEmp = employee;
        idLabel.setText("🆔 " + employee.getID_Emp());
        nameLabel.setText("👤 " + employee.getName_Emp());
        positionLabel.setText("💼 " + employee.getPosition_Emp());
        emailLabel.setText("📧 " + employee.getEmail_Emp());
        phoneLabel.setText("📞 " + employee.getPhone_Emp());
        cccdLabel.setText("💳 " + employee.getCCCD_Emp());
        
        if (Emp_DAO.hasCheckedToday(employee.getID_Emp())) {
            attendanceButton.setText("⛔ Đã chấm hôm nay");
            attendanceButton.setDisable(true);
        } else {
            attendanceButton.setText("✅ Chấm công");
            attendanceButton.setDisable(false);
        }
    }

    @FXML
    private void handleAttendance() {
        boolean success = Emp_DAO.recordAttendance(currentEmp.getID_Emp());

        if (success) {
            attendanceButton.setText("✅ Đã chấm");
            attendanceButton.setDisable(true);
        } else {
            attendanceButton.setText("⛔ Đã chấm hôm nay");
            attendanceButton.setDisable(true);
        }
    }
    @SuppressWarnings("static-access")
	@FXML
    private void handleEdit() {
        Emp_Controller.setEditingEmployee(currentEmp); 
        if (parentController != null) {
            parentController.setEditingEmployee(currentEmp);  
            parentController.showEditForm(currentEmp);                   
        }
    }

    @FXML
    private void handleDelete() {
        if (ShowDialog.showConfirmation("Bạn có chắc muốn xóa nhân viên" + currentEmp.getName_Emp()+ " ?")) {
            boolean success = Emp_BUS.deleteEmployee(currentEmp.getID_Emp());
            if (success) {
            	ShowDialog.showAlert("Đã xóa: " + currentEmp.getName_Emp());
                removeCardFromGUI();
            } else {
              
                ShowDialog.showAlert("Không thể xóa nhân viên.");
            }
        }
        boolean confirmed = Emp_BUS.deleteEmployee(currentEmp.getID_Emp());
        if (confirmed && parentController != null) {
            parentController.loadData(); 
        }
    }
    private void removeCardFromGUI() {
        Button source = (Button) attendanceButton.getScene().getFocusOwner();
        VBox card = (VBox) source.getParent().getParent(); 
        ((Pane) card.getParent()).getChildren().remove(card);
    }
}