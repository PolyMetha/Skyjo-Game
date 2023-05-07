import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class App {
    private static short nbPlayers;

    public static void main(String[] args) {
        //get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        //player selection window
        JFrame playersSelect = new JFrame();

        JTextField textField = new JTextField(20);
        JButton playButton = new JButton("Play");        
                //add a label and a button
        JLabel infoNbPlayers = new JLabel("Select the number of players");
        infoNbPlayers.setBounds(550, 380, 1000, 30);
        infoNbPlayers.setFont(new Font("Verdana", Font.PLAIN, 18));

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(playButton);
        panel.add(infoNbPlayers);

        playersSelect.add(panel);
        playersSelect.getContentPane().add(panel);


        playersSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playersSelect.setResizable(false);
        playersSelect.setTitle("Skyjo game players selection");
        playersSelect.setLayout(new FlowLayout());
        playersSelect.setSize(screenWidth, screenHeight);
        playersSelect.getContentPane().setBackground(Color.GRAY);

        playersSelect.setLocationRelativeTo(null);
        playersSelect.setVisible(true);
        playersSelect.repaint();

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the text field
                nbPlayers =(short)Integer.parseInt(textField.getText());
            }
        });

        while(nbPlayers <2 || nbPlayers >5){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // Handle the exception if necessary
            }
        }
        playersSelect.dispose();



        JFrame window = new JFrame();        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Skyjo game");
        window.setLayout(null);
        window.setSize(screenWidth, screenHeight);
        window.getContentPane().setBackground(Color.GRAY);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        JLabel waitJLabel = new JLabel("Game is loading, please wait...");
        waitJLabel.setBounds(550, 380, 1000, 30);
        waitJLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        window.add(waitJLabel);

        // Initialize the variables for the game
        int maxScore = 50;
        boolean gameOver = false;

        // Create and shuffle a deck of cards
        Deck deck = new Deck(true);

        // Create an empty deck for the discard pile
        Deck discard_pile = new Deck(false);

        // Create an array list to store the players
        ArrayList<Player> players = new ArrayList<>();

        // Create the players and add them to the array list
        for (short i = 0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
        }

        // Print the welcome message and prompt the players to choose a card to determine the starting player
        System.out.println("Welcome to the UTBM version of the Skyjo game!" +
                "\nLet's choose a card within your cards and see who will begin!");

        //initialize the base view
        GameLoop.initializeRoundUI(window, players, deck, discard_pile);

        window.remove(waitJLabel);
        window.repaint();

        // Play the game until a player reaches the maximum score
        while(!gameOver) {

            // Execute a round of the game
            GameLoop.executeRound(players, deck, discard_pile, window);

            // Display the scores of all the players
            Utility.displayScore(players, window);

            // Check if any player has reached the maximum score
            for(Player player : players)
            {
                if(player.getScore() >= maxScore)
                {
                    gameOver = true;
                    break;
                }
            }

            // Reset the game state for the next round
            GameLoop.resetGame(players, deck, discard_pile);
        }

        // Print the message to indicate that the game is finished
        System.out.println("The game is finished. Well played!");
    }
}
