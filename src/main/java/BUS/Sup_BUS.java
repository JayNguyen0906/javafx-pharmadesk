package main.java.BUS;

import java.sql.SQLException;
import java.util.List;

import main.java.DAO.Sup_DAO;
import main.java.DTO.Supplier;

public class Sup_BUS {
	
		private Sup_DAO supDAO;

		public Sup_BUS() {
			supDAO = new Sup_DAO();
		}
		
		public List<Supplier> getAllSuppliers() throws SQLException {
			return supDAO.getAllSup();
		}
		
		public boolean addSupplier(Supplier Supplier) throws SQLException {
		    return supDAO.addSupplier(Supplier);
		}

		public boolean deleteSupplier(String id) throws SQLException {
		    return supDAO.deleteSupplier(id);
		}

		public boolean updateSupplier(Supplier Supplier) throws SQLException {
		    return supDAO.updateSupplier(Supplier);
		}

		
		public Supplier getSupplierById(String Supplier_ID){
	        return Sup_DAO.getSupplierById(Supplier_ID);
	    }

}
