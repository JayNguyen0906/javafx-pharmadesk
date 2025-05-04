package main.java.DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DTO.Warehouse;

public class Warehouse_DAO {

	public List<Warehouse> getAllWarehouse() throws SQLException {
		List<Warehouse> warehouse_list = new ArrayList<>();
		String query = "SELECT i.Items_Name AS ItemName, s.Supplier_Name AS SupplierName, w.CurrentQuantity, w.Expiry, w.PrimeCost "
				+ "FROM Warehouse w "
				+ "JOIN Items i ON w.Items_ID = i.Items_ID "
				+ "JOIN Supplier s ON w.Supplier_ID = s.Supplier_ID";

		try (Connection conn = DB_CONECTION.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Warehouse Warehouse = new Warehouse(rs.getString("itemName"), rs.getString("supplierName"),
						rs.getInt("currentQuantity"), rs.getDate("expiry"), rs.getDouble("primeCost"));
				warehouse_list.add(Warehouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return warehouse_list;
	}

	public boolean addWarehouse(Warehouse warehouse) {
	    String getItemIdQuery = "SELECT Items_ID FROM Items WHERE Items_Name = ?";
	    String getSupplierIdQuery = "SELECT Supplier_ID FROM Supplier WHERE Supplier_Name = ?";
	    String insertQuery = "INSERT INTO Warehouse (Items_ID, Supplier_ID, CurrentQuantity, Expiry, PrimeCost) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = DB_CONECTION.getConnection()) {
	        String itemId = null, supplierId = null;

	        // Lấy Item_ID từ tên
	        try (PreparedStatement stmt = conn.prepareStatement(getItemIdQuery)) {
	            stmt.setString(1, warehouse.getItemName());
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                itemId = rs.getString("Items_ID");
	            } else {
	                return false; // Không tìm thấy item
	            }
	        }

	        // Lấy Supplier_ID từ tên
	        try (PreparedStatement stmt = conn.prepareStatement(getSupplierIdQuery)) {
	            stmt.setString(1, warehouse.getSupplierName());
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                supplierId = rs.getString("Supplier_ID");
	            } else {
	                return false; // Không tìm thấy supplier
	            }
	        }

	        // Thêm vào Warehouse
	        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
	            pstmt.setString(1, itemId);
	            pstmt.setString(2, supplierId);
	            pstmt.setInt(3, warehouse.getCurrentQuantity());
	            pstmt.setDate(4, new java.sql.Date(warehouse.getExpiry().getTime()));
	            pstmt.setDouble(5, warehouse.getPrimeCost());
	            return pstmt.executeUpdate() > 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}


	public boolean updateWarehouse(Warehouse warehouse) {
	    String getItemIdQuery = "SELECT Items_ID FROM Items WHERE Items_Name = ?";
	    String getSupplierIdQuery = "SELECT Supplier_ID FROM Supplier WHERE Supplier_Name = ?";
	    String updateQuery = "UPDATE Warehouse SET CurrentQuantity = ?, Expiry = ?, PrimeCost = ? "
	                       + "WHERE Items_ID = ? AND Supplier_ID = ?";

	    try (Connection conn = DB_CONECTION.getConnection()) {
	        String itemId = null, supplierId = null;

	        try (PreparedStatement stmt = conn.prepareStatement(getItemIdQuery)) {
	            stmt.setString(1, warehouse.getItemName());
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) itemId = rs.getString("Items_ID");
	            else return false;
	        }

	        try (PreparedStatement stmt = conn.prepareStatement(getSupplierIdQuery)) {
	            stmt.setString(1, warehouse.getSupplierName());
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) supplierId = rs.getString("Supplier_ID");
	            else return false;
	        }

	        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
	            pstmt.setInt(1, warehouse.getCurrentQuantity());
	            pstmt.setDate(2, new java.sql.Date(warehouse.getExpiry().getTime()));
	            pstmt.setDouble(3, warehouse.getPrimeCost());
	            pstmt.setString(4, itemId);
	            pstmt.setString(5, supplierId);
	            return pstmt.executeUpdate() > 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}


	public boolean deleteWarehouse(String itemName, String supplierName) {
	    String query = "DELETE FROM Warehouse WHERE Items_ID = (SELECT Items_ID FROM Items WHERE Items_Name = ?) "
	                 + "AND Supplier_ID = (SELECT Supplier_ID FROM Supplier WHERE Supplier_Name = ?)";

	    try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, itemName);
	        pstmt.setString(2, supplierName);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public static Warehouse getWarehouseByName(String itemName, String supplierName) {
	    String query = "SELECT w.CurrentQuantity, w.Expiry, w.PrimeCost FROM Warehouse w "
	                 + "JOIN Items i ON w.Items_ID = i.Items_ID "
	                 + "JOIN Supplier s ON w.Supplier_ID = s.Supplier_ID "
	                 + "WHERE i.Items_Name = ? AND s.Supplier_Name = ?";

	    try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, itemName);
	        pstmt.setString(2, supplierName);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return new Warehouse(itemName, supplierName,
	                    rs.getInt("CurrentQuantity"),
	                    rs.getDate("Expiry"),
	                    rs.getDouble("PrimeCost"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}


}
