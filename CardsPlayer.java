public class CardsPlayer {
    private PackOfCards hand;
    private boolean theirTurn;
    private GameOutcome gameOutcome;
    private int nbOfWins;
    private final String name;

    public CardsPlayer(boolean theirTurn, String name) {
        this.name = name;
        hand = new PackOfCards();
        this.theirTurn = theirTurn;
        gameOutcome = GameOutcome.UNFINISHED;
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
        gameOutcome = GameOutcome.VICTORY;
        System.out.println(name + " has won the round.");
        nbOfWins++;
    }

    public void lose() {
        gameOutcome = GameOutcome.DEFEAT;
        System.out.println(name + " has lost the round.");
    }

    public void draw() {
        gameOutcome = GameOutcome.DRAW;
        System.out.println("Round has ended in a draw.");
    }

    public void reset() {
        gameOutcome = GameOutcome.UNFINISHED;
    }

    public PackOfCards getHand() {
        return hand;
    }

    public boolean isTheirTurn() {
        return theirTurn;
    }

    public GameOutcome getGameOutcome() {
        return gameOutcome;
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
