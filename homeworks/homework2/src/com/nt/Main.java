package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>
{

	ScrollPane[] panes;
	Button[] buttonsAsc;
	Button[] buttonsDesc;
	TextField[] filters;
	String currentColumnOrdering;
	String currentFilter;
	String sqlColumns;
	String tableName;
	String[] sqlColumnsSeparated;
	
	@Override
	public void start(Stage myStage) throws ClassNotFoundException, SQLException
	{
		
		myStage.setScene(tableSelection(myStage));
	
	    myStage.show();
	}
	
	public Scene tableSelection(Stage myStage) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");

        ResultSet rs = connection.createStatement().executeQuery("select table_name from user_tables");
        ResultSet rs2 = connection.createStatement().executeQuery("select table_name from user_tables");
        int nrRows = 1;
        
		while(rs.next())
		{
			nrRows++;
		}
		rs.close();
		
		Button[] selectTableButton = new Button[nrRows];
		ScrollPane pane = new ScrollPane();
		VBox vbox = new VBox();
		
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		pane.setContent(vbox);
		pane.setFitToWidth(true);
		
		int cont = 0;
		while(rs2.next())
		{
			selectTableButton[cont] = new Button(rs2.getString(1));
			selectTableButton[cont].setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().addAll(selectTableButton[cont]);
			cont++;
		}
		
		for(int i = 0; i < selectTableButton.length-1; i++)
		{
			AtomicInteger j = new AtomicInteger(i);
			selectTableButton[i].setOnAction(e -> {
				try {
					tableName = selectTableButton[j.get()].getText();
					myStage.setScene(columnSelection(myStage, tableName));
					myStage.show();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		
		Scene scene = new Scene(pane, 400, 400);
		return scene;
	}
	
	public Scene columnSelection(Stage myStage, String tableName) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");

        ResultSet rs = connection.createStatement().executeQuery("select * from " + tableName);
        
        ResultSetMetaData rsmd = rs.getMetaData();
		 
		int columnSize = rsmd.getColumnCount();
		
		CheckBox[] selectColumnButton = new CheckBox[columnSize];
		Button proceed = new Button("Next"), back = new Button("Back");
		proceed.setMaxWidth(Double.MAX_VALUE); back.setMaxWidth(Double.MAX_VALUE);
		ScrollPane pane = new ScrollPane();
		VBox vbox = new VBox();
		
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		pane.setContent(vbox);
		pane.setFitToWidth(true);
		
		for(int i = 1; i <= columnSize; i++)
		{
			selectColumnButton[i-1] = new CheckBox(rsmd.getColumnName(i));
			selectColumnButton[i-1].setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().addAll(selectColumnButton[i-1]);
		}
		vbox.getChildren().add(proceed);
		vbox.getChildren().add(back);
		
		proceed.setOnAction(e -> {
			String options = "";
			boolean isFirst = true;
			for(int i = 0; i < selectColumnButton.length; i++)
			{
				if(selectColumnButton[i].isSelected() && isFirst)
				{
					isFirst = false;
					options = options + selectColumnButton[i].getText();
				}
				else if(selectColumnButton[i].isSelected())
				{
					options = options + "," + selectColumnButton[i].getText();
				}
			}
			
			if(!options.isEmpty())
			{
				try {
					myStage.setScene(getTable(options));
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		back.setOnAction(e -> {
			try {
				myStage.setScene(tableSelection(myStage));
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			myStage.show();
		});
		
		Scene scene = new Scene(pane, 400, 400);
		return scene;
	}
	
	public Scene getTable(String columns) throws ClassNotFoundException, SQLException
	{
		loadData("select " + columns + " from " + tableName + " order by 1 desc");
		List<VBox> panesContainer = new ArrayList<>();
		
		for(int i = 0; i < panes.length; i++)
		{
			buttonsAsc[i] = new Button("Asc");
			buttonsAsc[i].setMaxWidth(Double.MAX_VALUE);
			buttonsDesc[i] = new Button("Desc");
			buttonsDesc[i].setMaxWidth(Double.MAX_VALUE);
			filters[i] = new TextField();
			panes[i].setFitToWidth(true);
			panesContainer.add(new VBox(buttonsAsc[i], buttonsDesc[i], filters[i], panes[i]));
			panesContainer.get(i).setAlignment(Pos.CENTER);
			panesContainer.get(i).setSpacing(5);
		}
		
		for(int i = 1; i < panes.length; i++)
		{
			panes[0].vvalueProperty().bindBidirectional(panes[i].vvalueProperty());
		}
		
		for(int i = 0; i < panes.length; i++)
		{
			AtomicInteger j = new AtomicInteger(i+1);
			AtomicInteger k = new AtomicInteger(i);
			buttonsAsc[i].setOnAction(e -> {
				try {
					if(!currentFilter.isEmpty())
					{
						currentColumnOrdering = String.valueOf(j.get()) + " asc";
						loadData("select " + sqlColumns + " from " + tableName + " where " + currentFilter + " order by " + String.valueOf(j.get()) + " asc");
					}
					else
					{
						currentColumnOrdering = String.valueOf(j.get()) + " asc";
						loadData("select " + sqlColumns + " from " + tableName + " order by " + String.valueOf(j.get()) + " asc");
					}
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			buttonsDesc[i].setOnAction(e -> {
				try {
					if(!currentFilter.isEmpty())
					{
						currentColumnOrdering = String.valueOf(j.get()) + " desc";
						loadData("select " + sqlColumns + " from " + tableName + " where " + currentFilter + " order by " + String.valueOf(j.get()) + " desc");
					}
					else
					{
						currentColumnOrdering = String.valueOf(j.get()) + " desc";
						loadData("select " + sqlColumns + " from " + tableName + " order by " + String.valueOf(j.get()) + " desc");
					}
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			filters[i].setOnAction(e -> {
				try {
					if(!filters[k.get()].getText().isEmpty())
					{
						currentFilter = sqlColumnsSeparated[k.get()] + filters[k.get()].getText();
						loadData("select " + sqlColumns + " from " + tableName + " where " + sqlColumnsSeparated[k.get()] + filters[k.get()].getText() + 
								" order by " + currentColumnOrdering);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(panesContainer);
		hbox.setPadding(new Insets(10,10,10,10));
		hbox.setSpacing(10);
		for(int i = 0; i < panesContainer.size(); i++)
		{
			HBox.setHgrow(panesContainer.get(i), Priority.ALWAYS);
		}
		Scene scene = new Scene(hbox, panes.length*150, 600);
		return scene;
	}
	 
	public void loadData(String SQL) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "student", "STUDENT");

        ResultSet rs = connection.createStatement().executeQuery(SQL);
        
        ResultSetMetaData rsmd = rs.getMetaData();
		 
		int columnSize = rsmd.getColumnCount();
		
		if(panes == null)
		{
			currentColumnOrdering = "1 desc";
			currentFilter = "";
			panes = new ScrollPane[columnSize];
			buttonsAsc = new Button[columnSize];
			buttonsDesc = new Button[columnSize];
			filters = new TextField[columnSize];
		}
		VBox[] vBoxes = new VBox[columnSize];
		for(int i = 0; i < columnSize; i++)
			vBoxes[i] = new VBox();
		
		if(sqlColumns == null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append(rsmd.getColumnName(1));
			sqlColumnsSeparated = new String[columnSize];
			sqlColumnsSeparated[0] = rsmd.getColumnName(1);
			for(int i = 2; i <= columnSize; i++)
			{
				builder.append(",");
				builder.append(rsmd.getColumnName(i));
				sqlColumnsSeparated[i-1] = rsmd.getColumnName(i);
			}
			sqlColumns = builder.toString();
		}

		while(rs.next())
		{
			for(int i = 1; i <= columnSize; i++)
			{
				String columnContent = rs.getString(i);
				TextField textField = new TextField(columnContent);
				textField.setEditable(false);
				vBoxes[i-1].getChildren().add(textField);
			}
		}
		for(int i = 0; i < columnSize; i++)
		{
			if(panes[i] == null) panes[i] = new ScrollPane(vBoxes[i]);
			else panes[i].setContent(vBoxes[i]);
		}

        rs.close();
		connection.close();
	}

    public static void main(String[] args)
    {
        launch(args);
    }

	@Override
	public void handle(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}