import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;



public class App {
    public static void main(String[] args) {

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
        window.setVisible(true);



        short nbPlayers = 0;
        
        while(nbPlayers <2 || nbPlayers >8){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an integer representing the number of players : ");
            nbPlayers = scanner.nextShort();
            if(nbPlayers < 2 || nbPlayers > 8) {
                System.out.println("The number of players must be between 2 and 8, retry.");
            }
        }

        Deck deck = new Deck(true);
        Deck talon = new Deck(false);
        ArrayList<Player> players = new ArrayList<Player>();

        deck.PrintDeck(window);

        for (int i=0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
            players.get(i).printHand();
        }

        deck.PrintDeck("Deck");
        talon.PrintDeck("Talon");

        GameLoop gameLoop = new GameLoop(players);
    }
}