/**
 * 
 */
package edu.uta.cse.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Arun
 *
 */
public class MySQLHelper {

	Connection conn;

	public MySQLHelper() {
		// TODO Auto-generated constructor stub
		String url = "jdbc:mysql://localhost:3306/attendancemgmtsystem";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, "root", "rainbow");
			System.out.println("Connection Successful");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	public ResultSet executeQueryAndGetResultSet(String query) {
		ResultSet result = null;

		try {
			Statement st = conn.createStatement();
			result= st.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// dispose the connection
	public void disposeConnection() {
		try {
			conn.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Dispose Failed");
			e.printStackTrace();
		}
	}

}
