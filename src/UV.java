import javax.swing.ImageIcon;

public class UV {
    private final String name;
    private final java.awt.Color color;
    private final ImageIcon image;

    public UV(String name, java.awt.Color color, ImageIcon image){
            this.name = name;
            this.color = color;
            this.image = image;
    }

    public String getName()
    {
        return this.name;
    }

    public java.awt.Color getColor()
    {
        return this.color;
    }

    public ImageIcon getImage(){
        return image;
    }
}
