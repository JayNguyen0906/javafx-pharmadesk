package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import BUS.User_BUS;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

public class Login_Controller {
	
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

	@FXML
	private ImageView login;

	public Login_Controller() {
	}

	@FXML
	public void initialize() {
		// Khởi tạo nếu cần
	}

	@FXML
	private ProgressIndicator progressIndicator;

	@FXML
	private void handleLogin(ActionEvent event) {
		progressIndicator.setVisible(true);
		new Thread(() -> {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> {

				String user = usernameField.getText();
				String pass = passwordField.getText();
				

				User_BUS userBUS = new User_BUS();
				
				if (user.isEmpty() || pass.isEmpty()) {
					showAlert(Alert.AlertType.ERROR, "Error", "Please enter complete information!");
					return;
				}

				if (userBUS.login(user, pass) != null) {
					try {
						Parent mainView = FXMLLoader.load(getClass().getResource("/Fxml/MainGUI.fxml"));
						Scene mainScene = new Scene(mainView);
						
						Stage loginstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		                loginstage.close();
		                
						Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						stage.setScene(mainScene);
						stage.setTitle("Pharma DeskApp - Home");
						stage.setMaximized(true);
						stage.setResizable(true);
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
						showAlert(Alert.AlertType.ERROR, "Error", "Cannot open main interface!");
					}
				} else {
					showAlert(Alert.AlertType.ERROR, "Incorrect account", "Incorrect account or password!");
				}
			});
		}).start();
	}


	@FXML
	private void handleRegister(ActionEvent event) {
		progressIndicator.setVisible(true);
		new Thread(() -> {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				try {
					Parent registerView = FXMLLoader.load(getClass().getResource("/Fxml/Register.fxml"));
					Scene registerScene = new Scene(registerView);
					
	                Stage loginstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	                loginstage.close();

					Stage stage = new Stage();
					stage.setScene(registerScene);
					stage.setTitle("Pharma DeskApp - Register");
					stage.setMaximized(true);
					stage.setResizable(true);
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}).start();
	}

	private void showAlert(Alert.AlertType alertType, String title, String massage) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(massage);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/Warning.png")));

		alert.showAndWait();
	}

}
