package BUS;

import DAO.PrescriptionDetail_DAO;
import DTO.Medicine;
import DTO.PrescriptionDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDetail_BUS {

    private final PrescriptionDetail_DAO dao;

    public PrescriptionDetail_BUS() throws SQLException {
        this.dao = new PrescriptionDetail_DAO();
    }

    // Lưu danh sách thuốc trong đơn thuốc
    public boolean insertPrescriptionDetails(int prescriptionId, List<Medicine> medicineList) {
        List<PrescriptionDetail> details = new ArrayList<>();
        for (Medicine med : medicineList) {
            details.add(new PrescriptionDetail(
                prescriptionId,
                med.getItemId(),   // lấy ID thuốc từ Medicine DTO
                med.getDosage(),
                med.getNote()
            ));
        }

        try {
            return dao.insertPrescriptionDetails(details);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách thuốc của 1 đơn thuốc (trả về Medicine)
    public List<Medicine> getMedicinesOfPrescription(int prescriptionId) {
        try {
            return dao.getMedicinesByPrescriptionId(prescriptionId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Xóa toàn bộ thuốc của đơn (khi update đơn thuốc)
    public boolean deleteDetailsByPrescription(int prescriptionId) {
        try {
            return dao.deleteByPrescriptionId(prescriptionId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
