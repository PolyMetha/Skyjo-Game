import java.util.ArrayList;
import java.util.Iterator;

public class GameLoop {

    public short calculScore(Player player)
    {
        short score = 0;
        for (Card card : player.getHand()) {
            score += (short) card.getValue();
        }
        return score;
    }

    public void displayScore(ArrayList<Player> players)
    {
        ArrayList<Short> scoreTable = new ArrayList<>();

        for (Player player : players) {
            scoreTable.add(calculScore(player));
        }

        System.out.println("------- Score -------");

        for (short k = 0 ; k < players.size(); k++)
        {
            System.out.println("Player " + (k + 1) + " : " + scoreTable.get(k));
        }

        System.out.println("---------------------");
    }

    public GameLoop(ArrayList<Player> players, Deck deck, Deck discard_pile)
    {
        //the actual round playing
        short nbRound = 0;
        boolean play = true;
        Iterator<Player> its = players.iterator();

        boolean atLeastOnePlayerFinished = false;

        System.out.println("Welcome to the a UTBM version fo the Skyjo game !" +
                "\nLet's choose a card within your cards and see who will begin !");

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

        short firstPlayer = (short) minIndex;

        short i;
        while(play) {
            if (nbRound == 0)
            {
                i = (short) minIndex;
                System.out.println("\nThe first player to start is player " + (i + 1));
            }
            else
            {
                i = 0;
            }

            do
            {
                if (i != minIndex) {
                    System.out.println("It's player " + (i + 1) + " round " + i);
                    minIndex = 100;
                }
                else {
                    minIndex = 100;
                }

                for (Player player : players) {
                    player.printHand();
                }

                players.get(i).round(deck, discard_pile);
                play = !its.next().verifyWin(nbRound, (short) players.size());
                if (!play)
                {
                    atLeastOnePlayerFinished = true;
                }
                nbRound++;

                if (i == players.size() - 1)
                {
                    i = 0;
                }
                else
                {
                    i ++;
                }

            } while(its.hasNext());

            if (atLeastOnePlayerFinished)
            {
                play = false;
            }
            //reinitialise l'itérateur
            its = players.iterator();
        }

        this.displayScore(players);
    }
}
