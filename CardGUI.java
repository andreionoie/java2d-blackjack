public class CardGUI extends Card {
    public enum CardState {INVISIBLE, FACE_UP, FACE_DOWN};

    private boolean selected;
    private CardState cardState;

    public CardGUI(Suit suit, Rank rank) {
        super(suit, rank);
        cardState = CardState.FACE_UP;
        selected = false;
    }

    public void show() {
        cardState = CardState.FACE_UP;
    }

    public void conceal() {
        cardState = CardState.FACE_DOWN;
    }

    public void hide() {
        cardState = CardState.INVISIBLE;
    }

    public void toggleShow() {
        if (cardState == CardState.FACE_UP)
            conceal();
        else if (cardState == CardState.FACE_DOWN)
            show();
    }

    public void toggleSelect() {
        if (selected)
            selected = false;
        else
            selected = true;
    }

    public boolean isSelected() {
        return selected;
    }

    public CardState getCardState() {
        return cardState;
    }
}
