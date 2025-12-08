import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        JFrame frame = new JFrame("Modern Card Game UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the screen manager and register screens
        ScreenManager screenManager = new ScreenManager();
        Home homeScreen = new Home(screenManager);
        Modes modesScreen = new Modes(screenManager);

        screenManager.addScreen("home", homeScreen);
        screenManager.addScreen("modes", modesScreen);

        // set the frame content to the screen manager's container
        frame.setContentPane(screenManager.getContentPanel());

        // show the initial screen
        screenManager.showScreen("home");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}