import java.util.ArrayList;
import java.util.Iterator;

public class GameLoop {
    private int nbRound;    //the actual round playing
    private int roundOf;    //The number of the player which is playing (0 or 1)
    private boolean play;

    public short calculScore(Player player)
    {
        short score = 0;
        short indice = 0;
        Iterator<Card> it = player.getHand().iterator();
        while (it.hasNext())
        {
            Card card = it.next();
            score += (short) card.getValue();
        }
        return score;
    }

    public void displayScore(ArrayList<Player> players)
    {
        ArrayList<Short> scoreTable = new ArrayList<>();

        for (int j = 0 ; j < players.size(); j++)
        {
            scoreTable.add(calculScore(players.get(j)));
        }

        System.out.println("------- Score -------");

        for (int k = 0 ; k < players.size(); k++)
        {
            System.out.println("Player " + (k + 1) + " : " + scoreTable.get(k));
        }

        System.out.println("---------------------");
    }

    public GameLoop(ArrayList<Player> players, Deck deck, Deck discard_pile)
    {
        this.nbRound = 0;
        roundOf = (int)(Math.random()*players.size());
        this.play = true;
        Iterator<Player> its = players.iterator();

        while(play) {
            int i = 0;
            while(its.hasNext()){
                if (i == 0 && nbRound != 0) {
                    players.get(i).printHand();
                }
                players.get(i).round(deck, discard_pile);
                play = !its.next().verifyWin((short) nbRound, (short) (players.size()-1));
                i++;
                if (i < players.size()) {
                    players.get(i).printHand();
                }
                nbRound ++;
            }
            //reinitialise l'itérateur
            its = players.iterator();
        }

        this.displayScore(players);
    }

    //switch the player playing
    public void nextTurn(){
        if(roundOf == 0){
            roundOf = 1;
        }
        else{
            roundOf=0;
        }
    }

    public void nextRound(){
        nbRound+=1;
    }

    public int getNbRound(){
        return nbRound;
    }

    public int getRoundOf(){
        return roundOf;
    }
}
