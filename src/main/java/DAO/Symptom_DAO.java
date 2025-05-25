package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Symptom;

public class Symptom_DAO {

    public List<Symptom> getAllSuggestions() {
        List<Symptom> suggestions = new ArrayList<>();
        String query = "SELECT * FROM SymptomSuggestion";

        try (Connection conn = DB_CONECTION.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Symptom suggestion = new Symptom(
                        rs.getInt("Suggestion_ID"),
                        rs.getString("Symptom"),
                        rs.getInt("Item_ID"),
                        rs.getString("RecommendedDosage"),
                        rs.getString("Note"));
                suggestions.add(suggestion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suggestions;
    }

    public boolean addSuggestion(Symptom symptom) {
        String query = "INSERT INTO SymptomSuggestion (Symptom, Item_ID, RecommendedDosage, Note) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, symptom.getSymptom());
            pstmt.setInt(2, symptom.getItemId());
            pstmt.setString(3, symptom.getRecommendedDosage());
            pstmt.setString(4, symptom.getNote());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateSuggestion(Symptom symptom) {
        String query = "UPDATE SymptomSuggestion SET Symptom = ?, Item_ID = ?, RecommendedDosage = ?, Note = ? WHERE Suggestion_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, symptom.getSymptom());
            pstmt.setInt(2, symptom.getItemId());
            pstmt.setString(3, symptom.getRecommendedDosage());
            pstmt.setString(4, symptom.getNote());
            pstmt.setInt(5, symptom.getSuggestionId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteSuggestion(int suggestionId) {
        String query = "DELETE FROM SymptomSuggestion WHERE Suggestion_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, suggestionId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Symptom getSuggestionById(int suggestionId) {
        String query = "SELECT * FROM SymptomSuggestion WHERE Suggestion_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, suggestionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Symptom(
                            rs.getInt("Suggestion_ID"),
                            rs.getString("Symptom"),
                            rs.getInt("Item_ID"),
                            rs.getString("RecommendedDosage"),
                            rs.getString("Note")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Symptom> getSuggestionsBySymptom(String symptomKeyword) throws SQLException {
        List<Symptom> list = new ArrayList<>();
        String sql = "SELECT ss.*, i.Items_Name FROM SymptomSuggestion ss JOIN Items i ON ss.Item_ID = i.Items_ID WHERE ss.Symptom LIKE ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + symptomKeyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Symptom s = new Symptom();
                    s.setSuggestionId(rs.getInt("Suggestion_ID"));
                    s.setSymptom(rs.getString("Symptom"));
                    s.setItemId(rs.getInt("Item_ID"));
                    s.setItemName(rs.getString("Items_Name"));
                    s.setRecommendedDosage(rs.getString("RecommendedDosage"));
                    s.setNote(rs.getString("Note"));
                    list.add(s);
                }
            }
        }

        return list;
    }

}
