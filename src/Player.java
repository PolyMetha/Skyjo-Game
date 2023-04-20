import javax.sound.midi.SysexMessage;
import java.util.ArrayList;

public class Player {
    private final int id;                 // 0 or 1
    private final ArrayList<Card> hand;   // hand constitued by 12 cards

    public Player(int ID, Deck deck){
        this.id = ID;
        this.hand = new ArrayList<>();
        initializeHand(deck);
    }

    public ArrayList<Short> askPosition()
    {
        ArrayList<Short> position = new ArrayList<>();
        short row = Utility.controlInt((short) 1, (short) 4, "Enter an integer to select a row :", "The integer must between 1 and 4, retry.");
        short column = Utility.controlInt((short) 1, (short) 3, "Enter an integer to select a column :", "The integer must between 1 and 3, retry.");
        position.add(row);
        position.add(column);
        return position;
    }

    public boolean takeACardFromADeck(Deck deck, Deck discard_pile, short state)
    {
        ArrayList<Short> position;

        if (state == 2)
        {
            deck.changeFirstCard(deck.getValueCard(), deck.getUvCard(), true);
            System.out.println("Card picked : " + deck.getCard());
        }

        position = this.askPosition();
        int indiceHand = (position.get(0)-1) * 3 + position.get(1) - 1;
        Card temp = new Card(this.hand.get(indiceHand).getValue(), this.hand.get(indiceHand).getUv()); // récuperation de la carte qui va être remplacé

        if (state == 1)
        {
            this.hand.get(indiceHand).changeCard(discard_pile.getValueCard(), discard_pile.getUvCard(), true);
            discard_pile.removeCard();
            discard_pile.addCard(temp);
        }
        else if (state == 2)
        {
            this.hand.get(indiceHand).changeCard(deck.getValueCard(), deck.getUvCard(), true); // changement de la carte dans le jeu du joueur
            deck.removeCard();
            discard_pile.addCard(temp);
        }
        else if (state == 3)
        {
            if (this.hand.get(indiceHand).getVisibility())
            {
                System.out.println("This card is already returned, please select another card.");
                this.printHand();
                return false;
            }
            else
            {
                this.hand.get(indiceHand).changeVisibility(true);
            }
        }
        else
        {
            System.out.println("Error");
        }
        this.printHand();
        return true;
    }

    public void verifyRowsAndColumns(Player player)
    {
        for (int i = 0 ; i < 12 ; i = i + 3)
        {
            if (player.hand.get(i).getUv().equals(player.hand.get(i+1).getUv()) && player.hand.get(i).getUv().equals(player.hand.get(i+2).getUv()))
            {
                player.hand.remove(i);
                player.hand.remove(i+1);
                player.hand.remove(i+2);
            }
        }
    }

    public void round(Deck deck, Deck discard_pile) {
        boolean round_played;
        do {
            round_played = false;
            System.out.println("--------------------------------------");
            System.out.println("1. Choose a card from the discard pile");
            System.out.println("2. Choose a card from the deck");
            System.out.println("3. Discover a card within your game");
            System.out.println("--------------------------------------");
            short indice = Utility.controlInt((short) 1, (short) 3, "Enter an integer to select a line :", "The integer must between 1 and 3, retry.");

            switch (indice) {
                case 1 -> {
                    if (!discard_pile.verifyExistence()) {
                        System.out.println("The discard pile is empty, choose another action.");
                        break;
                    }
                    round_played = this.takeACardFromADeck(deck, discard_pile, (short) 1);
                }
                case 2 -> {
                    if (!deck.verifyExistence()) {
                        // reprendre le talon moins la dernière carte
                        // le melanger
                        // le copier dans le deck
                        // voilà
                        break;
                    }
                    round_played = this.takeACardFromADeck(deck, discard_pile, (short) 2);
                }
                case 3 -> round_played = this.takeACardFromADeck(deck, discard_pile, (short) 3);
                default -> {
                    /*for (Card card : this.hand) {
                        card.changeVisibility(true);
                    }
                    this.printHand();
                    round_played = true;*/
                    System.out.println("Error");
                }
            }

            deck.printDeck("Deck");
            discard_pile.printDeck("Discard pile");

        } while (!round_played);

        // verify color rows or column !
    }

    public boolean verifyWin(short round, short nbPlayers) {
        short count = 0;
        for (int i = 0 ; i < this.hand.size() - 1 ; i++)
        {
            if (!this.hand.get(i).getVisibility())
            {
                count ++;
            }
        }
        if (count <= 0)
        {
            if (round != nbPlayers - 1)
            {
                System.out.println("All of your cards are returned, you finished ! Please wait the end of the round");
            }
            else
            {
                System.out.println("Game finished !");
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    //initialize the base cards of the player
    public void initializeHand(Deck deck){
        for(int i=0; i < 12; i++){
            hand.add(deck.draw());
        }
    }

    //print hand of the player
    public void printHand(){
        System.out.print("\nHand of player : " + this.id + "\n");
        System.out.println("--------------------------------");
        for(int i = 1; i <= this.hand.size(); i++){

            if(i%3==0)
            {
                System.out.print(this.hand.get(i-1).getCard());
                System.out.println("\n--------------------------------");
            }
            else {
                System.out.print(this.hand.get(i-1).getCard() + " | ");
            }
        }
    }

    public ArrayList<Card> getHand()
    {
        return this.hand;
    }
}
