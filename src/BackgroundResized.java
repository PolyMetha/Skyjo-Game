import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class BackgroundResized extends ImageIcon{
    //screen dimensions
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenHeight = (int) screenSize.getHeight();
    int screenWidth = (int) screenSize.getWidth();

    //resize the background image
    public BackgroundResized(String fileName){
        super(fileName);
        Image imageResized = this.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        this.setImage(imageResized);
    }
}
