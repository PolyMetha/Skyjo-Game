import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

public class GameLoop {

    final private static int UI_HAND_OFFSET = 50;
    public static short playerTurn=0;
    public static Card firstSelection = null;
    public static Card secondSelection = null;
    private static Card discardPileUI=null;
    private static Card deckUI=null;

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

    public static void initializeRoundUI(JFrame window, ArrayList<Player> players, Deck deck, Deck discardPile){
        //initialize player hands
        int i =0, j=0;
        for (Player player : players) {
            player.printHand(window, i*(player.getUiHandSize()[0]+UI_HAND_OFFSET), j*(player.getUiHandSize()[1]+UI_HAND_OFFSET));
            System.out.println(player.getUiHandSize()[0]);
            i++;
            if(i==3){
                j+=1;
                i=0;
            }
        }
        

        //initialize deck
        deckUI = deck.printDeck(window, i*(players.get(0).getUiHandSize()[0]+UI_HAND_OFFSET), j*(players.get(0).getUiHandSize()[1]+UI_HAND_OFFSET), "img/back.png", deck.getFirstCard());
        //initialize discard pile
        discardPileUI = discardPile.PrintDiscardPile(window, i*(players.get(0).getUiHandSize()[0]+UI_HAND_OFFSET), j*(players.get(0).getUiHandSize()[1]+UI_HAND_OFFSET)+ImageResized.IMG_HEIGHT+20, "img/Discard_empty.png", new Card(new ImageResized("img/Discard_Empty.png")));
    }

    // Execute a round of the game
    public static void executeRound(ArrayList<Player> players, Deck deck, Deck discard_pile, JFrame window) {
        // Initialize some variables for the round
        short nbRound = 0;
        boolean play = true;
        Iterator<Player> its = players.iterator();
        boolean atLeastOnePlayerFinished = false;

        while(play){    //while nobody outpassed the max score
            
            while(playerTurn<players.size()){
                //wait until an input of the player
                while(firstSelection == null){
                    try {
                        Thread.sleep(10);
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
                        //wait until the player clicks on something
                        while(secondSelection == null){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
                        }
                        players.get(playerTurn).takeACardFromDeck(deck, discard_pile, discardPileUI, deckUI, firstSelection, secondSelection);
                        playerTurn+=1;
                        break;
                        /*
                            System.out.println("Replace : " + secondSelection.getName() + " with deck card");
                        players.get(playerTurn).takeACardFromDeck(deck, discard_pile, secondSelection);

                        Card tmp = new Card(secondSelection);
                        secondSelection.changeCard(firstSelection);
                        secondSelection.setPlayerID(playerTurn);
                        secondSelection.changeCardImage(secondSelection.getFront());
                        //afficher la carte de la discard pile
                        tmp.setPlayerID(-1);
                        tmp.addMouseListener(new MouseHandler(tmp, players.get(playerTurn)));
                        if(discardPileUI.getMouseListeners()[0]!=null){
                            discardPileUI.removeMouseListener(discardPileUI.getMouseListeners()[0]);
                        }
                        discardPileUI.addMouseListener(new MouseHandler(tmp, players.get(playerTurn)));
                        discardPileUI.changeCard(tmp);
                        discardPileUI.changeCardImage(tmp.getFront());
                        playerTurn+=1;
                        System.out.println(discard_pile.getCard() + " put to the discard pile");
                        break;
*/
                    case -1:
                    
                        //discard pile card selected
                        //verify if there is a discard pile
                        if (!discard_pile.verifyExistence()) {
                            //if there is no discard pile, you can't draw in it, say it to the player and replay
                            System.out.println("There is no discard pile, choose another");
                            break;
                        }
                        //wait until the player clicks on something
                        while(secondSelection == null){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
                        }
                        players.get(playerTurn).takeACardFromDiscardPile(discard_pile, discardPileUI, firstSelection, secondSelection);
                        playerTurn+=1;
                        break;
                        /*
                            System.out.println("Took : " + secondSelection.getName() + " from the discard pile");
                        players.get(playerTurn).takeACardFromDiscardPile(discard_pile, secondSelection);
                            System.out.println(discard_pile.getFirstCard().getCardName() + " is the next card of the discard pile");
                        secondSelection.changeCard(discard_pile.getFirstCard());
                        secondSelection.changeCardImage(secondSelection.getFront());
                            System.out.println(secondSelection.getFront().toString());
                        //afficher la carte de la discard pile
                        playerTurn+=1;
                        break;
                        */
                    default:
                        //player card selected
                        firstSelection.changeCardImage(firstSelection.getFront());
                        System.out.println("Changed the card side");
                        playerTurn+=1;
                        break;
                }
                System.out.println("Discard pile : " + discard_pile.getFirstCard().getCardName());
                firstSelection=null;
                secondSelection=null;
            }

            //check win

            playerTurn=0;
        }
        

        /* 
        // Print each player's hand
        for (Player player : players) {
            player.printHand();
        }

        // Each player takes a card from the deck to know who is the first player to start
        for (short j = 0 ; j < players.size() ; j++) {
            if (j != 0)
            {
                players.get(j).printHand();
            }
            players.get(j).takeACardFromADeck(deck, discard_pile, (short) 3);
        }

        // Get the first card returned by each player and find the player with the lowest card
        ArrayList<Short> listFirstCardPlayer = new ArrayList<>();
        for (Player player : players)
        {
            listFirstCardPlayer.add(player.getFirstCardReturned());
        }
        short min = Short.MAX_VALUE; // Initialize the variable min with the maximum possible value of short
        int minIndex = -1; // Initialize the index with an invalid value
        for (int i = 0; i < listFirstCardPlayer.size(); i++) {
            if (listFirstCardPlayer.get(i) < min) {
                min = listFirstCardPlayer.get(i);
                minIndex = i;
            }
        }

        // Play the round until all players have finished
        while(play) {
            if (nbRound == 0)
            {
                playerTurn = (short) minIndex;
                if (playerTurn != 0)
                {
                    its.next();
                }
                System.out.println("\nThe first player to start is player " + (playerTurn + 1));
            }
            else
            {
                playerTurn = 0;
            }

            // Play one round for each player
            do
            {
                if (playerTurn != minIndex) {
                    System.out.println("It's player " + (playerTurn + 1) + " round's");
                }

                // Print each player's hand and execute their turn
                for (Player player : players) {
                    player.printHand();
                }
                players.get(playerTurn).round(deck, discard_pile);

                // Check if the player has won and update the play variable accordingly
                play = !its.next().verifyWin(nbRound, (short) players.size());
                if (!play)
                {
                    atLeastOnePlayerFinished = true;
                }
                nbRound++;

                // Move to the next player
                if (playerTurn == players.size() - 1)
                {
                    playerTurn = 0;
                }
                else
                {
                    playerTurn ++;
                }
            } while(its.hasNext());

            // Check if at least one player has finished and update the play variable accordingly
            if (atLeastOnePlayerFinished)
            {
                play = false;
            }

            // Reset the iterator to the beginning
            its = players.iterator();
        }*/
    }
}
