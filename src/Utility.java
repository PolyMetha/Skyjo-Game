import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Utility {

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
    public static void displayScore(ArrayList<Player> players, JFrame window, Card deckUI)
    {
        int i=0;
        for (Player player : players) {

            calculScore(player); // calculates the score of each player        
            JLabel scoreUI = new JLabel("Player "+ player.getID()+" score : "+player.getScore());
            // scoreUI.setBounds((players.size()%3)*(players.get(0).getUiHandSize()[0]+), players.size()/3*(players.get(0).getUiHandSize()[1]+100), 500, 30);
            scoreUI.setBounds(deckUI.getBounds().x+deckUI.getWidth()+20, deckUI.getBounds().y+deckUI.getHeight()+i, 500, 30);
            scoreUI.setFont(new Font("Verdana", Font.BOLD, 18));
            scoreUI.setForeground(new Color(255, 221, 131));
            window.add(scoreUI);
            window.repaint();
            i+=30;
        }

    }

    public static boolean isInt(String text){
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
