package lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Pause extends JPanel {
    public Pause(ScreenManager screenManager, String previousScreen, Runnable restartAction) {
        Font headerFont = Constants.HEADER_FONT;
        Font buttonFont = Constants.BUTTON_FONT;

        setLayout(new GridBagLayout());
        setBackground(Color.decode(Constants.bg));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode(Constants.bg));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = StyleHelpers.createStyledLabel("Paused", headerFont, Constants.fg);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton resumeButton = StyleHelpers.createStyledButton("Resume", buttonFont, Constants.fg, Constants.txt);
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.addActionListener((ActionEvent e) -> screenManager.showScreen(previousScreen));

        JButton restartButton = StyleHelpers.createStyledButton("Restart", buttonFont, Constants.fg, Constants.txt);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener((ActionEvent e) -> {
            restartAction.run();
            screenManager.showScreen(previousScreen);
        });

        JButton backToModesButton = StyleHelpers.createStyledButton("Back to Modes", buttonFont, Constants.secondary, Constants.txt);
        backToModesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToModesButton.addActionListener((ActionEvent e) -> screenManager.showScreen("modes"));

        contentPanel.add(title);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(resumeButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(restartButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(backToModesButton);

        add(contentPanel);
    }
}
