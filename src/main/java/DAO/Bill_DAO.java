package DAO;

import DTO.Bill;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bill_DAO {

    private Connection conn;

    public Bill_DAO() throws SQLException {
        this.conn = DB_CONECTION.getConnection();
    }

    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();

        String query = "SELECT * FROM Bill";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idBill = rs.getInt("Bill_ID");
                int idCus = rs.getInt("Customer_ID");
                int quantity = rs.getInt("Quantity");
                double totalAmount = rs.getDouble("TotalAmount");
                LocalDate orderDate = rs.getDate("OrderDate").toLocalDate();
                String status = rs.getString("Status");

                Bill bill = new Bill(idBill, idCus, quantity, totalAmount, orderDate, status);
                bills.add(bill);
            }
        }

        return bills;
    }

    public Bill getBillById(String id) {
        String sql = "SELECT * FROM Bill WHERE Bill_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Bill(
                        rs.getInt("Bill_ID"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("Quantity"),
                        rs.getDouble("TotalAmount"),
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getString("Status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertOrUpdateBill(Bill bill) {
        String checkSql = "SELECT COUNT(*) FROM Bill WHERE Bill_ID = ?";
        String insertSql = "INSERT INTO Bill (Bill_ID, Customer_ID, Quantity, TotalAmount, OrderDate, Status) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE Bill SET Customer_ID = ?, Quantity = ?, TotalAmount = ?, OrderDate = ?, Status = ? WHERE Bill_ID = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, bill.getID_Bill());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // UPDATE
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, bill.getID_Cus());
                    updateStmt.setInt(2, bill.getQuantity());
                    updateStmt.setDouble(3, bill.getTotalAmount());
                    updateStmt.setDate(4, java.sql.Date.valueOf(bill.getOrderDate()));
                    updateStmt.setString(5, bill.getStatus());
                    updateStmt.setInt(6, bill.getID_Bill());
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                // INSERT
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, bill.getID_Bill());
                    insertStmt.setInt(2, bill.getID_Cus());
                    insertStmt.setInt(3, bill.getQuantity());
                    insertStmt.setDouble(4, bill.getTotalAmount());
                    insertStmt.setDate(5, java.sql.Date.valueOf(bill.getOrderDate()));
                    insertStmt.setString(6, bill.getStatus());
                    return insertStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBill(int idBill) throws SQLException {
        String sql = "DELETE FROM Bill WHERE Bill_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBill);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public int insertBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO Bill (Customer_ID, Quantity, TotalAmount, OrderDate, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bill.getID_Cus());
            ps.setInt(2, bill.getQuantity());
            ps.setDouble(3, bill.getTotalAmount());
            ps.setDate(4, java.sql.Date.valueOf(bill.getOrderDate()));
            ps.setString(5, bill.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Tạo hóa đơn thất bại, không có hàng nào bị ảnh hưởng.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Tạo hóa đơn thất bại, không lấy được ID.");
                }
            }
        }
    }
}
