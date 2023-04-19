import java.util.ArrayList;

public class GameLoop {
    private int nbRound;    //the actual round playing
    private int roundOf;    //The number of the player which is playing (0 or 1)

    public GameLoop(ArrayList<Player> players){
        this.nbRound = 0;
        roundOf = (int)(Math.random()*players.size());
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
