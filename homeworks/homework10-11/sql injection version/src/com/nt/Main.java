package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
				
				Statement st = sqlConnection.createStatement();
				
				ResultSet rs = st.executeQuery("select * from studenti where an = " + textField.getText());
				
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
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		pane.setCenter(centerVBox);
		
		stage.setScene(new Scene(pane, 800, 600));
		stage.show();
	}

}
