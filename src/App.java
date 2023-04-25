import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        short nbPlayers = Utility.controlInt((short)2, (short)8, "Enter an integer representing the number of players :", "The number of players must be between 2 and 8, retry.");

        // Initialisation des variables
        GameLoop gameLoop = new GameLoop();
        int maxScore = 50;
        boolean gameOver = false;

        Deck deck = new Deck(true);
        Deck discard_pile = new Deck(false);
        ArrayList<Player> players = new ArrayList<>();

        for (short i = 0 ; i < nbPlayers ; i++) {
            players.add(new Player(i, deck));
        }


        System.out.println("Welcome to the a UTBM version fo the Skyjo game !" +
                "\nLet's choose a card within your cards and see who will begin !");

        while(!gameOver) {
            // Exécution d'une manche de jeu
            gameLoop.executeRound(players, deck, discard_pile);

            Utility.displayScore(players);

            // Vérification du score de chaque joueur
            for(Player player : players)
            {
                if(player.getScore() >= maxScore)
                {
                    gameOver = true;
                    break;
                }
            }
            gameLoop.resetGame(players, deck, discard_pile);
        }

        System.out.println("Le jeu est terminé !");
    }
}