package DAO;

import DTO.Employee;
import DTO.Supplier;

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
    public static boolean recordAttendance(String empID) {
        String checkSql = "SELECT COUNT(*) FROM Attendance WHERE emp_id = ? AND date = CURDATE()";
        String insertSql = "INSERT INTO Attendance (emp_id, date) VALUES (?, CURDATE())";

        try (Connection conn = DB_CONECTION.getConnection()) {

            // 1. Kiểm tra xem đã chấm công chưa
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, empID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Đã chấm công rồi
                }
            }

            // 2. Chưa chấm thì chấm công
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, empID);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteEmployee(String idEmp) {
        String sql = "DELETE FROM employees WHERE ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idEmp);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name=?, Position=?, Email=?, Phone=?, CCCD=? WHERE ID=?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getName_Emp());
            stmt.setString(2, emp.getPosition_Emp());
            stmt.setString(3, emp.getEmail_Emp());
            stmt.setString(4, emp.getPhone_Emp());
            stmt.setString(5, emp.getCCCD_Emp());
            stmt.setString(6, emp.getID_Emp());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
