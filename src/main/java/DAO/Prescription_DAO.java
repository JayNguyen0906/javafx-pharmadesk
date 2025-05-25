package DAO;

import DTO.Prescription;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Prescription_DAO {

    private Connection conn;

    public Prescription_DAO() throws SQLException {
        this.conn = DB_CONECTION.getConnection();
    }

    // Lấy tất cả đơn thuốc
    public List<Prescription> getAllPrescriptions() throws SQLException {
        List<Prescription> list = new ArrayList<>();
        String query = "SELECT * FROM Prescriptions";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Prescription p = new Prescription(
                        rs.getInt("Prescription_ID"),
                        rs.getInt("Customer_ID"),
                        rs.getString("VitalSigns"),
                        rs.getString("Symptom"),
                        rs.getString("Diagnosis"),
                        rs.getDate("PrescribedDate").toLocalDate(),
                        rs.getString("PrescriberName")
                );
                list.add(p);
            }
        }
        return list;
    }

    // Lấy đơn thuốc theo ID
    public Prescription getPrescriptionById(int id) {
        String sql = "SELECT * FROM Prescriptions WHERE Prescription_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Prescription(
                        rs.getInt("Prescription_ID"),
                        rs.getInt("Customer_ID"),
                        rs.getString("VitalSigns"),
                        rs.getString("Symptom"),
                        rs.getString("Diagnosis"),
                        rs.getDate("PrescribedDate").toLocalDate(),
                        rs.getString("PrescriberName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertOrUpdatePrescription(Prescription pres) {
        String checkSql = "SELECT COUNT(*) FROM Prescriptions WHERE Prescription_ID = ?";
        String insertSql = "INSERT INTO Prescriptions (Customer_ID, VitalSigns, Symptom, Diagnosis, PrescribedDate, PrescriberName) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE Prescriptions SET Customer_ID = ?, VitalSigns = ?, Symptom = ?, Diagnosis = ?, PrescribedDate = ?, PrescriberName = ? WHERE Prescription_ID = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, pres.getPrescriptionID());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Cập nhật
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setInt(1, pres.getCustomerID());
                    ps.setString(2, pres.getVitalSigns());
                    ps.setString(3, pres.getSymptom());
                    ps.setString(4, pres.getDiagnosis());
                    ps.setDate(5, java.sql.Date.valueOf(pres.getPrescribedDate()));
                    ps.setString(6, pres.getPrescriberName());
                    ps.setInt(7, pres.getPrescriptionID());
                    return ps.executeUpdate() > 0;
                }
            } else {
                // Thêm mới
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setInt(1, pres.getCustomerID());
                    ps.setString(2, pres.getVitalSigns());
                    ps.setString(3, pres.getSymptom());
                    ps.setString(4, pres.getDiagnosis());
                    ps.setDate(5, java.sql.Date.valueOf(pres.getPrescribedDate()));
                    ps.setString(6, pres.getPrescriberName());
                    return ps.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePrescription(Prescription pres) {
        String sql = "UPDATE Prescriptions SET Customer_ID = ?, VitalSigns = ?, Symptom = ?, Diagnosis = ?, PrescribedDate = ?, PrescriberName = ? WHERE Prescription_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pres.getCustomerID());
            ps.setString(2, pres.getVitalSigns());
            ps.setString(3, pres.getSymptom());
            ps.setString(4, pres.getDiagnosis());
            ps.setDate(5, java.sql.Date.valueOf(pres.getPrescribedDate()));
            ps.setString(6, pres.getPrescriberName());
            ps.setInt(7, pres.getPrescriptionID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePrescription(int prescriptionID) {
        String sql = "DELETE FROM Prescriptions WHERE Prescription_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean insertPrescriptionSuggestions(int prescriptionId, List<Integer> suggestionIds) throws SQLException {
        Connection conn = DB_CONECTION.getConnection();
        String sql = "INSERT INTO Prescription_Suggestions (PrescriptionID, SuggestionID) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int suggId : suggestionIds) {
                ps.setInt(1, prescriptionId);
                ps.setInt(2, suggId);
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
