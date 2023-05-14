import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class FancyButton extends JButton {
    //define the colors of the button and the text
    private static final Color BACKGROUND_COLOR = new Color(57, 62, 70);
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    //generate a super nice looking button (that we are proud of too :) )
    public FancyButton(String label) {
        super(label);
        setPreferredSize(new Dimension(100, 50)); // set preferred size for the button
        setContentAreaFilled(false); // make the button background transparent
        setForeground(FOREGROUND_COLOR);
        setBorder(new EmptyBorder(10, 20, 10, 20)); // add padding to the button text
    }

    //Rendering nice and smooth our beautiful button
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());

        // Draw text
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fontMetrics = g2.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(getText())) / 2;
        int y = (getHeight() + fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}
