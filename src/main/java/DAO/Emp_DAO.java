package DAO;

import DTO.Employee;
import DTO.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Emp_DAO {
    public List<Employee> getAllEmp() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "Select * FROM employees";
        try (Connection con = DB_CONECTION.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
        ){
            while (rs.next()){
                Employee employee = new Employee();
                employee.setID_Emp(rs.getString("Emp_Id"));
                employee.setName_Emp(rs.getString("Emp_Name"));
                employee.setPhone_Emp(rs.getString("Emp_Phone"));
                employee.setCCCD_Emp(rs.getString("Emp_CCCD"));
                employees.add(employee);
                System.out.println(employee);
            }
        }
        return employees;
    }
}
