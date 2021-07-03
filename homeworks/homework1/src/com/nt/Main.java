package com.nt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

	Stage window;
	String correctAnswer, highestScore;
	Button buttonA, buttonB, buttonC, buttonD;
	int correctAnswers = 0, wrongAnswers = 0, secondsPassed;
	private static final int ROUNDTIME = 10;
	static Timer myTimer = new Timer();
	Text timeRemaining;
	
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			secondsPassed++;
			if(secondsPassed > ROUNDTIME)
			{
				secondsPassed = 0;
				wrongAnswers++;
				Platform.runLater( () -> { try {
					window.setScene(getNewScene());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				});
			}
			Platform.runLater( () -> {
				timeRemaining.setText(String.valueOf("Time Remaining: " + (ROUNDTIME-secondsPassed)) + " s"); 
			});
		}

	};
	
	public static void main(String[] args) throws Exception
	{
		launch(args);
		myTimer.cancel();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
		window.setTitle("Quizz Game");
		
		window.setOnCloseRequest(e -> {
			try {
				closeProgram();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		myTimer.scheduleAtFixedRate(task, 1000, 1000);
		
		highestScore = getHighScore();
		
		window.setScene(getNewScene());
		window.show();
	}
	
	public Scene getNewScene() throws SQLException, ClassNotFoundException
	{	
		String[] strings = getNewQuestion();
		correctAnswer = strings[5];
		timeRemaining = new Text("Time Remaining: " + ROUNDTIME + "s");
		
		VBox topMenu = new VBox();
		Text question = new Text(strings[0]);
		question.setStyle("-fx-font: 17 arial;");
		question.wrappingWidthProperty().bind(window.widthProperty().subtract(35));
		topMenu.setPadding(new Insets(10));
		topMenu.setSpacing(8);
		topMenu.getChildren().addAll(question, timeRemaining);
		
		VBox centerMenu = new VBox();
		centerMenu.setPadding(new Insets(10));
		centerMenu.setSpacing(8);

		buttonA = new Button(strings[1]);
		buttonA.setOnAction(this);
		buttonA.setMaxWidth(Double.MAX_VALUE-200);
		buttonB = new Button(strings[2]);
		buttonB.setOnAction(this);
		buttonB.setMaxWidth(Double.MAX_VALUE);
		buttonC = new Button(strings[3]);
		buttonC.setOnAction(this);
		buttonC.setMaxWidth(Double.MAX_VALUE);
		buttonD = new Button(strings[4]);
		buttonD.setOnAction(this);
		buttonD.setMaxWidth(Double.MAX_VALUE);
		
		centerMenu.getChildren().addAll(buttonA, buttonB, buttonC, buttonD);
		
		VBox bottomMenu = new VBox();
		bottomMenu.setAlignment(Pos.TOP_CENTER);
		bottomMenu.setPadding(new Insets(10));
		
		Text scoreText = new Text("Score: " + String.valueOf(correctAnswers*10-wrongAnswers*9));
		Text correctText = new Text("Correct answers: " + String.valueOf(correctAnswers));
		Text wrongText = new Text("Wrong answers: " + String.valueOf(wrongAnswers));
		Text highScore = new Text("High Score: " + highestScore);
		scoreText.setStyle("-fx-font: 17 arial;");
		highScore.setStyle("-fx-font: 17 arial;");
		bottomMenu.getChildren().addAll(scoreText, highScore, correctText, wrongText);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topMenu);
		borderPane.setCenter(centerMenu);
		borderPane.setBottom(bottomMenu);
		
		Scene scene = new Scene(borderPane, 400, 400);
		
		return scene;
	}
	
	public String[] getNewQuestion() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "quizz", "QUIZZ");
		
		Statement st = sqlConnection.createStatement();
		
		ResultSet rs = st.executeQuery("select * from (select * from questions order by dbms_random.value) where rownum < 2");
		
		ResultSetMetaData rsmd = rs.getMetaData();
		 
		int columnSize = rsmd.getColumnCount();
		
		String[] resultedStrings = new String[columnSize+1];
		
		while(rs.next())
		{
			for(int i = 1; i <= columnSize; i++)
			{
				resultedStrings[i-1] = rs.getString(i);
			}
		}
		
		rs.close();
		st.close();
		sqlConnection.close();
		
		return resultedStrings;
	}
	
	public String getHighScore() throws ClassNotFoundException, SQLException
	{
		String highScore = new String("0");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "quizz", "QUIZZ");
		
		Statement st = sqlConnection.createStatement();
		
		ResultSet rs = st.executeQuery("select max(score) from scores");
		
		if(rs.next())
		{
			highScore = rs.getString(1);
			if(highScore.equals("null")) highScore = "0";
		}
		
		rs.close();
		st.close();
		sqlConnection.close();
		
		return highScore;
	}
	
	public void closeProgram() throws ClassNotFoundException, SQLException
	{
		if(correctAnswers > 0 && wrongAnswers > 0)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "quizz", "QUIZZ");
			
			Statement st = sqlConnection.createStatement();
			
			ResultSet rs = st.executeQuery("insert into scores values (" + String.valueOf(10*correctAnswers-9*wrongAnswers) + "," + String.valueOf(correctAnswers) + "," + String.valueOf(wrongAnswers) + ")");
			
			rs.close();
			st.close();
			sqlConnection.close();
		}
	}

	@Override
	public void handle(ActionEvent event)
	{
		secondsPassed = 0;
		if(event.getSource() == buttonA)
		{
			if(correctAnswer.equals("A"))
			{
				correctAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				wrongAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		else if(event.getSource() == buttonB)
		{
			if(correctAnswer.equals("B"))
			{
				correctAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				wrongAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		else if(event.getSource() == buttonC)
		{
			if(correctAnswer.equals("C"))
			{
				correctAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				wrongAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		else if(event.getSource() == buttonD)
		{
			if(correctAnswer.equals("D"))
			{
				correctAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				wrongAnswers++;
				try {
					window.setScene(getNewScene());
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
	}

}
