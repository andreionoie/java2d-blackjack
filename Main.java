public class Main {

    public static void main(String[] args) throws Exception {
//        Scanner reader = new Scanner(System.in);
//        BlackjackLogic GUIBlackjack = new BlackjackLogic(1, reader);
//
//        GUIBlackjack.playGame();

        GUIBlackjack GUIBlackjack = new GUIBlackjack("Blackjack", 500, 300);
        GUIBlackjack.start();
    }

}
