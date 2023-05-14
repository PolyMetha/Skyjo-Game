import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BackgroundResized extends ImageIcon{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenHeight = (int) screenSize.getHeight();
    int screenWidth = (int) screenSize.getWidth();

    public BackgroundResized(String fileName){
        super(fileName);
        Image imageResized = this.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        this.setImage(imageResized);
    }
}
