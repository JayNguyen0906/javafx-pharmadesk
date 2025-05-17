package GUI;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Main_Controller {
	
	@FXML
	private ImageView headerImage;

	@FXML
	private StackPane mainContent;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button homeButton;
	
	@FXML
	private Button forwardButton;
	
	
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

}
