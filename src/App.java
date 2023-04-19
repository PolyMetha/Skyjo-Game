import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        short nbPlayers = 2;//Utility.controlInt((short)2, (short)8, "Enter an integer representing the number of players :", "The number of players must be between 2 and 8, retry.");

        Deck deck = new Deck(true);
        Deck discard_pile = new Deck(false);
        ArrayList<Player> players = new ArrayList<Player>();

        for (int i=0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
            players.get(i).printHand();
        }

        deck.printDeck("Deck");
        discard_pile.printDeck("Discard pile");

        GameLoop gameLoop = new GameLoop(players, deck, discard_pile);
    }
}