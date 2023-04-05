import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    private ArrayList<Card> discardPile;

    public Deck(){
        cards = new ArrayList<Card>();
        //adding 5 -2 cards to the deck
        for(int i = 0; i<5; i++){
            cards.add(new Card(-2));
        }
        //adding 15 0 cards to the deck
        for(int i = 0; i<15; i++){
            cards.add(new Card(0));
        }
        //adding -2 cards to the deck
        for(int i = 0; i<5; i++){
            cards.add(new Card(-2));
        }
        //adding -2 cards to the deck
        for(int i = -1; i<=12; i++){
            for(int j=0; j<10; j++){
                cards.add(new Card(i));
            }
        }
        Shuffle();
    }

    //prints the deck
    public void PrintDeck(){
        System.out.println("\n__________________");
        cards.forEach((n) -> System.out.print(n.GetValue() + " | "));
        System.out.println("\n__________________");
    }

    //shuffle the deck
    public void Shuffle(){
        Collections.shuffle(cards);
    }

    //return the last card on the pile and delete it from the deck
    public Card Draw(){
        if(cards.size()!=0){
            Card lastCard = cards.get(cards.size()-1);
            cards.remove(cards.size()-1);
            return lastCard;
        }
        return null;
    }
}
