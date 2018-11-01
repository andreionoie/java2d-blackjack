public enum Rank {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    private String symbol;
    private int blackJackValue;

    Rank(String symbol) {
        this.symbol = symbol;
        if (symbol.matches("[JQK]"))
            this.blackJackValue = 10;
        else if (symbol.equals("A"))
            this.blackJackValue = 1;
        else
            this.blackJackValue = Integer.parseInt(symbol);
    }

    public int getValue() {
        return blackJackValue;
    }

    public String getSymbol() {
        return symbol;
    }
}
