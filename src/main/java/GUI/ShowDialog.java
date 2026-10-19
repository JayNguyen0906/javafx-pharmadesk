package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ShowDialog {

    public static void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Segoe UI';"
        );

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(ShowDialog.class.getResource("/img/info_icon.png").toExternalForm()));
        alert.showAndWait();
    }

    public static boolean showConfirmation(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-family: 'Segoe UI';"
        );

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(ShowDialog.class.getResource("/img/question_icon.png").toExternalForm()));

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}
