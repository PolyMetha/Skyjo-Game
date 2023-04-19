import javax.swing.ImageIcon;

public class UV {
    private String name;
    private java.awt.Color color;
    private ImageIcon image;

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
