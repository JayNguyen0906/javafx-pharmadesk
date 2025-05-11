package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

public class CreatePrescription_Controller {

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtMedicineName;

    @FXML
    private TextField txtDosage;

    @FXML
    private void handleSave(ActionEvent event) {
        String patientName = txtPatientName.getText();
        String medicineName = txtMedicineName.getText();
        String dosage = txtDosage.getText();

        // Kiểm tra đầu vào
        if (patientName.isEmpty() || medicineName.isEmpty() || dosage.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Thiếu thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin trước khi lưu.");
            alert.showAndWait();
            return;
        }

        // TODO: Lưu đơn thuốc vào cơ sở dữ liệu hoặc danh sách
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText("Đơn thuốc đã được lưu thành công!");
        alert.showAndWait();
    }
}
