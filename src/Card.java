import java.awt.*;

public class Card {
    private int value;
    private UV uv;

    public Card(int value, UV uv){
        this.value = value;
        this.uv = uv;
    }

    public int getValue() {
        return this.value;
    }

    public String getName(){
        return this.uv.name;
    }
}
