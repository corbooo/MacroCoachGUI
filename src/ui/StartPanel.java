package ui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    
    private static final Color BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    public StartPanel() {
        setBackground(BG);
        setLayout(new BorderLayout());

        // Center content panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(50, 50, 50));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("MacroCoachGUI");
        title.setForeground(ACCENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 42f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(title);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        // centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    }
}
