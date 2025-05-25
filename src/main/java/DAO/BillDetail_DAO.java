package DAO;

import DTO.Item;
import DTO.ProductInBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDetail_DAO {
    private Connection conn;
    Item Item = new Item();
    public BillDetail_DAO() throws SQLException {
        this.conn = DB_CONECTION.getConnection(); // dùng chung kết nối DB
    }

    // Lấy danh sách sản phẩm trong hóa đơn theo ID_Bill
    public List<ProductInBill> getProductsByBillId(String billId) throws SQLException {
        List<ProductInBill> products = new ArrayList<>();
        String sql = "SELECT ID_Item,Name_Item, Quantity, UnitPrice FROM BillDetail WHERE ID_Bill = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("ID_Item");
                String nameProductId = rs.getString("Name_Item");
                int quantity = rs.getInt("Quantity");
                double unitPrice = rs.getDouble("UnitPrice");

                // Tên sản phẩm bạn có thể lấy từ bảng sản phẩm (Item) nếu cần (để hiển thị)
                ProductInBill p = new ProductInBill(productId, nameProductId, quantity, unitPrice);
                products.add(p);
            }
        }
        return products;
    }

    // Thêm 1 chi tiết hóa đơn (1 sản phẩm trong hóa đơn)
    public void insertProductInBill(String billId, List<ProductInBill> products) throws SQLException {
        String sql = "INSERT INTO BillDetail (ID_Bill, ID_Item,Name_Item, Quantity, UnitPrice) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ProductInBill p : products) {
                ps.setString(1, billId);
                ps.setString(2, p.getProductId());
                ps.setString(3, p.getProductName());
                ps.setInt(4, p.getQuantity());
                ps.setDouble(5, p.getUnitPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // Xóa tất cả chi tiết của 1 hóa đơn (để chuẩn bị cập nhật)
    public boolean deleteByBillId(String billId) throws SQLException {
        String sql = "DELETE FROM BillDetail WHERE ID_Bill = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, billId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
}