import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class BackgroundResized extends ImageIcon{
    //screen dimensions
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenHeight = (int) screenSize.getHeight();
    private final int screenWidth = (int) screenSize.getWidth();

    //resize the background image (that we are proud of :) )
    public BackgroundResized(String fileName){
        super(fileName);
        Image imageResized = this.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        this.setImage(imageResized);
    }
}
