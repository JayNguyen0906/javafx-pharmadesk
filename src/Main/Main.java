package Main;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load file FXML
		Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Customer.fxml"));

		// Set title và scene
		primaryStage.setTitle("Pharma DeskApp");
		primaryStage.setScene(new Scene(root));

		// Set icon
		InputStream is = getClass().getResourceAsStream("/MainLogo.png");
		primaryStage.getIcons().add(new Image(is));

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
