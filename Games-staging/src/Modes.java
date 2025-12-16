import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Modes extends JPanel {
    public Modes(ScreenManager screenManager) {
        Font Header = Constants.HEADER_FONT;
        Font ButtonFont = Constants.BUTTON_FONT;

        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBackground(Color.decode(Constants.bg));

        JLabel title = StyleHelpers.createStyledLabel("MODES", Header, Constants.fg);

        JButton blackjack = StyleHelpers.createStyledButton("Blackjack", ButtonFont, Constants.fg, Constants.txt);
        JButton slots = StyleHelpers.createStyledButton("Slots", ButtonFont, Constants.secondary, Constants.txt);
        JButton home = StyleHelpers.createStyledButton("Home", ButtonFont, Constants.secondary, Constants.txt);

        blackjack.addActionListener((ActionEvent e) -> screenManager.showScreen("blackjack"));

        slots.addActionListener((ActionEvent e) -> screenManager.showScreen("slots"));

        home.addActionListener((ActionEvent e) -> screenManager.showScreen("home"));

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        contentPanel.add(blackjack);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Spacing between buttons
        contentPanel.add(slots);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(contentPanel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(home, gbc);
    }
}