package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws Exception
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");
		
		Statement st = sqlConnection.createStatement();
		
		ResultSet rs = st.executeQuery("select get_friend_suggestions(1) from dual");
		
		ResultSetMetaData rsmd = rs.getMetaData();
		 
		int columnSize = rsmd.getColumnCount();
		
		while(rs.next())
		{
			for(int i = 1; i <= columnSize; i++)
			{
				System.out.println(rs.getString(i));
			}
		}
		
		rs.close();
		st.close();
		sqlConnection.close();
	}
}
