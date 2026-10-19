package DAO;

import DTO.ProductInBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDetail_DAO {
    private Connection conn;
    public BillDetail_DAO() throws SQLException {
        this.conn = DB_CONECTION.getConnection(); 
    }

    public List<ProductInBill> getProductsByBillId(int billId) throws SQLException {
        List<ProductInBill> products = new ArrayList<>();
        String sql = "SELECT ID_Item,Name_Item, Quantity, UnitPrice FROM BillDetail WHERE ID_Bill = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ID_Item");
                String nameProductId = rs.getString("Name_Item");
                int quantity = rs.getInt("Quantity");
                double unitPrice = rs.getDouble("UnitPrice");

                ProductInBill p = new ProductInBill(productId, nameProductId, quantity, unitPrice);
                products.add(p);
            }
        }
        return products;
    }

    public void insertProductInBill(int billId, List<ProductInBill> products) throws SQLException {
        String sql = "INSERT INTO BillDetail (ID_Bill, ID_Item, Name_Item, Quantity, UnitPrice) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ProductInBill p : products) {
                ps.setInt(1, billId);
                ps.setInt(2, p.getProductId());
                ps.setString(3, p.getProductName());
                ps.setInt(4, p.getQuantity());
                ps.setDouble(5, p.getUnitPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public boolean deleteByBillId(int billId) throws SQLException {
        String sql = "DELETE FROM BillDetail WHERE ID_Bill = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
}