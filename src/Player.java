// import the required ArrayList class
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player {
    // player ID which is either 0 or 1
    private final short id;
    // array list of cards representing the player's hand
    private ArrayList<Card> hand;
    // the number of columns the player has removed
    private short nbColumnRemoved;
    // the number of lines the player has removed
    private short nbLineRemoved;
    // the player's score
    private short score;

    // constructor for creating a player with a given ID and deck
    public Player(short ID, Deck deck){
        this.id = ID;
        this.hand = new ArrayList<>();
        this.nbColumnRemoved = 0;
        this.nbLineRemoved = 0;
        this.score = 0;
        // initialize the player's hand
        initializeHand(deck);
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
        //save the hand card
        Card tmp = new Card(secondSelection);

        //change and return the hand card
        secondSelection.changeCard(firstSelection);
        secondSelection.setPlayerID(GameLoop.playerTurn);
        this.ChangeCardSide(secondSelection);

        //put the old hand card in the discard pile
        tmp.setVisibility(true);
        tmp.setPlayerID(-1);
        discard_pile.addCard(tmp);
        //update discard pile UI
        discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
        discardUI.changeCard(discard_pile.getFirstCard());
        discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));
        discardUI.changeCardImage(discard_pile.getFirstCard().getFront());
        discardUI.setVisibility(true);

        //draw the deck card and change the UI of the top card
        deck.draw();
        deckUI.removeMouseListener(deckUI.getMouseListeners()[0]);
        deckUI.changeCard(deck.getFirstCard());
        deckUI.addMouseListener(new MouseHandler(deck.getFirstCard(), null));
    }

    public void takeACardFromDiscardPile(Deck discard_pile,Card discardUI, Card firstSelection, Card secondSelection){
        
        //save the hand card
        Card tmp = new Card(secondSelection);

        //change the hand card
        secondSelection.changeCard(firstSelection);
        secondSelection.setPlayerID(GameLoop.playerTurn);
        this.ChangeCardSide(secondSelection);

        //put the old hand card into the discard
        tmp.setPlayerID(-1);
        tmp.setVisibility(true);
        discard_pile.addCard(tmp);

        //change discard UI
        discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
        discardUI.changeCard(discard_pile.getFirstCard());
        discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));
        this.ChangeCardSide(discardUI);

    }

//checks id a column or a row of a deck can be removed
    public void verifyRowsAndColumns(JLabel infoBar, Deck discard_pile, Card discardUI)
    {
            // verify rows in case of 0 columns already removed
            if (this.nbColumnRemoved == 0)
            {   //for all the rows
                for (int i = 0; i <= 9 - (this.nbLineRemoved * 3) ; i = i + 3)
                {
                    try
                    {   //check if the 3 cards have the same color
                        if (this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 1).getUv().getColor())
                                && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 2).getUv().getColor())
                                && this.hand.get(i).getVisibility()
                                && this.hand.get(i + 1).getVisibility()
                                && this.hand.get(i + 2).getVisibility())
                        {      
                                                  
                            infoBar.setText("Row removed !");
                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                // Handle the exception if necessary
                            }

                            //removing it 1 by 1 and adding it to the discard pile
                            for(int j=0; j<3; j++){
                                hand.get(i).setPlayerID(-1);
                                hand.get(i).setVisibility(true);
                                discard_pile.addCard(hand.get(i));
                                hand.get(i).getPanel().remove(hand.get(i));
                                this.hand.remove(i);
                            }

                            //refresh the ui discard UI
                            discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
                            discardUI.changeCard(discard_pile.getFirstCard());
                            discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));
                            discardUI.changeCardImage(discard_pile.getFirstCard().getFront());

                            this.nbLineRemoved += 1;
                            //repaint the panel if there still a card
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
                        //if 0 lines removed, we must remove 4 cards
                        case 0 -> {
                            try {
                                //check the colors of the 4 cards
                                if (this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 3 - this.nbColumnRemoved).getUv().getColor())
                                        && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getUv().getColor())
                                        && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 9 - (this.nbColumnRemoved * 3)).getUv().getColor())
                                        && this.hand.get(i).getVisibility()
                                        && this.hand.get(i + 3 - this.nbColumnRemoved).getVisibility()
                                        && this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getVisibility()
                                        && this.hand.get(i + 9 - (this.nbColumnRemoved * 3)).getVisibility()) {                                    
                                    infoBar.setText("Column removed !");
                                    try {
                                        Thread.sleep(700);
                                    } catch (InterruptedException e) {
                                        // Handle the exception if necessary
                                    }
                                    
                                    //we must remove it 1 by 1 because of the choice ofArrayList we made
                                    //for our hand, here, to get the next line, the algorithm changes
                                    //for every card we remove 
                                    hand.get(i).setPlayerID(-1);
                                    hand.get(i).setVisibility(true);
                                    discard_pile.addCard(hand.get(i));
                                    hand.get(i).getPanel().remove(hand.get(i));
                                    this.hand.remove(i);

                                    hand.get(i+3-this.nbColumnRemoved-1).setPlayerID(-1);
                                    hand.get(i+3-this.nbColumnRemoved-1).setVisibility(true);
                                    discard_pile.addCard(hand.get(i+3-this.nbColumnRemoved-1));
                                    hand.get(i+3-this.nbColumnRemoved-1).getPanel().remove(hand.get(i+3-this.nbColumnRemoved-1));
                                    this.hand.remove(i + 3 - this.nbColumnRemoved - 1);

                                    hand.get(i+6-this.nbColumnRemoved*2-2).setPlayerID(-1);
                                    hand.get(i+6-this.nbColumnRemoved*2-2).setVisibility(true);
                                    discard_pile.addCard(hand.get(i+6-this.nbColumnRemoved*2-2));
                                    hand.get(i+6-this.nbColumnRemoved*2-2).getPanel().remove(hand.get(i+6-this.nbColumnRemoved*2-2));
                                    this.hand.remove(i + 6 - (this.nbColumnRemoved * 2) - 2);


                                    hand.get(i+9-this.nbColumnRemoved*3-3).setPlayerID(-1);
                                    hand.get(i+9-this.nbColumnRemoved*3-3).setVisibility(true);
                                    discard_pile.addCard(hand.get(i+9-this.nbColumnRemoved*3-3));  
                                    hand.get(i+9-this.nbColumnRemoved*3-3).getPanel().remove(hand.get(i+9-this.nbColumnRemoved*3-3));
                                    this.hand.remove(i + 9 - (this.nbColumnRemoved * 3) - 3);

                                    //update the hand UI
                                    discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
                                    discardUI.changeCard(discard_pile.getFirstCard());
                                    discardUI.changeCardImage(discard_pile.getFirstCard().getFront());
                                    discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));
        
                                    this.nbColumnRemoved += 1;
                                    if(hand.get(0)!=null){
                                        hand.get(0).getPanel().repaint();
                                    }
                                }
                            } catch (Exception exception) {}
                        }
                        case 1 -> {//id 1 line was removed
                            try {
                                if (this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 3 - this.nbColumnRemoved).getUv().getColor())
                                        && this.hand.get(i).getUv().getColor().equals(this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getUv().getColor())
                                        && this.hand.get(i).getVisibility()
                                        && this.hand.get(i + 3 - this.nbColumnRemoved).getVisibility()
                                        && this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getVisibility()) {
                                    infoBar.setText("Column removed !");
                                    try {
                                        Thread.sleep(700);
                                    } catch (InterruptedException e) {
                                        // Handle the exception if necessary
                                    }

                                    //same process as the case 0 but with  cards instead of 4
                                    hand.get(i).setPlayerID(-1);
                                    hand.get(i).setVisibility(true);
                                    discard_pile.addCard(hand.get(i));
                                    hand.get(i).getPanel().remove(hand.get(i));
                                    this.hand.remove(i);

                                    hand.get(i+3-this.nbColumnRemoved-1).setPlayerID(-1);
                                    hand.get(i+3-this.nbColumnRemoved-1).setVisibility(true);
                                    discard_pile.addCard(hand.get(i+3-this.nbColumnRemoved-1));
                                    hand.get(i+3-this.nbColumnRemoved-1).getPanel().remove(hand.get(i+3-this.nbColumnRemoved-1));
                                    this.hand.remove(i + 3 - this.nbColumnRemoved - 1);

                                    hand.get(i+6-this.nbColumnRemoved*2-2).setPlayerID(-1);
                                    hand.get(i+6-this.nbColumnRemoved*2-2).setVisibility(true);
                                    discard_pile.addCard(hand.get(i+6-this.nbColumnRemoved*2-2));
                                    hand.get(i+6-this.nbColumnRemoved*2-2).getPanel().remove(hand.get(i+6-this.nbColumnRemoved*2-2));
                                    this.hand.remove(i + 6 - (this.nbColumnRemoved * 2) - 2);

                                    //update the hand UI
                                    discardUI.removeMouseListener(discardUI.getMouseListeners()[0]);
                                    discardUI.changeCard(discard_pile.getFirstCard());
                                    discardUI.changeCardImage(discard_pile.getFirstCard().getFront());
                                    discardUI.addMouseListener(new MouseHandler(discard_pile.getFirstCard(), null));

                                    this.nbColumnRemoved += 1;
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
    //check if a player finished the round
    public boolean verifyWin(short round, short nbPlayers) {
        // Count number of visible cards in the player's hand
        short count = 0;
        //if there is no more cards in the player hand the player finished
        try {
            if(this.hand.get(0)==null){
                return true;
            }}
        catch (Exception exception){
            count = 0;
        }
        
        //count the non visible cards
        for (Card card : this.hand) {
            if (!card.getVisibility()) {
                count++;
            }
        }
        // If all cards are visible, the player finished
        if (count == 0) {
            return true;
        } else {
            return false;
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

    //print the hand on the screen
    public JPanel printHand(JFrame window, int xPanel, int yPanel, int panelWidth, int panelHeight){
        int i=0,j=0;

        JPanel panel = new JPanel(null);
        panel.setBounds(xPanel, yPanel, panelWidth, panelHeight);

        //space between cards
        int CARDS_OFFSET = 10;
        int CARDS_WIDTH_PADDING = (panelWidth-(3*CardImgResized.IMG_WIDTH+20))/2;
        int CARDS_HEIGHT_PADDING = (panelHeight-(4*CardImgResized.IMG_HEIGHT+20))/3;

        for(Card card : hand){
            //instantiate the card grid
            card.setBounds(CARDS_OFFSET+i*(CARDS_WIDTH_PADDING+CardImgResized.IMG_WIDTH), CARDS_OFFSET+j*(CARDS_HEIGHT_PADDING+CardImgResized.IMG_HEIGHT), CardImgResized.IMG_WIDTH, CardImgResized.IMG_HEIGHT);
            panel.add(card);
            i++;
            if(i==3){
                i=0;
                j++;
            }

            card.setPanel(panel);
        }
        window.add(panel);
        window.repaint();
        return panel;
    }

    //getters and setters
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

    public short getID(){
        return (short) id;
    }
}
