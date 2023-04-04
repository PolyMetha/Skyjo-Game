import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;

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
    }

    public void PrintDeck(){

        cards.forEach((n) -> System.out.println(n.GetValue()));
    }
}
