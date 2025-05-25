package BUS;

import DAO.Bill_DAO;
import DTO.Bill;

import java.sql.SQLException;
import java.util.List;

public class Bill_BUS {
    private Bill_DAO billDAO = new Bill_DAO();

    public Bill_BUS() throws SQLException {
    }

    public List<Bill> getAllBills() throws SQLException {
        return billDAO.getAllBills();
    }

    public Bill getBillById(String id) {
        return billDAO.getBillById(id);
    }

    // Các nghiệp vụ logic nếu cần có thể bổ sung tại đây.

}