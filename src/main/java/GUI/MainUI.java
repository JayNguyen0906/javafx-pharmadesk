package GUI;

import DAO.Cus_DAO;
import DAO.Good_DAO;
import DAO.Sup_DAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainUI extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1200, 800); // hoặc tùy chỉnh kích thước
            stage.setTitle("Pharmacy Management System");
            stage.setScene(scene);
            stage.show();

            // Load database sau khi UI đã show
            loadInitialData();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Error loading MainUI.fxml: " + e.getMessage());
        }
    }

    private void loadInitialData() {
        try {
            Cus_DAO cusDao = new Cus_DAO();
            cusDao.getAllCustomer();

            Good_DAO goodDao = new Good_DAO();
            goodDao.getAllGood();

            Sup_DAO supDao = new Sup_DAO();
            supDao.getAllSup();

            System.out.println("✅ Loaded all initial data successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load initial data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
