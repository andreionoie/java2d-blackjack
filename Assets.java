import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

// credit: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src

public class Assets {
    // pixel deck
    private static final int width = 64, height = 96;
    // andrew tidey deck
    //private static final int width = 103, height = 138;

    private static Map<Card, BufferedImage> cardImgs;
    public static BufferedImage cardBackside, cardSelected;

    public static void load(String path) {
        // load spritesheet
        Spritesheet ss = new Spritesheet(path, width, height);

        cardImgs = new HashMap<Card, BufferedImage>();

        loadPixelDeck(ss);
        //loadAndrewDeck(ss);
    }

    public static BufferedImage getCardImg(CardGUI cardGUI) {
        BufferedImage cardImage = null;
        Card card = new Card(cardGUI.getSuit(), cardGUI.getRank());

        switch (cardGUI.getCardState()) {
            case FACE_UP:   cardImage = cardImgs.get(card);
                            break;

            case FACE_DOWN: cardImage = cardBackside;
                            break;

            default: cardImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                            break;
        }

        if (cardGUI.isSelected())
            ImgUtilities.addImg(cardImage,cardSelected, 1f);

        return cardImage;
    }

    public static void drawPack(int x, int y, PackOfCards deck, Graphics g) {
        int tmpX = 0, tmpY = 0;

        for (CardGUI c : deck.getCards()) {
            g.drawImage(Assets.getCardImg(c),
                    x + (width+10) *tmpX, y + (height+10) * tmpY, null);
            tmpX++;

            if (tmpX > 12) {
                tmpX = 0;
                tmpY++;
            }
        }
    }

    private static void loadPixelDeck(Spritesheet ss) {
        Suit[] ssSuits = {Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS};
        int xVal=0, yVal=0;

        for (Suit s : ssSuits) {
            yVal = 0;

            for (Rank r : Rank.values()) {
                Card c = new Card(s, r);
                BufferedImage img = ss.getTileAt(xVal, yVal);
                cardImgs.put(c, img);
                yVal++;
            }

            xVal++;
        }

        cardBackside = ss.getTileAt(0, 13);
        cardSelected = ss.getTileAt(3, 14);
    }

    private static void loadAndrewDeck(Spritesheet ss) {
        Suit[] ssSuits = {Suit.SPADES,  Suit.DIAMONDS, Suit.HEARTS, Suit.CLUBS};
        int xVal=0;

        for (Suit s : ssSuits) {
            for (Rank r : Rank.values()) {
                Card c = new Card(s, r);
                BufferedImage img = ss.getTileAt(xVal, 0);
                cardImgs.put(c, img);
            }

            xVal++;
        }

        cardBackside = ss.getTileAt(54, 0);
        cardSelected = ss.getTileAt(55, 0);
    }

}
