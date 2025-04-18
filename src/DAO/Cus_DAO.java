package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.Customer;

public class Cus_DAO {

	public List<Customer> getAllCustomer() throws SQLException {
		List<Customer> cus_list = new ArrayList<>();
		String query = "Select * from Customers";
		
	try (
		Connection conn = DB_CONECTION.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Customer customer = new Customer(
						rs.getString("Customer_ID"), 
						rs.getString("Name"), 
						rs.getString("Phone"));
				cus_list.add(customer);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	return cus_list;
	}

}
