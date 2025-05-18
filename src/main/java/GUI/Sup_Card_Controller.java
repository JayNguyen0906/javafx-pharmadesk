package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import BUS.Sup_BUS;
import DTO.Supplier;

import java.sql.SQLException;

public class Sup_Card_Controller {
    @FXML
    private FlowPane cardContainer;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;

    private Sup_BUS supBUS;
    private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
    private Supplier selectedSupplier;

    @FXML
    public void initialize() throws SQLException {
        supBUS = new Sup_BUS();
        loadSupplierData();

        // Button actions
        addButton.setOnAction(e -> handleAdd());
        searchButton.setOnAction(e -> handleSearch());
        refreshButton.setOnAction(e -> handleRefresh());
        editButton.setOnAction(e -> {
            if (selectedSupplier != null) handleEdit(selectedSupplier);
            else ShowDialog.showAlert("Vui lòng chọn nhà cung cấp để sửa.");
        });
        deleteButton.setOnAction(e -> {
            if (selectedSupplier != null) handleDelete(selectedSupplier);
            else ShowDialog.showAlert("Vui lòng chọn nhà cung cấp để xóa.");
        });
    }

    private void loadSupplierData() throws SQLException {
        supplierList = FXCollections.observableArrayList(supBUS.getAllSuppliers());
        cardContainer.getChildren().clear();
        for (Supplier s : supplierList) {
            cardContainer.getChildren().add(createSupplierCard(s));
        }
    }

    private VBox createSupplierCard(Supplier supplier) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);

        ImageView image = new ImageView(new Image("/img/profile.png"));
        image.setFitWidth(100);
        image.setFitHeight(100);

        Label name = new Label(supplier.getName_Sup());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        Label phone = new Label("Điện thoại: " + supplier.getPhone_Sup());
        Label email = new Label("Email: " + supplier.getEmail_Sup());

        card.getChildren().addAll(image, name, phone, email);

        card.getStyleClass().add("card");

        card.setOnMouseClicked(e -> {
            if (selectedSupplier != supplier) {
                selectedSupplier = supplier; 
                
                for (Node node : cardContainer.getChildren()) {
                    node.getStyleClass().remove("selected");
                }

                card.getStyleClass().add("selected");
            }
        });

        card.setOnMouseEntered(e -> {
            if (!card.getStyleClass().contains("selected")) {
                card.getStyleClass().add("hover"); 
            }
        });

        card.setOnMouseExited(e -> {
            if (!card.getStyleClass().contains("selected")) {
                card.getStyleClass().remove("hover");
            }
        });

        return card;
    }


    private void handleAdd() {
        Dialog<Supplier> dialog = createSupplierDialog(null, false);
        dialog.showAndWait().ifPresent(newSupplier -> {
            if (newSupplier.getID_Sup().isEmpty() || newSupplier.getName_Sup().isEmpty()
                || newSupplier.getPhone_Sup().isEmpty() || newSupplier.getEmail_Sup().isEmpty()) {
                ShowDialog.showAlert("Vui lòng nhập đầy đủ thông tin nhà cung cấp!");
                return;
            }

            try {
                if (supBUS.addSupplier(newSupplier)) {
                    loadSupplierData();
                    ShowDialog.showAlert("Thêm nhà cung cấp thành công!");
                } else {
                    ShowDialog.showAlert("Nhà cung cấp đã tồn tại hoặc thêm thất bại!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi cơ sở dữ liệu khi thêm: " + e.getMessage());
            }
        });
    }

    private void handleEdit(Supplier supplier) {
        Dialog<Supplier> dialog = createSupplierDialog(supplier, true);
        dialog.showAndWait().ifPresent(updatedSupplier -> {
            try {
                if (supBUS.updateSupplier(updatedSupplier)) {
                    loadSupplierData();
                    ShowDialog.showAlert("Cập nhật nhà cung cấp thành công!");
                } else {
                    ShowDialog.showAlert("Cập nhật thất bại!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi cơ sở dữ liệu khi cập nhật: " + e.getMessage());
            }
        });
    }

    private void handleDelete(Supplier supplier) {
        if (ShowDialog.showConfirmation("Bạn có chắc muốn xóa nhà cung cấp này không?")) {
            try {
                if (supBUS.deleteSupplier(supplier.getID_Sup())) {
                    loadSupplierData();
                    ShowDialog.showAlert("Xóa nhà cung cấp thành công!");
                }
            } catch (SQLException e) {
                ShowDialog.showAlert("Lỗi cơ sở dữ liệu khi xóa: " + e.getMessage());
            }
        }
    }

    private void handleSearch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tìm kiếm nhà cung cấp");
        dialog.setHeaderText("Nhập thông tin để tìm kiếm");
        dialog.setContentText("Nhập ID, tên, số điện thoại hoặc email:");

        dialog.showAndWait().ifPresent(keyword -> {
            String lowerKeyword = keyword.trim().toLowerCase();
            ObservableList<Supplier> filtered = FXCollections.observableArrayList();

            for (Supplier s : supplierList) {
                if (s.getID_Sup().toLowerCase().contains(lowerKeyword)
                    || s.getName_Sup().toLowerCase().contains(lowerKeyword)
                    || s.getPhone_Sup().toLowerCase().contains(lowerKeyword)
                    || s.getEmail_Sup().toLowerCase().contains(lowerKeyword)) {
                    filtered.add(s);
                }
            }

            cardContainer.getChildren().clear();
            if (filtered.isEmpty()) {
                ShowDialog.showAlert("Không tìm thấy nhà cung cấp phù hợp!");
            } else {
                for (Supplier s : filtered) {
                    cardContainer.getChildren().add(createSupplierCard(s));
                }
            }
        });
    }

    private void handleRefresh() {
        try {
            loadSupplierData();
            ShowDialog.showAlert("Đã làm mới danh sách nhà cung cấp.");
        } catch (SQLException e) {
            ShowDialog.showAlert("Lỗi khi làm mới: " + e.getMessage());
        }
    }

    private Dialog<Supplier> createSupplierDialog(Supplier existing, boolean isEdit) {
        Dialog<Supplier> dialog = new Dialog<>();
        
        String iconPath = isEdit ? "/img/edit_icon.png" : "/img/add_icon.png";
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResource(iconPath).toExternalForm()));
		
        dialog.setTitle(isEdit ? "Sửa nhà cung cấp" : "Thêm nhà cung cấp");
        dialog.setHeaderText(isEdit ? "Chỉnh sửa thông tin nhà cung cấp" : "Nhập thông tin nhà cung cấp");

        ButtonType actionType = new ButtonType(isEdit ? "Lưu" : "Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(actionType, ButtonType.CANCEL);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        
        idField.setPrefWidth(325);

        idField.setPromptText("Mã nhà cung cấp");
        nameField.setPromptText("Tên");
        phoneField.setPromptText("Số điện thoại");
        emailField.setPromptText("Email");

        if (isEdit && existing != null) {
            idField.setText(existing.getID_Sup());
            nameField.setText(existing.getName_Sup());
            phoneField.setText(existing.getPhone_Sup());
            emailField.setText(existing.getEmail_Sup());
            idField.setEditable(false);
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Mã:"), idField);
        grid.addRow(1, new Label("Tên:"), nameField);
        grid.addRow(2, new Label("Số điện thoại:"), phoneField);
        grid.addRow(3, new Label("Email:"), emailField);

        grid.setPrefWidth(400);
		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().setPrefWidth(450);

        dialog.setResultConverter(button -> {
            if (button == actionType) {
                if (!phoneField.getText().matches("0\\d{9}")) {
                    ShowDialog.showAlert("Số điện thoại không hợp lệ (phải gồm 10 chữ số bắt đầu bằng 0)!");
                    return null;
                }
                return new Supplier(idField.getText().trim(), nameField.getText().trim(),
                        phoneField.getText().trim(), emailField.getText().trim());
            }
            return null;
        });

        return dialog;
    }

}
