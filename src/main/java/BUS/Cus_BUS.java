package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.Cus_DAO;
import DTO.Customer;

public class Cus_BUS {
	
	private Cus_DAO cusDAO;

	public Cus_BUS() {
		cusDAO = new Cus_DAO();
	}

	public List<Customer> getAllCustomers() throws SQLException {
		return cusDAO.getAllCustomer();
	}

	public int addCustomerGetID(Customer customer) throws SQLException {
		return cusDAO.addCustomerGetID(customer);
	}
	
	public boolean addCustomer(Customer customer) throws SQLException {
		return cusDAO.addCustomer(customer);
	}

	public boolean deleteCustomer(int id) throws SQLException {
		return cusDAO.deleteCustomer(id);
	}

	public boolean updateCustomer(Customer customer) throws SQLException {
		return cusDAO.updateCustomer(customer);
	}

	public Customer getCustomerById(int customerId) {
		return cusDAO.getCustomerById(customerId);
	}
}
