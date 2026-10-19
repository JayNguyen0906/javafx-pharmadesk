package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Medicine;
import DTO.PrescriptionDetail;

public class PrescriptionDetail_DAO {

    public List<Medicine> getMedicinesByPrescriptionId(int prescriptionId) throws SQLException {
        List<Medicine> list = new ArrayList<>();
        String query = "SELECT i.Items_ID, i.Items_Name, i.Prices, pd.Dosage, pd.Note " +
                       "FROM PrescriptionDetails pd " +
                       "JOIN Items i ON pd.Items_ID = i.Items_ID " +
                       "WHERE pd.Prescription_ID = ?";

        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, prescriptionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Medicine med = new Medicine();
                med.setItemId(rs.getInt("Items_ID"));
                med.setName(rs.getString("Items_Name"));
                med.setPrice(rs.getDouble("Prices"));
                med.setDosage(rs.getString("Dosage"));
                med.setNote(rs.getString("Note"));
                list.add(med);
            }
        }
        return list;
    }

    public boolean insertPrescriptionDetails(List<PrescriptionDetail> details) throws SQLException {
        String sql = "INSERT INTO PrescriptionDetails (Prescription_ID, Items_ID, Dosage, Note) VALUES (?, ?, ?, ?)";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (PrescriptionDetail detail : details) {
                ps.setInt(1, detail.getPrescriptionId());
                ps.setInt(2, detail.getItemId());
                ps.setString(3, detail.getDosage());
                ps.setString(4, detail.getNote());
                ps.addBatch();
            }

            ps.executeBatch();
            return true;
        }
    }

    public boolean deleteByPrescriptionId(int prescriptionId) throws SQLException {
        String sql = "DELETE FROM PrescriptionDetails WHERE Prescription_ID = ?";
        try (Connection conn = DB_CONECTION.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionId);
            return ps.executeUpdate() > 0;
        }
    }
}
