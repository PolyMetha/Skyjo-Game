// import the required ArrayList class
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Player {
    // player ID which is either 0 or 1
    private final int id;
    // array list of cards representing the player's hand
    private ArrayList<Card> hand;
    // the number of columns the player has removed
    private short nbColumnRemoved;
    // the number of lines the player has removed
    private short nbLineRemoved;
    // the player's score
    private short score;

    private int[] uiHandSize;

    // constructor for creating a player with a given ID and deck
    public Player(int ID, Deck deck){
        this.id = ID;
        this.hand = new ArrayList<>();
        this.nbColumnRemoved = 0;
        this.nbLineRemoved = 0;
        this.score = 0;
        uiHandSize = new int[2];
        // initialize the player's hand
        initializeHand(deck);

        uiHandSize[0]=5*hand.get(0).getImage().getIconWidth();
        uiHandSize[1]=4*hand.get(0).getImage().getIconHeight();
    }

    // reset the player's state by removing their hand, column and line counts and initializing their hand
    public void resetPlayer(Deck deck)
    {
        this.hand = new ArrayList<>();
        this.nbColumnRemoved = 0;
        this.nbLineRemoved = 0;
        initializeHand(deck);
    }

    // get the player's chosen position by asking for a row and column
    public ArrayList<Short> askPosition()
    {
        ArrayList<Short> position = new ArrayList<>();
        short row = Utility.controlInt((short) 1, (short) (4 - this.nbLineRemoved), "Enter an integer to select a row :", "The integer must be between 1 and 4, retry.");
        short column = Utility.controlInt((short) 1, (short) (3 - this.nbColumnRemoved), "Enter an integer to select a column :", "The integer must be between 1 and 3, retry.");
        position.add(row);
        position.add(column);
        return position;
    }

    // take a card from a deck and replace it with a card from the player's hand based on a given state
    public boolean takeACardFromADeck(Deck deck, Deck discard_pile, short state)
    {
        ArrayList<Short> position;

        // if state is 2, change the first card of the deck and print it out
        if (state == 2)
        {
            deck.changeFirstCard(deck.getValueCard(), deck.getUvCard(), true);
            System.out.println("Card picked : " + deck.getCard());
        }

        // get the position of the card the player wants to replace
        position = this.askPosition();
        int indiceHand = (position.get(0)-1) * (3 - this.nbColumnRemoved) + position.get(1) - 1;
        // get the card that will be replaced by the new card
        Card temp = new Card(this.hand.get(indiceHand).getValue(), this.hand.get(indiceHand).getUv(), this.hand.get(indiceHand).getImage());

        // replace the card based on the given state
        if (state == 1)
        {
            // replace the card with a card from the discard pile and add the replaced card to the pile
            this.hand.get(indiceHand).changeCard(discard_pile.getValueCard(), discard_pile.getUvCard(), true);
            discard_pile.removeCard();
            discard_pile.addCard(temp);
        }
        else if (state == 2)
        {
            // replace the card with a card from the deck and add the replaced card to the discard pile
            this.hand.get(indiceHand).changeCard(deck.getValueCard(), deck.getUvCard(), true);
            deck.removeCard();
            discard_pile.addCard(temp);
        }
        else if (state == 3)
        {
            // Check if the card has already been revealed
            if (this.hand.get(indiceHand).getVisibility())
            {
                System.out.println("This card is already returned, please select another card.");
                return false;
            }
            else
            {
                // If not, reveal the card
                this.hand.get(indiceHand).setVisibility(true);
            }
        }
        else
        {
            // If state is not 3, it is invalid
            System.out.println("Error");
        }
        return true;
    }

    public void takeACardFromDeck(Deck deck, Deck discard_pile, Card discardUI, Card deckUI, Card firstSelection, Card secondSelection)
    {
        Card tmp = new Card(secondSelection);
        secondSelection.changeCard(firstSelection);
        secondSelection.setPlayerID(GameLoop.playerTurn);
        this.ChangeCardSide(secondSelection);

        tmp.setVisibility(true);
        tmp.setPlayerID(-1);
        System.out.println("tmp : " + tmp.getCardName());

        discard_pile.addCard(tmp);
        System.out.println("discard pile :"+discard_pile.getFirstCard().getName());
        discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
        discardUI.changeCard(discard_pile.getFirstCard());
        discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));
        discardUI.changeCardImage(discard_pile.getFirstCard().getFront());
        discardUI.setVisibility(true);

        deck.draw();
        deckUI.removeMouseListener(deckUI.getMouseListeners()[0]);
        deckUI.changeCard(deck.getFirstCard());
        deckUI.addMouseListener(new MouseHandler(deck.getFirstCard(), null));
    }

    public void takeACardFromDiscardPile(Deck discard_pile,Card discardUI, Card firstSelection, Card secondSelection){
        
        Card tmp = new Card(secondSelection);
        secondSelection.changeCard(firstSelection);
        secondSelection.setPlayerID(GameLoop.playerTurn);
        this.ChangeCardSide(secondSelection);

        tmp.setPlayerID(-1);
        discard_pile.addCard(tmp);
        discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
        discardUI.changeCard(discard_pile.getFirstCard());
        discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));
        this.ChangeCardSide(discardUI);

    }


    public void verifyRowsAndColumns(JFrame window)
    {
                // verify rows
            if (this.nbColumnRemoved == 0)
            {
                for (int i = 0; i <= 9 - (this.nbLineRemoved * 3) ; i = i + 3)
                {
                    try
                    {
                        if (this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 1).getUv().getColor())
                                && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 2).getUv().getColor())
                                && this.hand.get(i).getVisibility()
                                && this.hand.get(i + 1).getVisibility()
                                && this.hand.get(i + 2).getVisibility())
                        {
                            hand.get(i).getPanel().remove(hand.get(i));
                            this.hand.remove(i);
                            hand.get(i).getPanel().remove(hand.get(i));
                            this.hand.remove(i);
                            hand.get(i).getPanel().remove(hand.get(i));
                            this.hand.remove(i);
                            this.nbLineRemoved += 1;
                            System.out.println("Line removed !");
                            if(hand.get(0)!=null){
                                hand.get(0).getPanel().repaint();
                            }
                        }
                    }
                    catch (Exception exception) {}
                }
            }
            // verify columns
            if (this.nbLineRemoved < 2)
            {
                for (int i = 0 ; i <= 2 - this.nbColumnRemoved; i ++)
                {
                    switch (nbLineRemoved) {
                        case 0 -> {
                            try {
                                if (this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 3 - this.nbColumnRemoved).getUv().getColor())
                                        && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getUv().getColor())
                                        && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 9 - (this.nbColumnRemoved * 3)).getUv().getColor())
                                        && this.hand.get(i).getVisibility()
                                        && this.hand.get(i + 3 - this.nbColumnRemoved).getVisibility()
                                        && this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getVisibility()
                                        && this.hand.get(i + 9 - (this.nbColumnRemoved * 3)).getVisibility()) {
                                    hand.get(i).getPanel().remove(hand.get(i));
                                    this.hand.remove(i);
                                    hand.get(i+3-this.nbColumnRemoved-1).getPanel().remove(hand.get(i+3-this.nbColumnRemoved-1));
                                    this.hand.remove(i + 3 - this.nbColumnRemoved - 1);
                                    hand.get(i+6-this.nbColumnRemoved*2-2).getPanel().remove(hand.get(i+6-this.nbColumnRemoved*2-2));
                                    this.hand.remove(i + 6 - (this.nbColumnRemoved * 2) - 2);
                                    hand.get(i+9-this.nbColumnRemoved*3-3).getPanel().remove(hand.get(i+9-this.nbColumnRemoved*3-3));
                                    this.hand.remove(i + 9 - (this.nbColumnRemoved * 3) - 3);


                                    this.nbColumnRemoved += 1;
                                    System.out.println("Column removed !");
                                    if(hand.get(0)!=null){
                                        hand.get(0).getPanel().repaint();
                                    }
                                }
                            } catch (Exception exception) {}
                        }
                        case 1 -> {
                            try {
                                if (this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 3 - this.nbColumnRemoved).getUv().getColor())
                                        && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getUv().getColor())
                                        && this.hand.get(i).getVisibility()
                                        && this.hand.get(i + 3 - this.nbColumnRemoved).getVisibility()
                                        && this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getVisibility()) {
                                    hand.get(i).getPanel().remove(hand.get(i));
                                    this.hand.remove(i);
                                    hand.get(i+3-this.nbColumnRemoved-1).getPanel().remove(hand.get(i+3-this.nbColumnRemoved-1));
                                    this.hand.remove(i + 3 - this.nbColumnRemoved - 1);
                                    hand.get(i+6-this.nbColumnRemoved*2-2).getPanel().remove(hand.get(i+6-this.nbColumnRemoved*2-2));
                                    this.hand.remove(i + 6 - (this.nbColumnRemoved * 2) - 2);

                                    this.nbColumnRemoved += 1;
                                    System.out.println("Column removed !");
                                    if(hand.get(0)!=null){
                                        hand.get(0).getPanel().repaint();
                                    }
                                }
                            } catch (Exception exception) {}
                        }
                    }
                }
            }
    }

    public void round(Deck deck, Deck discard_pile, JFrame window) {
        boolean round_played;
        do {
            round_played = false;
            deck.printDeck("Deck");
            discard_pile.printDeck("Discard pile");
            System.out.println("--------------------------------------");
            System.out.println("1. Choose a card from the discard pile");
            System.out.println("2. Choose a card from the deck");
            System.out.println("3. Discover a card within your game");
            System.out.println("--------------------------------------");
            // Get user input to select an action
            short indice = Utility.controlInt((short) 1, (short) 3, "Enter an integer to select a line :", "The integer must between 1 and 3, retry.");

            switch (indice) {
                case 1 ->
                {
                    // Take a card from the discard pile
                    if (!discard_pile.verifyExistence()) {
                        System.out.println("The discard pile is empty, choose another action.");
                        break;
                    }
                    round_played = this.takeACardFromADeck(deck, discard_pile, (short) 1);
                }
                case 2 ->
                {
                    // Take a card from the deck
                    if (!deck.verifyExistence()) {
                        // Shuffle discard pile back into deck and draw a new card if the deck is empty
                        deck = new Deck(false);
                        deck.addAllCards(discard_pile);
                        discard_pile = new Deck(false);
                        discard_pile.addCard(deck.draw());
                        break;
                    }
                    round_played = this.takeACardFromADeck(deck, discard_pile, (short) 2);
                }
                case 3 -> round_played = this.takeACardFromADeck(deck, discard_pile, (short) 3);
                default ->
                {
                    // In case of an error, show all cards in the player's hand and mark the round as played
                    for (Card card : this.hand) {
                        card.setVisibility(true);
                    }
                    this.printHand();
                    round_played = true;
                    System.out.println("Error");
                }
            }
        } while (!round_played);

        // Verify rows and columns for the player's hand
        this.verifyRowsAndColumns(window);
    }

    public boolean verifyWin(short round, short nbPlayers) {
        // Count number of visible cards in the player's hand
        short count = 0;
        for (Card card : this.hand) {
            if (!card.getVisibility()) {
                count++;
            }
        }
        // If all cards are visible, the player has won
        if (count == 0) {
            if (round != nbPlayers - 1) {
                // If the round isn't over, notify the player that they have finished and must wait for the round to end
                System.out.println("All of your cards are returned, you finished ! Please wait the end of the round");
            } else {
                // If the round is over, notify the player that the round has finished
                System.out.println("Round finished !");
            }
            return true;
        } else {
            return false;
        }
    }

    public void ChangeCardSide(Card card){
        card.changeCardImage(card.getFront());
        card.setVisible(true);
    }

    // Initialize the player's hand with 12 cards drawn from the deck
    public void initializeHand(Deck deck) {
        for(int i = 0; i < 12; i++) {
            Card card = deck.draw();
            card.setPlayerID(id);       //set the cardId to the player ID
            card.addMouseListener(new MouseHandler(card, this));
            hand.add(card);
        }
    }

    public JPanel printHand(JFrame window, int xPanel, int yPanel){
        int j=0, tmpXCard = 10, xCard=10, yCard=10;
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.blue);
        panel.setBounds(xPanel, yPanel, 3*ImageResized.IMG_WIDTH+50, 4*ImageResized.IMG_HEIGHT+50);
        for(Card card : hand){
            card.setBounds(xCard, yCard, ImageResized.IMG_WIDTH, ImageResized.IMG_HEIGHT);
            panel.add(card);
            xCard+=hand.get(0).getWidth() + hand.get(0).getWidth()/5;
            if(j==2 || j==5 || j ==8){
                yCard+=hand.get(0).getHeight() + 10;
                xCard=tmpXCard;
            }
            j+=1;
            card.setPanel(panel);
        }
        window.add(panel);
        window.repaint();
        return panel;
    }

    public void printHand()
    {
        // Print the player's hand number
        System.out.print("\nHand of player : " + (this.id + 1) + "\n");

        // Print a divider to separate the hand from other information
        System.out.println("--------------------------------");

        // Loop through each card in the player's hand
        for (int i = 1; i <= this.hand.size(); i++)
        {
            // Use a switch statement to determine how to format the hand based on how many cards have been removed
            switch (this.nbColumnRemoved)
            {
                // If no cards have been removed yet
                case 0 ->
                {
                    // If this is the third card in a row, print it on a new line
                    if (i % 3 == 0)
                    {
                        System.out.print(this.hand.get(i - 1).getCardName());
                        System.out.println("\n--------------------------------");
                    }
                    // Otherwise, print the card followed by a separator
                    else
                    {
                        System.out.print(this.hand.get(i - 1).getCardName() + " | ");
                    }
                }

                // If one card has been removed
                case 1 ->
                {
                    // If this is the second card in a row, print it on a new line
                    if (i % 2 == 0)
                    {
                        System.out.print(this.hand.get(i - 1).getCardName());
                        System.out.println("\n--------------------------------");
                    }
                    // Otherwise, print the card followed by a separator
                    else
                    {
                        System.out.print(this.hand.get(i - 1).getCardName() + " | ");
                    }
                }

                // If two cards have been removed, print each card on a new line
                case 2 ->
                {
                    System.out.print(this.hand.get(i - 1).getCardName());
                    System.out.println("\n--------------------------------");
                }

                // If three cards have been removed, print "empty"
                case 3 -> System.out.println("empty");
            }
        }
    }

    public void setScore(short score) {
        // Add the given score to the player's total score
        this.score += score;
    }

    public short getScore() {
        // Return the player's current score
        return this.score;
    }

    public ArrayList<Card> getHand()
    {
        // Return the player's hand
        return this.hand;
    }

    public int[] getUiHandSize(){
        return uiHandSize;
    }

    public int getID(){
        return id;
    }

    public short getFirstCardReturned()
    {
        // Loop through each card in the player's hand
        for (Card card : hand)
        {
            // If the card is visible, return its value
            if (card.getVisibility())
            {
                return (short) card.getValue();
            }
        }
        // If no card is visible, return 20
        return 20;
    }
}
