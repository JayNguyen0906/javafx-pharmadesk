package DAO;

import DTO.Bill;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bill_DAO {

    private Connection conn;

    public Bill_DAO() throws SQLException {
        this.conn = DB_CONECTION.getConnection(); // Sử dụng kết nối từ DB_Connection
    }

    // Lấy tất cả các hóa đơn
    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();

        String query = "SELECT * FROM Bill";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String idBill = rs.getString("ID_Bill");
                String idItem = rs.getString("ID_Item");
                String idCus = rs.getString("ID_Cus");
                int quantity = rs.getInt("Quantity");
                double totalAmount = rs.getDouble("TotalAmount");

                // Lấy OrderDate và chuyển đổi thành LocalDate
                LocalDate orderDate = rs.getDate("OrderDate").toLocalDate();

                String status = rs.getString("Status");

                Bill bill = new Bill(idBill, idItem, idCus, quantity, totalAmount, orderDate, status);
                bills.add(bill);
            }
        }

        return bills;
    }

    // Lấy hóa đơn theo ID
    public Bill getBillById(String id) {
        String sql = "SELECT * FROM Bill WHERE ID_Bill = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Bill(
                        rs.getString("ID_Bill"),
                        rs.getString("ID_Item"),
                        rs.getString("ID_Cus"),
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

    // Thêm hóa đơn mới vào cơ sở dữ liệu
    public boolean insertOrUpdateBill(Bill bill) {
        String checkSql = "SELECT COUNT(*) FROM Bill WHERE ID_Bill = ?";
        String insertSql = "INSERT INTO Bill (ID_Bill, ID_Item, ID_Cus, Quantity, TotalAmount, OrderDate, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE Bill SET ID_Item = ?, ID_Cus = ?, Quantity = ?, TotalAmount = ?, OrderDate = ?, Status = ? WHERE ID_Bill = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, bill.getID_Bill());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // UPDATE
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, bill.getID_Item());
                    updateStmt.setString(2, bill.getID_Cus());
                    updateStmt.setInt(3, bill.getQuantity());
                    updateStmt.setDouble(4, bill.getTotalAmount());
                    updateStmt.setDate(5, java.sql.Date.valueOf(bill.getOrderDate()));
                    updateStmt.setString(6, bill.getStatus());
                    updateStmt.setString(7, bill.getID_Bill());
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                // INSERT
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, bill.getID_Bill());
                    insertStmt.setString(2, bill.getID_Item());
                    insertStmt.setString(3, bill.getID_Cus());
                    insertStmt.setInt(4, bill.getQuantity());
                    insertStmt.setDouble(5, bill.getTotalAmount());
                    insertStmt.setDate(6, java.sql.Date.valueOf(bill.getOrderDate()));
                    insertStmt.setString(7, bill.getStatus());
                    return insertStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật hóa đơn trong cơ sở dữ liệu
    public boolean updateBill(Bill bill) throws SQLException {
        String sql = "UPDATE Bill SET ID_Item = ?, ID_Cus = ?, Quantity = ?, TotalAmount = ?, OrderDate = ?, Status = ? WHERE ID_Bill = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bill.getID_Item());
            ps.setString(2, bill.getID_Cus());
            ps.setInt(3, bill.getQuantity());
            ps.setDouble(4, bill.getTotalAmount());
            ps.setDate(5, java.sql.Date.valueOf(bill.getOrderDate()));
            ps.setString(6, bill.getStatus());
            ps.setString(7, bill.getID_Bill());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa hóa đơn khỏi cơ sở dữ liệu
    public boolean deleteBill(String idBill) throws SQLException {
        String sql = "DELETE FROM Bill WHERE ID_Bill = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idBill);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }


}