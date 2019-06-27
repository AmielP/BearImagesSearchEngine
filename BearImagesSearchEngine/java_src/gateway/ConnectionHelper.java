package gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper
{
	private String url;
	private static ConnectionHelper instance;
	
	public ConnectionHelper()
	{
		try
		{
			// SQLite Driver
			Class.forName("org.sqlite.JDBC");
			url = "jdbc:sqlite:D://SQLite Database/BearImagesSearchEngine.db";
		}
		catch (Exception e)
		{
			System.out.println("ERROR: SQLite Driver - ConnectionHelper");
			e.printStackTrace(System.out);
			System.out.println(e.getMessage());
		}
	}
	
	public static Connection getConnection() throws SQLException
	{
		if (instance == null)
		{
			instance = new ConnectionHelper();
		}
		try
		{
			return DriverManager.getConnection(instance.url);
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: DriverManager getConnection on instance.url - ConnectionHelper");
			e.printStackTrace();
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: connection close - ConnectionHelper");
			e.printStackTrace(System.out);
		}
	}
}
