public class App {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Player player0 = new Player(0, deck);
        Player player1 = new Player(1, deck);
        player0.printHand();
        player1.printHand();
    }
}