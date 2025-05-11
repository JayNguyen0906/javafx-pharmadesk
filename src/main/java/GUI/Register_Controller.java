package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import BUS.User_BUS;
import DTO.User;

import java.io.IOException;

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
        genderChoiceBox.getItems().addAll("Male", "Female", "Other");
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
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all required fields.");
            return;
        }

        User newUser = new User(0, username, password, fullName, email, phone, gender, address, "customer", true);

        if (userBUS.addUser(newUser)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account registered successfully!");
            switchToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username already exists or an error occurred.");
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

            stage.show();
            
            stage.setMaximized(true);
    		stage.setResizable(true);

            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the login screen.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
