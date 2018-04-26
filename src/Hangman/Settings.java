package Hangman;

import javafx.stage.*;
import javafx.util.Pair;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;

import java.sql.SQLException;
import java.util.Optional;

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
				System.out.println("ausgewählt ist " + newValue);
				//tableViewWörter.getItems().addAll(new DBReader().getWörter());
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
        kategorieHinzufügenButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Kategorie hinzufügen");
				dialog.setHeaderText("Welche Kategorie möchten Sie hinzufügen");
				dialog.setContentText("Kategorie:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    System.out.println("Neue Kategorie hinzugefügt: " + result.get());
				    new DBReader().addKategorie(result.get());
				}

				// The Java 8 way to get the response value (with lambda expression).
				//result.ifPresent(name -> System.out.println("Your name: " + name));
			}
        	
		});
        // Kategorie löschen Button
        Button kategorieLöschenButton = new Button("Löschen");
        
        //HBOX Kategorie bearbeiten
        HBox kategorieBearbeitung = new HBox(20);
        kategorieBearbeitung.getChildren().addAll(kategorieHinzufügenButton, kategorieLöschenButton);
        GridPane.setConstraints(kategorieBearbeitung, 0, 4);
        
        
        
        //Wort hinzufügen/löschen Label
        Label wortBearbeitenLabel = new Label("Wort bearbeiten:");
        GridPane.setConstraints(wortBearbeitenLabel, 1, 3);
        
        // Wort bearbeiten Button
        Button wortBearbeitenButton = new Button("Beartbeiten");
        
        // Wort löschen Button
        Button wortLöschenButton = new Button("Löschen");
        
        //Hinzufügen Button
        Button wortHinzufügenButton = new Button("Hinzufügen");
        wortHinzufügenButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub TRENNUNG
				// Custom Dialog
				Dialog<Pair<String, String>> dialog = new Dialog<>();
				dialog.setTitle("Wort hinzufügen");
				dialog.setHeaderText("Geben Sie die benötigten Daten ein");
				
				//Set Button Types
				ButtonType wortHinzufuegen = new ButtonType("Hinzufügen", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(wortHinzufuegen, ButtonType.CANCEL);
				
				// Textinputfelder anlegen
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				TextField wort = new TextField();
				wort.setPromptText("Wort");
				TextField umschreibung = new TextField();
				umschreibung.setPromptText("Umschreibung");
				listViewKategorien = new ListView<>();
				listViewKategorien.getItems().addAll(new DBReader().getKategorien());
				listViewKategorien.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		        GridPane.setConstraints(listViewKategorien, 1, 2);
				grid.add(new Label("Wort:"), 0, 0);
				grid.add(wort, 1, 0);
				grid.add(new Label("Umschreibung:"), 0, 1);
				grid.add(umschreibung, 1, 1);
				
				//TODO Listview einbinden
				dialog.getDialogPane().setContent(grid);
				Optional<Pair<String, String>> result = dialog.showAndWait();
				//Optional<>;
				/*TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Wort hinzufügen");
				dialog.setHeaderText("Welches Wort möchten Sie hinzufügen");
				dialog.setContentText("Wort:");
				dialog.setContentText("Umschreibung:");
				
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    System.out.println("Neues Wort hinzugefügt: " + result.get());
			//	    new DBReader().addWort(result.get());
				}
			//	new DBReader().addKategorie(kategorieInput.getText());
			//	listView.getItems().add(kategorieInput.getText());
				
				listViewKategorien.getSelectionModel().getSelectedIndex();
			//	new DBReader().addWort(wortInput.getText(), umschreibungInput.getText(), listView.getSelectionModel().getSelectedIndex()); */
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

	


	