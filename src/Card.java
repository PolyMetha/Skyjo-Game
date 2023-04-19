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
        return this.uv.name;
    }

    public String getCard() {
        if (this.visible)
        {
            return this.getName() + " (" + this.getValue() +")";
        }
        else
        {
            return "-";
        }
    }
}
