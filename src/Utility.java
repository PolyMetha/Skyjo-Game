import java.awt.Font;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Utility {

    // This function is used to control the input of an integer from the user,
    // ensuring that the integer is within the specified range.
    static short controlInt(short borne1, short borne2, String message1, String message2)
    {
        short indice = 0;
        while (indice < borne1 || indice > borne2) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message1);
            try
            {
                indice = scanner.nextShort(); // reads the input as a short
            }
            catch (Exception exception)
            {
                System.out.println("This must be an integer, retry.");
                indice = 0;
            }
            // If the input is not within the range, the function displays a message
            // and prompts the user to enter a new input.
            if(indice < borne1 || indice > borne2) {
                System.out.println(message2);
            }
        }
        return indice;
    }

    // This function calculates the score of a player based on the values of their cards.
    static void calculScore(Player player)
    {
        short score = 0;
        for (Card card : player.getHand()) {
            score += (short) card.getValue(); // adds the value of each card to the score
        }
        player.setScore(score); // sets the player's score
    }

    // This function displays the scores of all the players in the game.
    static void displayScore(ArrayList<Player> players, JFrame window)
    {
        int i=0;
        for (Player player : players) {

            calculScore(player); // calculates the score of each player        
            JLabel scoreUI = new JLabel("Player "+ player.getID()+" score : "+player.getScore());
            scoreUI.setBounds(500, 550+i, 500, 30);
            scoreUI.setFont(new Font("Verdana", Font.PLAIN, 18));
            window.add(scoreUI);
            window.repaint();
            i+=30;
        }

    }
}
