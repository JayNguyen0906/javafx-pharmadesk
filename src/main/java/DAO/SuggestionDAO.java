package DAO;

import java.sql.*;
import java.util.*;

public class SuggestionDAO {
// Gợi ý về khách hàng/bệnh nhân
	public static List<String> getCustomerSuggestions() {
	    List<String> suggestions = new ArrayList<>();
	    String query = "SELECT DISTINCT Name, Phone FROM Customers WHERE Name IS NOT NULL AND Phone IS NOT NULL";

	    try (Connection conn = DB_CONECTION.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            String name = rs.getString("Name").trim();
	            String phone = rs.getString("Phone").trim();
	            if (!name.isEmpty() && !phone.isEmpty()) {
	                suggestions.add(name + " - " + phone);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return suggestions;
	}

// Gợi ý về thông tin đơn thuốc
    public static List<String> getDosages() {
        return getDistinctValues("RecommendedDosage");
    }

    public static List<String> getSymptoms() {
        return getDistinctValues("Symptom");
    }

    public static List<String> getNotes() {
        return getDistinctValues("Note");
    }

    public static List<String> getMedicineNames() {
        Set<String> resultSet = new HashSet<>();
        String query = "SELECT DISTINCT I.Items_Name " +
                       "FROM SymptomSuggestion S JOIN Items I ON S.Item_ID = I.Items_ID " +
                       "WHERE I.Items_Name IS NOT NULL";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("Items_Name");
                if (name != null && !name.trim().isEmpty()) {
                    resultSet.add(name.trim());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(resultSet);
    }

    private static List<String> getDistinctValues(String columnName) {
        Set<String> resultSet = new HashSet<>();
        String query = "SELECT DISTINCT " + columnName + " FROM SymptomSuggestion WHERE " + columnName + " IS NOT NULL";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String value = rs.getString(columnName);
                if (value != null && !value.trim().isEmpty()) {
                    resultSet.add(value.trim());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(resultSet);
    }
}
