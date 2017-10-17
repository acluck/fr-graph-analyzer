package io;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

/**
 * Makes a new connection to the database containing headphone information.
 * @author Adam Luck
 */
public class HPDBConnection {
	
	/** Path to SQL login information. */
	private static final String SQL_LOGIN = "C:\\Users\\Adam\\Web\\sql.txt";
	
	/**
	 * Returns a connection to the database containing headphone information.
	 * @return SQL Connection to the database.
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Scanner scanner = new Scanner(new File(SQL_LOGIN));
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
			"jdbc:mysql://160.153.91.133:3306/headphonedb?autoReconnect=true&useSSL=false", scanner.nextLine(), scanner.nextLine());
			scanner.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
	
}
