package lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Pause extends JPanel {
    public Pause(ScreenManager screenManager, String previousScreen, Runnable restartAction) {
        setBackground(Color.decode(Constants.bg));
        setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel title = StyleHelpers.createStyledLabel("Paused", Constants.HEADER_FONT, Constants.fg);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Resume: Just goes back
        JButton resumeButton = createMenuBtn("Resume", e -> screenManager.showScreen(previousScreen));

        // Restart: Runs the reset logic THEN goes back
        JButton restartButton = createMenuBtn("Restart", e -> {
            if (restartAction != null) {
                restartAction.run();
            }
            screenManager.showScreen(previousScreen);
        });

        JButton backToModesButton = StyleHelpers.createStyledButton("Back to Modes", Constants.BUTTON_FONT, Constants.secondary, Constants.txt);
        backToModesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToModesButton.addActionListener(e -> screenManager.showScreen("modes"));

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(resumeButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(restartButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(backToModesButton);

        add(contentPanel);
    }

    private JButton createMenuBtn(String text, java.awt.event.ActionListener l) {
        JButton b = StyleHelpers.createStyledButton(text, Constants.BUTTON_FONT, Constants.fg, Constants.txt);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.addActionListener(l);
        return b;
    }
}