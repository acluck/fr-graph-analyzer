package io;

import java.sql.*;

/**
 * Makes a new connection to the database containing headphone information.
 * @author Adam Luck
 */
public class HPDBConnection {
	
	/**
	 * Returns a connection to the database containing headphone information.
	 * @return SQL Connection to the database.
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {  
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/db?autoReconnect=true&useSSL=false","root","parsnipbagger");   
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
	
}
