package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Item;

public class Item_DAO {

    public List<Item> getAllItem() throws SQLException {
        List<Item> Items = new ArrayList<>();
        String sql = "SELECT * FROM Items";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item Item = new Item();
                Item.setId(rs.getInt("Items_ID"));
                Item.setName(rs.getString("Items_Name"));
                Item.setPrice(rs.getDouble("Prices"));
                Item.setClassification(rs.getString("Classifications"));
                Items.add(Item);
            }
        }
        return Items;
    }

    
    public boolean addItem(Item Item) throws SQLException {
        String sql = "INSERT INTO Items (Items_Name, Classifications, Prices) VALUES (?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, Item.getName());
            stmt.setString(2, Item.getClassification());
            stmt.setDouble(3, Item.getPrice());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        Item.setId(generatedId);
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean deleteItem(int selected) throws SQLException {
        String sql = "DELETE FROM Items WHERE Items_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, selected);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    public boolean updateItem(Item Item) throws SQLException {
        String sql = "UPDATE Items SET Items_Name = ?, Classifications = ?, Prices = ? WHERE Items_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Item.getName());
            stmt.setString(2, Item.getClassification());
            stmt.setDouble(3, Item.getPrice());
            stmt.setInt(4, Item.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public static Item getItemById(int ID_Item) {
        String query = "SELECT * FROM Items WHERE Items_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, ID_Item);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Item(rs.getInt("Items_ID"), rs.getString("Items_Name"), rs.getDouble("Prices"),
                            rs.getString("Classifications"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
