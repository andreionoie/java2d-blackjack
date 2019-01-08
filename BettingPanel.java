import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BettingPanel extends JPanel {
    private JLabel wagerAmount;
    private List<JButton> chipButtons;
    private static final String[] CHIPS = {"1", "5", "10", "20", "25", "50", "100", "500", "1000"};
    private BlackjackLogic blackjackLogic;
    private JButton allIn, clearWager, finishBet;
    private int temporaryBet;

    public BettingPanel(BlackjackLogic blackjackLogic) {
        super();
        this.setLayout(new GridLayout(16, 1));
        this.blackjackLogic = blackjackLogic;
        chipButtons = new ArrayList<JButton>();
        temporaryBet = 0;

        createComponents();
        createButtonListeners();

    }

    private void createComponents() {
        wagerAmount = new JLabel();
        allIn = new JButton("All In");
        clearWager = new JButton("Clear");
        finishBet = new JButton("Bet");

        for (String chip : CHIPS) {
            chipButtons.add(new JButton("$" + chip));
        }



        for (JButton but : chipButtons)
            add(but);

        add(new JLabel(" ")); // separator
        add(allIn);
        add(clearWager);

        add(new JLabel(""));
        add(new JLabel("Bet total: "));
        add(wagerAmount);
        add(finishBet);
    }

    public void updateWagerText(String text) {
        wagerAmount.setText("$" + temporaryBet);
        wagerAmount.setHorizontalAlignment(JLabel.CENTER);
    }


    private void createButtonListeners() {
        for (JButton but : chipButtons) {
            but.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (blackjackLogic.getGameState() == BlackjackLogic.GameState.PLACE_BETS) {
                        temporaryBet += Integer.parseInt(but.getText().substring(1));
                    }
                }
            });
        }

        allIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.PLACE_BETS) {
                    temporaryBet = Integer.parseInt(blackjackLogic.getPlayerCredit());
                }
            }
        });

        clearWager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.PLACE_BETS) {
                    temporaryBet = 0;
                }
            }
        });

        finishBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blackjackLogic.getGameState() == BlackjackLogic.GameState.PLACE_BETS) {
                    blackjackLogic.playerBet(temporaryBet);
                    blackjackLogic.setGameState(BlackjackLogic.GameState.DEAL_CARDS);
                    disableAllButtons();
                }
            }
        });
    }

    public void disableAllButtons() {
        for (JButton but : chipButtons)
            but.setEnabled(false);
        allIn.setEnabled(false);
        clearWager.setEnabled(false);
        finishBet.setEnabled(false);
    }

    public void enableAllButtons() {
        for (JButton but : chipButtons)
            but.setEnabled(true);
        allIn.setEnabled(true);
        clearWager.setEnabled(true);
        finishBet.setEnabled(true);
    }

    public void updateChipButtonsActive() {
         for (JButton but : chipButtons) {
             if ((Integer.parseInt(blackjackLogic.getPlayerCredit()) - temporaryBet) >= Integer.parseInt(but.getText().substring(1)))
                 but.setEnabled(true);
             else
                 but.setEnabled(false);
         }
    }
}
