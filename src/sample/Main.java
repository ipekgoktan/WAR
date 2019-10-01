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
import javafx.scene.text.Text;
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

    String[] suits = {"hearts", "spades", "diamonds", "clubs"}; //suits array
    ArrayList<Card> deck = new ArrayList<Card>(); //creates arraylist for the cards and each split deck
    ArrayList<Card> player = new ArrayList<Card>();
    ArrayList<Card> computer = new ArrayList<Card>();

    Group root = new Group(); //creates new group

    Text t_p = new Text(10, 15, "Player Cards: "); //text that displays player cards
    Text t_c = new Text(350, 15, "Computer Cards: "); //text that displays computer cards



    @Override
    public void start(Stage primaryStage) throws Exception{
        createDeck(); //creates deck
        shuffleCards(deck); //shuffles cards randomly

        splitDeck1(); //creates first half deck
        splitDeck2(); //creates second half deck

        //prints values to terminal for troubleshooting
        printCards(player);
        System.out.println("\n");
        printCards(computer);
        System.out.println("Player Cards: " + player.size() + " Computer Cards: " + computer.size() + "\n");

        //sets up root for GUI
        Scene scene = new Scene(root, 500,500, Color.DARKOLIVEGREEN);
        primaryStage.setTitle("WAR");

        //Sets up two decks
        Rectangle r_player = new Rectangle(x, player_y, 80, 120); //creates the deck of the player, which is represented by a rectangle
        r_player.setFill(Color.DARKRED);
        root.getChildren().add(r_player);
        Rectangle r_computer = new Rectangle(x, comp_y, 80, 120); //creates the opponent's deck
        r_computer.setFill(Color.DARKRED);
        root.getChildren().add(r_computer);

        //variables that store amount of cards
        int player_cards = player.size();
        int computer_cards = computer.size();

        //adds the players card count text to root
        t_p.setFill(Color.rgb(255, 255, 255));
        root.getChildren().add(t_p);

        //adds the computers card count text to root
        t_c.setFill(Color.rgb(255, 255, 255));
        root.getChildren().add(t_c);

        //adds eventhandler to the player's rectangle that reacts when it is clicked
        r_player.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        //creates scene
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void glide(int index){ //glide animation for computers card

        Rectangle r_computer = new Rectangle(x, comp_y, 80, 120); //creates the opponent's card
        Text t_c = new Text(computer.get(index).getSuit() + "\n" + computer.get(index).getValue()); //displays value of card

        StackPane stack = new StackPane(); //stack that will store both values
        stack.getChildren().addAll(r_computer, t_c); //adds both elements to the stack
        stack.setLayoutX(x);
        stack.setLayoutY(comp_y);
        root.getChildren().add(stack); //adds stack to root

        Timeline timeline_c = new Timeline( //animation of cards
                new KeyFrame(Duration.seconds(3),new KeyValue(stack.layoutXProperty(), 100))
                ,new KeyFrame(Duration.seconds(3),new KeyValue(stack.layoutYProperty(), 200)));

        timeline_c.setCycleCount(1); //runs once
        timeline_c.play(); //plays animation
        r_computer.setFill(Color.WHITESMOKE);
    }

    public void playerGlide(int index){
        Rectangle r_p = new Rectangle(80, 120); //creates the card of the player, which is represented by a rectangle
        Text t_p = new Text(player.get(index).getSuit() + "\n" + player.get(index).getValue()); //displays value of card
        StackPane stack = new StackPane();
        stack.getChildren().addAll(r_p, t_p);
        stack.setLayoutX(x);
        stack.setLayoutY(player_y);
        root.getChildren().add(stack); //adds stack to root

        Timeline timeline_p = new Timeline(//animation of cards
                new KeyFrame(Duration.seconds(3),new KeyValue(stack.layoutXProperty(), 300))
                ,new KeyFrame(Duration.seconds(3),new KeyValue(stack.layoutYProperty(), 200)));
        timeline_p.setCycleCount(1);//runs once
        timeline_p.play();//plays animation
        r_p.setFill(Color.WHITESMOKE);
    } //I learned how to create stacks from https://stackoverflow.com/questions/46997267/how-do-i-insert-text-into-a-shape-in-javafx

    public void createDeck(){ //creates deck
        for(int v = 1; v <= 13; v++){ //runs for all faces of a card
            for(String s: suits){ //runs for all suits of a card
                deck.add(new Card(v, s)); //creates card in deck
            }
        }
    }

    public void shuffleCards(List<Card> cards){//randomizes card order in the list
        Collections.shuffle(cards);
    }

    public void splitDeck1(){ //adds first 26 cards to the computers deck
        ArrayList<Card> first = new ArrayList<Card>(deck.size()/2);
        for(int i = 0; i < deck.size()/2; i++){
            computer.add(deck.get(i));
        }
    }

    public void splitDeck2(){ //adds last 26 cards to the players deck
        ArrayList<Card> second = new ArrayList<Card>(deck.size()/2);
        for(int i = (deck.size()/2) - 1; i >= 0; i--){
            player.add(deck.get(i));
        }
    }

    public void swapCards(ArrayList<Card> winner,ArrayList<Card> loser, int index){ //exchanges cards between players after each play
        for(int i = 0; i <= index; i++){ //exchanges existing amount of ties depending on whether a tie happened
            if(i < loser.size()) {
                winner.add(winner.size() - 1, winner.remove(i)); //moves player's own cards to the end
                winner.add(loser.remove(i)); //adds loser's cards
            }
        }
    }

    public void printCards(ArrayList<Card> cards){ //prints the cards in a deck
        for(int i = 0; i < cards.size(); i++){
            System.out.println(cards.get(i).getSuit() + " " + cards.get(i).getValue() + "\t");
        }
    }

    public boolean willWin(int index, ArrayList<Card> a, ArrayList<Card> b) { //Check if someone wins bjn
        if(a.size() > 1){
            return true;
        }
            if (a.get(index).getValue() < b.get(index).getValue()) {
                return false;
            } else if (a.get(index-1).getValue() == b.get(index-1).getValue()){
                if(a.size() < index + 1) {
                    return false;
                }else {
                    return willWin(index + 1, a, b);
                }

            }else {
                return true;
            }
    }

    public void War(int index) { //plays the game

        //glide animations
        playerGlide(index);
        glide(index);


        if (player.size() > 0 && computer.size() > 0) { //THIS USED TO BE > 1
            System.out.println("player: " + player.get(index).getValue() + " computer: " + computer.get(index).getValue());
            if (player.get(index).getValue() > computer.get(index).getValue()) {
                swapCards(player, computer, index);
                System.out.println("player wins!"
                        + "\nNumber of computer cards: " + computer.size()
                        + "\nNumber of player cards: " + player.size());
            } else if (player.get(index).getValue() < computer.get(index).getValue()) { //player looses if their value is less
                swapCards(computer, player, index);
                System.out.println("computer wins! " +
                        "\nNumber of computer cards: " + computer.size()
                        + "\nNumber of player cards: " + player.size());
                //computer wins
            } else { //recursively calls self if a tie is in place
                System.out.println("tied");
                playWar(index + 1);
            }
            System.out.println(" ~ ");
        }

    }

    public void playWar(int start){  //controls the operations of the game
        if (player.size() > 0 && computer.size() > 0){ //plays the game as long as neither deck is empty
            War(start);
            if(player.size() == 0) { //computer wins if player is out of cards
                Text win = new Text(200, 250, "Computer Wins!");
                root.getChildren().add(win);
                System.out.println("Computer Wins!");
                System.out.println("Game over");
                //System.exit(0);
            }else if (computer.size() == 0){ //player wins if computer is out of cards
                Text win = new Text(200, 250,"Player Wins!");
                root.getChildren().add(win);
                System.out.println("Player Wins!");
                System.out.println("Game over");
                //System.exit(0);
            }
        }

        if(player.size() == 0) { //computer wins if player is out of cards
            Text win = new Text(200, 250,"Computer Wins!");
            System.out.println("Computer Wins!");
            root.getChildren().add(win);
            //System.exit(0);
        }else if (computer.size() == 0){ //player wins if computer is out of cards
            Text win = new Text(200, 250, "Player Wins!");
            System.out.println("Player Wins!");
            root.getChildren().add(win);
            //System.exit(0);
        }

    }

    //Creating the mouse event handler
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { //acts when the player's deck is clicked on
        @Override
        public void handle(MouseEvent e) {
                //updates card numbers
                t_p.setText("Player Cards: " + player.size());
                t_c.setText("Computer Cards: " + computer.size());

                System.out.println("Clicked!"); //puts confirmation on terminal
                if (player.size() > 0 && computer.size() > 0) {
                    playWar(0); //plays the game if neither array is empty
                } else if (player.size() == 0) { //ends game with a player winning if one of the decks are empty
                    Text win = new Text(200, 250, "Computer Wins!");
                    root.getChildren().add(win);
                    //System.exit(0);
                } else if (computer.size() == 0) {
                    Text win = new Text(200, 250, "Player Wins!");
                    root.getChildren().add(win);
                    //System.exit(0);
                }
            }

    }; //I learned how to add the clicking reaction from https://www.tutorialspoint.com/javafx/javafx_event_handling.htm



    public static void main(String[] args) {
        launch(args);
    }
}



//gliding code research from https://www.mkyong.com/javafx/javafx-animated-ball-example/