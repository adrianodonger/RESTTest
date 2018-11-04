package service;

/*
 * Singleton class to fetch the DB connection.
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnection {
	
	//These are for the sake of testing the client
	//Can be moved to a common file and a key store can be used
	//to store the passwords.
	private static String Url = "jdbc:postgresql://localhost/postgres";
	private static String username = "postgres";
	private static String password = "Welcome1";
	
	private static Connection conn = null;
	
	private PostgresConnection() {

	}
	
	public static Connection getConnection() throws SQLException {
		if(conn == null) {
			conn = DriverManager.getConnection(Url, username, password);
		}
		
		return conn;
	}

	//This logic needs to be changed based on which the authentication
	//wrt the account. This is just for testing purposes.
	public static boolean hasNumber(String toNumber) throws SQLException {
		Connection testConn = null;
		try {
			testConn = PostgresConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(testConn == null)
			return false;
		else {
			String sql = "select number from phone_number where number = '" + toNumber + "'";
			Statement stmt = testConn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			if(!resultSet.next())
				return false;
		}
		return true;
	}
}
