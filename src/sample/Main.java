package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class Main extends Application {

    int player_y = 350; //set values for player deck
    int comp_y = 50; //set values for opponent deck
    int x = 210; //x value for center of screen for a rectangle with w = 80

    String[] suits = {"hearts", "spades", "diamonds", "clubs"};
    ArrayList<Card> deck = new ArrayList<Card>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        createDeck();
        printCards(deck);

        //sets up root for GUI
        Group root = new Group();
        Scene scene = new Scene(root, 500,500, Color.DARKOLIVEGREEN);
        primaryStage.setTitle("WAR");

        //Sets up two decks
        Rectangle r_player = new Rectangle(x, player_y, 80, 120); //creates the deck of the player, which is represented by a rectangle
        r_player.setFill(Color.RED);
        root.getChildren().add(r_player);
        Rectangle r_computer = new Rectangle(x, comp_y, 80, 120); //creates the opponent's deck
        r_computer.setFill(Color.RED);
        root.getChildren().add(r_computer);

        playerGlide(root);
        glide(root);

        //creates scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void glide(Group root){
        Pane canvas = new Pane();

        //creates two rectangles on top of each deck

        Rectangle r_computer = new Rectangle(x, comp_y, 80, 120); //creates the opponent's deck
        r_computer.setFill(Color.DARKRED);
        root.getChildren().add(r_computer);

        Timeline timeline_c = new Timeline(
                new KeyFrame(Duration.seconds(3),new KeyValue(r_computer.layoutXProperty(), -100))
                ,new KeyFrame(Duration.seconds(3),new KeyValue(r_computer.layoutYProperty(), 150)));

        timeline_c.setCycleCount(1);
        timeline_c.play();
        //r_computer.setFill(Color.WHITESMOKE);

    }

    public void playerGlide(Group root){
        Rectangle r_p = new Rectangle(x, player_y, 80, 120); //creates the deck of the player, which is represented by a rectangle
        r_p.setFill(Color.DARKRED);
        root.getChildren().add(r_p);

        Timeline timeline_p = new Timeline(
                new KeyFrame(Duration.seconds(3),new KeyValue(r_p.layoutXProperty(), 100))
                ,new KeyFrame(Duration.seconds(3),new KeyValue(r_p.layoutYProperty(), -150)));
        timeline_p.setCycleCount(1);
        timeline_p.play();
        //r_p.setFill(Color.WHITESMOKE);
    }

    public void createDeck(){
        for(int v = 1; v <= 13; v++){
            for(String s: suits){
                deck.add(new Card(v, s));
            }
        }
    }

    public void suffleCards(){
        
    }

    public void printCards(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            System.out.println(cards.get(i).getSuit() + " " + cards.get(i).getValue() + "\t");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}



//gliding code research from https://www.mkyong.com/javafx/javafx-animated-ball-example/