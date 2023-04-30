import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class UIComponent extends JLabel {
    private Runnable onClick;
    private ImageIcon image;

    public UIComponent(ImageIcon icon) {
        super(icon);
        image = icon;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) {
                    onClick.run();
                    System.out.println("clickes");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }


    public void changeCardImage(ImageIcon newCard){
        this.setIcon(newCard);
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public ImageIcon getImage(){
        return image;
    }
}