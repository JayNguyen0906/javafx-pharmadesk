package GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class Prescription_Controller {

    @FXML
    private Button btnCreatePrescription;
    

	@FXML
	private StackPane mainContent;

    @FXML
	private void handleCreatePrescription(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreatePrescription.fxml"));
	        Parent createPrescriptionView = loader.load();
	        
	        mainContent.getChildren().clear();
	        mainContent.getChildren().add(createPrescriptionView);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
