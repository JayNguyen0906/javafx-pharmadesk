package app;

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
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

		// Set title và scene
		primaryStage.setTitle("Pharma DeskApp - Đăng nhập");
		primaryStage.setScene(new Scene(root));

		// Set icon
		InputStream is = getClass().getResourceAsStream("/img/MainLogo.png");
		primaryStage.getIcons().add(new Image(is));
		
		primaryStage.setMaximized(true);
		primaryStage.setResizable(true);

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
