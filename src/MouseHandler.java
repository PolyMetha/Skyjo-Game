import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    private Card card;
    private Player player;

    public MouseHandler(Card card, Player player){
        super();
        this.card = card;
        this.player = player;
    }

    public MouseHandler(Card card){
        super();
        this.card = card;
        this.player = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //detects if we clicked on the deck, the discard pile or a card of the hand of a player
        switch(card.getPlayerId()){
            //case discard pile empty
            case -3:
                break;
            //case deck
            case -2:
                if(GameLoop.firstSelection==null){
                    GameLoop.firstSelection = card;
                    break;
                }
            //case discard pile
            case -1:
                if(GameLoop.firstSelection==null){
                    GameLoop.firstSelection = card;
                    break;
                }
            //case player hand
            default:
                if(GameLoop.playerTurn == player.getID() && !card.getVisibility()&&GameLoop.firstSelection == null){
                    GameLoop.firstSelection = this.card;
                }
                else if(GameLoop.playerTurn == player.getID()){
                    GameLoop.secondSelection = this.card;
                }
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        card.setBounds(card.getBounds().x, card.getBounds().y-2, card.getWidth(), card.getHeight());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        card.setBounds(card.getBounds().x, card.getBounds().y+2, card.getWidth(), card.getHeight());
    }
    
}
