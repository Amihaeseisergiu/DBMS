package com.nt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		BorderPane pane = new BorderPane();
		HBox centerHBox = new HBox();
		List<ScrollPane> scrollpanes = new ArrayList<>();
		VBox temp1 = new VBox();
		VBox temp2 = new VBox();
		VBox temp3 = new VBox();
		VBox temp4 = new VBox();
		VBox temp5 = new VBox();
		VBox temp6 = new VBox();
		VBox temp7 = new VBox();
		VBox temp8 = new VBox();
		VBox temp9 = new VBox();
		VBox temp10 = new VBox();
		VBox temp11 = new VBox();
		VBox temp12 = new VBox();
		VBox temp13 = new VBox();
		VBox temp14 = new VBox();
		VBox temp15 = new VBox();
		VBox temp16 = new VBox();
		VBox temp17 = new VBox();
		VBox temp18 = new VBox();
		VBox temp19 = new VBox();
		VBox temp20 = new VBox();
		VBox temp21 = new VBox();
		VBox temp22 = new VBox();
		VBox temp23 = new VBox();
		VBox temp24 = new VBox();
		VBox temp25 = new VBox();
		
		
		scrollpanes.add(new ScrollPane(temp1));
		scrollpanes.add(new ScrollPane(temp2));
		scrollpanes.add(new ScrollPane(temp3));
		scrollpanes.add(new ScrollPane(temp4));
		scrollpanes.add(new ScrollPane(temp5));
		scrollpanes.add(new ScrollPane(temp6));
		scrollpanes.add(new ScrollPane(temp7));
		scrollpanes.add(new ScrollPane(temp8));
		scrollpanes.add(new ScrollPane(temp9));
		scrollpanes.add(new ScrollPane(temp10));
		scrollpanes.add(new ScrollPane(temp11));
		scrollpanes.add(new ScrollPane(temp12));
		scrollpanes.add(new ScrollPane(temp13));
		scrollpanes.add(new ScrollPane(temp14));
		scrollpanes.add(new ScrollPane(temp15));
		scrollpanes.add(new ScrollPane(temp16));
		scrollpanes.add(new ScrollPane(temp17));
		scrollpanes.add(new ScrollPane(temp18));
		scrollpanes.add(new ScrollPane(temp19));
		scrollpanes.add(new ScrollPane(temp20));
		scrollpanes.add(new ScrollPane(temp21));
		scrollpanes.add(new ScrollPane(temp22));
		scrollpanes.add(new ScrollPane(temp23));
		scrollpanes.add(new ScrollPane(temp24));
		scrollpanes.add(new ScrollPane(temp25));
		centerHBox.getChildren().addAll(scrollpanes);
		
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		SAXParser saxParser = null;
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DefaultHandler handler = new DefaultHandler()
		{
			boolean bId = false;
			boolean bNrMatricol = false;
			boolean bNume = false;
			boolean bPrenume = false;
			boolean bAn = false;
			boolean bGrupa = false;
			boolean bBursa = false;
			boolean bDataNastere = false;
			boolean bEmail = false;
			boolean bCreatedAt = false;
			boolean bUpdatedAt = false;
			boolean bId1 = false;
			boolean bIdStudent = false;
			boolean bIdCurs = false;
			boolean bValoare = false;
			boolean bDataNotare = false;
			boolean bCreatedAt1 = false;
			boolean bUpdatedAt1 = false;
			boolean bId2 = false;
			boolean bTitluCurs = false;
			boolean bAn1 = false;
			boolean bSemestru = false;
			boolean bCredite = false;
			boolean bCreatedAt2 = false;
			boolean bUpdatedAt2 = false;
			
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
			{
				if(qName.equalsIgnoreCase("ID"))
				{
					bId = true;
				}
				if(qName.equalsIgnoreCase("NR_MATRICOL"))
				{
					bNrMatricol = true;
				}
				if(qName.equalsIgnoreCase("NUME"))
				{
					bNume = true;
				}
				if(qName.equalsIgnoreCase("PRENUME"))
				{
					bPrenume = true;
				}
				if(qName.equalsIgnoreCase("AN"))
				{
					bAn = true;
				}
				if(qName.equalsIgnoreCase("GRUPA"))
				{
					bGrupa = true;
				}
				if(qName.equalsIgnoreCase("BURSA"))
				{
					bBursa = true;
				}
				if(qName.equalsIgnoreCase("DATA_NASTERE"))
				{
					bDataNastere = true;
				}
				if(qName.equalsIgnoreCase("EMAIL"))
				{
					bEmail = true;
				}
				if(qName.equalsIgnoreCase("CREATED_AT"))
				{
					bCreatedAt = true;
				}
				if(qName.equalsIgnoreCase("UPDATED_AT"))
				{
					bUpdatedAt = true;
				}
				if(qName.equalsIgnoreCase("ID_1"))
				{
					bId1 = true;
				}
				if(qName.equalsIgnoreCase("ID_STUDENT"))
				{
					bIdStudent = true;
				}
				if(qName.equalsIgnoreCase("ID_CURS"))
				{
					bIdCurs = true;
				}
				if(qName.equalsIgnoreCase("VALOARE"))
				{
					bValoare = true;
				}
				if(qName.equalsIgnoreCase("DATA_NOTARE"))
				{
					bDataNotare = true;
				}
				if(qName.equalsIgnoreCase("CREATED_AT_1"))
				{
					bCreatedAt1 = true;
				}
				if(qName.equalsIgnoreCase("UPDATED_AT_1"))
				{
					bUpdatedAt1 = true;
				}
				if(qName.equalsIgnoreCase("ID_2"))
				{
					bId2 = true;
				}
				if(qName.equalsIgnoreCase("TITLU_CURS"))
				{
					bTitluCurs = true;
				}
				if(qName.equalsIgnoreCase("AN_1"))
				{
					bAn1 = true;
				}
				if(qName.equalsIgnoreCase("SEMESTRU"))
				{
					bSemestru = true;
				}
				if(qName.equalsIgnoreCase("CREDITE"))
				{
					bCredite = true;
				}
				if(qName.equalsIgnoreCase("CREATED_AT_2"))
				{
					bCreatedAt2 = true;
				}
				if(qName.equalsIgnoreCase("UPDATED_AT_2"))
				{
					bUpdatedAt2 = true;
				}
			}
			
			public void endElement(String uri, String localName, String qName) throws SAXException
			{
				
			}
			
			public void characters(char ch[], int start, int length) throws SAXException
			{
				if(bId)
				{
					System.out.println("id: " + new String(ch, start, length));
					temp1.getChildren().add(new Label(new String(ch, start, length)));
					bId = false;
				}
				if(bNrMatricol)
				{
					System.out.println("nr_matricol: " + new String(ch, start, length));
					temp2.getChildren().add(new Label(new String(ch, start, length)));
					bNrMatricol = false;
				}
				if(bNume)
				{
					System.out.println("nume: " + new String(ch, start, length));
					temp3.getChildren().add(new Label(new String(ch, start, length)));
					bNume = false;
				}
				if(bPrenume)
				{
					System.out.println("prenume: " + new String(ch, start, length));
					temp4.getChildren().add(new Label(new String(ch, start, length)));
					bPrenume = false;
				}
				if(bAn)
				{
					System.out.println("an: " + new String(ch, start, length));
					temp5.getChildren().add(new Label(new String(ch, start, length)));
					bAn = false;
				}
				if(bGrupa)
				{
					System.out.println("grupa: " + new String(ch, start, length));
					temp6.getChildren().add(new Label(new String(ch, start, length)));
					bGrupa = false;
				}
				if(bBursa)
				{
					System.out.println("bursa: " + new String(ch, start, length));
					temp7.getChildren().add(new Label(new String(ch, start, length)));
					bBursa = false;
				}
				if(bDataNastere)
				{
					System.out.println("data_nastere: " + new String(ch, start, length));
					temp8.getChildren().add(new Label(new String(ch, start, length)));
					bDataNastere = false;
				}
				if(bEmail)
				{
					System.out.println("email: " + new String(ch, start, length));
					temp9.getChildren().add(new Label(new String(ch, start, length)));
					bEmail = false;
				}
				if(bCreatedAt)
				{
					System.out.println("created_at: " + new String(ch, start, length));
					temp10.getChildren().add(new Label(new String(ch, start, length)));
					bCreatedAt = false;
				}
				if(bUpdatedAt)
				{
					System.out.println("updated_at: " + new String(ch, start, length));
					temp11.getChildren().add(new Label(new String(ch, start, length)));
					bUpdatedAt = false;
				}
				if(bId1)
				{
					System.out.println("id_2: " + new String(ch, start, length));
					temp12.getChildren().add(new Label(new String(ch, start, length)));
					bId1 = false;
				}
				if(bIdStudent)
				{
					System.out.println("id_student: " + new String(ch, start, length));
					temp13.getChildren().add(new Label(new String(ch, start, length)));
					bIdStudent = false;
				}
				if(bIdCurs)
				{
					System.out.println("id_curs: " + new String(ch, start, length));
					temp14.getChildren().add(new Label(new String(ch, start, length)));
					bIdCurs = false;
				}
				if(bValoare)
				{
					System.out.println("valoare: " + new String(ch, start, length));
					temp15.getChildren().add(new Label(new String(ch, start, length)));
					bValoare = false;
				}
				if(bDataNotare)
				{
					System.out.println("data_notare: " + new String(ch, start, length));
					temp16.getChildren().add(new Label(new String(ch, start, length)));
					bDataNotare = false;
				}
				if(bCreatedAt1)
				{
					System.out.println("created_at_1: " + new String(ch, start, length));
					temp17.getChildren().add(new Label(new String(ch, start, length)));
					bCreatedAt1 = false;
				}
				if(bUpdatedAt1)
				{
					System.out.println("updated_at_1: " + new String(ch, start, length));
					temp18.getChildren().add(new Label(new String(ch, start, length)));
					bUpdatedAt1 = false;
				}
				if(bId2)
				{
					System.out.println("id_2: " + new String(ch, start, length));
					temp19.getChildren().add(new Label(new String(ch, start, length)));
					bId2 = false;
				}
				if(bTitluCurs)
				{
					System.out.println("titlu_curs: " + new String(ch, start, length));
					temp20.getChildren().add(new Label(new String(ch, start, length)));
					bTitluCurs = false;
				}
				if(bAn1)
				{
					System.out.println("an_1: " + new String(ch, start, length));
					temp21.getChildren().add(new Label(new String(ch, start, length)));
					bAn1 = false;
				}
				if(bSemestru)
				{
					System.out.println("semestru: " + new String(ch, start, length));
					temp22.getChildren().add(new Label(new String(ch, start, length)));
					bSemestru = false;
				}
				if(bCredite)
				{
					System.out.println("credite: " + new String(ch, start, length));
					temp23.getChildren().add(new Label(new String(ch, start, length)));
					bCredite = false;
				}
				if(bCreatedAt2)
				{
					System.out.println("created_at_2: " + new String(ch, start, length));
					temp24.getChildren().add(new Label(new String(ch, start, length)));
					bCreatedAt2 = false;
				}
				if(bUpdatedAt2)
				{
					System.out.println("updated_at_2: " + new String(ch, start, length));
					temp25.getChildren().add(new Label(new String(ch, start, length)));
					bUpdatedAt2 = false;
				}
				
			}
		};
		
		try {
			saxParser.parse("tema8.xml", handler);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pane.setCenter(centerHBox);
		
		stage.setScene(new Scene(pane, 1000, 800));
		stage.show();
	}
}
