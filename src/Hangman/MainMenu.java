package Hangman;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    
    private static Stage pStage;
    
    private List<Pair<String, Runnable>> menuData = Arrays.asList(				
    		new Pair<String, Runnable>("Single Player", new	SpieleTyp(false)),	
    	    new Pair<String, Runnable>("Multiplayer", new SpieleTyp(true)),
            new Pair<String, Runnable>("Wörterdatenbank", new Settings()),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    
    
    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;
   
    
    private Parent createContent(Stage primaryStage) {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    private void addBackground() {
        ImageView imageView = new ImageView(new Image(getClass().getResource("res/Civ6_bg.png").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    private void addTitle() {
        MenuTitle title = new MenuTitle("Hangman");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 160); 
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());					
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
           
        });

        root.getChildren().add(menuBox);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setprimaryStage(primaryStage);
    	Scene scene1 = new Scene(createContent(primaryStage));
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene1);
        primaryStage.show();
        
       
    }

    public static void main(String[] args) {
    	launch(args);
    }
    
    private static void setprimaryStage(Stage primaryStage) {
    	pStage = primaryStage;
    }
    
    public static Stage getprimaryStage() {
    	return pStage;
    }
    

}