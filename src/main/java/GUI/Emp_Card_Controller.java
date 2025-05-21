package GUI;

import BUS.Emp_BUS;
import DAO.Emp_DAO;
import DTO.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Optional;

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
        cccdLabel.setText("🪪 " + employee.getCCCD_Emp());
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
    @FXML
    private void handleEdit() {
        System.out.println("Sửa: " + currentEmp.getName_Emp());

        Emp_Controller.setEditingEmployee(currentEmp); // Gửi dữ liệu cần sửa
        if (parentController != null) {
            parentController.setEditingEmployee(currentEmp);  // Gán đối tượng cần sửa
            parentController.showEditForm(currentEmp);                   // Hiển thị form sửa
        }
    }

    @FXML
    private void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc muốn xóa nhân viên này?");
        alert.setContentText(currentEmp.getName_Emp());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = Emp_BUS.deleteEmployee(currentEmp.getID_Emp());
            if (success) {
                System.out.println("Đã xóa: " + currentEmp.getName_Emp());
                removeCardFromGUI();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Xóa thất bại");
                errorAlert.setContentText("Không thể xóa nhân viên.");
                errorAlert.showAndWait();
            }
        }
        System.out.println("Xóa: " + currentEmp.getName_Emp());
        boolean confirmed = Emp_BUS.deleteEmployee(currentEmp.getID_Emp());
        if (confirmed && parentController != null) {
            parentController.loadData(); // Gọi lại sau khi xóa
        }
    }
    private void removeCardFromGUI() {
        // Giả sử mỗi Emp_Card nằm trong một VBox hoặc FlowPane
        Button source = (Button) attendanceButton.getScene().getFocusOwner();
        VBox card = (VBox) source.getParent().getParent(); // hoặc điều chỉnh nếu layout khác
        ((Pane) card.getParent()).getChildren().remove(card);
    }
}
