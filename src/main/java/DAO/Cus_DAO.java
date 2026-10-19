package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Customer;

public class Cus_DAO {

    public List<Customer> getAllCustomer() {
        List<Customer> cusList = new ArrayList<>();
        String query = "SELECT * FROM Customers";

        try (Connection conn = DB_CONECTION.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Name"),
                        rs.getInt("BirthYear"),
                        rs.getString("Gender"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getString("HealthInsuranceNumber")
                );
                cusList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cusList;
    }

    public int addCustomerGetID(Customer customer) {
        String query = "INSERT INTO Customers (Name, BirthYear, Gender, Phone, Address, HealthInsuranceNumber) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getName_Cus());
            pstmt.setInt(2, customer.getBirthYear());
            pstmt.setString(3, customer.getGender());
            pstmt.setString(4, customer.getPhone_Cus());
            pstmt.setString(5, customer.getAddress_Cus());
            pstmt.setString(6, customer.getHealthInsuranceNumber());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về Customer_ID vừa được tạo
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO Customers (Name, BirthYear, Gender, Phone, Address, HealthInsuranceNumber) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName_Cus());
            pstmt.setInt(2, customer.getBirthYear());
            pstmt.setString(3, customer.getGender());
            pstmt.setString(4, customer.getPhone_Cus());
            pstmt.setString(5, customer.getAddress_Cus());
            pstmt.setString(6, customer.getHealthInsuranceNumber());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customers SET Name = ?, BirthYear = ?, Gender = ?, Phone = ?, Address = ?, HealthInsuranceNumber = ? WHERE Customer_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName_Cus());
            pstmt.setInt(2, customer.getBirthYear());
            pstmt.setString(3, customer.getGender());
            pstmt.setString(4, customer.getPhone_Cus());
            pstmt.setString(5, customer.getAddress_Cus());
            pstmt.setString(6, customer.getHealthInsuranceNumber());
            pstmt.setInt(7, customer.getId_Cus());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteCustomer(int customerID) {
        String query = "DELETE FROM Customers WHERE Customer_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Customer getCustomerById(int customerID) {
        String query = "SELECT * FROM Customers WHERE Customer_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("Customer_ID"),
                            rs.getString("Name"),
                            rs.getInt("BirthYear"),
                            rs.getString("Gender"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("HealthInsuranceNumber")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
