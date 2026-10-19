package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.Sup_DAO;
import DTO.Supplier;

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

		public boolean deleteSupplier(int id) throws SQLException {
		    return supDAO.deleteSupplier(id);
		}

		public boolean updateSupplier(Supplier Supplier) throws SQLException {
		    return supDAO.updateSupplier(Supplier);
		}

		
		public Supplier getSupplierById(int Supplier_ID){
	        return Sup_DAO.getSupplierById(Supplier_ID);
	    }

}
