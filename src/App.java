import java.util.ArrayList;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        /*
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Skyjo game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        //ImageIcon image = new ImageIcon("C:/Users/reini/OneDrive/Images/carte.jpg");
        //JLabel label = new JLabel(image);
        //window.add(label);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);*/
       
        short nbPlayers = 2;//Utility.controlInt((short)2, (short)8, "Enter an integer representing the number of players :", "The number of players must be between 2 and 8, retry.");

        Deck deck = new Deck(true);
        Deck discard_pile = new Deck(false);
        ArrayList<Player> players = new ArrayList<>();

        // deck.PrintDeck(window);

        for (short i = 0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
        }

        /* deck.printDeck("Deck");
        discard_pile.printDeck("Discard pile"); */

        GameLoop gameLoop = new GameLoop(players, deck, discard_pile);
    }
}