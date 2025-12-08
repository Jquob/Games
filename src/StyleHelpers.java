import javax.swing.*;
import java.awt.*;

public class StyleHelpers {
    public static JButton createStyledButton(String text, Font font, String bgColor, String fgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(Color.decode(bgColor));
        button.setForeground(Color.decode(fgColor));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 70));
        button.setPreferredSize(new Dimension(300, 70));
        return button;
    }

    // Helper for nicely styled text labels. Two overloads:
    // 1) createStyledLabel(text, font, colorHex) -> centered horizontally
    // 2) createStyledLabel(text, font, colorHex, horizontalAlignment) -> specify SwingConstants.LEFT/CENTER/RIGHT
    public static JLabel createStyledLabel(String text, Font font, String colorHex) {
        return createStyledLabel(text, font, colorHex, SwingConstants.CENTER);
    }

    public static JLabel createStyledLabel(String text, Font font, String colorHex, int horizontalAlignment) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        if (colorHex != null && !colorHex.isEmpty()) {
            label.setForeground(Color.decode(colorHex));
        }
        // Not opaque so background shows through unless explicitly set
        label.setOpaque(false);
        // For BoxLayout centering on X axis
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        // For label text alignment
        label.setHorizontalAlignment(horizontalAlignment);
        // Add a little default padding so large headers don't touch other components
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return label;
    }
}
