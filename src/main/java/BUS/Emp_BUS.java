package BUS;

import DAO.Cus_DAO;
import DAO.Emp_DAO;
import DTO.Customer;
import DTO.Employee;

import java.sql.SQLException;
import java.util.List;

import BUS.Cus_BUS;

public class Emp_BUS {
    public static Emp_DAO emp_dao;
    public List<Employee> getAllEmp() throws SQLException {
        return emp_dao.getAllEmp();
    }
}
