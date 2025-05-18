package BUS;


import DAO.Item_DAO;
import DTO.Item;

import java.sql.SQLException;
import java.util.List;

public class Item_BUS {
	
    public static Item_DAO Item_dao;
    
    public Item_BUS() {
        Item_dao = new Item_DAO();
    }
    
    public List<Item> getAllItem() throws SQLException {

        return Item_dao.getAllItem();
    }

    public boolean updateItem(Item selected) throws SQLException {
        return Item_dao.updateItem(selected);
    }
    public boolean deleteItem(String selected) throws SQLException {
        return Item_dao.deleteItem(selected);
    }

    public boolean addItem(Item newItem) throws SQLException {
        return Item_dao.addItem(newItem);
    }
}
