package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws Exception
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");
		
		Statement st = sqlConnection.createStatement();
		
		try (ResultSet rs = st.executeQuery("select get_avg_grades('Mangalagiu', 'Lucian George') from dual")) {
		    System.out.println("Nicio exceptie");
		} catch (SQLException e) {
		    System.out.println("Exceptie aruncata de baza de date. Codul: " + e.getErrorCode());
		}

		st.close();
		sqlConnection.close();
	}
}
