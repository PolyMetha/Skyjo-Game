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

        short firstPlayer;

        for (short j = 0 ; j < players.size() ; j++) {
            if (j != 0)
            {
                players.get(j).printHand();
            }
            players.get(j).takeACardFromADeck(deck, discard_pile, (short) 3);
        }

        deck.printDeck("Deck");
        discard_pile.printDeck("Discard pile");

        for (Player player : players) {
            player.printHand();
        }

        while(play) {
            short i = 0;
            while(its.hasNext())
            {
                if (i == 0 && nbRound != 0) {
                    players.get(i).printHand();
                }
                players.get(i).round(deck, discard_pile);
                play = !its.next().verifyWin((short) nbRound, (short) players.size());
                if (!play)
                {
                    atLeastOnePlayerFinished = true;
                }
                nbRound++;
                i ++;
                if (i < players.size()) {
                    players.get(i).printHand();
                }
            }
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
