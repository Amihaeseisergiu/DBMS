package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception
	{
	
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");
		
		Statement st = sqlConnection.createStatement();
		
		ResultSet rs = st.executeQuery("select nume, prenume, nr_matricol, titlu_curs, valoare, data_notare from (select * from "
				+ "(select a.*, rownum rnum from (select /*+ USE_NL(studenti note cursuri) */ nume, prenume, nr_matricol, titlu_curs,"
				+ " valoare, data_notare from studenti s join note n on n.id_student = s.id join cursuri c on n.id_curs = c.id order by s.nume asc) a "
				+ "where rownum <= 50) where rnum >= 0)");
		
		ResultSetMetaData rsmd = rs.getMetaData();
		 
		int columnSize = rsmd.getColumnCount();
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		Label topLabel = new Label("Catalog");
		topLabel.setStyle("-fx-font-size: 20");
		HBox topHBox = new HBox(topLabel);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setSpacing(10);
		topHBox.setPadding(new Insets(5, 5, 5, 5));
		
		HBox centerHBox = new HBox();
		centerHBox.setAlignment(Pos.CENTER);
		centerHBox.setSpacing(10);
		centerHBox.setPadding(new Insets(5, 5, 5, 5));
		
		List<ScrollPane> scrollPanes = new ArrayList<>();
		List<VBox> vBoxes = new ArrayList<>();
		
		for(int i = 0; i < columnSize; i++)
		{
			VBox vBox = new VBox();
			vBox.setAlignment(Pos.CENTER);
			vBox.setSpacing(10);
			vBox.setPadding(new Insets(5, 5, 5, 5));
			
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setFitToWidth(true);
			scrollPane.setContent(vBox);
			scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			
			vBoxes.add(vBox);
			scrollPanes.add(scrollPane);
		}
		
		for(int i = 1; i < scrollPanes.size(); i++)
		{
			scrollPanes.get(0).vvalueProperty().bindBidirectional(scrollPanes.get(i).vvalueProperty());
		}
		
		while(rs.next())
		{
			String row = "";
			for(int i = 1; i <= columnSize; i++)
			{
				String res = rs.getString(i);
				row = row + res;
				
				TextField textField = new TextField(res);
				textField.setEditable(false);
				vBoxes.get(i-1).getChildren().addAll(textField);
			}
			System.out.println(row);
		}
		rs.close();
		
		centerHBox.getChildren().addAll(scrollPanes);
		
		HBox bottomScrollPaneHBox = new HBox();
		bottomScrollPaneHBox.setAlignment(Pos.CENTER);
		bottomScrollPaneHBox.setSpacing(10);
		bottomScrollPaneHBox.setPadding(new Insets(5, 5, 5, 5));
		ScrollPane bottomScrollPane = new ScrollPane(bottomScrollPaneHBox);
		bottomScrollPane.setFitToWidth(true);
		bottomScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		bottomScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		HBox bottomHBox = new HBox(bottomScrollPane);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(10);
		bottomHBox.setPadding(new Insets(5, 5, 5, 5));
		
		rs = st.executeQuery("select count(nume) from studenti s "
				+ "join note n on n.id_student = s.id join cursuri c on n.id_curs = c.id");
		
		while(rs.next())
		{
			int nrButtons = rs.getInt(1) / 50;
			
			TilePane layout = new TilePane();
	        layout.setHgap(10);
	        layout.setPrefColumns(nrButtons);
	        layout.setPadding(new Insets(10));
	        Group group = new Group(layout);	
	        bottomScrollPaneHBox.getChildren().add(group);
	        
			for(int i = 1; i <= nrButtons; i++)
			{
				Button page = new Button(String.valueOf(i));
				page.setPrefWidth(50);
				layout.getChildren().add(page);
				
				AtomicInteger icopy = new AtomicInteger(i);
				page.setOnAction(e -> {
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");
						
						Statement stmt = conn.createStatement();
						
						String interval1 = String.valueOf((icopy.get() - 1) * 50);
						String interval2 = String.valueOf(icopy.get() * 50);
						ResultSet resSet = stmt.executeQuery("select nume, prenume, nr_matricol, titlu_curs, valoare, data_notare from (select * from "
								+ "(select a.*, rownum rnum from (select /*+ USE_NL(studenti note cursuri) */ nume, prenume, nr_matricol, titlu_curs,"
								+ " valoare, data_notare from studenti s join note n on n.id_student = s.id join cursuri c on n.id_curs = c.id order by s.nume asc) a "
								+ "where rownum <=" + interval2 + ") where rnum >=" +  interval1 + ")");
						
						ResultSetMetaData resSetMetaData = resSet.getMetaData();
						 
						int columns = resSetMetaData.getColumnCount();
						
						for(VBox v : vBoxes)
						{
							v.getChildren().clear();
						}
						while(resSet.next())
						{
							String row = "";
							for(int j = 1; j <= columns; j++)
							{
								String res = resSet.getString(j);
								row = row + res;
								
								TextField textField = new TextField(res);
								textField.setEditable(false);
								vBoxes.get(j-1).getChildren().addAll(textField);
							}
							System.out.println(row);
						}
						resSet.close();
						stmt.close();
						conn.close();
						
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				});
			}
		}
		rs.close();
		
		st.close();
		sqlConnection.close();
		
		pane.setTop(topHBox);
		pane.setCenter(centerHBox);
		pane.setBottom(bottomHBox);
		
		Scene scene = new Scene(pane, 800, 600);
		stage.setScene(scene);
		stage.show();
	}

}
