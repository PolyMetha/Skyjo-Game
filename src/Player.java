import java.util.ArrayList;
import java.util.Scanner;

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

    public ArrayList<Short> askPosition()
    {
        ArrayList<Short> position = new ArrayList<Short>();
        short row = Utility.controlInt((short) 1, (short) 3, "Enter an integer to select a row :", "The integer must between 1 and 3, retry.");
        short column = Utility.controlInt((short) 1, (short) 4, "Enter an integer to select a column :", "The integer must between 1 and 4, retry.");
        position.add(row);
        position.add(column);
        return position;
    }

    public void round(Deck deck, Deck discard_pile) {
        boolean round_played = false;
        do {
            System.out.println("--------------------------------------");
            System.out.println("1. Choose a card from the discard pile");
            System.out.println("2. Choose a card from the deck");
            System.out.println("3. Discover a card within your game");
            System.out.println("--------------------------------------");
            short indice = Utility.controlInt((short) 1, (short) 3, "Enter an integer to select a line :", "The integer must between 1 and 3, retry.");

            switch (indice) {
                case 1:
                    if (!discard_pile.verifyExistence()) {
                        break;
                    }
                    ArrayList<Short> position = new ArrayList<Short>();
                    position = this.askPosition();
                    System.out.println(position.get(0) + " : " + position.get(1));
                    break;
                case 2:
                    if (!deck.verifyExistence()) {
                        break;
                    }
                    ArrayList<Short> position2 = new ArrayList<Short>();
                    position2 = this.askPosition();
                    System.out.println(position2.get(0) + " : " + position2.get(1));
                    break;
                case 3:
                    System.out.println("3");
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        } while (round_played);
    }

    public boolean verifyWin(){
        //code pour verifier si ce joueur a gagné
        return false;
    }

    //return hisTurn
    public boolean getTurn(){
        return this.hisTurn;
    }

    //set hisTurn
    public void setTurn(boolean state){
        this.hisTurn = state;
    }

    //initialize the base cards of the player
    public void initializeHand(Deck deck){
        for(int i=0; i<12; i++){
            hand.add(deck.draw());
        }
    }

    //print hand of the player
    public void printHand(){
        System.out.print("\nHand of player : " + this.id + "\n");
        System.out.println("--------------------------------");
        for(int i = 1; i <= 12; i++){

            if(i%3==0)
            {
                System.out.print(hand.get(i-1).getCard());
                System.out.println("\n--------------------------------");
            }
            else {
                System.out.print(hand.get(i-1).getCard() + " | ");
            }
        }
    }
}
