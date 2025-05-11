package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.Warehouse_DAO;
import DTO.Warehouse;

public class Warehouse_BUS {

	private Warehouse_DAO cusDAO;

	public Warehouse_BUS() {
		cusDAO = new Warehouse_DAO();
	}
	
	public List<Warehouse> getAllWarehouses() throws SQLException {
		return cusDAO.getAllWarehouse();
	}
	
	public boolean addWarehouse(Warehouse Warehouse) throws SQLException {
	    return cusDAO.addWarehouse(Warehouse);
	}

	public boolean deleteWarehouse(String name) throws SQLException {
	    return cusDAO.deleteWarehouse(name, name);
	}

	public boolean updateWarehouse(Warehouse Warehouse) throws SQLException {
	    return cusDAO.updateWarehouse(Warehouse);
	}

	
	public Warehouse getWarehouseByName(String itemName, String supplierName){
        return Warehouse_DAO.getWarehouseByName(itemName, supplierName);
    }

}
