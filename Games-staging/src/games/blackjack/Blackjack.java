package games.blackjack;

import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Blackjack extends JPanel {
    private final ScreenManager screenManager;
    boolean running = false;

    public Blackjack(ScreenManager screenManager) {
        this.screenManager = screenManager;

        Font Header = Constants.HEADER_FONT;
        Font ButtonFont = Constants.BUTTON_FONT;
        Font TextFont = Constants.TEXT_FONT;

        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        JLabel title = StyleHelpers.createStyledLabel("Blackjack", Header, Constants.fg);

        JLabel dealerCount = StyleHelpers.createStyledLabel("Dealer: 0", TextFont, Constants.txt);
        JLabel playerCount = StyleHelpers.createStyledLabel("You: 0", TextFont, Constants.txt);
        JPanel countsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        countsPanel.setBackground(Color.decode(Constants.bg));
        countsPanel.add(dealerCount);
        countsPanel.add(playerCount);
        
        JButton hitButton = StyleHelpers.createStyledButton("Hit", ButtonFont, Constants.fg, Constants.txt);
        JButton standButton = StyleHelpers.createStyledButton("Stand", ButtonFont, Constants.fg, Constants.txt);
        hitButton.addActionListener((ActionEvent e) -> { /* Add hit logic here */ });
        standButton.addActionListener((ActionEvent e) -> { /* Add stand logic here */ });

        JPanel hitStandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        hitStandPanel.setBackground(Color.decode(Constants.bg));
        hitStandPanel.add(hitButton);
        hitStandPanel.add(standButton);

        JButton pauseButton = StyleHelpers.createStyledButton("Pause", ButtonFont, Constants.secondary, Constants.txt);
        pauseButton.addActionListener((ActionEvent e) -> pause());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.decode(Constants.bg));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(title, gbc);

        gbc.gridy = 1;
        contentPanel.add(countsPanel, gbc);

        gbc.gridy = 2;
        contentPanel.add(hitStandPanel, gbc);
        
        gbc.gridy = 3;
        contentPanel.add(pauseButton, gbc);

        add(contentPanel);
    }

    public void startGame() {
        running = true;
        // The game logic should be started here, but not in a while loop
        // to avoid blocking the UI thread.
        game();
    }

    private void game () {
        // Game logic would go here, managed by timers or events, not a while loop.
    }

    private void pause() {
        running = false;
        screenManager.showScreen("pause_blackjack");
    }
}