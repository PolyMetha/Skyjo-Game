import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        int nbPlayers = 0;

        Deck deck = new Deck();
        ArrayList<Player> players = new ArrayList<Player>(); 
        
        while(nbPlayers <2 || nbPlayers >8){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an integer: ");
            nbPlayers = scanner.nextInt();
            if(nbPlayers <2 && nbPlayers >8) {
                System.out.println("The number of players must be between 2 and 8, retry.");
            }
        }

        deck.PrintDeck();

        for(int i=0; i < nbPlayers; i++){
            players.add(new Player(i, deck));
            players.get(i).printHand();
        }

        deck.PrintDeck();

        GameLoop gameLoop = new GameLoop();
    }
}