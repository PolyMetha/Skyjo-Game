import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class CardImgResized extends ImageIcon{

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenHeight = (int) screenSize.getHeight();

    public static int IMG_HEIGHT = ((screenHeight/2-60)-(20+3*5))/4, IMG_WIDTH = (int)(IMG_HEIGHT*0.778);

    public CardImgResized(String fileName){
        super(fileName);
        Image imageResized = this.getImage().getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        this.setImage(imageResized);
    }
}
