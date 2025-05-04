package main.java.GUI;


import java.io.IOException;
import java.io.InputStream;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Customer.fxml"));
	        Parent root = loader.load();

	        Stage stage = new Stage();
	        stage.setTitle("Quản lý khách hàng");
	        stage.setScene(new Scene(root));
	        
	        InputStream is = getClass().getResourceAsStream("/img/Customer.png");
			stage.getIcons().add(new Image(is));
			
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


//    @FXML
//    public void loadGood() throws Exception {
//    	try {
//	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Good.fxml"));
//	        Parent root = loader.load();
//
//	        Stage stage = new Stage();
//	        stage.setTitle("Quản lý hàng hóa");
//	        stage.setScene(new Scene(root));
//	        stage.show();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//    }
	
	@FXML
	private void loadSupplier(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Supplier.fxml"));
	        Parent root = loader.load();

	        Stage stage = new Stage();
	        stage.setTitle("Quản lý nhà cung cấp");
	        stage.setScene(new Scene(root));
	        
	        InputStream is = getClass().getResourceAsStream("/img/Customer.png");
			stage.getIcons().add(new Image(is));
			
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@FXML
	private void loadWarehouse(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Warehouse.fxml"));
	        Parent root = loader.load();

	        Stage stage = new Stage();
	        stage.setTitle("Quản lý kho hàng");
	        stage.setScene(new Scene(root));
	        
//	        InputStream is = getClass().getResourceAsStream("/img/Customer.png");
//			stage.getIcons().add(new Image(is));
			
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
