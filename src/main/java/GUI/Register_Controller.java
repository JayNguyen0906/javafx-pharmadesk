package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import BUS.User_BUS;
import DTO.User;

import java.io.IOException;
import java.io.InputStream;

public class Register_Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private Button registerButton;

    private final User_BUS userBUS = new User_BUS();

    @FXML
    public void initialize() {
        genderChoiceBox.getItems().addAll("Nam", "Nữ");
    }

    @FXML
    private void handleRegisterClick() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String gender = genderChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() ||
            email.isEmpty() || phone.isEmpty() || gender == null || address.isEmpty()) {
        	ShowDialog.showAlert("Lỗi! Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        User newUser = new User(0, username, password, fullName, email, phone, gender, address, "customer", true);

        if (userBUS.addUser(newUser)) {
        	ShowDialog.showAlert("Thành Công! Tài khoản đã được tạo!");
            switchToLogin();
        } else {
        	ShowDialog.showAlert("Đăng ký thất bại! Tên người dùng đã tồn tại hoặc đã xảy ra lỗi.");
        }
    }

    @FXML
    private void handleLoginClick() {
        switchToLogin();
    }

    private void switchToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));

    		InputStream is = getClass().getResourceAsStream("/img/MainLogo.png");
    		stage.getIcons().add(new Image(is));
            
            stage.show();
            
            stage.setMaximized(true);
    		stage.setResizable(true);

            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            ShowDialog.showAlert("Lỗi! Không tải được màn hình đăng nhập!");
        }
    }

    
}
