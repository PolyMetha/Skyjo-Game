// import the required ArrayList class
import java.awt.Color;
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

        uiHandSize[0]=5*hand.get(0).getFront().getIconWidth();
        uiHandSize[1]=4*hand.get(0).getFront().getIconHeight();
    }

    // reset the player's state by removing their hand, column and line counts and initializing their hand
    public void resetPlayer(Deck deck)
    {
        this.hand = new ArrayList<>();
        this.nbColumnRemoved = 0;
        this.nbLineRemoved = 0;
        initializeHand(deck);
    }

    public void takeACardFromDeck(Deck deck, Deck discard_pile, Card discardUI, Card deckUI, Card firstSelection, Card secondSelection)
    {
        Card tmp = new Card(secondSelection);
        secondSelection.changeCard(firstSelection);
        secondSelection.setPlayerID(GameLoop.playerTurn);
        this.ChangeCardSide(secondSelection);

        tmp.setVisibility(true);
        tmp.setPlayerID(-1);

        discard_pile.addCard(tmp);
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
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }
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
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        // Handle the exception if necessary
                                    }
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
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        // Handle the exception if necessary
                                    }
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

    public boolean verifyWin(short round, short nbPlayers) {
        // Count number of visible cards in the player's hand
        short count = 0;
        //if there is no more cards in the player hand
        if(this.hand.get(0)==null){
            System.out.println("Plus de cartes dans la main");
            return true;
        }

        for (Card card : this.hand) {
            System.out.println(card.getVisibility());
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
            return false;
        } else {
            return true;
        }
    }

    public void ChangeCardSide(Card card){
        card.changeCardImage(card.getFront());
        card.setVisibility(true);
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
}
