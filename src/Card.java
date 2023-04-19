import java.awt.*;

public class Card {
    private int value;
    private UV uv;
    private boolean visible;

    public Card(int value, UV uv){
        this.value = value;
        this.uv = uv;
        this.visible = false;
    }

    public int getValue() {
        return this.value;
    }

    public String getName(){
        return this.uv.getName();
    }

    public void changeVisibility(){
        this.visible = !this.visible;
    }

    public String getCard() {
        if (this.visible)
        {
            return " " + this.getName() + " (" + this.getValue() +") ";
        }
        else
        {
            return "-";
        }
    }
}
