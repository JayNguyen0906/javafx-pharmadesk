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
                		 rs.getInt("Emp_ID"),
                         rs.getString("Emp_Name"),
                         rs.getString("Position_Emp"),
                         rs.getString("Email_Emp"),
                         rs.getString("Emp_Phone"),
                         rs.getString("Emp_CCCD")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(Employee e) {
        String sql = "INSERT INTO Employees (Emp_Name, Position_Emp, Email_Emp, Emp_Phone, Emp_CCCD) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {  // <-- thêm Statement.RETURN_GENERATED_KEYS

            stmt.setString(1, e.getName_Emp());
            stmt.setString(2, e.getPosition_Emp());
            stmt.setString(3, e.getEmail_Emp());
            stmt.setString(4, e.getPhone_Emp());
            stmt.setString(5, e.getCCCD_Emp());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); 
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    
    public static boolean recordAttendance(int empID) {
        String checkSql = "SELECT COUNT(*) FROM Attendance WHERE emp_id = ? AND date = CAST(GETDATE() AS DATE)";
        String insertSql = "INSERT INTO Attendance (emp_id, date) VALUES (?, CAST(GETDATE() AS DATE))";

        try (Connection conn = DB_CONECTION.getConnection()) {

            // 1. Kiểm tra xem đã chấm công chưa
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, empID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; 
                }
            }

            // 2. Chưa chấm thì chấm công
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, empID);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean hasCheckedToday(int empID) {
        String sql = "SELECT COUNT(*) FROM Attendance WHERE emp_id = ? AND date = CAST(GETDATE() AS DATE)";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteEmployee(int idEmp) {
        String sql = "DELETE FROM employees WHERE Emp_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmp);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateEmployee(Employee emp) {
        String sql = "UPDATE Employees SET Emp_Name = ?, Position_Emp = ?, Email_Emp = ?, Emp_Phone = ?, Emp_CCCD = ? WHERE Emp_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getName_Emp());
            stmt.setString(2, emp.getPosition_Emp());
            stmt.setString(3, emp.getEmail_Emp());
            stmt.setString(4, emp.getPhone_Emp());
            stmt.setString(5, emp.getCCCD_Emp());
            stmt.setInt(6, emp.getID_Emp());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}