package Hangman;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import javafx.animation.RotateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {

	// Fenstgr��e und Schriftart, muss nat�rlich h�bscher
    private static final int APP_W = 1280; 
    private static final int APP_H = 720;
    private static final Font DEFAULT_FONT = new Font("Courier", 36);
    // Highscore Idee nicht schlecht
    private static final int POINTS_PER_LETTER = 100;
    private static final float BONUS_MODIFIER = 0.2f;

    /**
     * The word to guess
     */
    private SimpleStringProperty word = new SimpleStringProperty();

    /**
     * Single- or Multiplayer
     */
    private boolean isMultiplayer = true;
    
    /**
     * How many letters left to guess
     */
    private SimpleIntegerProperty lettersToGuess = new SimpleIntegerProperty();

    /**
     * Current score
     */
    private SimpleIntegerProperty score = new SimpleIntegerProperty();

    /**
     * How many points next correct letter is worth
     */
    private float scoreModifier = 1.0f;

    /**
     * Is game playable
     */
    private SimpleBooleanProperty playable = new SimpleBooleanProperty();

    /**
     * List for letters of the word {@link #word}
     * It is backed up by the HBox children list,
     * so changes to this list directly affect the GUI
     */
    private ObservableList<Node> letters;

    /**
     * K - characters [A..Z] and '-'
     * V - javafx.scene.Text representation of K
     */
    private HashMap<Character, Text> alphabet = new HashMap<Character, Text>();

    private HangmanImage hangman = new HangmanImage();

    private DBReader wordReader = new DBReader();

    private int[] kategorieIDs;
    
    private int maxFehlversuche;
    
    private SimpleStringProperty name1;
    
    private SimpleStringProperty name2;
    
    private SimpleIntegerProperty score1;
    
    private SimpleIntegerProperty score2;
    
    private boolean isPlayer1;
    
    private SimpleStringProperty currentPlayername;
    
    private Integer[] auswahlKategorieIDs;
    
    public Game(boolean isMulpiplayer, String name1, String name2, int maxFehlversuche, Integer auswahlKategorieIDs[]) {
    	this.isMultiplayer = isMulpiplayer;
    	this.maxFehlversuche = maxFehlversuche;
    	this.name1 = new SimpleStringProperty(name1);	
    	this.name2 = new SimpleStringProperty(name2);	
    	this.score1 = new SimpleIntegerProperty(0);
    	this.score2 = new SimpleIntegerProperty(0);
    	this.isPlayer1 = true;
    	this.currentPlayername = new SimpleStringProperty(getCurrentPlayername());
    	this.auswahlKategorieIDs = auswahlKategorieIDs;
    }
    
    public Parent createContent() throws Exception{
        HBox rowLetters = new HBox();
        rowLetters.setAlignment(Pos.CENTER);
        letters = rowLetters.getChildren();

        playable.bind(hangman.lives.greaterThan(0).and(lettersToGuess.greaterThan(0)));
        playable.addListener((obs, old, newValue) -> {
            if (!newValue.booleanValue()) {
	            if (isMultiplayer) {
	            	switchPlayer();
	            } else {
	            	stopGame();
	            }
            }
        });

        Button btnAgain = new Button("NEW GAME");
        btnAgain.setAlignment(Pos.CENTER);
        btnAgain.disableProperty().bind(playable);
        btnAgain.setOnAction(new EventHandler<ActionEvent>(){
        
			@Override
			public void handle(ActionEvent event) {
				try {
					try {
						startGame();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        });
        // ActionEvent event -> startGame()

        // layout
        HBox row1 = new HBox();
        HBox row3 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        for (int i = 0 ; i < 20; i++) {
            row1.getChildren().add(new Letter(' '));
            row3.getChildren().add(new Letter(' '));
        }

        HBox rowAlphabet = new HBox(5);
        rowAlphabet.setAlignment(Pos.CENTER);
        for (char c = 'A'; c <= 'Z'; c++) {
            Text t = new Text(String.valueOf(c));
            t.setFont(DEFAULT_FONT);
            alphabet.put(c, t);
            rowAlphabet.getChildren().add(t);
        }

        Text hyphen = new Text("-");
        hyphen.setFont(DEFAULT_FONT);
        alphabet.put('-', hyphen);
        rowAlphabet.getChildren().add(hyphen);
        
        Text textNameplate = new Text();
        textNameplate.textProperty().bind(currentPlayername);
        
        Text textScore = new Text();
        textScore.textProperty().bind(score.asString().concat(" Points"));
        
        HBox rowScore = new HBox(10, textNameplate, textScore); 
        rowScore.setAlignment(Pos.CENTER);
        
        HBox rowHangman = new HBox(10, hangman); //TODO hangman aus hbox raus
        rowHangman.setAlignment(Pos.CENTER);
        
        
        VBox vBox = new VBox(10);
        // vertical layout
        vBox.getChildren().addAll(
                row1,
                rowLetters,
                row3,
                rowAlphabet,
                rowScore,
                rowHangman,
                btnAgain);	//TODO btnAgain mittig?
        return vBox;
    }

    private void stopGame() {
        for (Node n : letters) {
            Letter letter = (Letter) n;
            letter.show();
        }
    }

    private void switchPlayer() {
    	isPlayer1 =!isPlayer1;
    	currentPlayername.set(getCurrentPlayername());
    	try {
			startGame();
		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void startGame() throws SQLException, InterruptedException {				
        for (Text t : alphabet.values()) {
            t.setStrikethrough(false);
            t.setFill(Color.BLACK);
        }

        hangman.reset();
        
        if(isMultiplayer) {
        	word.set(getWordFromHuman());
        } else {
        	word.set(wordReader.getRandomWord(kategorieIDs).toUpperCase());   
        }
        
        lettersToGuess.set(word.length().get());

        letters.clear();
        for (char c : word.get().toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    private static class HangmanImage extends Parent {
        private static final int SPINE_START_X = 100;
        private static final int SPINE_START_Y = 20;
        private static final int SPINE_END_X = SPINE_START_X;
        private static final int SPINE_END_Y = SPINE_START_Y + 50;

        /**
         * How many lives left
         */
        private SimpleIntegerProperty lives = new SimpleIntegerProperty();

        public HangmanImage() {
            Circle head = new Circle(20);
            head.setTranslateX(SPINE_START_X);

            Line spine = new Line();
            spine.setStartX(SPINE_START_X);
            spine.setStartY(SPINE_START_Y);
            spine.setEndX(SPINE_END_X);
            spine.setEndY(SPINE_END_Y);

            Line leftArm = new Line();
            leftArm.setStartX(SPINE_START_X);
            leftArm.setStartY(SPINE_START_Y);
            leftArm.setEndX(SPINE_START_X + 40);
            leftArm.setEndY(SPINE_START_Y + 10);

            Line rightArm = new Line();
            rightArm.setStartX(SPINE_START_X);
            rightArm.setStartY(SPINE_START_Y);
            rightArm.setEndX(SPINE_START_X - 40);
            rightArm.setEndY(SPINE_START_Y + 10);

            Line leftLeg = new Line();
            leftLeg.setStartX(SPINE_END_X);
            leftLeg.setStartY(SPINE_END_Y);
            leftLeg.setEndX(SPINE_END_X + 25);
            leftLeg.setEndY(SPINE_END_Y + 50);

            Line rightLeg = new Line();
            rightLeg.setStartX(SPINE_END_X);
            rightLeg.setStartY(SPINE_END_Y);
            rightLeg.setEndX(SPINE_END_X - 25);
            rightLeg.setEndY(SPINE_END_Y + 50);

            getChildren().addAll(head, spine, leftArm, rightArm, leftLeg, rightLeg);
            lives.set(getChildren().size());
        }

        public void reset() {
            getChildren().forEach(node -> node.setVisible(false));
            lives.set(getChildren().size());
        }

        public void takeAwayLife() {
            for (Node n : getChildren()) {
                if (!n.isVisible()) {
                    n.setVisible(true);
                    lives.set(lives.get() - 1);
                    break;
                }
            }
        }
    }

    private static class Letter extends StackPane {
        private Rectangle bg = new Rectangle(40, 60);
        private Text text;

        public Letter(char letter) {
            bg.setFill(letter == ' ' ? Color.DARKSEAGREEN : Color.WHITE);
            bg.setStroke(Color.BLUE);

            text = new Text(String.valueOf(letter).toUpperCase());
            text.setFont(DEFAULT_FONT);
            text.setVisible(false);

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }

        public void show() {
            RotateTransition rt = new RotateTransition(Duration.seconds(1), bg);
            rt.setAxis(Rotate.Y_AXIS);
            rt.setToAngle(180);
            rt.setOnFinished(event -> text.setVisible(true));
            rt.play();
        }

        public boolean isEqualTo(char other) {
            return text.getText().equals(String.valueOf(other).toUpperCase());
        }
    }

    public String getWordFromHuman() {
    	TextInputDialog dialog = new TextInputDialog("Suchwort");
		dialog.setTitle("Suchworteingabe");
		dialog.setHeaderText("Bitte Suchwort f�r " + getCurrentPlayername() + " eingeben:");
		dialog.setContentText("Suchwort:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return result.get();
		} else {
			return getWordFromHuman();
		}
    }
    
    private String getCurrentPlayername() {
    	if (isPlayer1) {
    		return name1.getValue();
    	} else {
    		return name2.getValue();
    	}
    } 	
    
    public void start(Stage primaryStage) throws Exception {
        Scene scene2 = new Scene(createContent());
        scene2.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;

            char pressed = event.getText().toUpperCase().charAt(0);
            if ((pressed < 'A' || pressed > 'Z') && pressed != '-')
                return;

            if (playable.get()) {
                Text t = alphabet.get(pressed);
                if (t.isStrikethrough())
                    return;

                // mark the letter 'used'
                t.setFill(Color.BLUE);
                t.setStrikethrough(true);

                boolean found = false;

                for (Node n : letters) {
                    Letter letter = (Letter) n;
                    if (letter.isEqualTo(pressed)) {
                        found = true;
                        score.set(score.get() + (int)(scoreModifier * POINTS_PER_LETTER));
                        lettersToGuess.set(lettersToGuess.get() - 1);
                        letter.show();
                    }
                }

                if (!found) {
                    hangman.takeAwayLife();
                    scoreModifier = 1.0f;
                }
                else {
                    scoreModifier += BONUS_MODIFIER;
                }
            }
        });

        //Szenenaufbau
        primaryStage.setResizable(false);
        primaryStage.setWidth(APP_W);
        primaryStage.setHeight(APP_H);
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene2);
        primaryStage.show();
        startGame();
    }

/*	@Override
	public void run() {
		try {
			start(MainMenu.getprimaryStage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


    public static void main(String[] args) {
        launch(args);
    } */
} 