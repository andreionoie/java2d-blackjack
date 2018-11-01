import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
//        CardsGame game = new CardsGame(1);
//        Scanner reader = new Scanner(System.in);
//        game.startGame(reader);

        Game game = new Game("Blackjack", 640, 360);
        game.start();
    }

}
