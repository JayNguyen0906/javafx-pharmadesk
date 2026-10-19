package GUI;

import BUS.Prescription_BUS;
import DAO.Cus_DAO;
import DTO.Customer;
import DTO.Prescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrescriptionList_Controller {

    @FXML private TableView<Prescription> tablePrescriptions;
    @FXML private TableColumn<Prescription, String> colId;
    @FXML private TableColumn<Prescription, String> colName;
    @FXML private TableColumn<Prescription, String> colDiagnosis;
    @FXML private TableColumn<Prescription, String> colDate, colPrescriberName;

    private final ObservableList<Prescription> prescriptions = FXCollections.observableArrayList();
    private Prescription_BUS prescriptionBUS;

    @FXML
    public void initialize() {
        try {
            prescriptionBUS = new Prescription_BUS();
            setupTableColumns();
            loadPrescriptionsFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
            ShowDialog.showAlert("Lỗi khi kết nối cơ sở dữ liệu!");
        }
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getPrescriptionID())));
        colDiagnosis.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDiagnosis()));
        colDate.setCellValueFactory(data -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new javafx.beans.property.SimpleStringProperty(data.getValue().getPrescribedDate().format(formatter));
        });

        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getCustomerID())));
        colPrescriberName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrescriberName()));
    }

    private void loadPrescriptionsFromDB() {
        try {
            List<Prescription> list = prescriptionBUS.getAllPrescriptions();
            prescriptions.setAll(list);
            tablePrescriptions.setItems(prescriptions);
        } catch (SQLException e) {
            e.printStackTrace();
            ShowDialog.showAlert("Không thể tải danh sách đơn thuốc từ cơ sở dữ liệu.");
        }
    }

    @FXML
    private void handleRefresh() {
        loadPrescriptionsFromDB();
        ShowDialog.showAlert("Làm mới danh sách đơn thuốc thành công!");
    }

    @FXML
    private void handleViewDetails() {
        Prescription selected = tablePrescriptions.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Cus_DAO customerDAO = new Cus_DAO();
                Customer customer = customerDAO.getCustomerById(selected.getCustomerID());

                if (customer != null) {
                	showDetailsDialog(selected.getPrescriptionID(), customer);
                } else {
                    ShowDialog.showAlert("Không tìm thấy thông tin khách hàng.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ShowDialog.showAlert("Lỗi khi lấy thông tin khách hàng.");
            }
        } else {
            ShowDialog.showAlert("Vui lòng chọn đơn thuốc để xem chi tiết!");
        }
    }


    private void showDetailsDialog(int prescriptionID, Customer customer) throws SQLException {
        Prescription fullPres = prescriptionBUS.getPrescriptionById(prescriptionID);
		if (fullPres == null) {
		    ShowDialog.showAlert("Không tìm thấy thông tin đơn thuốc.");
		    return;
		}

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Chi tiết đơn thuốc");
		dialog.setHeaderText("Thông tin đơn thuốc ID: " + prescriptionID);
		
		String iconPath = "/img/dashb.png";
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResource(iconPath).toExternalForm()));

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		grid.add(new Label("Tên bệnh nhân:"), 0, 0);
        grid.add(new Label(customer.getName_Cus()), 1, 0);

        grid.add(new Label("Ngày sinh:"), 0, 1);
        grid.add(new Label(String.valueOf(customer.getBirthYear())), 1, 1);

        grid.add(new Label("Giới tính:"), 0, 2);
        grid.add(new Label(customer.getGender()), 1, 2);

        grid.add(new Label("Số điện thoại:"), 0, 3);
        grid.add(new Label(customer.getPhone_Cus()), 1, 3);

        grid.add(new Label("Địa chỉ:"), 0, 4);
        grid.add(new Label(customer.getAddress_Cus()), 1, 4);

        //Cus
        grid.add(new Label("Mã đơn thuốc:"), 0, 5);
        grid.add(new Label(String.valueOf(fullPres.getPrescriptionID())), 1, 5);

        grid.add(new Label("Sinh hiệu:"), 0, 6);
        grid.add(new Label(fullPres.getVitalSigns()), 1, 6);

        grid.add(new Label("Triệu chứng:"), 0, 7);
        grid.add(new Label(fullPres.getSymptom()), 1, 7);

        grid.add(new Label("Chẩn đoán:"), 0, 8);
        grid.add(new Label(fullPres.getDiagnosis()), 1, 8);

        grid.add(new Label("Ngày kê đơn:"), 0, 9);
        grid.add(new Label(fullPres.getPrescribedDate().format((DateTimeFormatter.ofPattern("dd/MM/yyyy")))), 1, 9);

        grid.add(new Label("Người kê đơn:"), 0, 10);
        grid.add(new Label(fullPres.getPrescriberName()), 1, 10);

        //Pres
        StringBuilder meds = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        fullPres.getMedicineList().forEach(m -> {
            meds.append(index.getAndIncrement()).append(". ")
                .append(m.getName()).append(" - ")
                .append(m.getDosage()).append(" - ")
                .append(m.getNote()).append("\n");
        });

        grid.add(new Label("Thuốc:"), 0, 11);
        TextArea medsArea = new TextArea(meds.toString());
        medsArea.setEditable(false);
        medsArea.setWrapText(true);
        medsArea.setPrefRowCount(5);
        grid.add(medsArea, 1, 11);

		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().setMinWidth(500);

		// Nút
		ButtonType deleteButton = new ButtonType("Xóa", ButtonBar.ButtonData.YES);
		ButtonType closeButton = new ButtonType("Thoát", ButtonBar.ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(deleteButton, closeButton);

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == deleteButton) {
		        return deleteButton;
		    }
		    return null;
		});

		dialog.showAndWait().ifPresent(result -> {
		    if (result == deleteButton) {
		        boolean confirm = ShowDialog.showConfirmation("Bạn có chắc chắn xóa đơn thuốc này?");
		        if (confirm) {
		            boolean deleted = prescriptionBUS.deletePrescription(prescriptionID);
		            if (deleted) {
		                ShowDialog.showAlert("Xóa đơn thuốc thành công.");
		                loadPrescriptionsFromDB();
		            } else {
		                ShowDialog.showAlert("Xóa đơn thuốc thất bại.");
		            }
		        }
		    }
		});
    }
}
