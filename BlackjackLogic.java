import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class BlackjackLogic {
    public enum GameState {
        ERROR, INIT, PLACE_BETS, DEAL_CARDS,
        ASK_INSURANCE, DEAL_PLAYER, FORFEIT, DEAL_DEALER,
        REVEAL_CARDS, EVALUATE, REBUILD_DECK
    }

    private CardsPlayer dealer;
    private CardsPlayer player;
    private PackOfCards deck;
    private int gameCount;
    private Scanner reader;

    private GameState gameState;
    private boolean dealerHasAce, dealerHasBlackjack;

    public BlackjackLogic(int nbOfDecks, Scanner reader) {
        this.reader = reader;
        dealer = new CardsPlayer(false, "Dealer");
        player = new CardsPlayer(true, "Player1", 3000);
        gameCount = 0;
        gameState = GameState.INIT;
        deck = new PackOfCards();
        deck.initStandardPack();
        deck.shuffle();
    }

    private void nextState() {
        switch (gameState) {
            case INIT:
                gameState = GameState.PLACE_BETS;
                break;
            case PLACE_BETS:
                //gameState = GameState.DEAL_CARDS;
                break;
            case DEAL_CARDS:
                if (dealerHasAce)
                    gameState = GameState.ASK_INSURANCE;
                else if (player.isTheirTurn())
                    gameState = GameState.DEAL_PLAYER;
                break;
            case ASK_INSURANCE:
                if (dealerHasBlackjack) {
                    gameState = GameState.EVALUATE;
                } else {
                    gameState = GameState.DEAL_PLAYER;
                }
                break;
            case DEAL_PLAYER:
                if (player.hasForfeited())
                    gameState = GameState.FORFEIT;
                else {
                    if (player.getGameOutcome() != GameOutcome.UNFINISHED)  // player hit 21
                        gameState = GameState.REVEAL_CARDS;

                    if (dealer.isTheirTurn())                               // player stood, busted
                        gameState = GameState.DEAL_DEALER;
                }
                break;
            case FORFEIT:
                gameState = GameState.REVEAL_CARDS;
                break;
            case DEAL_DEALER:
                if (! dealer.isTheirTurn())                            // dealer stood or busted
                    gameState = GameState.EVALUATE;

                break;
            case EVALUATE:
                gameState = GameState.REVEAL_CARDS;
                break;
            case REVEAL_CARDS:
//                String s = reader.nextLine();
//                // press n to enter new game..
//                if (s.equals("n"))
//                    gameState = GameState.REBUILD_DECK;

                break;
            case REBUILD_DECK:
                gameState = GameState.INIT;
                break;
            default:
                gameState = GameState.ERROR;
        }

    }

    public void playGame() {
        gameCount++;
        int tickCount = 1;

        while (true) {
            System.out.println("BlackjackLogic.playGame: tick no. " + tickCount);
            tickGameLoop();
            tickCount++;
        }
    }

    public void tickGameLoop() {
        switch (gameState) {
            case INIT:
                player.reset();
                dealer.reset();
                dealerHasAce = dealerHasBlackjack = false;
                System.out.println("\nPlaying game no. " + gameCount + "\n");
                System.out.println("Player balance: " + player.getCredit());
                break;
            case PLACE_BETS:
//                while (true)
//                    try {
//                        System.out.print("Enter bet amount: ");
//                        int amount = Integer.parseInt(reader.nextLine());
//                        player.bet(amount);
//                        break;
//                    } catch (Exception e) {
//                        System.out.println("Enter a correct value!");
//                    }

                break;
            case DEAL_CARDS:
                dealCards();
                player.setTheirTurn(true);
                dealer.setTheirTurn(false);

                break;
            case ASK_INSURANCE:
                System.out.print("Do you want insurance? (y/n) ");
                if (reader.nextLine().trim().equals("y")) {
                    player.getInsurance();
                }

                break;
            case DEAL_PLAYER:
                int playerVal = PackUtilities.getPackValue(player.getHand());
                dealer.printHand();
                player.printHand();
                // optional check for blackjack (can only check for 21)
                if (PackUtilities.packContainsBlackjack(player.getHand())) {
                    player.blackjackPay();  // blackjack pays 3:2
                    stand(player);
                    System.out.println(player.getName() + " has blackjack hand!");

                } else if (playerVal == 21) {
                    stand(player);
                    System.out.println(player.getName() + " has scored 21!");

                } else if (playerVal > 21) {
                    bust(player);

                } else {
                    //readMove(player);
                }

                break;
            case FORFEIT:
                System.out.print("Player has forfeited. ");
                forfeit(player);
                break;
            case DEAL_DEALER:
                int dealerVal = PackUtilities.getPackValue(dealer.getHand());
                dealer.printHand();
                //player.printHand();

                if (dealerVal > 21) {
                    dealer.setTheirTurn(false);
                } else if (dealerVal >= 17) {
                    stand(dealer);
                } else {
                    hit(dealer);
                }

                break;
            case EVALUATE:
                evaluateGame();
                break;
            case REVEAL_CARDS:
                PackUtilities.revealPack(dealer.getHand());
                PackUtilities.revealPack(player.getHand());
                break;
            case REBUILD_DECK:
                PackUtilities.mergePacksShuffle(deck, player.getHand(), dealer.getHand());
                gameCount++;

                break;

            case ERROR:
            default:
                throw new IllegalStateException("GameState=ERROR");
        }

        nextState();

    }

    private void readMove(CardsPlayer player) {
        String cmd = reader.nextLine();

        switch (cmd) {
            case "n":
                stand(player);
                break;
            case "d":
                dblDown(player);
                break;
            case "f":
                player.forfeit();
                stand(player);
                break;
            default:
                hit(player);
                break;
        }
    }

    private void evaluateGame() {
        GameOutcome outcome = PackUtilities.comparePacks(player.getHand(), dealer.getHand());

        if (dealerHasBlackjack && player.insured()) {
            // get money from insurance back
            player.getWager().empty();
        }
        
        switch (outcome) {
            case VICTORY:
                player.win();
                dealer.lose();
                break;
            case DEFEAT:
                player.lose();
                dealer.win();
                break;
            case DRAW:
                player.draw();
                dealer.draw();
                break;
            default: throw new IllegalStateException();
        }

        System.out.println();

    }

    private void dealCards() {
        player.drawFromDeck(deck);

        dealer.drawFromDeck(deck); // concealed
        dealer.getHand().getTop().conceal();

        player.drawFromDeck(deck);

//        PackOfCards pack = new PackOfCards();
//        pack.insertIntoPack(new CardGUI(Suit.HEARTS, Rank.ACE));
        dealer.drawFromDeck(deck); // non concealed
//        dealer.drawFromDeck(pack);

        dealer.printHand();
        player.printHand();

        if (dealer.getHand().getTop().getRank() == Rank.ACE) {
            dealerHasAce = true;
            if (PackUtilities.packContainsBlackjack(dealer.getHand()))
                dealerHasBlackjack = true;
        }
    }

    private void dblDown(CardsPlayer player) {
        player.doubleDown();
        hit(player);
        player.getHand().getTop().conceal();
        stand(player);
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

    private void forfeit(CardsPlayer player) {
        player.halveWager();
        player.lose();
        dealer.win();
    }

    public void hit() {
        hit(player);
    }

    public void dblDown() {
        dblDown(player);
    }

    public void stand() {
        stand(player);
    }

    public void forfeit() {
        forfeit(player);
    }

    private void bust(CardsPlayer player) {
        player.setTheirTurn(false);
        System.out.println(player.getName() + " has went over 21!");
        player.lose();
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public PackOfCards getPlayerDeck() {
        return player.getHand();
    }

    public PackOfCards getDealerDeck() {
        return dealer.getHand();
    }

    public String getPlayerCredit() {
        return "" + (long) (player.getCredit().get() - player.getWager().get());
    }
    public String getPlayerWager() { return "" + (long) (player.getWager().get());}

    public void playerBet(int amount) {
        player.bet(amount);
    }


}
