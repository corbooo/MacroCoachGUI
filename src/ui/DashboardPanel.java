package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color GRAY_ACCENT = new Color(50, 50, 50);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    private final String username;

    public DashboardPanel(String username) {
        this.username = username;

        setBackground(BG);
        setLayout(new GridBagLayout());

        JLabel label = new JLabel("Dashboard for: " + username);
        label.setForeground(TEXT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 28f));

        add(label);
    }
}
