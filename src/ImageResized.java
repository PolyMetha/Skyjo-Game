import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageResized extends ImageIcon{

    public static int IMG_WIDTH = 78, IMG_HEIGHT = 100;

    public ImageResized(String fileName){
        super(fileName);
        Image imageResized = this.getImage().getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        this.setImage(imageResized);
    }
}
