import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class UIComponent extends JLabel {
    private Runnable onClick;
    private ImageIcon image;
    private ImageIcon back;
    private ImageIcon front;

    public UIComponent(ImageIcon icon) {
        super(icon);
        image = icon;
        front = icon;
        back = new ImageIcon("img/Back.png");
        this.setIcon(back);
    }


    public void changeCardImage(ImageIcon newCard){
        // this.setIcon(newCard);
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public ImageIcon getImage(){
        return this.image;
    }
}