package GUI;


import java.io.IOException;

import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Node;

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
		        "Lịch Sử Đơn Thuốc",
		        "Lịch Sử Hóa Đơn",
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
	        
	        Stage newStage = new Stage();
	        newStage.setTitle("Hóa đơn");
	        newStage.setScene(new Scene(root));
	        InputStream path = getClass().getResourceAsStream("/img/bill.png");
	        newStage.getIcons().add(new Image(path));
	        
	        newStage.show();
	        
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
	        case "Lịch Sử Đơn Thuốc":
	            loadListPres(event);
	            break;
	        case "Lịch Sử Hóa Đơn":
	            loadBillList(event);
	            break;
	        case "Báo Cáo Doanh Thu":
	            // loadReport();
	            break;
	        default:
	            break;
	    }
	}


	@FXML
	private void loadBillList(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BillList.fxml"));
	        Parent root = loader.load();
	        mainContent.getChildren().setAll(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void handleLogout(ActionEvent event) {
	    try {
	    	boolean confirm = ShowDialog.showConfirmation("Bạn có chắc chắn muốn đăng xuất?");
	    	if (confirm) {
	        // Quay lại màn hình Login
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
	        Parent loginRoot = loader.load();

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(new Scene(loginRoot));
	        stage.setTitle("Đăng nhập");
	        
	        InputStream is = getClass().getResourceAsStream("/img/MainLogo.png");
			stage.getIcons().add(new Image(is));
			
	        stage.setResizable(true);
	        stage.setMaximized(true);
			
	        stage.show();

	        GetNameLogin.clear();  
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	        ShowDialog.showAlert("Lỗi khi đăng xuất!");
	    }
	}
}
