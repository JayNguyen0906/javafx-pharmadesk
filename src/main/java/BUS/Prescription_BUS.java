package BUS;

import DAO.Prescription_DAO;
import DTO.Prescription;

import java.sql.SQLException;
import java.util.List;

public class Prescription_BUS {

    private Prescription_DAO prescriptionDAO = new Prescription_DAO();

    public Prescription_BUS() throws SQLException {
    }

    public List<Prescription> getAllPrescriptions() throws SQLException {
        return prescriptionDAO.getAllPrescriptions();
    }

    public Prescription getPrescriptionById(int id) {
        return prescriptionDAO.getPrescriptionById(id);
    }

    public boolean insertOrUpdatePrescription(Prescription prescription) {
        return prescriptionDAO.insertOrUpdatePrescription(prescription);
    }

    public boolean updatePrescription(Prescription prescription) {
        return prescriptionDAO.insertOrUpdatePrescription(prescription);
    }

    public boolean deletePrescription(int id) {
        return prescriptionDAO.deletePrescription(id);
    }

    public boolean insertPrescriptionSuggestions(int prescriptionId, List<Integer> suggestionIds) {
        try {
            return new Prescription_DAO().insertPrescriptionSuggestions(prescriptionId, suggestionIds);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
