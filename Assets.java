import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

// credit: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src

public class Assets {
    private static final int width = 64, height = 96;
    private static Map<Card, BufferedImage> cardImgs;
    public static BufferedImage cardBackside, cardSelected;

    public static void load(String path) {
        // load spritesheet
        Spritesheet ss = new Spritesheet(path, width, height);

        cardImgs = new HashMap<Card, BufferedImage>();

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



}
