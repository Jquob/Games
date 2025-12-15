package games.slots;

import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Slots extends JPanel {

    public Slots(ScreenManager screenManager) {
        Font Header = Constants.HEADER_FONT;
        Font ButtonFont = Constants.BUTTON_FONT;

        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        JLabel title = StyleHelpers.createStyledLabel("Slots", Header, Constants.fg);

        JButton spinButton = StyleHelpers.createStyledButton("Spin", ButtonFont, Constants.fg, Constants.txt);
        JButton pauseButton = StyleHelpers.createStyledButton("Pause", ButtonFont, Constants.secondary, Constants.txt);
        JButton backButton = StyleHelpers.createStyledButton("Back to Modes", ButtonFont, Constants.secondary, Constants.txt);
        
        backButton.addActionListener((ActionEvent e) -> screenManager.showScreen("modes"));
        pauseButton.addActionListener((ActionEvent e) -> screenManager.showScreen("pause_slots"));
        spinButton.addActionListener((ActionEvent e) -> startGame());


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode(Constants.bg));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.decode(Constants.bg));
        buttonPanel.add(spinButton);
        buttonPanel.add(pauseButton);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);
        
        gbc.gridy = 2;
        panel.add(backButton, gbc);

        add(panel);
    }

    public void startGame() {
        // In a real slots game, this would spin the reels.
        // For now, it does nothing.
        System.out.println("Spinning the reels...");
    }
}
