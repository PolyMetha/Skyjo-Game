import javax.sound.midi.SysexMessage;
import java.util.ArrayList;

public class Player {
    private final int id;                 // 0 or 1
    private final ArrayList<Card> hand;   // hand constitued by 12 cards
    private short nbColumnRemoved;
    private short nbLineRemoved;

    public Player(int ID, Deck deck){
        this.id = ID;
        this.hand = new ArrayList<>();
        this.nbColumnRemoved = 0;
        this.nbLineRemoved = 0;
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
        int indiceHand = (position.get(0)-1) * (3 - this.nbColumnRemoved) + position.get(1) - 1;
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
        return true;
    }

    public void verifyRowsAndColumns()
    {
        // verify rows
        if (this.nbColumnRemoved == 0)
        {
            for (int i = 0; i <= 9 - (this.nbLineRemoved * 3) ; i = i + 3)
            {
                try
                {
                    if (this.hand.get(i).getUv().equals(this.hand.get(i + 1).getUv()) && this.hand.get(i).getUv().equals(this.hand.get(i + 2).getUv()) &&
                            this.hand.get(i).getVisibility() && this.hand.get(i + 1).getVisibility() && this.hand.get(i + 2).getVisibility())
                    {
                        this.hand.remove(i);
                        this.hand.remove(i);
                        this.hand.remove(i);
                        this.nbLineRemoved += 1;
                        System.out.println("Line removed !");
                    }
                }
                catch (Exception exception)
                {
                    // System.out.println(exception);
                }
            }
        }
        // verify columns
        if (this.nbLineRemoved < 2)
        {
            for (int i = 0 ; i <= 2 - this.nbColumnRemoved; i ++)
            {
                try
                {
                    if (this.hand.get(i).getUv().equals(this.hand.get(i + 3 - this.nbColumnRemoved).getUv())
                            && this.hand.get(i).getUv().equals(this.hand.get(i + 6 - (this.nbColumnRemoved * 2)).getUv())
                            && this.hand.get(i).getUv().equals(this.hand.get(i + 9 - (this.nbColumnRemoved * 3)).getUv()))
                    {
                        this.hand.remove(i);
                        this.hand.remove(i + 3 - this.nbColumnRemoved - 1);
                        switch (this.nbLineRemoved)
                        {
                            case 0 ->
                            {
                                this.hand.remove(i + 6 - (this.nbColumnRemoved * 2) - 2);
                                this.hand.remove(i + 9 - (this.nbColumnRemoved * 3) - 3);
                            }
                            case 1 -> this.hand.remove(i + 6 - (this.nbColumnRemoved * 2) - 2);
                            default -> System.out.println("Error");
                        }
                        this.nbColumnRemoved += 1;
                        System.out.println("Column removed !");
                    }
                }
                catch (Exception exception)
                {
                    // System.out.println("Except 2");
                }
            }
        }
    }

    public void round(Deck deck, Deck discard_pile)
    {
        boolean round_played;
        do
        {
            round_played = false;
            deck.printDeck("Deck");
            discard_pile.printDeck("Discard pile");
            System.out.println("--------------------------------------");
            System.out.println("1. Choose a card from the discard pile");
            System.out.println("2. Choose a card from the deck");
            System.out.println("3. Discover a card within your game");
            System.out.println("--------------------------------------");
            short indice = Utility.controlInt((short) 1, (short) 4, "Enter an integer to select a line :", "The integer must between 1 and 3, retry.");

            switch (indice)
            {
                case 1 ->
                {
                    if (!discard_pile.verifyExistence())
                    {
                        System.out.println("The discard pile is empty, choose another action.");
                        break;
                    }
                    round_played = this.takeACardFromADeck(deck, discard_pile, (short) 1);
                }
                case 2 ->
                {
                    if (!deck.verifyExistence())
                    {
                        // reprendre le talon moins la dernière carte
                        // le melanger
                        // le copier dans le deck
                        // voilà
                        break;
                    }
                    round_played = this.takeACardFromADeck(deck, discard_pile, (short) 2);
                }
                case 3 -> round_played = this.takeACardFromADeck(deck, discard_pile, (short) 3);
                default ->
                {
                    for (Card card : this.hand)
                    {
                        card.changeVisibility(true);
                    }
                    this.printHand();
                    round_played = true;
                    System.out.println("Error");
                }
            }
        }
        while (!round_played);

        this.verifyRowsAndColumns();
        // verify color rows and columns !
    }

    public boolean verifyWin(short round, short nbPlayers)
    {
        short count = 0;
        for (Card card : this.hand) {
            if (!card.getVisibility()) {
                count++;
            }
        }
        if (count == 0)
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
    public void initializeHand(Deck deck)
    {
        for(int i=0; i < 12; i++)
        {
            hand.add(deck.draw());
        }
    }

    //print hand of the player
    public void printHand()
    {
        System.out.print("\nHand of player : " + (this.id + 1) + "\n");
        System.out.println("--------------------------------");
        for (int i = 1; i <= this.hand.size(); i++)
        {
            switch (this.nbColumnRemoved)
            {
                case 0 ->
                {
                    if (i % 3 == 0)
                    {
                        System.out.print(this.hand.get(i - 1).getCard());
                        System.out.println("\n--------------------------------");
                    }
                    else
                    {
                        System.out.print(this.hand.get(i - 1).getCard() + " | ");
                    }
                }
                case 1 ->
                {
                    if (i % 2 == 0)
                    {
                        System.out.print(this.hand.get(i - 1).getCard());
                        System.out.println("\n--------------------------------");
                    }
                    else
                    {
                        System.out.print(this.hand.get(i - 1).getCard() + " | ");
                    }
                }
                case 2 ->
                {
                    System.out.print(this.hand.get(i - 1).getCard());
                    System.out.println("\n--------------------------------");
                }
                case 3 -> System.out.println("empty");
            }
        }
    }

    public ArrayList<Card> getHand()
    {
        return this.hand;
    }

    public short getFirstCardReturned()
    {
        for (Card card : hand)
        {
            if (card.getVisibility())
            {
                return (short) card.getValue();
            }
        }
        return 20;
    }
}
