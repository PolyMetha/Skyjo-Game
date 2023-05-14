import java.util.ArrayList;
import java.util.Iterator;

public class GameLoop {

    // Reset the game with a new deck and empty discard pile for each player
    public static void resetGame(ArrayList<Player> players, Deck deck, Deck discard_pile)
    {
        // Create a new deck and discard pile
        deck = new Deck(true);
        discard_pile = new Deck(false);

        // Reset each player with the new deck
        for (Player player : players)
        {
            player.resetPlayer(deck);
        }
    }

    // Execute a round of the game
    public static void executeRound(ArrayList<Player> players, Deck deck, Deck discard_pile, short state) {
        // Initialize some variables for the round
        short nbRound = 0;
        boolean play = true;
        Iterator<Player> its = players.iterator();
        boolean atLeastOnePlayerFinished = false;

        short max = Short.MIN_VALUE; // Initialize the variable min with the maximum possible value of short
        int minIndex = 0; // Initialize the index with an invalid value

        // Print each player's hand
        for (Player player : players) {
            player.printHand();
        }

        if (state == 0) {
            // Each player takes a card from the deck to know who is the first player to start
            for (short j = 0; j < players.size(); j++) {
                if (j != 0) {
                    players.get(j).printHand();
                }
                players.get(j).takeACardFromADeck(deck, discard_pile, (short) 3);
            }

            // Get the first card returned by each player and find the player with the lowest card
            ArrayList<Short> listFirstCardPlayer = new ArrayList<>();
            for (Player player : players) {
                listFirstCardPlayer.add(player.getFirstCardReturned());
            }

            for (int i = 0; i < listFirstCardPlayer.size(); i++) {
                if (listFirstCardPlayer.get(i) > max) {
                    max = listFirstCardPlayer.get(i);
                    minIndex = i;
                }
            }
        }

        // Play the round until all players have finished
        short j;
        while(play) {
            if (nbRound == 0)
            {
                j = (short) minIndex;
                if (j != 0)
                {
                    its.next();
                }
                System.out.println("\nThe first player to start is player " + (j + 1));
            }
            else
            {
                j = 0;
            }

            // Play one round for each player
            do
            {
                System.out.println("It's player " + (j + 1) + " round's");

                // Print each player's hand and execute their turn
                for (Player player : players) {
                    player.printHand();
                }
                players.get(j).round(deck, discard_pile);

                // Check if the player has won and update the play variable accordingly
                play = !its.next().verifyWin(nbRound, (short) players.size());
                if (!play)
                {
                    atLeastOnePlayerFinished = true;
                }
                nbRound++;

                // Move to the next player
                if (j == players.size() - 1)
                {
                    j = 0;
                }
                else
                {
                    j ++;
                }
            } while(its.hasNext());

            // Check if at least one player has finished and update the play variable accordingly
            if (atLeastOnePlayerFinished)
            {
                play = false;
            }

            // Reset the iterator to the beginning
            its = players.iterator();
        }
    }
}
