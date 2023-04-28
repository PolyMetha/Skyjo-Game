public class UV {
    // UV name
    private final String name;
    // UV color
    private final java.awt.Color color;

    // Constructor
    public UV(String name, java.awt.Color color){
        // Set the name
        this.name = name;
        // Set the color
        this.color = color;
    }

    // Get the name
    public String getName()
    {
        return this.name;
    }

    // Get the color
    public java.awt.Color getColor()
    {
        return this.color;
    }
}
