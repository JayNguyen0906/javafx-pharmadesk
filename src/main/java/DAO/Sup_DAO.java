package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Supplier;

public class Sup_DAO {
    public List<Supplier> getAllSup() throws SQLException {
        List<Supplier> supp_list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";  // Chú ý tên bảng chính xác
        try (Connection con = DB_CONECTION.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setID_Sup(rs.getInt("Supplier_ID"));  // int
                supplier.setName_Sup(rs.getString("Supplier_Name"));
                supplier.setPhone_Sup(rs.getString("Supplier_Phone"));
                supplier.setEmail_Sup(rs.getString("Supplier_Email"));
                supp_list.add(supplier);
            }
        }
        return supp_list;
    }

    public boolean addSupplier(Supplier supplier) {
        String query = "INSERT INTO Supplier (Supplier_Name, Supplier_Phone, Supplier_Email) VALUES (?, ?, ?)";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, supplier.getName_Sup());
            pstm.setString(2, supplier.getPhone_Sup());
            pstm.setString(3, supplier.getEmail_Sup());

            int rowsInserted = pstm.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        supplier.setID_Sup(generatedKeys.getInt(1));  // Lấy ID tự sinh
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSupplier(Supplier supplier) {
        String query = "UPDATE Supplier SET Supplier_Name = ?, Supplier_Phone = ?, Supplier_Email = ? WHERE Supplier_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {
            pstm.setString(1, supplier.getName_Sup());
            pstm.setString(2, supplier.getPhone_Sup());
            pstm.setString(3, supplier.getEmail_Sup());
            pstm.setInt(4, supplier.getID_Sup());

            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSupplier(int supplier_ID) {
        String query = "DELETE FROM Supplier WHERE Supplier_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, supplier_ID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Supplier getSupplierById(int supplier_ID) {
        String query = "SELECT * FROM Supplier WHERE Supplier_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, supplier_ID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                        rs.getInt("Supplier_ID"),
                        rs.getString("Supplier_Name"),
                        rs.getString("Supplier_Phone"),
                        rs.getString("Supplier_Email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
