package ui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color GRAY_ACCENT = new Color(50, 50, 50);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    public StartPanel(Navigator navigator) {
        setBackground(BG);
        setLayout(new BorderLayout());

        // - Center content panel -
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(BG);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // title
        JLabel title = new JLabel("MacroCoach");
        title.setForeground(ACCENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 64f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // username search bar
        JTextField usernameField = new JTextField();
        usernameField.setColumns(18);
        usernameField.setBackground(GRAY_ACCENT);
        usernameField.setForeground(TEXT);
        JButton goButton = new JButton("GO");
        goButton.setBackground(ACCENT);
        goButton.setForeground(TEXT);
        
        Runnable submit = () -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username.");
                return;
            }
            navigator.showDashboard(username);
        };
        goButton.addActionListener(e -> submit.run());
        usernameField.addActionListener(e -> submit.run());

        JPanel searchRow = new JPanel(new BorderLayout(5, 0));
        searchRow.setOpaque(false);
        searchRow.add(usernameField, BorderLayout.CENTER);
        searchRow.add(goButton, BorderLayout.EAST);
        searchRow.setMaximumSize(new Dimension(420,35));
        

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(18));
        centerPanel.add(searchRow);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    
    }
}
