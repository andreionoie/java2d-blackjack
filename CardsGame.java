import java.util.Scanner;

public class CardsGame {
    private CardsPlayer dealer;
    private CardsPlayer player;
    private PackOfCards deck;
    private int gameCount;

    public CardsGame(int nbOfDecks) {
        dealer = new CardsPlayer(false, "Dealer");
        player = new CardsPlayer(true, "Player1");
        deck = new PackOfCards();
        deck.initStandardPack();
        deck.shuffle();
        gameCount = 0;
    }

    public void startGame(Scanner reader) {
        gameCount++;
        while (true) {
            playRound(reader);
        }
    }

    private void playRound(Scanner reader) {
        System.out.println("Playing game no. " + gameCount + "\n");
        player.reset();
        dealer.reset();
        dealCards();

        String cmd;
        int playerVal, dealerVal;
        player.setTheirTurn(true);
        dealer.setTheirTurn(false);

        do {
            playerVal = PackUtilities.getPackValue(player.getHand());
            dealer.printHand();
            player.printHand();

            if (PackUtilities.packContainsBlackjack(player.getHand())) {
                stand(player);
                System.out.println(player.getName() + " has blackjack hand!");
                break;
            }

            if (playerVal == 21) {
                stand(player);
                System.out.println(player.getName() + " has scored 21!");
                break;
            }

            if (playerVal > 21) {
                bust(player);
                reader.nextLine();
                break;
            }

            cmd = reader.nextLine();

            if (cmd.equals("n")) {
                stand(player);
            } else {
                hit(player);
            }

        } while (player.isTheirTurn());

        dealer.setTheirTurn(true);

        if (player.getGameState() == GameState.UNFINISHED) {
            do {
                dealerVal = PackUtilities.getPackValue(dealer.getHand());
                dealer.printHand();
                player.printHand();

                if (dealerVal >= 21) {
                    stand(dealer);
                } else {
                    cmd = reader.nextLine();

                    if (cmd.equals("n")) {
                        stand(dealer);
                    } else {
                        hit(dealer);
                    }
                }
            } while (dealer.isTheirTurn());
        }

        GameState outcome = PackUtilities.comparePacks(player.getHand(), dealer.getHand());

        switch (outcome) {
            case VICTORY:   player.win();
                            dealer.lose();
                            break;
            case DEFEAT:    player.lose();
                            dealer.win();
                            break;
            case DRAW:      player.draw();
                            dealer.draw();
                            break;
        }

        System.out.println("\n\n");
        PackUtilities.mergePacksShuffle(deck, player.getHand(), dealer.getHand());
        gameCount++;

    }

    private void dealCards() {
        player.drawFromDeck(deck);
        dealer.drawFromDeck(deck); // concealed
        player.drawFromDeck(deck);
        dealer.drawFromDeck(deck); // non concealed
    }

    private void hit(CardsPlayer player) {
        player.drawFromDeck(deck);
        player.setTheirTurn(true);
    }

    private void stand(CardsPlayer player) {
        player.setTheirTurn(false);
    }

    private void bust(CardsPlayer player) {
        player.setTheirTurn(false);
        System.out.println(player.getName() + " has went over 21!");
        player.lose();
    }
}
