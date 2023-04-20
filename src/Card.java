public class Card {
    private int value;
    private UV uv;
    private boolean visible;

    public Card(int value, UV uv){
        this.value = value;
        this.uv = uv;
        this.visible = false;
    }

    public UV getUv()
    {
        return this.uv;
    }

    public int getValue() {
        return this.value;
    }

    public boolean getVisibility(){
        return this.visible;
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

    public String getName(){
        return this.uv.getName();
    }

    public void changeVisibility(boolean visible){
        this.visible = visible;
    }

    public void changeCard(int value, UV uv, boolean visible)
    {
        this.visible = visible;
        this.value = value;
        this.uv = uv;
    }
}
