package GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class MainGUI_Controller {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private void handleGoods(ActionEvent event) {
        System.out.println("Goods button clicked");
        progressIndicator.setVisible(true); // Hiển thị ProgressIndicator

        new Thread(() -> {
            try {
                Thread.sleep(1500); // Giả lập delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                try {
                    // Tải màn hình mới
                    Parent newView = FXMLLoader.load(getClass().getResource("/fxml/Good.fxml"));
                    Scene newScene = new Scene(newView);

                    // Lấy Stage từ event và chuyển scene
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(newScene);
                    stage.setTitle("Pharma DeskApp - New Scene"); // Tùy chỉnh tên tiêu đề
                    stage.show();

                    progressIndicator.setVisible(false); // Ẩn ProgressIndicator sau khi hoàn thành
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @FXML
    private void handleCustomers(ActionEvent event) {
        System.out.println("Customers button clicked");
        progressIndicator.setVisible(true); // Hiển thị ProgressIndicator

        new Thread(() -> {
            try {
                Thread.sleep(1500); // Giả lập delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                try {
                    // Tải màn hình mới
                    Parent newView = FXMLLoader.load(getClass().getResource("/fxml/Customer.fxml"));
                    Scene newScene = new Scene(newView);

                    // Lấy Stage từ event và chuyển scene
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(newScene);
                    stage.setTitle("Pharma DeskApp - New Scene"); // Tùy chỉnh tên tiêu đề
                    stage.show();

                    progressIndicator.setVisible(false); // Ẩn ProgressIndicator sau khi hoàn thành
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @FXML
    private void handleBill(ActionEvent event) {
        System.out.println("Bill button clicked");

    }

    @FXML
    private void handleEmployee(ActionEvent event) {
        System.out.println("Employee button clicked");
        progressIndicator.setVisible(true); // Hiển thị ProgressIndicator

        new Thread(() -> {
            try {
                Thread.sleep(1500); // Giả lập delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                try {
                    // Tải màn hình mới
                    Parent newView = FXMLLoader.load(getClass().getResource("/fxml/Employee.fxml"));
                    Scene newScene = new Scene(newView);

                    // Lấy Stage từ event và chuyển scene
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(newScene);
                    stage.setTitle("Pharma DeskApp - New Scene"); // Tùy chỉnh tên tiêu đề
                    stage.show();

                    progressIndicator.setVisible(false); // Ẩn ProgressIndicator sau khi hoàn thành
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    @FXML
    private void handleSupplier(ActionEvent event) {
        System.out.println("Supplier button clicked");

    }

    @FXML
    private void handleWarehouse(ActionEvent event) {
        System.out.println("Warehouse button clicked");
    }
}
