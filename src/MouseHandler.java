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

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked");
        


        if(GameLoop.j == player.getID()){
                card.changeCardImage(card.getFront());
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
