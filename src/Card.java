import java.awt.*;

public class Card {
    private int value;
    private String name;

    public Card(int value, String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName(){
        return this.name;
    }
}
