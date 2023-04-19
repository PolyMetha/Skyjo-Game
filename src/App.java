import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        short nbPlayers = Utility.controlInt((short)2, (short)8, "Enter an integer representing the number of players : ", "The number of players must be between 2 and 8, retry.");

        Deck deck = new Deck(true);
        Deck talon = new Deck(false);
        ArrayList<Player> players = new ArrayList<Player>();

        for (int i=0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
            players.get(i).printHand();
        }

        deck.PrintDeck("Deck");
        talon.PrintDeck("Talon");

        GameLoop gameLoop = new GameLoop(players);
    }
}