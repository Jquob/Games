import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Home extends JPanel {
    public Home(ScreenManager screenManager) {
        // Use shared fonts from Constants
        Font Header = Constants.HEADER_FONT;
        Font ButtonFont = Constants.BUTTON_FONT;

        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode(Constants.bg));

        JLabel title = StyleHelpers.createStyledLabel("CASINO", Header, Constants.fg);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); // extra spacing

        JButton start = StyleHelpers.createStyledButton("START", ButtonFont, Constants.fg, Constants.txt);

        // Navigate to Modes screen when start is clicked
        start.addActionListener((ActionEvent e) -> {
            screenManager.showScreen("modes");
            // reference the event to avoid "parameter never used" warnings
            Object __unused = e.getSource();
        });

        contentPanel.add(title);
        contentPanel.add(start);

        // Add some space between the buttons
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton howToPlay = StyleHelpers.createStyledButton("HOW TO PLAY", ButtonFont, Constants.fg, Constants.txt);
        howToPlay.addActionListener((ActionEvent e) -> {
            screenManager.showScreen("how_to_play");
            Object __unused = e.getSource();
        });
        contentPanel.add(howToPlay);

        add(contentPanel);
    }
}
