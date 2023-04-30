import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ImageIcon;
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
                cards.add(new Card(-2, dict.get(-2),new ImageResized("img/-2.png")));
            }
            // Add 5 0 cards to the deck, 10 will be added in the next loop to have 15 of them
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(0, dict.get(0),new ImageResized("img/0.png")));
            }
            // Add 10 -1->10 cards to the deck
            for (int i = -1; i <= 12; i++) {
                for (int j = 0; j < 10; j++) {
                    cards.add(new Card(i, dict.get(i),new ImageResized("img/"+i+".png")));
                }
            }

            // Shuffle the deck
            shuffle();
        } else {
            // Initialize an empty deck
            cards = new ArrayList<>();
        }
    }

    // Get the value of the top card in the deck
    public int getValueCard() {
        return cards.get(this.cards.size() - 1).getValue();
    }

    // Get the list of cards in the deck
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    // Get the name of the top card in the deck
    public String getCard() {
        return cards.get(this.cards.size() - 1).getCard();
    }

    // Get the UV values of the top card in the deck
    public UV getUvCard() {
        return cards.get(this.cards.size() - 1).getUv();
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards); // shuffle the cards using the Collections.shuffle method
    }

    public void changeFirstCard(int value, UV uv, boolean visible) {
        // change the value, uv, and visibility of the first card in the deck
        this.cards.get(this.cards.size() - 1).changeCard(value, uv, visible);
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

    public void printDeck(String name) {
        if (this.cards.size() > 0) // check if there is at least one card in the deck
        {
            // change the visibility of the last card in the deck and print it
            this.cards.get(this.cards.size() - 1).changeVisibility(!name.equals("Deck"));
            System.out.print(name + " : |" + this.cards.get(this.cards.size() - 1).getCard() + "| \n");
        } else {
            System.out.println(name + " : empty"); // if there are no cards in the deck, print "empty"
        }
    }

    public void printDeck(JFrame window, int x, int y){
        UIComponent deck = new UIComponent(new ImageResized("img/back.png"));
        deck.setBounds(x, y, deck.getImage().getImage().getWidth(null), deck.getImage().getImage().getWidth(null));
        window.add(deck);
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
