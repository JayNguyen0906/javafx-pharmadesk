package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.Good;

public class Good_DAO {

	public List<Good> getAllGood() throws SQLException {
		List<Good> good_list = new ArrayList<>();
		String query = "Select * from Goods";
		try (
				Connection conn = DB_CONECTION.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
				
			while (rs.next()) {
				Good good = new Good(
						rs.getString("Goods_ID"),
						rs.getString("Goods_Name"),
						rs.getString("Classifications"),
						rs.getDouble("Prices"));
					good_list.add(good);	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
				
		return good_list;
	}

}
