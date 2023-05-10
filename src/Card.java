import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Card extends JLabel{
    // Private member variables to store the card's value, UV, and visibility status
    private int value;
    private UV uv;
    private boolean visible;
    private int playerID=-2; //set all the cards to-1, the cards -2 corresponds to the deck, -1 to the discard pile and others positive are for the players
    private int playerTurn;

    private ImageIcon back;
    private ImageIcon front;

    private JPanel panel;


    public void changeCardImage(ImageIcon newCard){
        this.visible = true;
        this.setIcon(newCard);
    }

    public void returnCard(){
        this.visible = true;
        this.setIcon(front);
    }

    public JPanel getPanel(){
        return this.panel;
    }

    public void setPanel(JPanel panel){
        this.panel=panel;
    }

    //to intervert 2 cards when we need to
    public void changeCard(Card other){
        this.value = other.getValue();
        this.uv = other.uv;
        this.front = other.getFront();
        this.visible = false;
    }

    //used only to instantiate the UI of the discard pile
    public Card(CardImgResized img){
        super();
        this.value = 0;
        this.uv = new UV("Discard empty",Color.black);
        this.visible = false;

        front = new CardImgResized("img/Discard_empty.png");
        back = new CardImgResized("img/Discard_empty.png");
        playerID=-1;
        this.setIcon(back);
    }

    // Constructor to initialize the card's value, UV, and visibility status
    public Card(int value, UV uv, ImageIcon icon){
        super(icon);
        this.value = value;
        this.uv = uv;
        this.visible = false;

        front = icon;
        back = new CardImgResized("img/Back.png");
        this.setIcon(back);
    }

        // Constructor to initialize the card's value, UV, and visibility status
        public Card(Card card){
            super(card.getFront());
            this.value = card.getValue();
            this.uv = card.getUv();
            this.visible = card.getVisibility();
    
            front = card.getFront();
            back = new CardImgResized("img/Back.png");
            this.setIcon(back);
        }

    public ImageIcon getFront(){
        return front;
    }

    public ImageIcon getBack(){
        return back;
    }

    public int getPlayerTurn(){
        return playerTurn;
    }

    public int getPlayerId(){
        return playerID;
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

    public Card getCard(){
        return this;
    }

    // Method to get the string representation of the card
    public String getCardName() {
        if (this.getVisibility()) {
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

    public void setPlayerID(int id){
        this.playerID = id;
    }

    // Setter method to change the visibility status of the card
    public void setVisibility(boolean vis) {
        this.visible = vis;
    }

    // Setter method to change the value, UV, and visibility status of the card
    public void changeCard(int value, UV uv, boolean vis) {
        this.visible = vis;
        this.value = value;
        this.uv = uv;
    }
}
