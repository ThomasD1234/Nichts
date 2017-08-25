package Hangman;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;

public class Settings implements Runnable{

	ListView<String> listView;
	
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
        
        //Bestehende Kategorien Label
        Label kategorieAuswahlLabel = new Label("Bestehende Kategorien:");
        GridPane.setConstraints(kategorieAuswahlLabel, 0, 3);
        
        //Kategorie Auswahl
        listView = new ListView<>();
        listView.getItems().addAll("Tiere", "Berühmtheiten", "Biologie", "Geographie", "Fantasy");
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        GridPane.setConstraints(listView, 1, 3);
        
        //neue Kategorien Label
        Label neueKategorieAuswahlLabel = new Label("Bestehende Kategorien:");
        GridPane.setConstraints(neueKategorieAuswahlLabel, 0, 4);
        
        //Kategorie hinzufügen
        TextField kategorieInput = new TextField("Neue Kategorie");
        GridPane.setConstraints(kategorieInput, 1, 4);
        
        //Hinzufügen Button
        Button hinzufügenButton = new Button("Wort Hinzufügen");
        GridPane.setConstraints(hinzufügenButton, 1, 5);
                
        grid.getChildren().addAll(topLabel, wortLabel, wortInput, umschreibungLabel,  umschreibungInput, kategorieAuswahlLabel, listView, neueKategorieAuswahlLabel, kategorieInput, hinzufügenButton);
        
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

	

	