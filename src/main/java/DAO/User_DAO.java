package DAO;

import DTO.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User_DAO {

    public boolean insertUser(User user) {
        String sql = "INSERT INTO Users (Username, Password, full_name, email, phone, gender, address, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getAddress());
            stmt.setString(8, user.getRole());
            stmt.setBoolean(9, user.isStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public User login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ? AND status = 1";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getFullName(String username) {
    	String sql = "SELECT Full_Name FROM Users WHERE Username = ? ";
    	try (Connection conn = DB_CONECTION.getConnection();
    			PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			return rs.getString("Full_Name");
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    		
    }
    
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET full_name=?, email=?, phone=?, gender=?, address=?, role=?, status=? WHERE id=?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.setBoolean(7, user.isStatus());
            stmt.setInt(8, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean softDeleteUser(int id) {
        String sql = "UPDATE Users SET status = 0 WHERE id = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUsers(boolean onlyActive) {
        List<User> list = new ArrayList<>();
        String sql = onlyActive ? "SELECT * FROM Users WHERE status = 1" : "SELECT * FROM Users";
        try (Connection conn = DB_CONECTION.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm hỗ trợ lấy thông tin từ ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("gender"),
            rs.getString("address"),
            rs.getString("role"),
            rs.getBoolean("status")
        );
    }
}
