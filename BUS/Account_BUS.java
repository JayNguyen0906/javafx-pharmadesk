package BUS;

import DAO.Account_DAO;

public class Account_BUS {

	private Account_DAO accDAO;
	
	public Account_BUS() {
		accDAO = new Account_DAO();
	}
	
	public boolean checkLogin(String user, String pass) {
		return accDAO.checkAcc(user, pass);
	}
}
