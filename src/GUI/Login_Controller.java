package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
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

    public Login_Controller() {}

    @FXML
    public void initialize() {
        // Khởi tạo nếu cần
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // Giả lập kiểm tra tài khoản
        if (user.equals("admin") && pass.equals("123")) {
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
            }
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu!");
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
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
    }

}
