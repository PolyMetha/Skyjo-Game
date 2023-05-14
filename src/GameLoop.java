import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameLoop {

    public static short playerTurn=0;

    //the intermediates between the interface and the gameloop
    public static Card firstSelection = null;
    public static Card secondSelection = null;

    //references to the discard pile and the deck on the screen (instantiated later)
    private static Card discardPileUI=null;
    private static Card deckUI=null;

    //reference to an information bar to indicate things to the player
    private static JLabel infoBar;

    //interface colors
    private static Color panelColor = new Color(57, 62, 70);
    private static Color panelColorPlaying = new Color(78, 204, 163);

    //panels representing the hand space of a player
    private static ArrayList<JPanel> panels = new ArrayList<>();

    // Reset the game with a new deck and empty discard pile for each player
    public static void resetGame(ArrayList<Player> players, Deck deck, Deck discard_pile)
    {
        // Create a new deck and discard pile
        deck = new Deck(true);
        discard_pile = new Deck(false);

        // Reset each player with the new deck
        for (Player player : players)
        {
            player.resetPlayer(deck);
        }
    }

    //reset the UI for another game
    public static void resetRoundUI(JFrame window){
        Container contentPane = window.getContentPane();
        discardPileUI = null;
        deckUI = null;
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        panels = new ArrayList<>();
    }

    //creates a new interface
    public static void initializeRoundUI(JFrame window, ArrayList<Player> players, Deck deck, Deck discardPile){

        //margin with the window border
        final int WIN_OFFSET = 10;
        //screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        //dimensions of a future player panel
        int panelWidth;
        int panelHeight = screenHeight/2-60;

        //calculate the width of a panel depending on the number of players
        if(players.size()<4){
            panelWidth = (screenWidth-160)/(players.size()+1);
        }
        else{
            panelWidth = (screenWidth-160)/4;
        }

        //initialize player hands in the panels created
        int i =0, j=0, h=0;
        for (Player player : players) {
            panels.add(player.printHand(window, WIN_OFFSET+i*(panelWidth+120/(players.size()-1)),WIN_OFFSET+ j*(panelHeight+30),panelWidth, panelHeight));
            panels.get(h).setBackground(panelColor);
            i++;
            if(i==4){
                j+=1;
                i=0;
            }
            h++;
        }


        //initialize deck
        deckUI = deck.printDeck(window,WIN_OFFSET+ i*(panelWidth)+160, WIN_OFFSET+j*(panelHeight+40), deck.getFirstCard());
        //initialize discard pile
        discardPileUI = discardPile.printDiscardPile(window, WIN_OFFSET+i*panelWidth+160,WIN_OFFSET+ j*(panelHeight+50)+CardImgResized.IMG_HEIGHT+20, "img/12.png", new Card(new CardImgResized("img/Discard_empty.png")));

        //setting up the infobar
        infoBar = new JLabel("Select 2 cards to know who will begin");
        infoBar.setBounds(screenWidth/2-250,WIN_OFFSET+panelHeight+5, 500, 30);
        infoBar.setFont(new Font("Verdana", Font.BOLD, 18));
        infoBar.setForeground(new Color(255, 221, 131));
        window.add(infoBar);
    }

    // Execute a round of the game, here is the core of the programm
    public static void executeRound(ArrayList<Player> players, Deck deck, Deck discard_pile, JFrame window) {
        // Initialize some variables for the round
        short nbRound = 0;      //number of the actual round
        boolean play = true;
        boolean atLeastOnePlayerFinished = false;
        boolean turnSkipped=false;     //used if a player turn is skipped

        //Decide who will begin
        playerTurn = whoBegins(players);

        infoBar.setText("Player "+(playerTurn+1)+" begins");

        //wait for the player to read
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // Handle the exception if necessary
        }

        while(play){    //while nobody outpassed the max score

            //for all the players
            while(playerTurn < players.size()){

                //get the player presently playing
                Player player = players.get(playerTurn);

                //change color of the playing player panel
                panels.get(playerTurn).setBackground(panelColorPlaying);

                infoBar.setText("Select a card, the deck or the discard pile");
                //wait until an click of the player
                while(firstSelection == null){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // Handle the exception if necessary
                    }
                }

                //switch between the possible player actions (choose deck, discard pile or a card)
                switch(firstSelection.getPlayerId()){
                    case -2:// Take a card from the deck
                        //verify if there is a deck
                        if (!deck.verifyExistence()) {
                            // Shuffle discard pile back into deck and draw a new card if the deck is empty
                            deck = new Deck(false);
                            deck.addAllCards(discard_pile);
                            //empty the discard pile
                            discard_pile = new Deck(false);
                            break;
                        }            
                        if(!discard_pile.verifyExistence()){
                            //create discard pile
                            discard_pile = new Deck(false);
                        }

                        //show the card picked
                        firstSelection.setVisibility(true);
                        deckUI.changeCardImage(firstSelection.getFront());
                        infoBar.setText("Picked : "+firstSelection.getCardName()+" put it in your hand");

                        //wait until the player clicks on a card of his deck
                        while(secondSelection == null){
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
                        }

                        //put the card of the deck in the hand and the hand card in the discard pile
                        players.get(playerTurn).takeACardFromDeck(deck, discard_pile, discardPileUI, deckUI, firstSelection, secondSelection);
                        playerTurn+=1;

                        //update the UI of the deck with a card back
                        deckUI.changeCardImage(firstSelection.getBack());
                        break;

                    case -1:
                    
                        //discard pile card selected
                        //verify if there is a discard pile
                        if (!discard_pile.verifyExistence()) {
                            //if there is no discard pile, you can't draw in it, say it to the player and replay
                            System.out.println("There is no discard pile, choose another");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
                            turnSkipped =true;
                            break;
                        }
                        infoBar.setText("Picked : "+firstSelection.getCardName()+" put it in your hand");
                        //wait until the player clicks on something
                        while(secondSelection == null){
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
                        }

                        //exchange discard card and hand card
                        players.get(playerTurn).takeACardFromDiscardPile(discard_pile, discardPileUI, firstSelection, secondSelection);
                        playerTurn+=1;
                        break;

                    default:
                        //player card selected
                        player.changeCardSide(firstSelection);
                        playerTurn+=1;
                        break;
                }
  
                //reset the variables for a new turn
                firstSelection=null;
                secondSelection=null;

                //reset the player background color
                if(!turnSkipped)
                    panels.get(playerTurn-1).setBackground(panelColor);

                //verify if a column or a row can be deleted
                player.verifyRowsAndColumns(infoBar, discard_pile, discardPileUI);

                //check if a player finished his hand, if so, the game will run until the last player plays, calculate the score and end the round
                if(!turnSkipped && !atLeastOnePlayerFinished){
                    atLeastOnePlayerFinished = players.get(playerTurn-1).verifyWin(nbRound, (short) players.size());
                    turnSkipped = false;
                }

                //end the round
                if (atLeastOnePlayerFinished)
                {
                    play = false;
                    infoBar.setText("Round finished, please wait for the next one");
                }
            }

            //Reset player turn
            playerTurn=0;
            
        }
        //display the score and the deck of all players at the end of the round
        for(Player player : players){
            for(Card card : player.getHand()){
                card.returnCard();
            }
        }
        //display score and wait 5 sec before running a new round
        Utility.displayScore(players, window, deckUI);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Handle the exception if necessary
        }

    }

    private static short whoBegins(ArrayList<Player> players){
        short bestSum = -5;   //initialise at -5 because the lowest wo can do wth 2 cards is -4
        short bestSumID=0;

        for(playerTurn =0; playerTurn<players.size(); playerTurn++){
            panels.get(playerTurn).setBackground(panelColorPlaying);
            //do 1 round of 2 cards return to know which player is going to begin
            //retry while there is no selection or if the player select the deck or the discard pile
            while(firstSelection == null || firstSelection.getPlayerId()<0){
                try {
                    firstSelection=null;
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // Handle the exception if necessary
                }
            }

            //change card side
            players.get(playerTurn).changeCardSide(firstSelection);

            //moreover, verify if the 2nd selection is different from the first
            while(secondSelection == null || secondSelection.getPlayerId()<0 || secondSelection.equals(firstSelection)){
                try {
                    secondSelection = null;
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // Handle the exception if necessary
                }
            }
            
            players.get(playerTurn).changeCardSide(secondSelection);

            if(firstSelection.getValue() + secondSelection.getValue()>bestSum){
                bestSum = (short)(firstSelection.getValue() + secondSelection.getValue());
                bestSumID = playerTurn;
            }
            firstSelection =null;
            secondSelection=null;
            panels.get(playerTurn).setBackground(panelColor);
        }

        //return the ID of the player that had the biggest sum
        //if there is an equality between 2 players, the first that took the cards begins
        return bestSumID;
    }
}
