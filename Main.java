import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
//        Scanner reader = new Scanner(System.in);
//        CardsGame game = new CardsGame(1, reader);
//
//        game.playGame();

        Game game = new Game("Blackjack", 500, 300);
        game.start();
    }

}
