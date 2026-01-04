package games.blackjack;

import games.blackjack.models.*;
import lib.*;
import javax.swing.*;
import java.awt.*;

public class Blackjack extends JPanel {
    private enum GameState { BETTING, PLAYING, ROUND_OVER }

    private final ScreenManager screenManager;
    private Deck deck;
    private Hand playerHand, dealerHand;
    private int playerMoney = 1000, currentBet = 0;

    private final JLabel dealerCount, playerCount, gameResult, moneyLabel;
    private final JPanel dealerCardsPanel, playerCardsPanel, actionPanel;
    private final JButton hitBtn, standBtn, betBtn, playAgainBtn, pauseBtn;
    private final JTextField betField;

    public Blackjack(ScreenManager screenManager) {
        this.screenManager = screenManager;
        setBackground(Color.decode(Constants.bg));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // UI Components
        moneyLabel = StyleHelpers.createStyledLabel("Money: $" + playerMoney, Constants.TEXT_FONT, "#FFFFFF");
        dealerCount = StyleHelpers.createStyledLabel("Dealer: ???", Constants.TEXT_FONT, "#FFFFFF");
        playerCount = StyleHelpers.createStyledLabel("You: 0", Constants.TEXT_FONT, "#FFFFFF");
        gameResult = StyleHelpers.createStyledLabel("Place your bet", Constants.TEXT_FONT, "#FFFFFF");

        dealerCardsPanel = createCardPanel();
        playerCardsPanel = createCardPanel();

        // Buttons & Input
        hitBtn = createBtn("Hit", e -> hit());
        standBtn = createBtn("Stand", e -> stand());
        pauseBtn = createBtn("Pause", e -> pause());
        betField = StyleHelpers.createStyledTextField("10", 5, Constants.TEXT_FONT, Constants.bg, Constants.fg);
        betBtn = createBtn("Place Bet", e -> placeBet());

        playAgainBtn = createBtn("Play Again", e -> {
            if (playerMoney <= 0) {
                fullReset(); // Reset the game state for a fresh start
                screenManager.showScreen("home"); // Go back to the main menu
            } else {
                resetRound();
            }
        });

        // Control Switching Panel
        actionPanel = new JPanel(new CardLayout());
        actionPanel.setOpaque(false);
        actionPanel.add(createFlowPanel(new JLabel("Bet:"), betField, betBtn), GameState.BETTING.name());
        actionPanel.add(createFlowPanel(hitBtn, standBtn, pauseBtn), GameState.PLAYING.name());
        actionPanel.add(playAgainBtn, GameState.ROUND_OVER.name());

        setupLayout();
        resetRound();
    }

    private void placeBet() {
        try {
            int bet = Integer.parseInt(betField.getText());
            if (bet > 0 && bet <= playerMoney) {
                currentBet = bet;
                playerMoney -= currentBet;
                startGame();
            } else {
                gameResult.setText("Invalid bet amount.");
            }
        } catch (NumberFormatException e) {
            gameResult.setText("Enter a valid number.");
        }
    }

    public void startGame() {
        deck = new Deck();
        deck.shuffle();
        playerHand = new Hand();
        dealerHand = new Hand();

        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());

        updateState(GameState.PLAYING);
        refreshUI(false); // Show player's hand, hide dealer's second card

        // Check for Blackjack conditions immediately after the deal
        boolean playerHasBlackjack = playerHand.getValue() == 21;
        boolean dealerHasBlackjack = dealerHand.getValue() == 21;

        if (playerHasBlackjack && dealerHasBlackjack) {
            handleEnd("Push! Both have Blackjack.", 1.0);
        } else if (playerHasBlackjack) {
            handleEnd("Blackjack! You win!", 2.5);
        } else if (dealerHasBlackjack) {
            // Reveal dealer's hand to show the Blackjack
            refreshUI(true);
            handleEnd("Dealer has Blackjack. You lose.", 0);
        }
        // If no one has Blackjack, the game continues in the PLAYING state
    }

    private void hit() {
        playerHand.addCard(deck.draw());
        if (playerHand.getValue() > 21) handleEnd("Bust! You lose.", 0);
        else refreshUI(false);
    }

    private void stand() {
        // Player's turn is over; reveal dealer's second card and then play out their turn.
        hitBtn.setEnabled(false);
        standBtn.setEnabled(false);

        // This timer animates the dealer's turn.
        Timer timer = new Timer(700, null);
        timer.addActionListener(e -> {
            // Dealer must hit until their hand value is 17 or more.
            if (dealerHand.getValue() < 17) {
                dealerHand.addCard(deck.draw());
                refreshUI(true); // Always show dealer's cards during their turn
            } else {
                // Turn is over. Stop the timer and determine the winner.
                ((Timer)e.getSource()).stop();
                determineWinner();
            }
        });

        // Start the dealer's turn after a brief pause.
        new Timer(500, e -> {
            ((Timer)e.getSource()).stop();
            refreshUI(true); // Reveal the hidden card first
            if (dealerHand.getValue() < 17) {
                timer.start();
            } else {
                determineWinner();
            }
        }).start();
    }

    private void determineWinner() {
        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        if (dealerValue > 21 || playerValue > dealerValue) {
            handleEnd("You win!", 2.0);
        } else if (playerValue < dealerValue) {
            handleEnd("Dealer wins!", 0);
        } else {
            handleEnd("Push!", 1.0);
        }
    }

    private void handleEnd(String msg, double multiplier) {
        gameResult.setText(msg);
        playerMoney += (int)(currentBet * multiplier);
        moneyLabel.setText("Money: $" + playerMoney); // Update money immediately

        updateState(GameState.ROUND_OVER);
        hitBtn.setEnabled(true);
        standBtn.setEnabled(true);

        if (playerMoney <= 0) {
            gameResult.setText("Bankrupt!");
            playAgainBtn.setText("Restart via Menu");
        } else {
            playAgainBtn.setText("Play Again");
        }
    }

    private void refreshUI(boolean revealDealerHand) {
        moneyLabel.setText("Money: $" + playerMoney);
        playerCount.setText("You: " + playerHand.getValue());

        if (revealDealerHand) {
            dealerCount.setText("Dealer: " + dealerHand.getValue());
        } else if (dealerHand != null && !dealerHand.getCards().isEmpty()) {
            // Only show the value of the first card, not the total.
            Card visibleCard = dealerHand.getCards().get(0);
            dealerCount.setText("Dealer: " + visibleCard.getValue() + " + ?");
        } else {
            dealerCount.setText("Dealer: ???");
        }

        updateCards(playerCardsPanel, playerHand, true);
        updateCards(dealerCardsPanel, dealerHand, revealDealerHand);
    }

    private void updateState(GameState state) {
        ((CardLayout) actionPanel.getLayout()).show(actionPanel, state.name());
    }

    private void resetRound() {
        // Hands are cleared here, but UI is updated after betting
        playerHand = new Hand();
        dealerHand = new Hand();
        gameResult.setText("Place your bet");
        updateState(GameState.BETTING);
        // Clear visual cards only, without trying to read from empty hands
        updateCards(playerCardsPanel, new Hand(), true);
        updateCards(dealerCardsPanel, new Hand(), false);
        dealerCount.setText("Dealer: ???");
        playerCount.setText("You: 0");
    }

    private void pause() {
        screenManager.showScreen("pause_blackjack");
    }

    /**
     * Resets the entire game state, including money,
     * and forces the user back to the betting screen.
     */
    public void fullReset() {
        this.playerMoney = 1000;
        this.currentBet = 0;
        playAgainBtn.setText("Play Again");
        resetRound(); // This clears hands and sets state to BETTING
    }

    // --- Helpers ---
    private JButton createBtn(String txt, java.awt.event.ActionListener l) {
        JButton b = StyleHelpers.createStyledButton(txt, Constants.BUTTON_FONT, Constants.fg, Constants.txt);
        b.addActionListener(l);
        return b;
    }

    private JPanel createCardPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        p.setOpaque(false);
        return p;
    }

    private JPanel createFlowPanel(Component... comps) {
        JPanel p = new JPanel(new FlowLayout());
        p.setOpaque(false);
        for (Component c : comps) p.add(c);
        return p;
    }

    private void updateCards(JPanel p, Hand h, boolean show) {
        p.removeAll();
        if (h != null) {
            java.util.List<Card> cards = h.getCards();
            for (int i = 0; i < cards.size(); i++)
                p.add(new CardPanel(cards.get(i), i == 1 && !show));
        }
        p.revalidate(); p.repaint();
    }

    private void setupLayout() {
        JPanel center = new JPanel(new GridLayout(4, 1));
        center.setOpaque(false);
        center.add(dealerCount); center.add(dealerCardsPanel);
        center.add(playerCardsPanel); center.add(playerCount);

        add(moneyLabel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.add(gameResult, BorderLayout.NORTH);
        south.add(actionPanel, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }

    private static class CardPanel extends JPanel {
        private final Card card;
        private final boolean isFaceDown;

        public CardPanel(Card card, boolean isFaceDown) {
            this.card = card; this.isFaceDown = isFaceDown;
            setPreferredSize(new Dimension(80, 110));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(isFaceDown ? new Color(25, 50, 100) : Color.WHITE);
            g2.fillRoundRect(0, 0, 79, 109, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, 79, 109, 10, 10);

            if (!isFaceDown && card != null) {
                String suit = card.getSuit();
                g2.setColor(suit.equals("Hearts") || suit.equals("Diamonds") ? Color.RED : Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(card.getRank(), 8, 20);
                g2.drawString(getSuitSymbol(suit), 8, 40);
            } else if (isFaceDown) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.drawString("?", 33, 60);
            }
            g2.dispose();
        }

        private String getSuitSymbol(String suit) {
            return switch(suit) {
                case "Hearts" -> "♥"; case "Diamonds" -> "♦";
                case "Clubs" -> "♣"; case "Spades" -> "♠";
                default -> "";
            };
        }
    }
}