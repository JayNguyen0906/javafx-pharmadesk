package BUS;

import DAO.Emp_DAO;
import DTO.Employee;

import java.sql.SQLException;
import java.util.List;


public class Emp_BUS {
    public static Emp_DAO emp_dao = new Emp_DAO();

    public List<Employee> getAllEmp() throws SQLException {
        return emp_dao.getAll();
    }

    public boolean addEmployee(Employee emp) {
    	 int id = emp_dao.insert(emp);
    	 if (id != -1) {
    	        emp.setID_Emp(id); 
    	        return true;
    	    }
    	    return false;
    }
    public static boolean deleteEmployee(int idEmp) {
        return Emp_DAO.deleteEmployee(idEmp);
    }
    public static boolean updateEmployee(Employee emp) {
        return Emp_DAO.updateEmployee(emp);
    }
}