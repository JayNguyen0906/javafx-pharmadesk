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
import javafx.scene.Node;
import BUS.Account_BUS;
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
				

				Account_BUS accBUS = new Account_BUS();
				boolean checkLogin = accBUS.checkLogin(user, pass);

				if (user.isEmpty() || pass.isEmpty()) {
					showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đầy đủ thông tin!");
					return;
				}

				if (checkLogin) {
					try {
						Parent mainView = FXMLLoader.load(getClass().getResource("/Fxml/MainGUI.fxml"));
						Scene mainScene = new Scene(mainView);

						// Lấy stage hiện tại từ button (hoặc bất kỳ node nào)
						Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						stage.setScene(mainScene);
						stage.setTitle("Pharma DeskApp - Home");
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
						showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở giao diện chính!");
					}
				} else {
					showAlert(Alert.AlertType.ERROR, "Sai tài khoản", "Tài khoản hoặc mật khẩu không đúng!");
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

					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.setScene(registerScene);
					stage.setTitle("Pharma DeskApp - Đăng ký");
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
		stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/Warning.png")));

		alert.showAndWait();
	}

}
