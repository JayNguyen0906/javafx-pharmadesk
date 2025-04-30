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

    public boolean addCustomer(Customer customer) throws SQLException {
        return cusDAO.addCustomer(customer);
    }

    public boolean deleteCustomer(String id) throws SQLException {
        return cusDAO.deleteCustomer(id);
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        return cusDAO.updateCustomer(customer);
    }


    public Customer getCustomerById(String Customer_ID){
        return Cus_DAO.getCustomerById(Customer_ID);
    }

}
