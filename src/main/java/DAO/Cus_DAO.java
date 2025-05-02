package main.java.DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.DTO.Customer;

public class Cus_DAO {

	public List<Customer> getAllCustomer() throws SQLException {
		List<Customer> cus_list = new ArrayList<>();
		String query = "Select * from Customers";

		try (Connection conn = DB_CONECTION.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Customer customer = new Customer(rs.getString("Customer_ID"), rs.getString("Name"),
						rs.getString("Phone"), rs.getString("Address"));
				cus_list.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cus_list;
	}

	public boolean addCustomer(Customer customer) {
		if (Cus_DAO.getCustomerById(customer.getId_Cus()) != null) {
			return false;
		} else {
			String query = "INSERT INTO Customers (Customer_ID, Name, Phone, Address) VALUES (?, ?, ?, ?)";
			try (Connection conn = DB_CONECTION.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query)) {

				pstmt.setString(1, customer.getId_Cus());
				pstmt.setString(2, customer.getName_Cus());
				pstmt.setString(3, customer.getPhone_Cus());
				pstmt.setString(4, customer.getAddress_Cus());
				int rowsInserted = pstmt.executeUpdate();
				return rowsInserted > 0;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean updateCustomer(Customer customer) {
		String query = "UPDATE Customers SET Name = ?, Phone = ?, Address =?  WHERE Customer_ID = ?";
		try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, customer.getName_Cus());
			pstmt.setString(2, customer.getPhone_Cus());
			pstmt.setString(3, customer.getAddress_Cus());
			pstmt.setString(4, customer.getId_Cus());
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteCustomer(String Customer_ID) {
		String query = "DELETE FROM Customers WHERE Customer_ID = ?";
		try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, Customer_ID);
			int rowsDeleted = pstmt.executeUpdate();
			return rowsDeleted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Customer getCustomerById(String Customer_ID) {
		String query = "SELECT * FROM Customers WHERE Customer_ID = ?";
		try (Connection conn = DB_CONECTION.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, Customer_ID);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Customer(rs.getString("Customer_ID"), rs.getString("Name"), rs.getString("Phone"),
							rs.getString("Address"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
