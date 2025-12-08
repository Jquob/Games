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

        JLabel title = StyleHelpers.createStyledLabel("GAMES", Header, Constants.fg);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        JButton blackjack = StyleHelpers.createStyledButton("Blackjack", ButtonFont, Constants.fg, Constants.txt);
        JButton slots = StyleHelpers.createStyledButton("Slots", ButtonFont, Constants.secondary, Constants.txt);

        // Example: go back to home when clicking slots (or wire up game screens later)
        slots.addActionListener((ActionEvent e) -> screenManager.showScreen("home"));

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        contentPanel.add(blackjack);
        contentPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Spacing between buttons
        contentPanel.add(slots);

        add(contentPanel);
    }
}