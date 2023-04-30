import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class App {
    public static void main(String[] args) {


        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Skyjo game");
        window.setLayout(null);
        window.setSize(5000, 5000);
        
        //PNG size : 1556*2000
        
        // UIComponent label = new UIComponent(new ImageResized("img/-1.png"));
        // label.setBounds(0, 0, label.getImage().getImage().getWidth(null), label.getImage().getImage().getHeight(null));
        
        // window.add(label);

        
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        // Prompt the user to input the number of players and validate the input
        short nbPlayers = Utility.controlInt((short)2, (short)8, "Enter an integer representing the number of players :", "The number of players must be between 2 and 8, retry.");
        String waitMessage = "Game Loading, Please wait...";
        window.add(new JLabel(waitMessage));
        System.out.println(waitMessage);

        // Initialize the variables for the game
        int maxScore = 50;
        boolean gameOver = false;

        // Create and shuffle a deck of cards
        Deck deck = new Deck(true);

        // Create an empty deck for the discard pile
        Deck discard_pile = new Deck(false);

        // Create an array list to store the players
        ArrayList<Player> players = new ArrayList<>();

        // Create the players and add them to the array list
        for (short i = 0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
        }

        // Print the welcome message and prompt the players to choose a card to determine the starting player
        System.out.println("Welcome to the UTBM version of the Skyjo game!" +
                "\nLet's choose a card within your cards and see who will begin!");

        //initialize the base view
        GameLoop.initializeRoundUI(window, players, deck, discard_pile);

        // Play the game until a player reaches the maximum score
        while(!gameOver) {

            // Execute a round of the game
            GameLoop.executeRound(players, deck, discard_pile, window);

            // Display the scores of all the players
            Utility.displayScore(players);

            // Check if any player has reached the maximum score
            for(Player player : players)
            {
                if(player.getScore() >= maxScore)
                {
                    gameOver = true;
                    break;
                }
            }

            // Reset the game state for the next round
            GameLoop.resetGame(players, deck, discard_pile);
        }

        // Print the message to indicate that the game is finished
        System.out.println("The game is finished. Well played!");
    }
}
