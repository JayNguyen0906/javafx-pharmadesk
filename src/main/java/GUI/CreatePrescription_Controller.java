package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import BUS.Cus_BUS;
import BUS.Prescription_BUS;
import BUS.Symptom_BUS;
import DAO.SuggestionDAO;
import DTO.Customer;
import DTO.Medicine;
import DTO.Prescription;
import DTO.Symptom;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreatePrescription_Controller {

    @FXML private TextField txtPatientName, txtBirthYear, txtPhone, txtAddress, txtBhyt, txtVitalSigns, txtSymptom, txtDiagnosis;
    @FXML private ChoiceBox<String> txtGender;
    @FXML private TextField txtMedicineName, txtDosage, txtNote;
    @FXML private TableView<Medicine> tableMedicines;
    @FXML private Label dateLabel, NguoiKeDon;
    @FXML private Button btnAddMedicine, btnPrint, btnSavePrescription1;

    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private final ObservableList<Medicine> medicineList = FXCollections.observableArrayList();
    private final List<Symptom> usedSuggestions = new ArrayList<>();

    private final Symptom_BUS symptomBUS = new Symptom_BUS();
 
    @FXML
    public void initialize() {
    	//Ngày
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'PharmaDeskApp, ngày' dd/MM/yyyy");
        dateLabel.setText(today.format(formatter));
        //Người kê đơn
        String fullName = GetNameLogin.getLoggedInFullName();
        NguoiKeDon.setText(fullName);

        tableMedicines.setItems(medicineList);
        tableMedicines.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableMedicines.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("dosage"));
        tableMedicines.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("note"));
   
        tableMedicines.setRowFactory(tv -> {
            TableRow<Medicine> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // 1-click
                    Medicine selectedMed = row.getItem();
                    showEditDeleteDialog(selectedMed);
                }
            });
            return row;
        });
        
        // Gợi ý
        TextFields.bindAutoCompletion(txtSymptom, SuggestionDAO.getSymptoms());
        TextFields.bindAutoCompletion(txtDosage, SuggestionDAO.getDosages());
        TextFields.bindAutoCompletion(txtNote, SuggestionDAO.getNotes());
        TextFields.bindAutoCompletion(txtMedicineName, SuggestionDAO.getMedicineNames());
        
        try {
			customerList.addAll(new Cus_BUS().getAllCustomers());
		} catch (SQLException e) {
			ShowDialog.showAlert("Lỗi khi lấy thông tin khách hàng cũ: " + e.getMessage());
		}
        
        TextFields.bindAutoCompletion(txtPatientName, SuggestionDAO.getCustomerSuggestions())
        
        .setOnAutoCompleted(event -> {
            String selected = event.getCompletion();
            String phone = selected.substring(selected.lastIndexOf("-") + 1).trim();

            for (Customer c : customerList) {
                if (c.getPhone_Cus().equals(phone)) {
                    fillCustomerInfo(c);
                    break;
                }
            }
        });
    }
    
    private void fillCustomerInfo(Customer customer) {
        txtPatientName.setText(customer.getName_Cus());
        txtBirthYear.setText(String.valueOf(customer.getBirthYear()));
        txtPhone.setText(customer.getPhone_Cus());
        txtGender.setValue(customer.getGender());
        txtAddress.setText(customer.getAddress_Cus());
        txtBhyt.setText(customer.getHealthInsuranceNumber());
    }


    @FXML
    private void handleSuggestMedicine(ActionEvent event) {
        String symptomInput = txtSymptom.getText().trim();
        if (symptomInput.isEmpty()) {
            ShowDialog.showAlert("Vui lòng nhập triệu chứng!");
            return;
        }

        try {
            List<Symptom> suggestions = symptomBUS.getSuggestionsByMultiSymptoms(symptomInput);

            if (suggestions.isEmpty()) {
                ShowDialog.showAlert("Không có gợi ý thuốc cho các triệu chứng này.");
                return;
            }

            usedSuggestions.clear();
            usedSuggestions.addAll(suggestions);
            
            // Tránh trùng thuốc đã có trong bảng
            List<String> addedMedicineNames = new ArrayList<>();
            for (Medicine m : medicineList) {
                addedMedicineNames.add(m.getName().toLowerCase());
            }

            int addedCount = 0;
            for (Symptom s : suggestions) {
                String medName = s.getItemName().trim().toLowerCase();
                if (!addedMedicineNames.contains(medName)) {
                    medicineList.add(new Medicine(
                        s.getItemName(),
                        s.getRecommendedDosage(),
                        s.getNote()
                    ));
                    addedMedicineNames.add(medName);
                    addedCount++;
                }
            }

            if (addedCount > 0) {
                ShowDialog.showAlert("Đã thêm " + addedCount + " thuốc gợi ý vào bảng!");
            } else {
                ShowDialog.showAlert("Tất cả thuốc gợi ý đã có trong bảng.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ShowDialog.showAlert("Lỗi khi gợi ý thuốc: " + e.getMessage());
        }
    }


    
    @FXML
    private void handleAddMedicine(ActionEvent event) {
        String name = txtMedicineName.getText().trim();
        String dosage = txtDosage.getText().trim();
        String note = txtNote.getText().trim();

        if (name.isEmpty() || dosage.isEmpty() ||  note.isEmpty()) {
        	ShowDialog.showAlert("Vui lòng nhập đầy đủ thông tin thuốc!");
            return;
        }

        medicineList.add(new Medicine(name, dosage, note));
        
        // Xoá trường sau khi thêm
        txtMedicineName.clear();
        txtDosage.clear();
        txtNote.clear();
    }
    
    private void showEditDeleteDialog(Medicine med) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa hoặc Xóa thuốc");

        String iconPath = "/img/dashb.png";
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResource(iconPath).toExternalForm()));
		
        // Giao diện trong dialog
        TextField nameField = new TextField(med.getName());
        TextField dosageField = new TextField(med.getDosage());
        TextField noteField = new TextField(med.getNote());
        nameField.setPrefWidth(350);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Tên thuốc:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Liều lượng:"), 0, 1);
        grid.add(dosageField, 1, 1);
        grid.add(new Label("Ghi chú:"), 0, 2);
        grid.add(noteField, 1, 2);

        grid.setPrefWidth(400);
		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().setPrefWidth(450);

        // Nút Sửa, Xóa, Hủy
        ButtonType btnSave = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnDelete = new ButtonType("Xóa", ButtonBar.ButtonData.LEFT);
        ButtonType btnCancel = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSave, btnDelete, btnCancel);

        // Xử lý kết quả
        dialog.showAndWait().ifPresent(result -> {
            if (result == btnSave) {
                med.setName(nameField.getText().trim());
                med.setDosage(dosageField.getText().trim());
                med.setNote(noteField.getText().trim());
                tableMedicines.refresh(); 
            } else if (result == btnDelete) {
                medicineList.remove(med);
            }
        });
    }


    @FXML
    private void handleSave(ActionEvent event) {
        try {
            Customer customer = createCustomerFromInput();

            Cus_BUS customerBUS = new Cus_BUS();
            int customerId = customerBUS.addCustomerGetID(customer);
            if (customerId == -1) {
                ShowDialog.showAlert("Thêm khách hàng thất bại!");
                return;
            }
            customer.setId_Cus(customerId);

            Prescription prescription = createPrescriptionFromInput(customerId);

            Prescription_BUS prescriptionBUS = new Prescription_BUS();
            boolean result = prescriptionBUS.insertOrUpdatePrescription(prescription);
            
            if (result) {
//                List<Integer> suggestionIDs = new ArrayList<>();
//                for (Symptom s : usedSuggestions) {
//                    suggestionIDs.add(s.getSuggestionId()); 
//                }
//                
//                prescriptionBUS.insertPrescriptionSuggestions(prescription.getPrescriptionID(), suggestionIDs); 
//            
                ShowDialog.showAlert("Đã lưu đơn thuốc thành công!");
            } else {
                ShowDialog.showAlert("Lưu đơn thuốc thất bại!");
            }
        } catch (IllegalArgumentException ex) {
            ShowDialog.showAlert(ex.getMessage());
        } catch (Exception ex) {
            ShowDialog.showAlert("Lỗi: " + ex.getMessage());
        }
    }

    @FXML
    private void handlePrint(ActionEvent event) {
        try {
            Customer customer = createCustomerFromInput();

            Cus_BUS customerBUS = new Cus_BUS();
            int customerId = customerBUS.addCustomerGetID(customer);
            if (customerId == -1) {
                ShowDialog.showAlert("Lỗi lưu khách hàng!");
                return;
            }
            customer.setId_Cus(customerId);

            Prescription prescription = createPrescriptionFromInput(customerId);

            String fileName = "DonThuoc_" + customer.getName_Cus().replaceAll("\\s+", "_") + ".pdf";

            ProcessPrint processPrint = new ProcessPrint();
            boolean success = processPrint.exportToPDF(fileName, prescription, customer);
            if (success) {
                processPrint.openPDF(fileName);
            } else {
                ShowDialog.showAlert("Lỗi: Tạo file PDF thất bại, không gửi đến máy in.");
            }
        } catch (IllegalArgumentException ex) {
            ShowDialog.showAlert(ex.getMessage());
        } catch (Exception ex) {
            ShowDialog.showAlert("Lỗi: " + ex.getMessage());
        }
    }

    
    private Customer createCustomerFromInput() throws IllegalArgumentException {
        if (!isPatientInfoValid()) throw new IllegalArgumentException("Thông tin bệnh nhân không hợp lệ");

        Customer customer = new Customer();
        customer.setName_Cus(txtPatientName.getText().trim());
        try {
            customer.setBirthYear(Integer.parseInt(txtBirthYear.getText().trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Năm sinh phải là số hợp lệ");
        }
        customer.setPhone_Cus(txtPhone.getText().trim());
        customer.setGender(txtGender.getValue());
        customer.setAddress_Cus(txtAddress.getText().trim());
        customer.setHealthInsuranceNumber(txtBhyt.getText().trim());
        return customer;
    }

    private Prescription createPrescriptionFromInput(int customerId) {
        Prescription prescription = new Prescription();
        prescription.setCustomerID(customerId);
        prescription.setDiagnosis(txtDiagnosis.getText().trim());
        prescription.setVitalSigns(txtVitalSigns.getText().trim());
        prescription.setPrescriberName(GetNameLogin.getLoggedInFullName());
        prescription.setPrescribedDate(LocalDate.now());
        prescription.setMedicineList(new ArrayList<>(medicineList));
        return prescription;
    }



    private boolean isPatientInfoValid() {
        return !txtPatientName.getText().trim().isEmpty() &&
               !txtBirthYear.getText().trim().isEmpty() &&
               txtGender.getValue() != null;
    }


}
