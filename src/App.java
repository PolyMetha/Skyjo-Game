import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        // Prompt the user to input the number of players and validate the input
        short nbPlayers = Utility.controlInt((short)2, (short)8, "Enter an integer representing the number of players :", "The number of players must be between 2 and 8, retry.");

        // Initialize the variables for the game
        GameLoop gameLoop = new GameLoop();
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

        // Play the game until a player reaches the maximum score
        while(!gameOver) {

            // Execute a round of the game
            gameLoop.executeRound(players, deck, discard_pile);

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
            gameLoop.resetGame(players, deck, discard_pile);
        }

        // Print the message to indicate that the game is finished
        System.out.println("The game is finished. Well played!");
    }
}
