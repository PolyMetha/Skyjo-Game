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
            case -2:
                GameLoop.firstSelection = card;
                System.out.println("First selection is deck card : "+card.getName()+" : -2");
                break;
            case -1:
                GameLoop.firstSelection = card;
                System.out.println("First selection is discard card : "+card.getName()+" : -1");
                break;
            default:
                if(GameLoop.playerTurn == player.getID() && !card.getVisibility()&&GameLoop.firstSelection == null){
                    GameLoop.firstSelection = this.card;
                    System.out.println("First selection is : "+card.getName());
                }
                else if(GameLoop.playerTurn == player.getID()){
                    GameLoop.secondSelection = this.card;
                    System.out.println("Second selection is : "+card.getName());
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
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
