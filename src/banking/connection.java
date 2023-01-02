package banking;

import java.sql.Connection;
import java.sql.DriverManager;
// Global connection Class
public class connection {
	static Connection gco; // Global Connection Object
	public static Connection getConnection()
	{
		try {
			
			
			String mysqlJDBCDriver
				= "com.mysql.cj.jdbc.Driver"; //jdbc driver
			String url
				= "jdbc:mysql://localhost:3001/mydata"; //mysql url
			String user = "root";	 //mysql username
			String pass = ""; //mysql passcode
			Class.forName(mysqlJDBCDriver);
			gco = DriverManager.getConnection(url, user,
											pass);
		}
		catch (Exception e) {
			System.out.println("Connection Failed!");
		}

		return gco;
	}
}
