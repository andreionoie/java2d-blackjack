import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PackOfCards {
    private List<CardGUI> cards;
    private Random random;

    public PackOfCards() {
        cards = new ArrayList<CardGUI>();
        random = new Random();
    }

    public void insertIntoPack(CardGUI card) {
        cards.add(card);
    }

    public void insertIntoPack(PackOfCards pack) {
        for (CardGUI c : pack.getCards())
            insertIntoPack(c);
    }

    public void insertRandomCard() {
        Suit s = randomEnum(Suit.class);
        Rank r = randomEnum(Rank.class);
        CardGUI card = new CardGUI(s, r);
        insertIntoPack(card);
    }


    public CardGUI extract(int index) {
        if (index > cards.size())
            throw new IndexOutOfBoundsException();

        CardGUI temp = cards.get(index);
        cards.remove(index);
        return temp;
    }
    public int size() { return cards.size(); }
    public boolean isEmpty() { return size() == 0; }
    public List<CardGUI> getCards() { return cards; }
    public CardGUI getTop() { return cards.get(cards.size() - 1); }
    public CardGUI extract() { return extract(0); }

    public CardGUI extractRandom() { return extract(random.nextInt(cards.size())); }

    public void initStandardPack() {
        emptyPack();

        for (Suit s : Suit.values())
            for (Rank r : Rank.values())
                cards.add(new CardGUI(s, r));

    }

    public void shuffle() {
            Collections.shuffle(cards, random);
    }

    public void emptyPack() { cards.clear(); }

    public void printCards() {
        for (Card c : cards)
            System.out.println(c);
    }

    public <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
