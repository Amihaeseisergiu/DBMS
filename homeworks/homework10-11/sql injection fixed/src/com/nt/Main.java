package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		TextField textField = new TextField();
		Button testBtn = new Button("Test");
		VBox centerVBox = new VBox(textField, testBtn);
		centerVBox.setPadding(new Insets(5, 5, 5, 5));
		centerVBox.setSpacing(10);
		centerVBox.setAlignment(Pos.CENTER);
		
		testBtn.setOnAction(e -> {
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");
				
				String sql = "select * from studenti where an = ?";
				
				PreparedStatement p = sqlConnection.prepareStatement(sql);
			    p.setString(1, textField.getText());
			    ResultSet rs = p.executeQuery(); 
				
				ResultSetMetaData rsmd = rs.getMetaData();
				 
				int columnSize = rsmd.getColumnCount();
				
				while(rs.next())
				{
					String row = "";
					for(int i = 1; i <= columnSize; i++)
					{
						String res = rs.getString(i);
						row = row + " " + res;
					}
					System.out.println(row);
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLSyntaxErrorException e2)
			{
				System.out.println("Eroare aruncata la incercarea de sql injection");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		pane.setCenter(centerVBox);
		
		stage.setScene(new Scene(pane, 800, 600));
		stage.show();
	}

}