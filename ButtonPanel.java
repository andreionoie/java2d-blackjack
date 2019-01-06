import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private JLabel credit;
    private JButton doubleDown;
    private JButton hit;
    private JButton stand;
    private JButton surrender;
    private JButton newGame;

    public ButtonPanel(BlackjackLogic blackjackLogic) {
        super();
        this.setLayout(new FlowLayout());

        createComponents();
        createButtonListeners(blackjackLogic);
    }

    private void createComponents() {
        credit = new JLabel();
        doubleDown = new JButton("Double Down");
        hit = new JButton("Hit");
        stand = new JButton("Stand");
        surrender = new JButton("Surrender");
        newGame = new JButton("New GUIBlackjack");

        add(credit);
        add(doubleDown);
        add(hit);
        add(stand);
        add(surrender);
        add(newGame);
    }

    private void createButtonListeners(BlackjackLogic blackjackLogic) {
        doubleDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.DEAL_PLAYER)
                    blackjackLogic.dblDown();
            }
        });

        hit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.DEAL_PLAYER)
                    blackjackLogic.hit();
            }
        });

        stand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.DEAL_PLAYER)
                    blackjackLogic.stand();
            }
        });

        surrender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.DEAL_PLAYER)
                    blackjackLogic.forfeit();
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.REVEAL_CARDS) {
                    blackjackLogic.setGameState(BlackjackLogic.GameState.REBUILD_DECK);
                }
            }
        });
    }

    public void updateCreditText(String text) {
        credit.setText("$" + text);
    }
}
