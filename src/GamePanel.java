import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.Color;

public class GamePanel extends JPanel{
    //Screen Settings
    final int screenWidth = 1080;
    final int screenHeight = 720;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
    }
}
