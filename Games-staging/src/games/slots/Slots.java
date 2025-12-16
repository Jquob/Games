package games.slots;

import lib.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class Slots extends JPanel {

    public Slots(ScreenManager screenManager) {
        Font Header = Constants.HEADER_FONT;
        Font ButtonFont = Constants.BUTTON_FONT;
        Font TextFont = Constants.TEXT_FONT;

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

        JPanel

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        gbc.gridy = 2;
        panel.add(backButton, gbc);

        add(panel);
    }

    public class SlotsFunctions {
        public static void Roll() {
            Constants.SLOT_1 = (int) (Math.random() * 10);
            Constants.SLOT_2 = (int) (Math.random() * 10);
            Constants.SLOT_3 = (int) (Math.random() * 10);
        }

        public static void Reward() {
            if (Constants.SLOT_1 == Constants.SLOT_2 || Constants.SLOT_1 == Constants.SLOT_3) {
                if (Constants.SLOT_1 == Constants.SLOT_2 && Constants.SLOT_2 == Constants.SLOT_3) {
                    Constants.REWARD = 5;
                } else {
                    Constants.REWARD = 3;
                }
            }
        }
    }

    public class DebugFunctions {
        public static void print_array(int[] array) {
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i]);
            }
        }
    }

    public void startGame() {
        SlotsFunctions.Roll();
        SlotsFunctions.Reward();
        System.out.println("Spinning the reels...");
    }
}
