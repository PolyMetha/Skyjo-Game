import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;

public class GameLoop {
    private int nbRound;    //the actual round playing
    private int roundOf;    //The number of the player which is playing (0 or 1)
    private boolean play;

    public GameLoop(ArrayList<Player> players, Deck deck, Deck discard_pile){
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
                i++;
                if (i <= players.size() - 1) {
                    players.get(i).printHand();
                }
                play = !its.next().verifyWin();
                nbRound ++;
            }
            //reinitialise l'itérateur
            its = players.iterator();
        }

        // calcul score
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
