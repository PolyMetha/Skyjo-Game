import java.util.ArrayList;

public class Player {
    private int id;                 //0 or 1
    private boolean hisTurn;        //true if its his turn
    private ArrayList<Card> hand;   //hand constitued by 12 cards

    public Player(int ID, Deck deck){
        this.id = ID;
        this.hisTurn = false;
        hand = new ArrayList<Card>();
        initializeHand(deck);
    }

    //return hisTurn
    public boolean getTurn(){
        return hisTurn;
    }

    //set hisTurn
    public void setTurn(boolean state){
        this.hisTurn = state;
    }

    //initialize the base cards of the player
    public void initializeHand(Deck deck){
        for(int i=0; i<12; i++){
            hand.add(deck.Draw());
        }
    }

    //print hand of the player
    public void printHand(){
        System.out.print("\nHand of player : " + this.id + "\n");
        for(int i =1; i<=12; i++){
            System.out.print(hand.get(i-1).GetValue()+" | ");

            if(i%3==0){
                System.out.println("\n-------------");
            }
        }
        System.out.print("\n");
    }
}
