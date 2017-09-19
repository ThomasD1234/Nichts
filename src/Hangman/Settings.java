package Hangman;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class Settings implements Runnable{

	ListView<String> listViewKategorien;
	TableView<String> tableViewWörter;
	
	public void display() {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Spieleinstellungen");
        primaryStage.setMinWidth(250);
        
        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        //Bestehende Kategorien Label
        Label kategorieAuswahlLabel = new Label("Bestehende Kategorien:");
        GridPane.setConstraints(kategorieAuswahlLabel, 0, 0);
        
        //Kategorie Auswahl
        listViewKategorien = new ListView<>();
        listViewKategorien.getItems().addAll(new DBReader().getKategorien());
        listViewKategorien.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewKategorien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String >() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub ===> hier übergeben des newValue an die tableview der wörter
				System.out.println("ausgewählt ist" + newValue);
			}
        }) ;
        GridPane.setConstraints(listViewKategorien, 0, 1);
        
        //Bestehende Kategorien Label
        Label wortAuswahlLabel = new Label("Bestehende Wörter:");
        GridPane.setConstraints(wortAuswahlLabel, 1, 0);
        
        //Bestehende Wörter in DB
        tableViewWörter = new TableView<>();
       // tableViewWörter.getItems().adsAll(new DBReader().getWords(int));
        GridPane.setConstraints(tableViewWörter, 1, 1);
        
        //Wörter Spalte
        TableColumn wordCol = new TableColumn("Wort");
        wordCol.setMinWidth(200);
        TableColumn umschreibungsCol = new TableColumn("Umschreibung");
        umschreibungsCol.setMinWidth(300);
        
        tableViewWörter.getColumns().addAll(wordCol, umschreibungsCol);
        
        
        //Kategorie hinzufügen/löschen Label
        Label kategorieBearbeitenLabel = new Label("Kategorie bearbeiten:");
        GridPane.setConstraints(kategorieBearbeitenLabel, 0, 3);
        
        //Kategorie hinzufügen Button
        Button kategorieHinzufügenButton = new Button("Hinzufügen");
       
        // Kategorie löschen Button
        Button kategorieLöschenButton = new Button("Löschen");
        
        //HBOX Kategorie bearbeiten
        HBox kategorieBearbeitung = new HBox(20);
        kategorieBearbeitung.getChildren().addAll(kategorieHinzufügenButton, kategorieLöschenButton);
        GridPane.setConstraints(kategorieBearbeitung, 0, 4);
        
        
        
        //Wort hinzufügen/löschen Label
        Label wortBearbeitenLabel = new Label("Wort bearbeiten:");
        GridPane.setConstraints(wortBearbeitenLabel, 1, 3);
        
        // Wort löschen Button
        Button wortBearbeitenButton = new Button("Beartbeiten");
        
        // Wort löschen Button
        Button wortLöschenButton = new Button("Löschen");
        
        //Hinzufügen Button
        Button wortHinzufügenButton = new Button("Hinzufügen");
  //      GridPane.setConstraints(wortHinzufügenButton, 1, 5);
  //      hinzufügenButton.setOnAction(e -> new DBReader().addKategorie(kategorieInput.getText()));
        wortHinzufügenButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub TRENNUNG
			//	new DBReader().addKategorie(kategorieInput.getText());
			//	listView.getItems().add(kategorieInput.getText());
				
				listViewKategorien.getSelectionModel().getSelectedIndex();
			//	new DBReader().addWort(wortInput.getText(), umschreibungInput.getText(), listView.getSelectionModel().getSelectedIndex());
			}
		});
        
        //HBox Wörter Bearbeiten
        HBox wortBearbeitung = new HBox(30);
        wortBearbeitung.getChildren().addAll(wortHinzufügenButton, wortBearbeitenButton, wortLöschenButton);
        GridPane.setConstraints(wortBearbeitung, 1, 4);
        
        
        
        grid.getChildren().addAll(kategorieAuswahlLabel, wortAuswahlLabel, listViewKategorien, tableViewWörter, kategorieBearbeitenLabel, kategorieBearbeitung, wortBearbeitenLabel, wortBearbeitung);
        
        grid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}	

	
/* ALTES ZEUGS
 * 
   // Top Label - constrains use (child, column, row)
        Label topLabel = new Label("Wörter hinzufügen");
        GridPane.setConstraints(topLabel, 0, 0);
        
        //Wort Label 
        Label wortLabel = new Label("Zu eratendes Wort:");
        GridPane.setConstraints(wortLabel, 0, 1);
        
        //Umschreibung Label 
        Label umschreibungLabel = new Label("Suchwort:");
        GridPane.setConstraints(umschreibungLabel, 0, 2);
        
        //Wort Input 
        TextField wortInput = new TextField("Wort");
        GridPane.setConstraints(wortInput, 1, 1);
        
        //Umschreibung Input 
        TextField umschreibungInput = new TextField("Umschreibung");
        GridPane.setConstraints(umschreibungInput, 1, 2);
  
  		//neue Kategorien Label
        Label neueKategorieAuswahlLabel = new Label("Bestehende Kategorien:");
        GridPane.setConstraints(neueKategorieAuswahlLabel, 0, 4);
        
        //Kategorie hinzufügen
        TextField kategorieInput = new TextField("Neue Kategorie");
        GridPane.setConstraints(kategorieInput, 1, 4);
  
  
 
 * */

	