package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.*;
import java.util.Collections;


public class Main extends Application {

    int player_y = 350; //set values for player deck
    int comp_y = 50; //set values for opponent deck
    int x = 210; //x value for center of screen for a rectangle with w = 80

    String[] suits = {"hearts", "spades", "diamonds", "clubs"};
    ArrayList<Card> deck = new ArrayList<Card>();
    ArrayList<Card> player = new ArrayList<Card>();
    ArrayList<Card> computer = new ArrayList<Card>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        createDeck();
        shuffleCards(deck);
        //printCards(deck);

        splitDeck1();
        splitDeck2();
        printCards(player);
        System.out.println("\n");
        printCards(computer);
        System.out.println("Player Cards: " + player.size() + " Computer Cards: " + computer.size() + "\n");



        //sets up root for GUI
        Group root = new Group();
        Scene scene = new Scene(root, 500,500, Color.DARKOLIVEGREEN);
        primaryStage.setTitle("WAR");

        //Button btn = new Button();
        //btn.setText("Say 'Hello World'");

        //Sets up two decks
        Rectangle r_player = new Rectangle(x, player_y, 80, 120); //creates the deck of the player, which is represented by a rectangle
        r_player.setFill(Color.RED);
        root.getChildren().add(r_player);
        Rectangle r_computer = new Rectangle(x, comp_y, 80, 120); //creates the opponent's deck
        r_computer.setFill(Color.RED);
        root.getChildren().add(r_computer);

        playerGlide(root);
        glide(root);

        r_player.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        //creates scene
        primaryStage.setScene(scene);
        primaryStage.show();

        //playWar(primaryStage);

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

    public void shuffleCards(List<Card> cards){
        Collections.shuffle(cards);
    }

    public void splitDeck1(){
        ArrayList<Card> first = new ArrayList<Card>(deck.size()/2);
        for(int i = 0; i < deck.size()/2; i++){
            computer.add(deck.get(i));
        }
    }

    public void splitDeck2(){
        ArrayList<Card> second = new ArrayList<Card>(deck.size()/2);
        for(int i = (deck.size()/2) - 1; i >= 0; i--){
            player.add(deck.get(i));
        }
    }

    public void swapCards(ArrayList<Card> winner,ArrayList<Card> loser, int index){
        for(int i = 0; i <= index; i++){ //SOOB problems occur sometimes, check it out
            winner.add(winner.remove(i));
            winner.add(loser.remove(i));
        }
    }

    public void printCards(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            System.out.println(cards.get(i).getSuit() + " " + cards.get(i).getValue() + "\t");
        }
    }

    //public boolean willWin(int index){ //Check if someone wins bjn
    //}

    public void War(int index){ //start from 0
        if(player.size() > 0 && computer.size() > 0) {
            System.out.println("player: " + player.get(index).getValue() + " computer: " + computer.get(index).getValue());
            if (player.get(index).getValue() > computer.get(index).getValue()) {
                swapCards(player, computer, index);
                System.out.println("player wins!"
                        + "\nNumber of computer cards: " + computer.size()
                        + "\nNumber of player cards: " + player.size());
            } else if (player.get(index).getValue() < computer.get(index).getValue()) {
                swapCards(computer, player, index);
                System.out.println("computer wins! " +
                        "\nNumber of computer cards: " + computer.size()
                        + "\nNumber of player cards: " + player.size());
                //computer wins
            } else {
                System.out.println("tied");
                while (index < deck.size())
                    War(index + 1);
            }
            System.out.println(" ~ ");
        }else{
            playWar();
        }
    }

    public void playWar(){
        //primaryStage.show();
        while (player.size() > 0 && computer.size() > 0){
            War(0);
        }
        if(player.size() == 0) {
            System.out.println("Computer Wins!");
            System.exit(0);
        }else if (computer.size() == 0){
            System.out.println("Player Wins!");
            System.exit(0);
        }

    }

    //Creating the mouse event handler
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("Clicked!");
            if(player.size() > 0 && computer.size() > 0){
                War(0);
            }else if(player.size() == 0) {
                System.out.println("Computer Wins!");
                System.exit(0);
            }else if (computer.size() == 0){
                System.out.println("Player Wins!");
                System.exit(0);
            }
        }
    }; //I learned how to add the clicking reaction from https://www.tutorialspoint.com/javafx/javafx_event_handling.htm



    public static void main(String[] args) {
        launch(args);
    }
}



//gliding code research from https://www.mkyong.com/javafx/javafx-animated-ball-example/