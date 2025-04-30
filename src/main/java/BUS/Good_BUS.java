package BUS;


import DAO.Good_DAO;

import DTO.Good;

import java.sql.SQLException;
import java.util.List;

public class Good_BUS {
    public static Good_DAO good_dao;
    public Good_BUS() {
        good_dao = new Good_DAO(); // Khởi tạo DAO
    }
    public List<Good> getAllGood() throws SQLException {

        return good_dao.getAllGood();
    }

    public boolean updateGood(Good selected) throws SQLException {
        return good_dao.updateGood(selected);
    }
    public boolean deleteGood(String selected) throws SQLException {
        return good_dao.deleteGood(selected);
    }

    public boolean addGood(Good newGood) throws SQLException {
        return good_dao.addGood(newGood);
    }
}
