package GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Main_Controller {
	
	@FXML
	private ImageView headerImage;

	@FXML
	public void initialize() {
	    Platform.runLater(() -> {
	        headerImage.fitWidthProperty().bind(headerImage.getScene().widthProperty());
	    });
	}


    @FXML
    private StackPane contentPane;

    @FXML
    public void loadPage1() throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        contentPane.getChildren().setAll(pane);
    }

    @FXML
    public void loadPage2() throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        contentPane.getChildren().setAll(pane);
    }


}
