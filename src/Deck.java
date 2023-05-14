import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JFrame;

public class Deck {

    // List of cards in the deck
    private ArrayList<Card> cards;

    // Constructor for the deck
    public Deck(boolean fill) {
        if (fill) {
            // Map of UV values for each card value
            HashMap<Integer, UV> dict = new HashMap<>();
            dict.put(-2, new UV("PC20", java.awt.Color.decode("#FEB801")));
            dict.put(-1, new UV("LE09", java.awt.Color.decode("#E2595C")));
            dict.put(0, new UV("MBE3", java.awt.Color.decode("#FEB801")));
            dict.put(1, new UV("IF2B", java.awt.Color.decode("#FEB801")));
            dict.put(2, new UV("MT2A", java.awt.Color.decode("#22A60D")));
            dict.put(3, new UV("IF3B", java.awt.Color.decode("#FEB801")));
            dict.put(4, new UV("LE08", java.awt.Color.decode("#E2595C")));
            dict.put(5, new UV("PS2A", java.awt.Color.decode("#22A60D")));
            dict.put(6, new UV("LE03", java.awt.Color.decode("#E2595C")));
            dict.put(7, new UV("IF2A", java.awt.Color.decode("#FEB801")));
            dict.put(8, new UV("GE00", java.awt.Color.decode("#E2595C")));
            dict.put(9, new UV("MT3E", java.awt.Color.decode("#22A60D")));
            dict.put(10, new UV("PS28", java.awt.Color.decode("#22A60D")));
            dict.put(11, new UV("MT28", java.awt.Color.decode("#22A60D")));
            dict.put(12, new UV("PS25", java.awt.Color.decode("#22A60D")));

            // Initialize the deck with the cards according to the game rules
            cards = new ArrayList<>();
            // Add 5 -2 cards to the deck
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(-2, dict.get(-2),new CardImgResized("img/-2.png")));
            }
            // Add 5 0 cards to the deck, 10 will be added in the next loop to have 15 of them
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(0, dict.get(0),new CardImgResized("img/0.png")));
            }
            // Add 10 -1->10 cards to the deck
            for (int i = -1; i <= 12; i++) {
                for (int j = 0; j < 10; j++) {
                    cards.add(new Card(i, dict.get(i),new CardImgResized("img/"+i+".png")));
                }
            }

            //set the player id to -2 to detect later when a card is from deck or not
            for(int i=0; i<cards.size(); i++){
                cards.get(i).setPlayerID(-2);
            }

            // Shuffle the deck
            shuffle();
        } else {
            // Initialize an empty deck
            cards = new ArrayList<>();
        }
    }

    // Get the list of cards in the deck
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards); // shuffle the cards using the Collections.shuffle method
    }

    //return the card on the top of the deck
    public Card getFirstCard(){
        return cards.get(cards.size()-1);
    }

    // Return the last card on the pile and delete it from the deck
    public Card draw() {
        if (this.cards.size() != 0) { // check if there is at least one card in the deck
            Card lastCard = this.cards.get(this.cards.size() - 1); // get the last card in the deck
            this.removeCard(); // remove the last card from the deck
            return lastCard; // return the last card
        }
        return null; // return null if there are no cards in the deck
    }

    //return and print the deck on the screen by printing his top card, facing down
    public Card printDeck(JFrame window, int x, int y, Card card){
        card.setBounds(x, y, card.getFront().getImage().getWidth(null), card.getFront().getImage().getHeight(null));
        card.addMouseListener(new MouseHandler(card));  //adding a mouse listner of type mouse handler
        window.add(card);
        return card;
    }

    //return and print the discard pile by printing an empty card, in fact, at this moment, the discard pile is empty
    public Card PrintDiscardPile(JFrame window, int x, int y, String path, Card card){
        //generate an empty card
        Card discardPile = new Card(new CardImgResized(path));
        discardPile.setBounds(x, y, discardPile.getFront().getImage().getWidth(null), discardPile.getFront().getImage().getHeight(null));
        discardPile.addMouseListener(new MouseHandler(card));   //adding a mouse listner of type mouse handler
        window.add(discardPile);
        return discardPile;
    }

    public void addAllCards(Deck deck) {
        this.getCards().addAll(deck.getCards()); // add all the cards from the provided deck to this deck
    }

    public void removeCard() {
        this.cards.remove(this.cards.size() - 1); // remove the last card from the deck
    }

    public boolean verifyExistence() {
        return this.cards.size() > 0; // check if there is at least one card in the deck and return a boolean value
    }

    public void addCard(Card card) {
        this.cards.add(card); // add a card to the deck
    }
}
