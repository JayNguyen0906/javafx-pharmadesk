package GUI;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Main_Controller {
	
	@FXML
	private ImageView headerImage;

	@FXML
	private StackPane mainContent;
	
	@FXML 
	private ChoiceBox<String> choiceOther;
	
	@FXML
	private Button backButton, homeButton, forwardButton;
	
	private boolean isInitializing = true;
	
	@FXML private void handleBack(ActionEvent e) {
	    // TODO: quay về màn hình trước
	}
	@FXML
	private void handleHome(ActionEvent e) {
		loadPrescription();
	}

	@FXML private void handleForward(ActionEvent e) {
	    // TODO: quay tới màn hình tiếp theo
	}

	
	@FXML
	public void initialize() {
		loadPrescription();
		choiceOther.getItems().addAll(
		        "Khác",
		        "Đơn Thuốc Đã Tạo",
		        "Chấm Công Nhân Viên",
		        "Báo Cáo Doanh Thu"
		    );
		    choiceOther.setValue("Khác");

		    isInitializing = false; // Cho phép xử lý sau khi khởi tạo xong
	}

	private void loadPrescription() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Prescription.fxml"));
	        Parent prescriptionView = loader.load();


	        Prescription_Controller controller = loader.getController();
	        controller.setMainController(this);
	           
	        mainContent.getChildren().clear();
	        mainContent.getChildren().add(prescriptionView);
	        
	        prescriptionView.getStylesheets().add(getClass().getResource("/css/mainGui.css").toExternalForm());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void loadCreatePrescription() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreatePrescription.fxml"));
	        Parent createPrescriptionView = loader.load();
	        
	        mainContent.getChildren().clear();
	        mainContent.getChildren().add(createPrescriptionView);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void loadCustomer(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Customer.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void loadBill(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Bill.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void loadEmployee(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Employee.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    @FXML
    public void loadItem(ActionEvent event) {
    	try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Item.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	
	@FXML
	private void loadSupplier(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Sup_Card.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void loadWarehouse(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Warehouse.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void loadListPres(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PrescriptionList.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void loadOtherSelection(ActionEvent event) {
	    if (isInitializing) return;

	    String selected = (String) choiceOther.getValue();

	    switch (selected) {
	        case "Đơn Thuốc Đã Tạo":
	            loadListPres(event);
	            break;
	        case "Chấm Công Nhân Viên":
	            // loadAttendance();
	            break;
	        case "Báo Cáo Doanh Thu":
	            // loadReport();
	            break;
	        default:
	            break;
	    }
	}


	@FXML
	private void loadOther(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Supplier.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
