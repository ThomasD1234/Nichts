package Hangman;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class SpieleTyp implements Runnable{

	ListView<String> listView;
	boolean isMultiPlayer;
	
	public SpieleTyp(boolean isMultiPlayer) {
		this.isMultiPlayer = isMultiPlayer; 
	}
	
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

//Labels        
        // Top Label - constrains use (child, column, row)
        Label topLabel = new Label("W�hlen Sie Ihre gew�nschten Einstellungen");
        GridPane.setConstraints(topLabel, 0, 0);
        
        //Spieler Label SinglePlayer
        Label nameLabel = new Label("Spielername:");
        GridPane.setConstraints(nameLabel, 0, 1);
        
        //Spieler1 Label 
        Label nameLabel1 = new Label("Spieler 1:");
        GridPane.setConstraints(nameLabel1, 0, 1);

        //Spieler2 Label 
        Label nameLabel2 = new Label("Spieler 2:");
        GridPane.setConstraints(nameLabel2, 0, 2);
        
        //Fehlversuche Label 
        Label fehlerLabel = new Label("Maximale Anzahl Fehlversuche:");
        GridPane.setConstraints(fehlerLabel, 0, 3);

        //Wortkategorie Label
        Label kategorieLabel = new Label("Gew�nschte Wortkategorie:");
        GridPane.setConstraints(kategorieLabel, 0, 4);
        
        //KategorieAuswahl Label
        Label kategorieAuswahlLabel = new Label("Kategorien w�hlen:");
        GridPane.setConstraints(kategorieAuswahlLabel, 0, 5);
        
//Inputs        
        //Spieler Input SinglePlayer
        TextField nameInput = new TextField("Lucky");
        GridPane.setConstraints(nameInput, 1, 1);
        
        //Spieler2 Input
        TextField nameInput2 = new TextField("Fucky");
        GridPane.setConstraints(nameInput2, 1, 2);
        
         //Spieler1 Input
        TextField nameInput1 = new TextField("Bucky");
        GridPane.setConstraints(nameInput1, 1, 1);
        
//Auswahlen        
        //Fehlversuche Auswahl
        ChoiceBox<String> choiceBoxFehlversuche = new ChoiceBox<>();
        choiceBoxFehlversuche.getItems().addAll("12", "10", "8");
        choiceBoxFehlversuche.setValue("8");
        GridPane.setConstraints(choiceBoxFehlversuche, 1, 3);
                
        //Wortkategorie Auswahl
        listView = new ListView<>();
        listView.getItems().addAll(new DBReader().getKategorien());
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        GridPane.setConstraints(listView, 1, 4);
       
       
//Buttons
        //SelectAll Button (Kategorieauswahl)
        Button selectAllButton = new Button("Alles Ausw�hlen");
        selectAllButton.setOnAction(e -> listView.getSelectionModel().selectAll());
        
        //DeSelectAll Button (Kategorieauswahl)
        Button deselectAllButton = new Button("Alles Abw�hlen");
        deselectAllButton.setOnAction(e -> listView.getSelectionModel().clearSelection());
        
        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(selectAllButton, deselectAllButton);
        GridPane.setConstraints(hBox, 1, 5);
        
        
        
      //MultiplayerButton zur ersten Worteingabe		
      	Button multiplayerButton = new Button("Spiel starten");
        GridPane.setConstraints(multiplayerButton, 2, 5);

      		multiplayerButton.setOnAction(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					  try {
						new Game(isMultiPlayer, nameInput1.getText(), nameInput2.getText(), Integer.parseInt(choiceBoxFehlversuche.getValue()), new Integer[0]).start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	      			
				}
			}); 
        
      		
      	//Button zum Gamestart f�r SinglePlayer
        Button startButton = new Button("Spiel starten");
        GridPane.setConstraints(startButton, 2, 5);	
        startButton.setOnAction(new EventHandler<ActionEvent>() {
        
			@Override
			public void handle(ActionEvent event) {
				try {
					Integer[] auswahlKategorieIDs = new Integer[0]; 
					auswahlKategorieIDs = listView.getSelectionModel().getSelectedIndices().toArray(auswahlKategorieIDs);
					for ( int i = 0; i < auswahlKategorieIDs.length; i++  ) {
						auswahlKategorieIDs[i] = auswahlKategorieIDs[i] + 1; 
					}
					new Game(isMultiPlayer, nameInput.getText(), new String(), Integer.parseInt((String) choiceBoxFehlversuche.getValue()), auswahlKategorieIDs).start(primaryStage);
					
					
					listView.getSelectionModel().getSelectedItems().toArray(new String[0]);
			        String[] testarray = listView.getSelectionModel().getSelectedItems().toArray(new String[0]);
			        
					//TODO 	Spieleinstellungen �bernehmen 	
					// (new Thread(new Game())).start();
				} catch (Exception e) {
					e.printStackTrace();
				};
		
		
				// TODO Auto-generated method stub
				//new DBReader().addKategorie(kategorieInput.getText());
				//listView.getItems().add(kategorieInput.getText());
			}
		});
 //       startButton.setOnAction(e -> new Game());
  //    startButton.setOnAction(e -> System.out.println("Es Spielen " + nameInput1.getText() + " gegen " + nameInput2.getText() + " in den Kategorien " +
  //      	listView.getSelectionModel().getSelectedItems() + ". Die maximale Anzahl Fehlversuche betr�gt: " + choiceBoxFehlversuche.getSelectionModel().getSelectedItem()));
        
        // TODO: Set und Get Settings
        
        //TODO: multiplayer und singleplayer confirmbox bauen ==> boolean dann f�r gameinstanz
        
        if (isMultiPlayer) {
        	//true = MP
        	grid.getChildren().addAll(topLabel, nameLabel1, nameInput1, nameLabel2, nameInput2, fehlerLabel, choiceBoxFehlversuche, multiplayerButton);
        }
        else {
        	grid.getChildren().addAll(topLabel, nameLabel, nameInput, fehlerLabel, choiceBoxFehlversuche, kategorieLabel, listView, kategorieAuswahlLabel, hBox, startButton);
        }
       
        //Add Interface
        
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


