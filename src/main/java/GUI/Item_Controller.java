package main.java.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.java.BUS.Item_BUS;
import main.java.DTO.Item;

import java.sql.SQLException;

public class Item_Controller {
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn;
    @FXML
    private TableColumn<Item, String> quantityColumn;
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

    private Item_BUS ItemBUS;
    private ObservableList<Item> ItemList;

    @FXML
    public void initialize() {
        ItemBUS = new Item_BUS();

        // Set up columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        // Load initial data
        try {
            loadItemData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set event handlers for buttons
        addButton.setOnAction(this::handleAdd);
        deleteButton.setOnAction(this::handleDelete);
        editButton.setOnAction(this::handleEdit);
        searchButton.setOnAction(this::handleSearch);
    }

    private void loadItemData() throws SQLException {
        ItemList = FXCollections.observableArrayList(Item_BUS.Item_dao.getAllItem());
        tableView.setItems(ItemList);
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

            Item newItem = new Item(id, name, price, quantity);

            if (ItemBUS.addItem(newItem)) {
                ItemList.add(newItem);
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
            Item selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (ItemBUS.deleteItem(selected.getId())) {
                    ItemList.remove(selected);
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
            Item selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setName(nameField.getText());
                selected.setPrice(Double.parseDouble(priceField.getText()));
                selected.setQuantity(quantityField.getText());

                ItemBUS.updateItem(selected);
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
            ObservableList<Item> filtered = FXCollections.observableArrayList();
            for (Item g : ItemList) {
                if (g.getName().toLowerCase().contains(keyword) || g.getId().toLowerCase().contains(keyword)) {
                    filtered.add(g);
                }
            }
            tableView.setItems(filtered);
        } else {
            tableView.setItems(ItemList);
        }
    }
}
