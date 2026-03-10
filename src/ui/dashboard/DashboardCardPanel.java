package ui.dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DashboardCardPanel extends JPanel {

    protected static final Color CARD_BG = new Color(40, 40, 40);
    protected static final Color ACCENT = new Color(0, 255, 255);
    protected static final Color TEXT = Color.WHITE;

    protected JPanel contentPanel;

    public DashboardCardPanel(String title) {

        setBackground(CARD_BG);
        setLayout(new BorderLayout());

        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));

        contentPanel = new JPanel();
        contentPanel.setBackground(CARD_BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    protected JLabel makeRowLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 25));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
}