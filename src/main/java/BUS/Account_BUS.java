package BUS;

import DAO.Account_DAO;

import java.sql.SQLException;

public class Account_BUS {

	private Account_DAO accDAO;
	
	public Account_BUS() {
		accDAO = new Account_DAO();
	}
	
	public boolean checkLogin(String user, String pass) throws SQLException {
		return accDAO.checkAcc(user, pass);
	}
}
