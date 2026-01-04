package games.slots;

import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Slots extends JPanel {
    private final ScreenManager screenManager;
    private int playerMoney = 1000;
    private int currentBet = 10;

    private final JLabel moneyLabel, messageLabel;
    private final JTextField betField;
    private final JPanel reelsPanel;
    private final JLabel[] reelLabels;

    public Slots(ScreenManager screenManager) {
        this.screenManager = screenManager;
        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        // --- UI Components ---
        JLabel title = StyleHelpers.createStyledLabel("Slots", Constants.HEADER_FONT, Constants.fg);
        moneyLabel = StyleHelpers.createStyledLabel("Money: $" + playerMoney, Constants.TEXT_FONT, "#FFFFFF");
        messageLabel = StyleHelpers.createStyledLabel("Place your bet!", Constants.TEXT_FONT, "#FFFFFF");
        betField = StyleHelpers.createStyledTextField("10", 5, Constants.TEXT_FONT, Constants.bg, Constants.fg);

        // --- Reels ---
        reelsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        reelsPanel.setOpaque(false);
        reelLabels = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            reelLabels[i] = createReelLabel();
            reelsPanel.add(reelLabels[i]);
        }

        // --- Buttons ---
        JButton spinButton = StyleHelpers.createStyledButton("Spin", Constants.BUTTON_FONT, Constants.fg, Constants.txt);
        JButton pauseButton = StyleHelpers.createStyledButton("Pause", Constants.BUTTON_FONT, Constants.secondary, Constants.txt);
        JButton backButton = StyleHelpers.createStyledButton("Back to Modes", Constants.BUTTON_FONT, Constants.secondary, Constants.txt);
        
        backButton.addActionListener((ActionEvent e) -> screenManager.showScreen("modes"));
        pauseButton.addActionListener((ActionEvent e) -> screenManager.showScreen("pause_slots"));
        spinButton.addActionListener((ActionEvent e) -> startGame());

        // --- Layout ---
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 2;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(moneyLabel, gbc);
        
        gbc.gridy = 2;
        panel.add(reelsPanel, gbc);
        
        gbc.gridy = 3;
        panel.add(messageLabel, gbc);

        // Betting controls
        JPanel betPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        betPanel.setOpaque(false);
        betPanel.add(StyleHelpers.createStyledLabel("Bet:", Constants.TEXT_FONT, Constants.fg));
        betPanel.add(betField);
        
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(betPanel, gbc);

        // Main action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(spinButton);
        buttonPanel.add(pauseButton);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        gbc.gridy = 6;
        panel.add(backButton, gbc);

        add(panel);
    }

    private final String[] SYMBOLS = {"C", "L", "O", "S", "B", "BAR", "7"};
    private boolean isSpinning = false;
    private final java.util.Random random = new java.util.Random();

    public void startGame() {
        if (isSpinning) return;

        try {
            int bet = Integer.parseInt(betField.getText());
            if (bet > 0 && bet <= playerMoney) {
                currentBet = bet;
                playerMoney -= currentBet;
                moneyLabel.setText("Money: $" + playerMoney);
                messageLabel.setText("Spinning...");
                isSpinning = true;

                // Animation timer
                javax.swing.Timer animationTimer = new javax.swing.Timer(50, null);
                animationTimer.addActionListener(e -> {
                    for (JLabel label : reelLabels) {
                        label.setText(SYMBOLS[random.nextInt(SYMBOLS.length)]);
                    }
                });
                animationTimer.start();

                // Stop timer
                javax.swing.Timer stopTimer = new javax.swing.Timer(1000, e -> {
                    animationTimer.stop();
                    setFinalReels();
                    checkWin();
                    isSpinning = false;
                });
                stopTimer.setRepeats(false);
                stopTimer.start();

            } else {
                messageLabel.setText("Invalid bet.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid number.");
        }
    }

    private void checkWin() {
        String s1 = reelLabels[0].getText();
        String s2 = reelLabels[1].getText();
        String s3 = reelLabels[2].getText();

        int payout = 0;
        if (s1.equals(s2) && s2.equals(s3)) {
            // All three match
            payout = getPayout(s1, true);
            messageLabel.setText("Big Win!");
        } else if (s1.equals(s2) || s2.equals(s3) || s1.equals(s3)) {
            // Two match
            String symbol = s1.equals(s2) ? s1 : s3;
            payout = getPayout(symbol, false);
            messageLabel.setText("Small Win!");
        } else {
            messageLabel.setText("You lose.");
        }

        if (payout > 0) {
            int winnings = currentBet * payout;
            playerMoney += winnings;
            moneyLabel.setText("Money: $" + playerMoney);
            messageLabel.setText(messageLabel.getText() + " You won $" + winnings);
        }
    }

    private int getPayout(String symbol, boolean isThreeOfAKind) {
        int multiplier = 1;
        switch (symbol) {
            case "C": multiplier = isThreeOfAKind ? 10 : 2; break; // Cherry
            case "L": multiplier = isThreeOfAKind ? 15 : 3; break; // Lemon
            case "O": multiplier = isThreeOfAKind ? 20 : 4; break; // Orange
            case "S": multiplier = isThreeOfAKind ? 25 : 5; break; // Strawberry
            case "B": multiplier = isThreeOfAKind ? 50 : 10; break; // Bell
            case "BAR": multiplier = isThreeOfAKind ? 100 : 20; break; // BAR
            case "7": multiplier = isThreeOfAKind ? 500 : 50; break; // 7
        }
        return multiplier;
    }

    private void setFinalReels() {
        for (int i = 0; i < 3; i++) {
            reelLabels[i].setText(SYMBOLS[random.nextInt(SYMBOLS.length)]);
        }
    }

    private JLabel createReelLabel() {
        JLabel label = new JLabel("?");
        label.setFont(new Font("Arial", Font.BOLD, 48));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(100, 100));
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return label;
    }
}
