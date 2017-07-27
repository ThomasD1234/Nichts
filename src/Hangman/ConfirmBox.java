package Hangman;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;

public class ConfirmBox implements Runnable{

	static ListView<String> listView;
	
    public static void display() {
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
        Label topLabel = new Label("Wählen Sie Ihre gewünschten Einstellungen");
        GridPane.setConstraints(topLabel, 0, 0);

        //Name Label 
        Label nameLabel = new Label("Spielername:");
        GridPane.setConstraints(nameLabel, 0, 1);

        //Name Input
        TextField nameInput = new TextField("Bucky");
        GridPane.setConstraints(nameInput, 1, 1);
      
        //Schwierigkeitsgrad Label
        Label skillLabel = new Label("Gewünschter Schwierigkeitsgrad:");
        GridPane.setConstraints(skillLabel, 0, 2);
        
        //Schwierigkeitsgrad Auswahl
        ChoiceBox<String> choiceBoxSchwierigkeit = new ChoiceBox<>();
        choiceBoxSchwierigkeit.getItems().addAll("Einfach", "Normal", "Schwer", "Benutzerdefiniert");
        choiceBoxSchwierigkeit.setValue("Normal");
        GridPane.setConstraints(choiceBoxSchwierigkeit, 1, 2);
    /*  Falls die Auswahl live übergeben werden soll    
    *   choiceBoxSchwierigkeit.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> System.out.println(newValue) );
    */  
        
        
        
        //Fehlversuche Label 
        Label fehlerLabel = new Label("Maximale Anzahl Fehlversuche:");
        GridPane.setConstraints(fehlerLabel, 0, 3);
        
        //Fehlversuche Auswahl
        ChoiceBox<String> choiceBoxFehlversuche = new ChoiceBox<>();
        choiceBoxFehlversuche.getItems().addAll("12", "10", "8", "6", "4");
        choiceBoxFehlversuche.setValue("8");
        GridPane.setConstraints(choiceBoxFehlversuche, 1, 3);
       
        //Wortkategorie Label
        Label kategorieLabel = new Label("Gewünschte Wortkategorie:");
        GridPane.setConstraints(kategorieLabel, 0, 4);
        
        //Wortkategorie Auswahl
        listView = new ListView<>();
        listView.getItems().addAll("Berühmtheiten", "Biologie", "Geographie", "Fantasy");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        GridPane.setConstraints(listView, 1, 4);
        
        //Einstellungen bestätigen Label
        Label korrektLabel = new Label("Alle Einstellungen korrekt?");
        GridPane.setConstraints(korrektLabel, 0, 5);
        
        //Einstellungen bestätigt Checkbox
        CheckBox box = new CheckBox();
        GridPane.setConstraints(box, 1, 5);
        
        //Button zum Gamestart
        Button startButton = new Button("Spiel starten");
        GridPane.setConstraints(startButton, 2, 5);
        startButton.setOnAction(e -> handleOptions(box));	
        
        /* startButton.setOnAction(e -> System.out.println(nameInput.getText()));
        	   
        	
        */
        
        //Add Interface
        grid.getChildren().addAll(topLabel, nameLabel, nameInput, skillLabel, choiceBoxSchwierigkeit, fehlerLabel, choiceBoxFehlversuche, kategorieLabel, listView, korrektLabel, box, startButton);
        grid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        //Make sure to return answer
        //return answer;
        
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

	private static void handleOptions(CheckBox box){
        String message = "User hat ";

        if(box.isSelected())
            message += "bestätigt" + "\n";
        else message += "nicht bestätigt" + "\n";
        
        /*
        Zugriff auf Kategorien    */
        ObservableList<String> kategorien;
        kategorien = listView.getSelectionModel().getSelectedItems();
        
        for(String k: kategorien){
        	message +=k + "\n";
        }
        	
    
        
        System.out.println(message);
    }

}
