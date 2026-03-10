package ui.dashboard;

import api.MacroCoachClient;
import model.dashboard.DashboardResponse;
import ui.Navigator;

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

        // fetch dashboard data
        DashboardResponse data;
        try {
            data = MacroCoachClient.getDashboard(username);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load dashboard for username: " + username,
                "Dashboard Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        
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

        // - Dashboard Cards -
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(BG);

        cardsPanel.add(new TargetMacrosPanel(data.target));
        cardsPanel.add(makePlaceholderCard("Latest Logged Macros"));
        cardsPanel.add(makePlaceholderCard("Rolling Weekly Average"));
        cardsPanel.add(makePlaceholderCard("Weight Trend"));

        
        add(topBar, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
        
    }
    private JPanel makePlaceholderCard(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHTER_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel label = new JLabel(title);
        label.setForeground(ACCENT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));

        panel.add(label, BorderLayout.NORTH);

        return panel;
    }
}
