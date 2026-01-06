import lib.Constants;
import lib.ScreenManager;
import lib.StyleHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HowToPlay extends JPanel {
    public HowToPlay(ScreenManager screenManager) {
        Font Header = Constants.HEADER_FONT;
        Font ButtonFont = Constants.BUTTON_FONT;

        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode(Constants.bg));

        JLabel title = StyleHelpers.createStyledLabel("How to Play", Header, Constants.fg);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

        JButton back = StyleHelpers.createStyledButton("BACK", ButtonFont, Constants.fg, Constants.txt);

        back.addActionListener((ActionEvent e) -> {
            screenManager.showScreen("home");
            Object __unused = e.getSource();
        });

        contentPanel.add(title);

        JTextArea blackjackInstructions = new JTextArea(
                "Blackjack:\n" +
                        "Objective: Get a hand value as close to 21 as possible without going over.\n" +
                        "- You start with $1000. Place a bet to start the round.\n" +
                        "- You and the dealer get two cards. One dealer card is hidden.\n" +
                        "- 'Hit' to get another card. 'Stand' to keep your current hand.\n" +
                        "- If your hand exceeds 21, you 'bust' and lose the bet.\n" +
                        "- The dealer must hit until their hand is 17 or more.\n" +
                        "- If your hand is higher than the dealer's without busting, you win!\n"
        );
        blackjackInstructions.setFont(Constants.TEXT_FONT);
        blackjackInstructions.setEditable(false);
        blackjackInstructions.setOpaque(false);
        blackjackInstructions.setForeground(Color.decode(Constants.fg));
        contentPanel.add(blackjackInstructions);

        JTextArea slotsInstructions = new JTextArea(
                "\nSlots:\n" +
                        "Objective: Get matching symbols on the three reels.\n" +
                        "- You start with $1000. Place a bet and spin the reels.\n" +
                        "- Three matching symbols is a 'Big Win'.\n" +
                        "- Two matching symbols is a 'Small Win'.\n" +
                        "- Payouts depend on the symbol and the number of matches.\n"
        );
        slotsInstructions.setFont(Constants.TEXT_FONT);
        slotsInstructions.setEditable(false);
        slotsInstructions.setOpaque(false);
        slotsInstructions.setForeground(Color.decode(Constants.fg));
        contentPanel.add(slotsInstructions);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some space


        contentPanel.add(back);

        add(contentPanel);
    }
}
