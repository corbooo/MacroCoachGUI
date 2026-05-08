package ui.history;

import ui.Navigator;
import api.MacroCoachClient;
import model.history.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class HistoryPanel extends JPanel {
    
    private static final Color BG = new Color(30, 30, 30);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    private final String username;
    private final Navigator navigator;

    public HistoryPanel(String username, Navigator navigator) {
        this.username = username;
        this.navigator = navigator;

        setBackground(BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        buildHistory();
    }

    private void buildHistory() {
        // - Top Bar -
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        
        JLabel titleLabel = new JLabel("History", SwingConstants.CENTER);
        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        
        JButton backButton = new JButton("Back");
        backButton.setBackground(ACCENT);
        backButton.setForeground(TEXT);
        backButton.addActionListener(e -> navigator.showDashboard(username));

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setForeground(TEXT);
        userLabel.setFont(userLabel.getFont().deriveFont(30f));
        userLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(userLabel, BorderLayout.EAST);

        // - History Cards -
        JPanel historyContent = new JPanel(new GridLayout(1, 2, 20, 0));
        historyContent.setBackground(BG);

        JPanel weightPanel = createWeightHistoryCard();
        JPanel macroPanel = createMacroHistoryCard();

        historyContent.add(weightPanel);
        historyContent.add(macroPanel);

        add(topBar, BorderLayout.NORTH);
        add(historyContent, BorderLayout.CENTER);
    }

    private JPanel createWeightHistoryCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(40, 40, 40));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACCENT, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("Weight History", SwingConstants.CENTER);
        titleLabel.setForeground(TEXT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        DefaultListModel<String> listModel = new DefaultListModel<>();

        try {
            WeightHistoryResponse response = MacroCoachClient.getWeightHistory(username);

            if (response.weights == null || response.weights.isEmpty()) {
                listModel.addElement("No weight entries found.");
            } else {
                for (WeightHistoryEntry entry : response.weights) {
                    listModel.addElement(entry.day + " - " + entry.weight_lbs + " lbs");
                }
            }
        } catch (Exception e) {
            listModel.addElement("Failed to load weight history.");
        }

        JList<String> list = new JList<>(listModel);
        list.setBackground(BG);
        list.setForeground(TEXT);
        list.setSelectionBackground(ACCENT);
        list.setFont(new Font("SansSerif", Font.PLAIN, 16));
        list.setFixedCellHeight(32);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(ACCENT);
        styleScrollPane(scrollPane);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private JPanel createMacroHistoryCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(40, 40, 40));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACCENT, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("Macro History", SwingConstants.CENTER);
        titleLabel.setForeground(TEXT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        DefaultListModel<String> listModel = new DefaultListModel<>();

        try {
            MacroHistoryResponse response = MacroCoachClient.getMacroHistory(username);

            if (response.macros == null || response.macros.isEmpty()) {
                listModel.addElement("No weight entries found.");
            } else {
                for (MacroHistoryEntry entry : response.macros) {
                    String line = entry.day
                            + " - Cals: " + entry.calories
                            + ", P: " + entry.protein_g
                            + ", C: " + entry.carbs_g
                            + ", F: " + entry.fat_g;

                    listModel.addElement(line);
                }
            }
        } catch (Exception e) {
            listModel.addElement("Failed to load macro history.");
        }

        JList<String> list = new JList<>(listModel);
        list.setBackground(BG);
        list.setForeground(TEXT);
        list.setSelectionBackground(ACCENT);
        list.setFont(new Font("SansSerif", Font.PLAIN, 16));
        list.setFixedCellHeight(32);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(ACCENT);
        styleScrollPane(scrollPane);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(12, 0));

        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ACCENT;
                this.trackColor = new Color(45, 45, 45);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            
            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
    }
}
