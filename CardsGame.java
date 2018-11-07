import java.util.Scanner;

public class CardsGame {
    public enum GameState {ERROR, DEAL_CARDS, DEAL_PLAYER, DEAL_DEALER, REVEAL_CARDS, EVALUATE, REBUILD_DECK}

    private CardsPlayer dealer;
    private CardsPlayer player;
    private PackOfCards deck;
    private int gameCount;
    private Scanner reader;
    private GameState gameState;

    public CardsGame(int nbOfDecks, Scanner reader) {
        this.reader = reader;
        dealer = new CardsPlayer(false, "Dealer");
        player = new CardsPlayer(true, "Player1");
        deck = new PackOfCards();
        deck.initStandardPack();
        deck.shuffle();
        gameCount = 0;
        gameState = GameState.DEAL_CARDS;
    }

    private void nextState() {
        switch (gameState) {
            case DEAL_CARDS:
                if (player.isTheirTurn())
                    gameState = GameState.DEAL_PLAYER;
                break;
            case DEAL_PLAYER:
                if (player.getGameOutcome() != GameOutcome.UNFINISHED)  // player hit 21
                    gameState = GameState.REVEAL_CARDS;

                if (dealer.isTheirTurn())                               // player stood or busted
                    gameState = GameState.DEAL_DEALER;
                break;
            case DEAL_DEALER:
                if (! dealer.isTheirTurn())                            // dealer stood or busted
                    gameState = GameState.EVALUATE;

                break;
            case EVALUATE:
                gameState = GameState.REVEAL_CARDS;
                break;
            case REVEAL_CARDS:
                String s = reader.nextLine();
                // press n to enter new game..
                if (s.equals("n"))
                    gameState = GameState.REBUILD_DECK;

                break;
            case REBUILD_DECK:
                gameState = GameState.DEAL_CARDS;
                break;
            default:
                gameState = GameState.ERROR;
        }

    }

    public void playGame() {
        gameCount++;
        int tickCount = 1;

        while (true) {
            System.out.println("CardsGame.playGame: tick no. " + tickCount);
            tickGameLoop();
            tickCount++;
        }
    }

    public void tickGameLoop() {
        switch (gameState) {
            case DEAL_CARDS:
                player.reset();
                dealer.reset();
                System.out.println("\nPlaying game no. " + gameCount + "\n");
                dealCards();
                player.setTheirTurn(true);
                dealer.setTheirTurn(false);

                break;
            case DEAL_PLAYER:
                int playerVal = PackUtilities.getPackValue(player.getHand());
                dealer.printHand();
                player.printHand();

                // optional check for blackjack (can only check for 21)
                if (PackUtilities.packContainsBlackjack(player.getHand())) {
                    stand(player);
                    System.out.println(player.getName() + " has blackjack hand!");

                } else if (playerVal == 21) {
                    stand(player);
                    System.out.println(player.getName() + " has scored 21!");

                } else if (playerVal > 21) {
                    bust(player);

                } else {
                    readMove(player);
                }

                break;
            case DEAL_DEALER:
                int dealerVal = PackUtilities.getPackValue(dealer.getHand());
                dealer.printHand();
                player.printHand();

                if (dealerVal >= 21) {
                    dealer.setTheirTurn(false);
                } else {
                    readMove(dealer);
                }

                break;
            case EVALUATE:
                evaluateGame();
                break;
            case REVEAL_CARDS:
                PackUtilities.revealPack(dealer.getHand());
                break;
            case REBUILD_DECK:
                PackUtilities.mergePacksShuffle(deck, player.getHand(), dealer.getHand());
                gameCount++;

                break;

            case ERROR:
            default:
                System.out.println("GameState=ERROR");
                System.exit(-1);
        }

        nextState();

    }

    private void readMove(CardsPlayer player) {
        String cmd = reader.nextLine();

        if (cmd.equals("n"))
            stand(player);
        else
            hit(player);
    }

    private void evaluateGame() {
        GameOutcome outcome = PackUtilities.comparePacks(player.getHand(), dealer.getHand());

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

    }

    private void dealCards() {
        player.drawFromDeck(deck);
        dealer.drawFromDeck(deck); // concealed
        dealer.getHand().getTop().conceal();
        player.drawFromDeck(deck);
        dealer.drawFromDeck(deck); // non concealed
    }

    private void hit(CardsPlayer player) {
        player.drawFromDeck(deck);
        player.setTheirTurn(true);
    }

    private void stand(CardsPlayer player) {
        player.setTheirTurn(false);
        if (player != dealer)
            dealer.setTheirTurn(true);
    }

    private void bust(CardsPlayer player) {
        player.setTheirTurn(false);
        System.out.println(player.getName() + " has went over 21!");
        player.lose();
    }


    public PackOfCards getPlayerDeck() {
        return player.getHand();
    }

    public PackOfCards getDealerDeck() {
        return dealer.getHand();
    }
}
