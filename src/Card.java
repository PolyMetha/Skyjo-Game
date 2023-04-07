public class Card {
    private int value;
    private String name;

    public Card(int value, String name){
        this.value = value;
        this.name = name;
    }

    public int GetValue(){
        return this.value;
    }

    public String GetName(){
        return this.name;
    }
}
