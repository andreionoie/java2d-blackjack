public class CardsPlayer {
    private PackOfCards hand;
    private boolean theirTurn;
    private boolean tookInsurance;
    private boolean forfeited;
    private GameOutcome gameOutcome;
    private int nbOfWins;
    private final String name;
    private Credit credit;
    private Credit wager;

    public CardsPlayer(boolean theirTurn, String name, double money) {
        this.name = name;
        this.hand = new PackOfCards();
        this.theirTurn = theirTurn;
        this.gameOutcome = GameOutcome.UNFINISHED;
        this.nbOfWins = 0;
        this.credit = new Credit(money);
        this.wager = new Credit();
        this.tookInsurance = false;
        this.forfeited = false;
    }

    public CardsPlayer(boolean theirTurn, String name) {
        this(theirTurn, name, 0);
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
        System.out.println(name + " has won the round, and " + wager.get() + " credit.");
        credit.deposit(wager.empty());
        nbOfWins++;
    }

    public void lose() {
        gameOutcome = GameOutcome.DEFEAT;
        System.out.println(name + " has lost the round, and " + wager.get() + " credit.");
        credit.withdraw(wager.empty());
    }

    public void draw() {
        gameOutcome = GameOutcome.DRAW;
        System.out.println("Round has ended in a draw.");
    }

    public void reset() {
        this.tookInsurance = false;
        this.forfeited = false;
        wager.empty();
        gameOutcome = GameOutcome.UNFINISHED;
    }

    public void bet(double amount) { wager.deposit(amount); }

    public void bet(Credit credit) { wager.deposit(credit); }

    public void doubleDown() { wager.deposit(wager.get()); }

    public double halveWager() { return wager.withdraw(wager.get()/2); }

    public double getInsurance() {
        tookInsurance = true;
        return halveWager();
    }
    public void forfeit() { forfeited = true; }

    public void blackjackPay() { wager.deposit(wager.get()/2); }

    public PackOfCards getHand() { return hand; }

    public boolean isTheirTurn() { return theirTurn; }

    public GameOutcome getGameOutcome() { return gameOutcome; }

    public int getNbOfWins() { return nbOfWins; }

    public String getName() { return name; }

    public Credit getCredit() { return credit; }

    public Credit getWager() { return wager; }

    public boolean insured() { return tookInsurance; }

    public boolean hasForfeited() { return forfeited; }

    public void printHand() {
        System.out.print(name + "s hand: ");
        PackUtilities.printPack(hand);
        System.out.println("Value is " + PackUtilities.getPackValue(hand));
    }

}
