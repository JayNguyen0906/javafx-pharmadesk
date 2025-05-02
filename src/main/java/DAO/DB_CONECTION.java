package main.java.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_CONECTION {
//	private static final String DB_URL = "jdbc:mysql://192.168.1.139:3306/phramacydata";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
	
	private static final String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVERHOA:1433;databaseName=PharmacyData;encrypt=true;trustServerCertificate=true;";
	private static final String USER = "sa";
	private static final String PASSWORD = "123";
	
//	private static final String DB_URL = "jdbc:sqlserver://192.168.1.141\\MSSQLSERVER:1433;databaseName=PharmacyData2;encrypt=true;trustServerCertificate=true;";
//	private static final String USER = "sa";
//	private static final String PASSWORD = "123";

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error Message: " + e.getMessage());
			throw new RuntimeException("Failed to connect to the database.");

		}

	}
}
