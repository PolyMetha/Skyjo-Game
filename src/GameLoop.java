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
    public static Card firstSelection = null;
    public static Card secondSelection = null;
    private static Card discardPileUI=null;
    private static Card deckUI=null;
    private static JLabel infoBar;
    private static Color panelColor = new Color(57, 62, 70);
    private static Color panelColorPlaying = new Color(78, 204, 163);

    
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

    public static void resetRoundUI(JFrame window){
        Container contentPane = window.getContentPane();
        discardPileUI = null;
        deckUI = null;
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        panels = new ArrayList<>();
    }

    public static void initializeRoundUI(JFrame window, ArrayList<Player> players, Deck deck, Deck discardPile){

        final int WIN_OFFSET = 10;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int panelWidth;
        int panelHeight = screenHeight/2-60;

        if(players.size()<4){
            panelWidth = (screenWidth-160)/(players.size()+1);
        }
        else{
            panelWidth = (screenWidth-160)/4;
        }
        //initialize player hands
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
        discardPileUI = discardPile.PrintDiscardPile(window, WIN_OFFSET+i*panelWidth+160,WIN_OFFSET+ j*(panelHeight+50)+CardImgResized.IMG_HEIGHT+20, "img/12.png", new Card(new CardImgResized("img/Discard_Empty.png")));

        infoBar = new JLabel("Select 2 cards to know who will begin");
        infoBar.setBounds(screenWidth/2-250,WIN_OFFSET+panelHeight+5, 500, 30);
        infoBar.setFont(new Font("Verdana", Font.BOLD, 18));
        infoBar.setForeground(new Color(255, 221, 131));
        window.add(infoBar);
    }

    // Execute a round of the game
    public static void executeRound(ArrayList<Player> players, Deck deck, Deck discard_pile, JFrame window) {
        // Initialize some variables for the round
        short nbRound = 0;
        short idPlayerFinished=-1;
        boolean play = true;
        boolean atLeastOnePlayerFinished = false;
        boolean roundSkipped=false;

        //Decide who will begin
        playerTurn = whoBegins(players);

        infoBar.setText("Player "+(playerTurn+1)+" begins");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // Handle the exception if necessary
        }

        while(play){    //while nobody outpassed the max score

            while(playerTurn < players.size()){

                
                Player player = players.get(playerTurn);
                //size up the player panel, size down the others
                panels.get(playerTurn).setBackground(panelColorPlaying);

                infoBar.setText("Select a card, the deck or the discard pile");
                //wait until an input of the player
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

                        firstSelection.setVisibility(true);
                        deckUI.changeCardImage(firstSelection.getFront());
                        infoBar.setText("Picked : "+firstSelection.getCardName()+" put it in your hand");
                        //wait until the player clicks on something
                        while(secondSelection == null){
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
                        }

                        players.get(playerTurn).takeACardFromDeck(deck, discard_pile, discardPileUI, deckUI, firstSelection, secondSelection);
                        playerTurn+=1;

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
                            roundSkipped =true;
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
                        players.get(playerTurn).takeACardFromDiscardPile(discard_pile, discardPileUI, firstSelection, secondSelection);
                        playerTurn+=1;
                        break;

                    default:
                        //player card selected
                        player.ChangeCardSide(firstSelection);
                        playerTurn+=1;
                        break;
                }
  
                firstSelection=null;
                secondSelection=null;
                if(!roundSkipped)
                    panels.get(playerTurn-1).setBackground(panelColor);

                player.verifyRowsAndColumns(infoBar);
                if(!roundSkipped && !atLeastOnePlayerFinished){
                    atLeastOnePlayerFinished = players.get(playerTurn-1).verifyWin(nbRound, (short) players.size());
                    roundSkipped = false;
                }

                if (atLeastOnePlayerFinished)
                {
                    play = false;
                    infoBar.setText("Round finished");
                }
            }

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
            players.get(playerTurn).ChangeCardSide(firstSelection);

            //moreover, verify if the 2nd selection is different from the first
            while(secondSelection == null || secondSelection.getPlayerId()<0 || secondSelection.equals(firstSelection)){
                try {
                    secondSelection = null;
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // Handle the exception if necessary
                }
            }
            
            players.get(playerTurn).ChangeCardSide(secondSelection);

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
