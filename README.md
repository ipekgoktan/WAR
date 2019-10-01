# WAR

* I started off with creating the animation of the card placement. I created two rectangles that represent each 
deck and created two methods (one for the user & the other for the computer) that created a similar triangle on top of the deck that glides towards the center. 
  I used this website as a reference: https://www.mkyong.com/javafx/javafx-animated-ball-example/
  
* I started adding simple logic to the game by creating a class for cards and creating array lists that represent decks

* I created methods that create a deck with individual card objects, shuffle decks and split the main deck into two parts

* I created the swap cards method that I will use to exchange cards when a player wins

* I created the war function that compares the cards of the players and either swaps cards if one player wins or plays again if the game is tied. I updated the swap method to swap a series of cards so that I can use it if the game is tied.

* I created the playWar function that checks to see if one of the players has lost and either keeps playing or ends the game

* I linked the animations to the gameplay by calling the glide methods that I had created the first week into my war method. I also created texts that display the amount of cards each player has 

* I modified the glide method so that the cards display their value. I discovered that creating a stack would be the best way the handle their collective animation, I learned how to create the stacks that included the rectangle and text from https://stackoverflow.com/questions/46997267/how-do-i-insert-text-into-a-shape-in-javafx

* I linked my gameplay so that it works when the user clicks on their card. I learned about eventhandlers at https://www.tutorialspoint.com/javafx/javafx_event_handling.htm

* My code keeps having index out of bound problems. I created a willWin method to check if anyone wins before the game is played. This helps me avoid checking extra cards if a player does not have anough cards to keep going.

* My code still has index out of bounds problems. I added if else methods to check the bounds into the War and PlayWar functions to stop the problem but it does not seem to solve all cases

* I noticed that my event handler for the click was linked to the war function and not the playwar function, which meant that it was skipping my initial checks.

* I traced my issues back to the while loop within the tied case in the if else statement. This while loop runs continuously and calls war instead of payWar, which skips all the checks and results in a SOOB issue.

* I integrated checks that end the game in the case of the players being tied and one player running out of cards

* I completely removed the while loops from the tied case and simply modified the playWar method to take in an index so that I can recursively call playWar and continue to check for endcases.

* This seemed to fix my code! It now runs without problems, the animation skips the tied cases and only represents a single play in a tied series, but all other events are integrated into it. A end result is printed on screen and clicking the card no longer draws cards from the decks. 
