package ui.dashboard;

import api.MacroCoachClient;
import model.dashboard.DashboardResponse;
import ui.Navigator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardPanel extends JPanel {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    private final String username;
    private final Navigator navigator;
    private JLabel lastUpdatedLabel;

    public DashboardPanel(String username, Navigator navigator) {
        this.username = username;
        this.navigator = navigator;

        setBackground(BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        buildDashboard();
    }

    private void buildDashboard() {
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
        
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"));

        // - Top Name Bar - 
        JPanel topBar = new JPanel(new BorderLayout(20,0));
        topBar.setBackground(BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(BG);

        JButton backButton = new JButton("BACK");
        backButton.setBackground(ACCENT);
        backButton.setForeground(TEXT);
        backButton.addActionListener(e -> navigator.showStart());
        
        leftPanel.add(backButton);

        JLabel titleLabel = new JLabel("MacroCoach Dashboard");
        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 40f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(BG);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setForeground(TEXT);
        userLabel.setFont(userLabel.getFont().deriveFont(30f));
        userLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        lastUpdatedLabel = new JLabel("Last Updated: " + currentTime);
        lastUpdatedLabel.setForeground(TEXT);
        lastUpdatedLabel.setFont(lastUpdatedLabel.getFont().deriveFont(16f));
        lastUpdatedLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(ACCENT);
        refreshButton.setForeground(TEXT);
        refreshButton.addActionListener(e -> refreshDashboard());
        refreshButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(userLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(lastUpdatedLabel);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(refreshButton);
        
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);

        // - Dashboard Cards -
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(BG);

        cardsPanel.add(new TargetMacrosPanel(data.target));
        cardsPanel.add(new LatestLoggedPanel(data.latest_macro));
        cardsPanel.add(new WeeklyAveragePanel(data.rolling));
        cardsPanel.add(new WeightProgressPanel(data.rolling));

        // - Bottom Button Section -
        JPanel bottomSection = new JPanel(new GridLayout(2, 1, 20, 10));
        bottomSection.setBackground(BG);
        bottomSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // top button row
        JPanel dataButtonsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        dataButtonsRow.setBackground(BG);

        // placeholder buttons for now
        JButton insightsButton = new JButton("Insights");
        insightsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Insights feature coming soon."));
        JButton chartsButton = new JButton("Charts");
        chartsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Charts feature coming soon."));
        JButton historyButton = new JButton("History");
        historyButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "History feature coming soon."));
        dataButtonsRow.add(insightsButton);
        dataButtonsRow.add(chartsButton);
        dataButtonsRow.add(historyButton);

        // bottom button row
        JPanel actionButtonsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        actionButtonsRow.setBackground(BG);

        JButton macroEntryButton = new JButton("Add Macro");
        macroEntryButton.setBackground(ACCENT);
        macroEntryButton.setForeground(TEXT);
        macroEntryButton.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 26f));
        macroEntryButton.addActionListener(e -> {
            MacroEntryDialog dialog = new MacroEntryDialog(username, this::refreshDashboard);
            dialog.setVisible(true);
        });
        JButton weightEntryButton = new JButton("Add Weight");
        weightEntryButton.setBackground(ACCENT);
        weightEntryButton.setForeground(TEXT);
        weightEntryButton.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 26f));
        weightEntryButton.addActionListener(e -> {
            WeightEntryDialog dialog = new WeightEntryDialog(username, this::refreshDashboard);
            dialog.setVisible(true);
        });
        actionButtonsRow.add(macroEntryButton);
        actionButtonsRow.add(weightEntryButton);

        bottomSection.add(dataButtonsRow);
        bottomSection.add(actionButtonsRow);


        add(topBar, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
        add(bottomSection, BorderLayout.SOUTH);
    }

    private void refreshDashboard() {
        removeAll();
        buildDashboard();
        revalidate();
        repaint();
    }
}
