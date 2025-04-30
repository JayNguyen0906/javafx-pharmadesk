package DAO;

import DTO.Good;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Good_DAO {

    // Lấy toàn bộ danh sách sản phẩm
    public List<Good> getAllGood() throws SQLException {
        List<Good> goods = new ArrayList<>();
        String sql = "SELECT * FROM goods";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getString("id"));
                good.setName(rs.getString("name"));
                good.setPrice(rs.getDouble("price"));
                good.setQuantity(rs.getString("quantity"));
                goods.add(good);
            }
        }
        return goods;
    }

    // Thêm sản phẩm mới
    public boolean addGood(Good good) throws SQLException {
        String sql = "INSERT INTO goods (id, name, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, good.getId());
            stmt.setString(2, good.getName());
            stmt.setDouble(3, good.getPrice());
            stmt.setString(4, good.getQuantity());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    // Xóa sản phẩm theo ID
    public boolean deleteGood(String id) throws SQLException {
        String sql = "DELETE FROM goods WHERE id = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateGood(Good good) throws SQLException {
        String sql = "UPDATE goods SET name = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, good.getName());
            stmt.setDouble(2, good.getPrice());
            stmt.setString(3, good.getQuantity());
            stmt.setString(4, good.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
