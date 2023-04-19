import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.awt.color.*;;

public class Deck {

    private ArrayList<Card> cards;
    private HashMap<Integer, UV> dict = new HashMap<>();

    public Deck(boolean fill) {

        if (fill)
        {
            dict.put(-2, new UV("PC20", java.awt.Color.decode("#FEB801")));
            dict.put(-1,  new UV("LE09", java.awt.Color.decode("#E2595C")));
            dict.put(0,  new UV("MBE3", java.awt.Color.decode("#FEB801")));
            dict.put(1,  new UV("IF2B", java.awt.Color.decode("#FEB801")));
            dict.put(2, new UV("MT2A", java.awt.Color.decode("#22A60D")));
            dict.put(3,  new UV("IF3B", java.awt.Color.decode("#FEB801")));
            dict.put(4, new UV("LE08", java.awt.Color.decode("#E2595C")));
            dict.put(5, new UV("PS2A", java.awt.Color.decode("#22A60D")));
            dict.put(6, new UV("LE03", java.awt.Color.decode("#E2595C")));
            dict.put(7, new UV("IF2A", java.awt.Color.decode("#22A60D")));
            dict.put(8, new UV("GE00", java.awt.Color.decode("#E2595C")));
            dict.put(9, new UV("MT3E", java.awt.Color.decode("#22A60D")));
            dict.put(10, new UV("PS28", java.awt.Color.decode("#22A60D")));
            dict.put(11, new UV("MT28", java.awt.Color.decode("#22A60D")));
            dict.put(12, new UV("PS25", java.awt.Color.decode("#22A60D")));


            cards = new ArrayList<Card>();
            //adding 5 -2 cards to the deck
            for(int i = 0; i<5; i++){
                cards.add(new Card(-2, dict.get(-2)));
            }
            //adding 5 0 cards to the deck, 10 will be added in the next loop to have 15 of it
            for(int i = 0; i<5; i++){
                cards.add(new Card(0, dict.get(0)));
            }
            //adding 10 1->10 cards to the deck
            for(int i = -1; i<=12; i++){
                for(int j=0; j<10; j++){
                    cards.add(new Card(i, dict.get(i)));
                }
            }
            Shuffle();
        }
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

    public void PrintDeck(String name) {
        System.out.println(name + " : ");
        System.out.print("|" + cards.get(0).getCard() + "| \n");
    }
}
