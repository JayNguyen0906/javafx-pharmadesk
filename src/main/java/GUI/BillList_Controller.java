package GUI;

import BUS.Bill_BUS;
import DAO.Bill_DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import DTO.Bill;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class BillList_Controller {

    @FXML private TableView<Bill> tableBills;
    @FXML private TableColumn<Bill, String> colBillId;
    @FXML private TableColumn<Bill, String> colCustomerName;
    @FXML private TableColumn<Bill, LocalDate> colDate;
    @FXML private TableColumn<Bill, Double> colTotal;
    @FXML private TableColumn<Bill, Void> colDetail;
    @FXML
    private TableColumn<Bill, Void> colDelete;


    private ObservableList<Bill> billList = FXCollections.observableArrayList();

    private Bill_BUS billBus = new Bill_BUS();

    public BillList_Controller() throws SQLException {
    }


    @FXML
    public void initialize() {
        colBillId.setCellValueFactory(new PropertyValueFactory<>("ID_Bill"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("ID_Cus"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));

        addDetailButtonToTable();
        addDeleteButtonToTable();
        try {
            billBus = new Bill_BUS();
            loadBills();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            billList.addAll(billBus.getAllBills()); // Lấy hóa đơn từ database
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Xử lý lỗi, ví dụ: hiển thị thông báo lỗi cho người dùng
        }
        tableBills.setItems(billList);
    }
    private void loadBills() {
        try {
            billList.clear();
            billList.addAll(billBus.getAllBills());
            tableBills.setItems(billList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Đây là phương thức bạn đang gọi trong openBillDetail
    private void reloadBillList() {
        loadBills();
    }


    private void addDetailButtonToTable() {
        colDetail.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Chi tiết");

            {
                btn.setOnAction(e -> {
                    Bill selectedBill = getTableView().getItems().get(getIndex());
                    openBillDetail(selectedBill);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }
    private void addDeleteButtonToTable() {
        Callback<TableColumn<Bill, Void>, TableCell<Bill, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Bill, Void> call(final TableColumn<Bill, Void> param) {
                final TableCell<Bill, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Xóa");

                    {
                        btn.setOnAction(event -> {
                            Bill bill = getTableView().getItems().get(getIndex());
                            try {
                                Bill_DAO billDao = new Bill_DAO();
                                boolean success = billDao.deleteBill(bill.getID_Bill());
                                if (success) {
                                    getTableView().getItems().remove(bill);
                                    showAlert(Alert.AlertType.INFORMATION, "Xóa thành công", "Hóa đơn đã được xóa.");
                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Lỗi xóa", "Không thể xóa hóa đơn.");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi xóa hóa đơn.");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colDelete.setCellFactory(cellFactory);
    }

    private void openBillDetail(Bill bill) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Bill.fxml"));
            BorderPane billRoot = loader.load();

            // Truyền dữ liệu nếu cần
            Bill_Controller controller = loader.getController();
            controller.setBillData(bill); // bạn phải tạo method này trong BillController

            Stage stage = new Stage();
            stage.setTitle("Chi tiết hóa đơn " + bill.getID_Bill());
            stage.setScene(new Scene(billRoot));

            stage.setOnHidden(event -> reloadBillList());
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
