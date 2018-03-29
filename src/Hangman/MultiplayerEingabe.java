package Hangman;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class MultiplayerEingabe implements Runnable {

	ListView<String> listView;
	String playerName;

	public MultiplayerEingabe(String playerName) {
		this.playerName = playerName;
	}

	public void display() {
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle("Spieleinstellungen");
		primaryStage.setMinWidth(250);

		// GridPane with 10px padding around edge
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		// Labels
		// Top Label - Multiplayer
		Label topLabelMP = new Label(playerName + " gib dein Suchwort ein.");
		GridPane.setConstraints(topLabelMP, 0, 0);

		// Inputs
		// Spieler Input SinglePlayer
		TextField wordInput = new TextField("Suchwort");
		GridPane.setConstraints(wordInput, 0, 1);

		// Buttons
		// Button zum Gamestart
		Button startButton = new Button("Runde beginnen");
		GridPane.setConstraints(startButton, 1, 2);
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO suchwortübergabe über db? benni ist ehrlich. jaein
			}
		});

		grid.setAlignment(Pos.CENTER);
		Scene scene3 = new Scene(grid);
		primaryStage.setScene(scene3);
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
