import java.util.ArrayList;
import java.util.Iterator;

public class GameLoop {

    public void resetGame(ArrayList<Player> players, Deck deck, Deck discard_pile)
    {
        deck = new Deck(true);
        discard_pile = new Deck(false);

        for (Player player : players)
        {
            player.resetPlayer(deck);
        }
    }

    public void executeRound(ArrayList<Player> players, Deck deck, Deck discard_pile) {
        //the actual round playing
        short nbRound = 0;
        boolean play = true;
        Iterator<Player> its = players.iterator();

        boolean atLeastOnePlayerFinished = false;

        for (Player player : players) {
            player.printHand();
        }

        for (short j = 0 ; j < players.size() ; j++) {
            if (j != 0)
            {
                players.get(j).printHand();
            }
            players.get(j).takeACardFromADeck(deck, discard_pile, (short) 3);
        }

        ArrayList<Short> listFirstCardPlayer = new ArrayList<>();

        for (Player player : players)
        {
            listFirstCardPlayer.add(player.getFirstCardReturned());
        }

        short min = Short.MAX_VALUE; // Initialisez la variable min avec une valeur maximale possible de short
        int minIndex = -1; // Initialisez l'indice avec une valeur invalide
        for (int i = 0; i < listFirstCardPlayer.size(); i++) {
            if (listFirstCardPlayer.get(i) < min) {
                min = listFirstCardPlayer.get(i);
                minIndex = i;
            }
        }

        short j;
        while(play) {
            if (nbRound == 0)
            {
                j = (short) minIndex;
                if (j != 0)
                {
                    its.next();
                }
                System.out.println("\nThe first player to start is player " + (j + 1));
            }
            else
            {
                j = 0;
            }

            do
            {
                if (j != minIndex) {
                    System.out.println("It's player " + (j + 1) + " round's");
                }
                minIndex = 100;

                for (Player player : players) {
                    player.printHand();
                }

                players.get(j).round(deck, discard_pile);
                play = !its.next().verifyWin(nbRound, (short) players.size());
                if (!play)
                {
                    atLeastOnePlayerFinished = true;
                }
                nbRound++;

                if (j == players.size() - 1)
                {
                    j = 0;
                }
                else
                {
                    j ++;
                }
            } while(its.hasNext());

            if (atLeastOnePlayerFinished)
            {
                play = false;
            }
            //reinitialise l'itérateur
            its = players.iterator();
        }
    }
}
