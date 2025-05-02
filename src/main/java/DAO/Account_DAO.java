package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account_DAO {

	public boolean checkAcc(String user, String pass) {
		boolean isValid = false;
		Connection conn = DB_CONECTION.getConnection();

		String query = "SELECT * FROM Account WHERE Username = ? AND Password = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user);
			pstmt.setString(2, pass);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				isValid = true;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isValid;

	}

}
