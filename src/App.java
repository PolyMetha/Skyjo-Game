import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App {
    private static short nbPlayers;

    public static void main(String[] args) throws IOException {
        //get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        Color backgoundColor = new Color(35, 41, 49);

        //create player selection window
        JFrame playersSelect = new JFrame();

        //number of players inf=put field
        JTextField textField = new JTextField(20);
        textField.setBounds(screenWidth/2-50, screenHeight/2-textField.getHeight()/2+100, 100, 50);
        //play button
        FancyButton playButton = new FancyButton("Play");
        playButton.setBounds(screenWidth/2-50, screenHeight/2+170, 100, 50);
        playButton.setForeground(Color.WHITE);
        
        //add an informative text
        JLabel infoNbPlayers = new JLabel("Select the number of players");
        infoNbPlayers.setBounds(screenWidth/2-205, screenHeight/2+50, 410, 30);
        infoNbPlayers.setFont(new Font("Verdana", Font.BOLD, 25));
        infoNbPlayers.setForeground(Color.white);

        //adding it to a panel
        JPanel BackgroundPanel = new JPanel();
        BackgroundPanel.setLayout(new BorderLayout());
        JLabel background = new JLabel(new BackgroundResized("img/Background_Selection.png")); 
        background.add(textField);
        background.add(playButton);
        background.add(infoNbPlayers);
        BackgroundPanel.add(background, BorderLayout.CENTER);

        

        //adding panel to frame
        playersSelect.getContentPane().add(BackgroundPanel);

        //set window parameters
        playersSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playersSelect.setResizable(false);
        playersSelect.setTitle("Skyjo game players selection");
        playersSelect.setLayout(new FlowLayout());
        playersSelect.setSize(screenWidth, screenHeight);
        playersSelect.getContentPane().setBackground(backgoundColor);
        playersSelect.setLocationRelativeTo(null);
        playersSelect.setVisible(true);

        //adding logic behind play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the text field
                if(textField.getText().length()!=0){
                    nbPlayers =(short)Integer.parseInt(textField.getText());
                }
            }
        });

        //take user input under conditions
        while(nbPlayers <2 || nbPlayers >7){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // Handle the exception if necessary
            }
        }
        //close the window
        playersSelect.dispose();


        //start game window
        JFrame window = new JFrame();        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Skyjo game");
        window.setLayout(null);
        window.setSize(screenWidth, screenHeight);

        window.getContentPane().setBackground(backgoundColor);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Adding a please wait label
        JLabel waitJLabel = new JLabel("Game is loading, please wait...");
        waitJLabel.setBounds(screenWidth/2-175, screenHeight/2-30, 350, 30);
        waitJLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        waitJLabel.setForeground(Color.WHITE);
        window.add(waitJLabel);

        // Initialize the variables for the game
        int maxScore = 100;
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


        //remove the wait label and print the game view
        window.remove(waitJLabel);
        window.repaint();

        // Play the game until a player reaches the maximum score
        while(!gameOver) {
            //initialize the base view
            GameLoop.initializeRoundUI(window, players, deck, discard_pile);
            // Execute a round of the game
            GameLoop.executeRound(players, deck, discard_pile, window);

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
  
            GameLoop.resetRoundUI(window);
        }
    }
}
