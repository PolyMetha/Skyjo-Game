public class Card {

    // Private member variables to store the card's value, UV, and visibility status
    private int value;
    private UV uv;
    private boolean visible;

    // Constructor to initialize the card's value, UV, and visibility status
    public Card(int value, UV uv){
        this.value = value;
        this.uv = uv;
        this.visible = false;
    }

    // Getter method to retrieve the card's UV
    public UV getUv() {
        return this.uv;
    }

    // Getter method to retrieve the card's value
    public int getValue() {
        return this.value;
    }

    // Getter method to retrieve the card's visibility status
    public boolean getVisibility() {
        return this.visible;
    }

    // Method to get the string representation of the card
    public String getCard() {
        if (this.visible) {
            // If the card is visible, return the name and value of the card
            return this.getName() + " (" + this.getValue() +")";
        } else {
            // If the card is not visible, return a dash to represent a face-down card
            return "-";
        }
    }

    // Method to get the name of the UV of the card
    public String getName() {
        return this.uv.getName();
    }

    // Setter method to change the visibility status of the card
    public void changeVisibility(boolean visible) {
        this.visible = visible;
    }

    // Setter method to change the value, UV, and visibility status of the card
    public void changeCard(int value, UV uv, boolean visible) {
        this.visible = visible;
        this.value = value;
        this.uv = uv;
    }
}
