package main.java.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.DTO.Supplier;

public class Sup_DAO {
    public List<Supplier> getAllSup() throws SQLException{
        List<Supplier> supp_list = new ArrayList<>();
        String sql = "Select * FROM supplier";
        try (Connection con = DB_CONECTION.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             ){
            while (rs.next()){
                Supplier supplier = new Supplier();
                supplier.setID_Sup(rs.getString("Supplier_ID"));
                supplier.setName_Sup(rs.getString("Supplier_Name"));
                supplier.setPhone_Sup(rs.getString("Supplier_Phone"));
                supplier.setEmail_Sup(rs.getString("Supplier_Email"));
                supp_list.add(supplier);
            }
        }
        return supp_list;
    }
    
    public boolean addSupplier(Supplier supplier) {
    	String query = "INSERT INTO Supplier (Supplier_ID, Supplier_Name, Supplier_Phone, Supplier_Email) VALUES (?, ?, ?, ?)";
    	try (Connection conn = DB_CONECTION.getConnection();
    			PreparedStatement pstm = conn.prepareStatement(query)) {
    				pstm.setString(1, supplier.getID_Sup());
    				pstm.setString(2, supplier.getName_Sup());
    				pstm.setString(3, supplier.getPhone_Sup());
    				pstm.setString(4, supplier.getEmail_Sup());
    				
    				int rowsInserted = pstm.executeUpdate();
    				return rowsInserted > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    return false;
    }
    
    public boolean updateSupplier(Supplier supplier) {
    	String query = "UPDATE Supplier SET Supplier_Name = ?, Supplier_Phone = ?, Supplier_Email = ? WHERE Supplier_ID = ?";
    	try (Connection conn = DB_CONECTION.getConnection();
    			PreparedStatement pstm = conn.prepareStatement(query)) {
    				pstm.setString(1, supplier.getID_Sup());
    				pstm.setString(2, supplier.getName_Sup());
    				pstm.setString(3, supplier.getPhone_Sup());
    				pstm.setString(4, supplier.getEmail_Sup());
    				
    				int rowsAffected = pstm.executeUpdate();
    				return rowsAffected > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    return false;
    }
    
    public boolean deleteSupplier(String Supplier_ID) {
		String query = "DELETE FROM Suppliers WHERE Supplier_ID = ?";
		try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, Supplier_ID);
			int rowsDeleted = pstmt.executeUpdate();
			return rowsDeleted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    
    public static Supplier getSupplierById(String Supplier_ID) {
		String query = "SELECT * FROM Suppliers WHERE Supplier_ID = ?";
		try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, Supplier_ID);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Supplier(rs.getString("Supplier_ID"), rs.getString("Supplier_Name"), rs.getString("Supplier_Phone"),
							rs.getString("Supplier_Email"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}