package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color LIGHTER_BG = new Color(33, 33, 33);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    public DashboardPanel(String username, Navigator navigator) {
        setBackground(BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // - Top Name Bar - 
        JPanel topBar = new JPanel(new BorderLayout(20,0));
        topBar.setBackground(BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JButton button = new JButton("BACK");
        button.setBackground(ACCENT);
        button.setForeground(TEXT);
        button.addActionListener(e -> navigator.showStart());
        
        JLabel titleLabel = new JLabel("MacroCoach Dashboard");
        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 26f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setForeground(TEXT);
        userLabel.setFont(userLabel.getFont().deriveFont(30f));
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        topBar.add(button, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(userLabel, BorderLayout.EAST);
        
        // - Center content panel -
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(LIGHTER_BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setForeground(TEXT);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 32f));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel placeholderLabel = new JLabel("Macro data will appear here.");
        placeholderLabel.setForeground(TEXT);
        placeholderLabel.setFont(placeholderLabel.getFont().deriveFont(18f));
        placeholderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(welcomeLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(placeholderLabel);
        

        add(topBar, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}
