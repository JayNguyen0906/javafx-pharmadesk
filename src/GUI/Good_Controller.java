package GUI;

import BUS.Good_BUS;
import DTO.Good;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class Good_Controller {
    @FXML
    private TableView<Good> tableView;
    @FXML
    private TableColumn<Good, String> idColumn;
    @FXML
    private TableColumn<Good, String> nameColumn;
    @FXML
    private TableColumn<Good, Double> priceColumn;
    @FXML
    private TableColumn<Good, String> quantityColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;

    private Good_BUS goodBUS;
    private ObservableList<Good> goodList;

    @FXML
    public void initialize() {
        goodBUS = new Good_BUS();

        // Set up columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        // Load initial data
        try {
            loadGoodData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set event handlers for buttons
        addButton.setOnAction(this::handleAdd);
        deleteButton.setOnAction(this::handleDelete);
        editButton.setOnAction(this::handleEdit);
        searchButton.setOnAction(this::handleSearch);
    }

    private void loadGoodData() throws SQLException {
        goodList = FXCollections.observableArrayList(Good_BUS.good_dao.getAllGood());
        tableView.setItems(goodList);
    }

    private void handleAdd(ActionEvent event) {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String quantity = quantityField.getText();

            if (id.isEmpty() || name.isEmpty() || price <= 0 || quantity.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }

            Good newGood = new Good(id, name, price, quantity);

            if (goodBUS.addGood(newGood)) {
                goodList.add(newGood);
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Product already exists or error adding.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
        }
    }

    private void handleDelete(ActionEvent event) {
        try {
            Good selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (goodBUS.deleteGood(selected.getId())) {
                    goodList.remove(selected);
                    System.out.println("Product deleted successfully.");
                }
            } else {
                System.out.println("Please select a product to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleEdit(ActionEvent event) {
        try {
            Good selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setName(nameField.getText());
                selected.setPrice(Double.parseDouble(priceField.getText()));
                selected.setQuantity(quantityField.getText());

                goodBUS.updateGood(selected);
                tableView.refresh();
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Please select a product to edit.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
        }
    }

    private void handleSearch(ActionEvent event) {
        String keyword = nameField.getText().trim().toLowerCase();
        if (!keyword.isEmpty()) {
            ObservableList<Good> filtered = FXCollections.observableArrayList();
            for (Good g : goodList) {
                if (g.getName().toLowerCase().contains(keyword) || g.getId().toLowerCase().contains(keyword)) {
                    filtered.add(g);
                }
            }
            tableView.setItems(filtered);
        } else {
            tableView.setItems(goodList);
        }
    }
}
