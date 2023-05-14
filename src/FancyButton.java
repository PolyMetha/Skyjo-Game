import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class FancyButton extends JButton {

    public FancyButton(String label) {
        super(label);
        setPreferredSize(new Dimension(100, 50)); // set preferred size for the button
        setContentAreaFilled(false); // make the button background transparent
    }

    // override the paintComponent method to draw a rounded rectangle with a color background
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(57, 62, 70));
        g2.fillRoundRect(0, 0, 100, 50, 50, 50); // set the corner radius to 25 pixels
        
        super.paintComponent(g2);
        g2.dispose();
    }

    // override the getPreferredSize method to ensure that the button is not too small
    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        int width = Math.max(preferredSize.width, 100);
        int height = Math.max(preferredSize.height, 40);
        return new Dimension(width, height);
    }

    // override the paintBorder method to draw an empty border and remove the focus border
    @Override
    protected void paintBorder(Graphics g) {
        // do nothing to remove the focus border
    }
}