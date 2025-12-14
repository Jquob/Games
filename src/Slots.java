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

        JButton backButton = StyleHelpers.createStyledButton("Back to Modes", ButtonFont, Constants.secondary, Constants.txt);
        backButton.addActionListener((ActionEvent e) -> screenManager.showScreen("modes"));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode(Constants.bg));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);
        
        gbc.gridy = 1;
        panel.add(backButton, gbc);

        add(panel);
    }
}
