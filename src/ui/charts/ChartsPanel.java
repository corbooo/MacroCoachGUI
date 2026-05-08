package ui.charts;

import ui.Navigator;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class ChartsPanel extends JPanel {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color CARD_BG = new Color(33, 33, 33);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    private final String username;
    private final Navigator navigator;

    public ChartsPanel(String username, Navigator navigator) {
        this.username = username;
        this.navigator = navigator;

        setBackground(BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buildCharts();
    }

    private void buildCharts() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Charts", SwingConstants.CENTER);
        JLabel userLabel = new JLabel("User: " + username);

        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        userLabel.setForeground(ACCENT);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        backButton.addActionListener(e -> navigator.showDashboard(username));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(userLabel, BorderLayout.EAST);

        JPanel chartContent = new JPanel(new GridLayout(1, 2, 20, 0));
        chartContent.setBackground(BG);

        JPanel weightChartCard = createChartCard("Weight Chart");
        JPanel macroChartCard = createChartCard("Macro Chart");

        chartContent.add(weightChartCard);
        chartContent.add(macroChartCard);

        add(topBar, BorderLayout.NORTH);
        add(chartContent, BorderLayout.CENTER);

    }

    private JPanel createChartCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ACCENT, 2), BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(TEXT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel placeholder = new JLabel("Chart will go here", SwingConstants.CENTER);
        placeholder.setForeground(TEXT);
        placeholder.setFont(new Font("SansSerif", Font.PLAIN, 18));

        String[] limitOptions = {"7", "14", "30", "60", "90", "180", "365", "1000"};
        JComboBox<String> limitDropdown = new JComboBox<>(limitOptions);
        limitDropdown.setSelectedItem("30");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(CARD_BG);
        bottomPanel.add(new JLabel("Entries: "));
        bottomPanel.add(limitDropdown);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(placeholder, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

}
