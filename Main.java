import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
//        CardsGame game = new CardsGame(1);
//        Scanner reader = new Scanner(System.in);
//        game.startGame(reader);
        Game game = new Game("Blackjack", 640, 360);

        game.start();
    }

//    public static void main2(String[] args) throws Exception {
//        PackOfCards p = new PackOfCards();
//        PackOfCards defendantPack = new PackOfCards();
//        PackOfCards dealerPack = new PackOfCards();
//        Scanner reader = new Scanner(System.in);
//        String cmd;
//        boolean defendantLost = false;
//        int wins, losses, draws;
//        wins = losses = draws = 0;
//
//        p.initStandardPack();
//        p.shuffle();
//
//        while (true) {
//            System.out.println();
//            System.out.println(wins + losses + draws + " games total");
//            System.out.println("SCORE: " + wins + " - " + losses + " (" + draws + " draws)");
//
//            defendantLost = false;
//
//            do {
//                Card newCard = p.extract();
//                defendantPack.insertIntoPack(newCard);
//
//                System.out.print("Your cards: ");
//                PackUtilities.printPack(defendantPack);
//                System.out.println("Pack value: " + PackUtilities.getPackValue(defendantPack));
//
//                if (PackUtilities.getPackValue(defendantPack) == 21)
//                    break;
//
//                if (PackUtilities.getPackValue(defendantPack) > 21) {
//                    defendantLost = true;
//                    break;
//                }
//
//                System.out.print("Press n for no more cards / press any key for more cards... ");
//                cmd = reader.nextLine();
//                clearScreen();
//            } while (!cmd.equals("n"));
//
//            System.out.println();
//
//            if (defendantLost) {
//                System.out.println(GameState.DEFEAT);
//                losses++;
//            } else {
//                do {
//
//                    Card newCard = p.extract();
//                    dealerPack.insertIntoPack(newCard);
//
//                    System.out.print("Dealers cards: ");
//                    PackUtilities.printPack(dealerPack);
//
//                    if (PackUtilities.getPackValue(dealerPack) >= 21) {
//                        break;
//                    }
//
//                    System.out.println("Dealer has " + PackUtilities.getPackValue(dealerPack) +
//                            " and has to beat " + PackUtilities.getPackValue(defendantPack));
//
//
//                    System.out.println("Press n for no more cards / press any key for more cards... ");
//                    cmd = reader.nextLine();
//                    clearScreen();
//                } while (!cmd.equals("n"));
//
//                GameState outcome = PackUtilities.comparePacks(defendantPack, dealerPack);
//                System.out.println(outcome);
//
//                switch(outcome) {
//                    case VICTORY:   wins++;
//                                    break;
//                    case DEFEAT:    losses++;
//                                    break;
//                    case DRAW:      draws++;
//                                    break;
//                }
//
//            }
//
//            reader.nextLine();
//
//            PackUtilities.mergePacksShuffle(p, defendantPack, dealerPack);
//            clearScreen();
//        }
//    }

    public static void clearScreen() throws Exception {
        System.out.println(new String(new char[100]).replace("\0", "\r\n"));
    }
}
