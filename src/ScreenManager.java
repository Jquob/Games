import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ScreenManager {
    private final JPanel container;
    private final CardLayout cardLayout;
    private final HashMap<String, JPanel> screens = new HashMap<>();

    public ScreenManager() {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
    }

    public void addScreen(String name, JPanel panel) {
        if (screens.containsKey(name)) {
            container.remove(screens.get(name));
        }
        screens.put(name, panel);
        container.add(panel, name);
    }

    public void showScreen(String name) {
        if (!screens.containsKey(name)) {
            System.err.println("Screen not found: " + name);
            return;
        }
        cardLayout.show(container, name);
    }

    public JPanel getContentPanel() {
        return container;
    }
}

