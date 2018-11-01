public class CardsPlayer {
    private PackOfCards hand;
    private boolean theirTurn;
    private GameState gameState;
    private int nbOfWins;
    private final String name;

    public CardsPlayer(boolean theirTurn, String name) {
        this.name = name;
        hand = new PackOfCards();
        this.theirTurn = theirTurn;
        gameState = GameState.UNFINISHED;
        nbOfWins = 0;
    }

    public void drawFromDeck(PackOfCards deck, int count) {
        if (count > deck.size())
            throw new IllegalArgumentException("Can't extract more cards than there are already in the deck.");

        PackOfCards extracted = new PackOfCards();

        for (int i=0; i < count; i++)
            extracted.insertIntoPack(deck.extract());

        hand.insertIntoPack(extracted);
    }

    public void drawFromDeck(PackOfCards deck) {
        drawFromDeck(deck, 1);
    }

    public void setTheirTurn(boolean theirTurn) {
        this.theirTurn = theirTurn;
    }

    public void win() {
        gameState = GameState.VICTORY;
        System.out.println(name + " has won the round.");
        nbOfWins++;
    }

    public void lose() {
        gameState = GameState.DEFEAT;
        System.out.println(name + " has lost the round.");
    }

    public void draw() {
        gameState = GameState.DRAW;
        System.out.println("Round has ended in a draw.");
    }

    public void reset() {
        gameState = GameState.UNFINISHED;
    }

    public PackOfCards getHand() {
        return hand;
    }

    public boolean isTheirTurn() {
        return theirTurn;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getNbOfWins() {
        return nbOfWins;
    }

    public String getName() {
        return name;
    }

    public void printHand() {
        System.out.print(name + "s hand: ");
        PackUtilities.printPack(hand);
        System.out.println("Value is " + PackUtilities.getPackValue(hand));
    }
}
