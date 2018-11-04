package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * This class is used as an Authentication utility to authenticate the users.
 * Can add new methods if needed to handle the authentication requests
 * coming directly as part of the HTTP request.
 * */
public class AuthClient {
	
	public static boolean authenticate(String username, String auth_id) throws SQLException {
		Connection conn = PostgresConnection.getConnection();
		
		//Debug code
		/*username = "azr1";
		auth_id = "20S0KPNOIM";*/
		
		
		String sql = "select auth_id from account where username='" + username + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		
		if(!result.next())
			return false;
		
		String outputAuthID = result.getString(1);
		if(auth_id.equals(outputAuthID))
			return true;
		return false;
		
	}
	
	private static void testAuthenticate() throws SQLException {
		System.out.println(authenticate("azr1","20S0KPNOIM"));
		System.out.println(authenticate("azr1","20S0KPN"));
	}
	
	public static void main(String[] args) throws SQLException {
		testAuthenticate();
	}
	
}
