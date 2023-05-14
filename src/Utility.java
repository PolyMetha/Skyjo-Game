import java.util.ArrayList;
import java.util.Scanner;

public class Utility {

    // This function is used to control the input of an integer from the user,
    // ensuring that the integer is within the specified range.
    public static short controlInt(short borne1, short borne2, String message1, String message2)
    {
        short indice = 0;
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);

        while (!validInput) {
            System.out.println(message1);
            String input = scanner.nextLine();

            try {
                indice = Short.parseShort(input);
                if (indice >= borne1 && indice <= borne2) {
                    validInput = true;
                } else {
                    System.out.println(message2);
                }
            } catch (NumberFormatException exception) {
                System.out.println(message2);
            }
        }
        return indice;
    }

    // This function calculates the score of a player based on the values of their cards.
    public static void calculScore(Player player)
    {
        short score = 0;
        for (Card card : player.getHand()) {
            score += (short) card.getValue(); // adds the value of each card to the score
        }
        player.setScore(score); // sets the player's score
    }

    // This function displays the scores of all the players in the game.
    public static void displayScore(ArrayList<Player> players)
    {
        for (Player player : players) {
            calculScore(player); // calculates the score of each player
        }

        System.out.println("------- Score -------");

        for (short k = 0 ; k < players.size(); k++)
        {
            System.out.println("Player " + (k + 1) + " : " + players.get(k).getScore()); // displays each player's score
        }

        System.out.println("---------------------");
    }
}
