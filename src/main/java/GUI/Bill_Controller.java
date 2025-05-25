package GUI;

import DAO.BillDetail_DAO;
import DAO.Bill_DAO;
import DAO.Item_DAO;
import DTO.Bill;
import DTO.Item;
import DTO.ProductInBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class Bill_Controller {

    @FXML private TextField txtBillId, txtCustomerName, txtTotal, txtGrandTotal, txtTax, txtQuantity;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Item> comboProductId;
    @FXML private TableView<ProductInBill> tableProducts;
    @FXML private TableColumn<ProductInBill, String> colProductId, colProductName;
    @FXML private TableColumn<ProductInBill, Integer> colQuantity;
    @FXML private TableColumn<ProductInBill, Double> colUnitPrice, colTotal;

    private boolean isEditMode = false;
    private Bill bill;

    private final ObservableList<ProductInBill> productList = FXCollections.observableArrayList();
    private final BillDetail_DAO billDetailDao = new BillDetail_DAO();
    private final Item_DAO ItemDAO = new Item_DAO();

    public Bill_Controller() throws SQLException {}

    public void setEditMode(boolean mode) {
        this.isEditMode = mode;
    }

    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(cell -> cell.getValue().productIdProperty());
        colProductName.setCellValueFactory(cell -> cell.getValue().productNameProperty());
        colQuantity.setCellValueFactory(cell -> cell.getValue().quantityProperty().asObject());
        colUnitPrice.setCellValueFactory(cell -> cell.getValue().unitPriceProperty().asObject());
        colTotal.setCellValueFactory(cell -> cell.getValue().totalProperty().asObject());

        tableProducts.setItems(productList);
        try {
            List<Item> Items = ItemDAO.getAllItem();
            comboProductId.setItems(FXCollections.observableArrayList(Items));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi khi tải danh sách sản phẩm từ cơ sở dữ liệu");
        }
    }

    @FXML
    private void handleAddProduct() throws SQLException {
        Item Item = comboProductId.getValue();
        if (Item == null) {
            showAlert("Vui lòng chọn sản phẩm!");
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(txtQuantity.getText());
        } catch (NumberFormatException e) {
            showAlert("Số lượng không hợp lệ!");
            return;
        }

        Item ItemDetail = ItemDAO.getItemById(Item.getId());
        if (ItemDetail == null) {
            showAlert("Không tìm thấy sản phẩm!");
            return;
        }

        for (ProductInBill p : productList) {
            if (p.getProductId().equals(Item.getId())) {
                int newQty = p.getQuantity() + qty;
                p.setQuantity(newQty);
                p.setTotal(newQty * p.getUnitPrice());
                tableProducts.refresh();
                updateTotal();
                return;
            }
        }

        ProductInBill item = new ProductInBill(
                ItemDetail.getId(),
                ItemDetail.getName(),
                qty,
                ItemDetail.getPrice()
        );
        productList.add(item);
        updateTotal();
    }

    private void updateTotal() {
        double total = productList.stream().mapToDouble(ProductInBill::getTotal).sum();
        txtTotal.setText(String.format("%.0f", total));

        try {
            double vat = Double.parseDouble(txtTax.getText());
            double grandTotal = total * (1 + vat / 100);
            txtGrandTotal.setText(String.format("%.2f", grandTotal));
        } catch (NumberFormatException e) {
            txtGrandTotal.setText(""); // nếu VAT chưa hợp lệ
        }
    }

    public void setBillData(Bill bill) {
        this.bill = bill;
        displayBillDetails();
    }

    private void displayBillDetails() {
        txtBillId.setText(bill.getID_Bill());
        txtCustomerName.setText(bill.getID_Cus());

        if (bill.getOrderDate() != null) {
            datePicker.setValue((bill.getOrderDate()));
        }

        try {
            List<ProductInBill> products = billDetailDao.getProductsByBillId(bill.getID_Bill());
            productList.setAll(products);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi khi tải chi tiết hóa đơn");
        }

        txtTotal.setText(String.valueOf(bill.getTotalAmount()));
        txtTax.setText("10");
        txtGrandTotal.setText(String.format("%.2f", bill.getTotalAmount() * 1.1));
    }

    @FXML
    private void handleConfirm() {
        try {
            Bill newBill = new Bill(
                    txtBillId.getText(),
                    "", // ID_Staff nếu có
                    txtCustomerName.getText(),
                    getTotalQuantity(),
                    Double.parseDouble(txtTotal.getText()),
                    datePicker.getValue(),
                    "Đã thanh toán"
            );

            Bill_DAO dao = new Bill_DAO();

            if (isEditMode) {
                dao.updateBill(newBill);
                billDetailDao.deleteByBillId(newBill.getID_Bill());
                billDetailDao.insertProductInBill(newBill.getID_Bill(), productList);
                showAlert("Cập nhật hóa đơn thành công!");
            } else {
                dao.insertOrUpdateBill(newBill);
                billDetailDao.insertProductInBill(newBill.getID_Bill(), productList);
                showAlert("Tạo hóa đơn thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi khi lưu hóa đơn!");
        }
    }

    private int getTotalQuantity() {
        return productList.stream().mapToInt(ProductInBill::getQuantity).sum();
    }

    @FXML
    private void handleCancel() {
        txtTax.setText("");
        txtGrandTotal.setText("");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}