public enum Suit {
    SPADES("Spades", "♠"),
    CLUBS("Clubs", "♣"),
    HEARTS("Hearts", "♥"),
    DIAMONDS("Diamonds", "♦");

    private String name;
    private String symbol;

    Suit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
