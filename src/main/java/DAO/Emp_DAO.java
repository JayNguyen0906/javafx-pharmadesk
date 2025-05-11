package DAO;

import DTO.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Emp_DAO {
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (Connection conn = DB_CONECTION.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Employee(
                        rs.getString("ID"),
                        rs.getString("name"),
                        rs.getString("Position"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("CCCD")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Employee e) {
        String sql = "INSERT INTO employees (ID, name,Position,Email, Phone, CCCD) VALUES (?, ?, ?, ?,?,?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getID_Emp());
            stmt.setString(2, e.getName_Emp());
            stmt.setString(3, e.getPosition_Emp());
            stmt.setString(4, e.getEmail_Emp());
            stmt.setString(5, e.getPhone_Emp());
            stmt.setString(6, e.getCCCD_Emp());

            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
