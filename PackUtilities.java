public class PackUtilities {
    public static int getPackValue(PackOfCards p) {
        int val = 0;
        PackOfCards aces = new PackOfCards();

        for (CardGUI c : p.getCards()) {
            if (c.getRank() == Rank.ACE)
                aces.insertIntoPack(c);

            val += c.getValue();
        }

        for (CardGUI c : aces.getCards())
            if (val+10 <= 21)
                val += 10;


        return val;
    }

    public static boolean packContainsBlackjack(PackOfCards p) {
        if (p.size() != 2)
            return false;

        boolean hasAce = false;
        boolean hasTenCard = false;

        for (CardGUI c : p.getCards()) {
            if (c.getRank() == Rank.ACE)
                hasAce = true;
            else if (c.getValue() == 10)
                hasTenCard = true;
        }

        return hasAce && hasTenCard;
    }

    public static GameOutcome comparePacks(PackOfCards defendant, PackOfCards opponent) {
        boolean defBlackjack, opBlackjack;
        int defValue, opValue;

        defBlackjack = packContainsBlackjack(defendant);
        opBlackjack = packContainsBlackjack(opponent);
        defValue = getPackValue(defendant);
        opValue = getPackValue(opponent);

        // check for valid packs
        if (opValue > 21 && defValue > 21)
            return GameOutcome.DRAW;

        if (opValue > 21)
            return GameOutcome.VICTORY;

        if (defValue > 21)
            return GameOutcome.DEFEAT;

        // check for blackjack (ace + 10-value card)
        if (defBlackjack || opBlackjack)
            if (defBlackjack && opBlackjack) {
                return GameOutcome.DRAW;
            } else if (defBlackjack){
                return GameOutcome.VICTORY;
            } else {
                return GameOutcome.DEFEAT;
            }

        // check when there is no special case
        if (defValue == opValue)
            return GameOutcome.DRAW;

        if (defValue > opValue)
            return GameOutcome.VICTORY;

        return GameOutcome.DEFEAT;
    }

    public static void printPack(PackOfCards p) {
        for (CardGUI c : p.getCards())
            System.out.print(c + " ");

        System.out.println();
    }
    public static void mergePacksShuffle(PackOfCards dest, PackOfCards ... src) {
        // dest += src, src = {}, dest is shuffled
        for (PackOfCards p : src) {
            dest.insertIntoPack(p);
            p.emptyPack();
        }

        dest.shuffle();
    }

    public static void revealPack(PackOfCards p) {
        for (CardGUI c : p.getCards())
            c.show();
    }
}
