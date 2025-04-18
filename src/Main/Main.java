package Main;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.Cus_DAO;
import DAO.DB_CONECTION;
import DAO.Good_DAO;

public class Main {

	public static void main(String[] args) throws SQLException {
		Connection conn = DB_CONECTION.getConnection();
		if (conn != null) {
			System.out.println("Connection database succesfully! ");
		} else {
			System.out.println("Connect database failed!");
		}
		
		Cus_DAO cus = new Cus_DAO();
		System.out.println(cus.getAllCustomer());
		
		Good_DAO good = new Good_DAO();
		System.out.println(good.getAllGood());
		
		
		
	}
}
