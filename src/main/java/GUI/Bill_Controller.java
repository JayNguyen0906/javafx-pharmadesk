package GUI;

import DAO.BillDetail_DAO;
import DAO.Bill_DAO;
import DAO.Item_DAO;
import DAO.SuggestionDAO;
import DAO.Warehouse_DAO;
import DTO.Bill;
import DTO.Customer;
import DTO.Item;
import DTO.ProductInBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import BUS.Cus_BUS;

public class Bill_Controller {

    @FXML private TextField txtBillId, txtPhoneCustomer, txtCustomerName, txtProductName, txtTotal, txtGrandTotal, txtTax, txtQuantity;
    @FXML private DatePicker datePicker;
    @FXML private TableView<ProductInBill> tableProducts;
    @FXML private TableColumn<ProductInBill, String> colProductName;
    @FXML private TableColumn<ProductInBill, Integer> colQuantity, colProductId;
    @FXML private TableColumn<ProductInBill, Double> colUnitPrice, colTotal;
    @FXML private Button btnSave, btnCancel, btnPrint;

    private boolean isEditMode = false;
    private Bill bill;

    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private final ObservableList<ProductInBill> productList = FXCollections.observableArrayList();
    private final BillDetail_DAO billDetailDao = new BillDetail_DAO();

    public Bill_Controller() throws SQLException {}

    public void setEditMode(boolean mode) {
        this.isEditMode = mode;
    }

    @FXML
    public void initialize() {
    	txtBillId.setDisable(true);
    	    
        colProductId.setCellValueFactory(cell -> cell.getValue().productIdProperty().asObject());
        colProductName.setCellValueFactory(cell -> cell.getValue().productNameProperty());
        colQuantity.setCellValueFactory(cell -> cell.getValue().quantityProperty().asObject());
        colUnitPrice.setCellValueFactory(cell -> cell.getValue().unitPriceProperty().asObject());
        colTotal.setCellValueFactory(cell -> cell.getValue().totalProperty().asObject());

        tableProducts.setItems(productList);

        TextFields.bindAutoCompletion(txtProductName, SuggestionDAO.getItemSuggestions())
        .setOnAutoCompleted(event -> {
            Item selectedItem = event.getCompletion();
            txtProductName.setText(selectedItem.getName());   
            txtProductName.setUserData(selectedItem.getId());
        });

        
        try {
            customerList.addAll(new Cus_BUS().getAllCustomers());
        } catch (SQLException e) {
            ShowDialog.showAlert("Lỗi khi lấy thông tin khách hàng cũ: " + e.getMessage());
        }

        TextFields.bindAutoCompletion(txtCustomerName, SuggestionDAO.getCustomerSuggestions())
        .setOnAutoCompleted(event -> {
            String selected = event.getCompletion();
            String phone = selected.substring(selected.lastIndexOf("-") + 1).trim();

            for (Customer c : customerList) {
                if (c.getPhone_Cus().equals(phone)) {
                	 txtCustomerName.setText(c.getName_Cus());
                	 txtPhoneCustomer.setText(c.getPhone_Cus());
                     txtCustomerName.setUserData(c.getId_Cus()); 
                    break;
                }
            }
        });
        
        datePicker.setValue(java.time.LocalDate.now());
        
        btnCancel.setOnAction(e -> handleCancel());
  //      btnPrint.setOnAction(e -> handlePrint());
        btnSave.setOnAction(e -> handleSave());
        
        txtTax.setText("5"); 
        txtTax.setDisable(true); 


    }

    @FXML
    private void handleAddProduct() throws SQLException {
    	String productName = txtProductName.getText().trim();
    	 Object userData = txtProductName.getUserData();
    	    if (userData == null) {
            ShowDialog.showAlert("Vui lòng nhập tên sản phẩm!");
            return;
        }

    	    int id = (int) userData;
    	    
        Item item = Item_DAO.getItemById(id);
        if (item == null) {
            ShowDialog.showAlert("Không tìm thấy sản phẩm với tên: " + productName);
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(txtQuantity.getText().trim());
        } catch (NumberFormatException e) {
            ShowDialog.showAlert("Số lượng không hợp lệ!");
            return;
        }

        for (ProductInBill p : productList) {
            if (p.getProductId() == item.getId()) {
                int newQty = p.getQuantity() + qty;
                p.setQuantity(newQty);
                p.setTotal(newQty * p.getUnitPrice());
                tableProducts.refresh();
                updateTotal();
                return;
            }
        }

        ProductInBill pib = new ProductInBill(
                item.getId(),
                item.getName(),
                qty,
                item.getPrice()
        );
        productList.add(pib);
        updateTotal();
        
        txtProductName.clear();
        txtQuantity.clear();
        txtProductName.setUserData(null);

    }

    private void updateTotal() {
        double total = productList.stream().mapToDouble(ProductInBill::getTotal).sum();
        txtTotal.setText(String.format("%.0f", total));

        try {
            double vat = Double.parseDouble(txtTax.getText());
            double grandTotal = total * (1 + vat / 100);
            txtGrandTotal.setText(String.format("%.0f", grandTotal));
        } catch (NumberFormatException e) {
            txtGrandTotal.setText(""); 
        }
    }

    public void setBillData(Bill bill) {
        this.bill = bill;
        displayBillDetails();
    }

    private void displayBillDetails() {
        txtBillId.setText(String.valueOf(bill.getID_Bill()));

        int idCus = bill.getID_Cus();
        Customer c = customerList.stream()
            .filter(x -> x.getId_Cus() == idCus)
            .findFirst()
            .orElse(null);
        if (c != null) {
            txtCustomerName.setText(c.getName_Cus());
            txtPhoneCustomer.setText(c.getPhone_Cus());
        }

        if (bill.getOrderDate() != null) {
            datePicker.setValue((bill.getOrderDate()));
        }

        try {
            List<ProductInBill> products = billDetailDao.getProductsByBillId(bill.getID_Bill());
            productList.setAll(products);
        } catch (SQLException e) {
            e.printStackTrace();
            ShowDialog.showAlert("Lỗi khi tải chi tiết hóa đơn");
        }

        txtTotal.setText(String.valueOf(bill.getTotalAmount()));
        txtTax.setText("10");
        txtGrandTotal.setText(String.format("%.2f", bill.getTotalAmount() * 1.1));
    }
    
    @FXML
    private void handleSave() {
        try {
            Object userData = txtCustomerName.getUserData();

            if (userData == null || productList.isEmpty()) {
                ShowDialog.showAlert("Vui lòng chọn khách hàng và thêm ít nhất một sản phẩm!");
                return;
            }

            for (ProductInBill p : productList) {
                Item item = Item_DAO.getItemById(p.getProductId());
                if (item == null) {
                    ShowDialog.showAlert("Sản phẩm với ID " + p.getProductId() + " không tồn tại!");
                    return;
                }
            }

            int customerId = (int) userData;

            Bill newBill = new Bill(
                0,
                customerId,
                getTotalQuantity(),
                Double.parseDouble(txtTotal.getText()),
                datePicker.getValue(),
                "Đã thanh toán"
            );

            Bill_DAO dao = new Bill_DAO();

            if (isEditMode) {
                dao.insertOrUpdateBill(newBill);
                billDetailDao.deleteByBillId(newBill.getID_Bill());
                billDetailDao.insertProductInBill(newBill.getID_Bill(), productList);
                ShowDialog.showAlert("Cập nhật hóa đơn thành công!");
            } else {
                int newBillId = dao.insertBill(newBill);
                if (newBillId > 0) {
                    newBill.setID_Bill(newBillId);
                    txtBillId.setText(String.valueOf(newBillId));
                    bill = newBill;
                    billDetailDao.insertProductInBill(newBillId, productList);
                    
                    boolean allUpdated = true;

                    for (ProductInBill p : productList) {
                        boolean updated = Warehouse_DAO.updateQuantityAfterSale(p.getProductId(), p.getQuantity());
                        if (!updated) {
                            allUpdated = false;
                            System.err.println("Không thể cập nhật số lượng cho sản phẩm ID: " + p.getProductId());
                        }
                    }

                    if (!allUpdated) {
                        ShowDialog.showAlert("Một số sản phẩm không đủ tồn kho để trừ số lượng!");
                    } else {
                        ShowDialog.showAlert("Tạo hóa đơn thành công!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            ShowDialog.showAlert("Lỗi khi lưu hóa đơn!");
        }
    }


    private int getTotalQuantity() {
        return productList.stream().mapToInt(ProductInBill::getQuantity).sum();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) txtTax.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handlePrintBill() {
        try {
        	if (bill == null || bill.getID_Bill() == 0) {
                ShowDialog.showAlert("Vui lòng lưu hóa đơn trước khi in!");
                return;
            }
        	
        	 List<ProductInBill> products = billDetailDao.getProductsByBillId(bill.getID_Bill());
             if (products.isEmpty()) {
                 ShowDialog.showAlert("Hóa đơn không có sản phẩm nào để in!");
                 return;
             }

            String fileName = "HoaDon_" + bill.getID_Bill() + ".pdf";

            ProcessPrint processPrint = new ProcessPrint();
            boolean success = processPrint.exportBillPDF(fileName, bill);
            if (success) {
                processPrint.openPDF(fileName);
            } else {
                ShowDialog.showAlert("Lỗi: Tạo file PDF thất bại, không gửi đến máy in.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ShowDialog.showAlert("Lỗi khi in hóa đơn: " + ex.getMessage());
        }
    }

    public void prefillProducts(List<ProductInBill> incomingProducts) {
        productList.clear();  
        productList.addAll(incomingProducts);
        updateTotal(); 
    }
    
    public void setCustomerData(Customer customer) {
        if (customer != null) {
            txtCustomerName.setText(customer.getName_Cus());
            txtPhoneCustomer.setText(customer.getPhone_Cus());
        }
    }


}