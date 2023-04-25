import java.util.ArrayList;
import java.util.Scanner;

public class Utility {

    static short controlInt(short borne1, short borne2, String message1, String message2)
    {
        short indice = 0;
        while (indice < borne1 || indice > borne2) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message1);
            try
            {
                indice = scanner.nextShort();
            }
            catch (Exception exception)
            {
                System.out.println("This must be an integer, retry.");
                indice = 0;
            }
            if(indice < borne1 || indice > borne2) {
                System.out.println(message2);
            }
        }
        return indice;
    }

    static void calculScore(Player player)
    {
        short score = 0;
        for (Card card : player.getHand()) {
            score += (short) card.getValue();
        }
        player.setScore(score);
    }

    static void displayScore(ArrayList<Player> players)
    {
        for (Player player : players) {
            calculScore(player);
        }

        System.out.println("------- Score -------");

        for (short k = 0 ; k < players.size(); k++)
        {
            System.out.println("Player " + (k + 1) + " : " + players.get(k).getScore());
        }

        System.out.println("---------------------");
    }
}
