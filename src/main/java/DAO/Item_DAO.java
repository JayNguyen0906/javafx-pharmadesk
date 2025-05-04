package main.java.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.DTO.Item;

public class Item_DAO {

    // Lấy toàn bộ danh sách sản phẩm
    public List<Item> getAllItem() throws SQLException {
        List<Item> Items = new ArrayList<>();
        String sql = "SELECT * FROM Items";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item Item = new Item();
                Item.setId(rs.getString("id"));
                Item.setName(rs.getString("name"));
                Item.setPrice(rs.getDouble("price"));
                Item.setQuantity(rs.getString("quantity"));
                Items.add(Item);
            }
        }
        return Items;
    }

    // Thêm sản phẩm mới
    public boolean addItem(Item Item) throws SQLException {
        String sql = "INSERT INTO Items (id, name, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Item.getId());
            stmt.setString(2, Item.getName());
            stmt.setDouble(3, Item.getPrice());
            stmt.setString(4, Item.getQuantity());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    // Xóa sản phẩm theo ID
    public boolean deleteItem(String id) throws SQLException {
        String sql = "DELETE FROM Items WHERE id = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateItem(Item Item) throws SQLException {
        String sql = "UPDATE Items SET name = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Item.getName());
            stmt.setDouble(2, Item.getPrice());
            stmt.setString(3, Item.getQuantity());
            stmt.setString(4, Item.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
