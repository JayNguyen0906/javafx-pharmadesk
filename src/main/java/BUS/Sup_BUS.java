package BUS;

import DAO.Cus_DAO;
import DAO.Sup_DAO;
import DTO.Customer;
import DTO.Supplier;

import java.sql.SQLException;
import java.util.List;

public class Sup_BUS {
    public static Sup_DAO sup_dao;
    public List<Supplier> getAllSup() throws SQLException {
        return sup_dao.getAllSup();
    }
}
