import java.awt.*;

import javax.swing.JFrame;

// credit: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src

public class Display {

    private JFrame frame;
    private Canvas canvas;
    private ButtonPanel buttonPanel;
    private BettingPanel bettingPanel;
    private BlackjackLogic blackjackLogic;
    private String title;
    private int width, height;

    public Display(String title, int width, int height, BlackjackLogic blackjackLogic) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.blackjackLogic = blackjackLogic;
        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setBackground(new Color(0, 132, 0));
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        frame.add(canvas);

        buttonPanel = new ButtonPanel(blackjackLogic);
        bettingPanel = new BettingPanel(blackjackLogic);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(bettingPanel, BorderLayout.EAST);
        frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public BettingPanel getBettingPanel() {
        return bettingPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
