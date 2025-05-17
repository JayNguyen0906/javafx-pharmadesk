package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Prescription_Controller {

    @FXML
    private Button btnCreatePrescription;

	 
    private Main_Controller mainController;

    public void setMainController(Main_Controller mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    public void initialize() {
    	Timeline timeline = new Timeline(
    	        new KeyFrame(Duration.seconds(2), e -> playShakeAnimation()),
    	        new KeyFrame(Duration.seconds(3)) // lặp sau mỗi 3s
    	    );
    	    timeline.setCycleCount(Timeline.INDEFINITE);
    	    timeline.play();
    }

    private void playShakeAnimation() {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), btnCreatePrescription);
        shake.setByX(10);
        shake.setCycleCount(8);
        shake.setAutoReverse(true);
        shake.play();
    }
    
    @FXML
    private void handleCreatePrescription(ActionEvent event) {
        if (mainController != null) {
            mainController.loadCreatePrescription();
        }
    }

}
