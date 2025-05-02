package main.java.GUI;


import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main_Controller {
	
	@FXML
	private ImageView headerImage;

	@FXML
	public void initialize() {
	    Platform.runLater(() -> {
	        headerImage.fitWidthProperty().bind(headerImage.getScene().widthProperty());
	    });
	}

//
//    @FXML
//    private StackPane contentPane;

	@FXML
	private void loadCustomer(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Customer.fxml"));
	        Parent root = loader.load();

	        Stage stage = new Stage();
	        stage.setTitle("Quản lý khách hàng");
	        stage.setScene(new Scene(root));
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


//    @FXML
//    public void loadEmployee() throws Exception {
//        Pane pane = FXMLLoader.load(getClass().getResource("/GUI/Employee.fxml"));
//        contentPane.getChildren().setAll(pane);
//    }


}
