import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    private Card card;
    private Player player;

    //constructor for a card
    public MouseHandler(Card card, Player player){
        super();
        this.card = card;
        this.player = player;
    }

    //constructor for a deck UI card
    public MouseHandler(Card card){
        super();
        this.card = card;
        this.player = null;
    }

    //detect mouse click
    @Override
    public void mouseClicked(MouseEvent e) {

        //detects if we clicked on the deck, the discard pile or a card of the hand of a player
        switch(card.getPlayerId()){
            //case discard pile empty
            case -3:
            //do nothing
                break;

            //case deck
            case -2:
                if(GameLoop.firstSelection==null){      //if its the first time we select something
                    GameLoop.firstSelection = card;     //set the first selection to this card
                    break;
                }

            //case discard pile
            case -1:
                if(GameLoop.firstSelection==null){      //if its the first time we select something
                    GameLoop.firstSelection = card;     //set the first selection to this card
                    break;
                }

            //case player hand
            default:
            //if its the right player hand, and the card isn't visible and nothing else was selected before
                if(GameLoop.playerTurn == player.getID() && !card.getVisibility()&&GameLoop.firstSelection == null){
                    GameLoop.firstSelection = this.card;    //return this card in first selection
                }
                else if(GameLoop.playerTurn == player.getID()){     //if deck or discard pile selected before
                    GameLoop.secondSelection = this.card;           //return this card in second selection
                }
                break;
        }
    }

    //method from the abstract class we don't use
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    //little animations on cards hover
    @Override
    public void mouseEntered(MouseEvent e) {
        card.setBounds(card.getBounds().x, card.getBounds().y-2, card.getWidth(), card.getHeight());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        card.setBounds(card.getBounds().x, card.getBounds().y+2, card.getWidth(), card.getHeight());
    }
    
}
