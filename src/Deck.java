import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Deck {
    private ArrayList<Card> cards;
    private ArrayList<Card> discardPile;

    private HashMap<Integer, String> dict = new HashMap<>(Map.of(
        -2,"PC20",
        -1, "LE09" ,
        0, "MBE1",
        1, "IF2B",
        2, "MT2A",
        3, "IF3B",
        4, "LE08",
        5, "PS2A",
        6, "LE03",
        7, "IF1A",
        8, "GE00",
        9, "MT3E",
        10, "PS28",
        11, "MT28",
        12, "PS25"
    ));

    public Deck(){
        dict.put(-2, "PC20");
        dict.put(-1, "LE09");
        dict.put(0, "MBE1");
        dict.put(1, "IF2B");
        dict.put(2, "MT2A");
        dict.put(3, "IF3B");
        dict.put(4, "PC20");
        dict.put(5, "LE09");
        dict.put(6, "PC20");
        dict.put(7, "LE09");
        dict.put(8, "PC20");
        dict.put(9, "LE09");
        dict.put(10, "PC20");
        dict.put(11, "LE09");
        dict.put(12, "PC20");
        

        cards = new ArrayList<Card>();
        //adding 5 -2 cards to the deck
        for(int i = 0; i<5; i++){
            cards.add(new Card(-2, "PC20"));
        }
        //adding 5 0 cards to the deck, 10 will be added in the next loop to have 15 of it
        for(int i = 0; i<5; i++){
            cards.add(new Card(0, "MBE1"));
        }
        //adding 10 1->10 cards to the deck
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
