package ui.dashboard;

import model.dashboard.Target;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

public class TargetMacrosPanel extends JPanel {
    
    private static final Color CARD_BG = new Color(33, 33, 33);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    public TargetMacrosPanel(Target target) {
        setBackground(CARD_BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACCENT, 1, true),
            new EmptyBorder(15, 15, 15, 15
            )));
        
        JLabel titleLabel = new JLabel("Target Macros");
        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(CARD_BG);
        contentPanel.setLayout(new GridLayout(4, 1, 0, 8));

        JLabel caloriesLabel = makeRowLabel("Calories: " + target.calories_target);
        JLabel proteinLabel = makeRowLabel("Protein: " + target.protein_target + " g");
        JLabel carbsLabel = makeRowLabel("Carbs: " + target.carbs_target + " g");
        JLabel fatLabel = makeRowLabel("Fat: " + target.fat_target + " g");

        contentPanel.add(caloriesLabel);
        contentPanel.add(proteinLabel);
        contentPanel.add(carbsLabel);
        contentPanel.add(fatLabel);

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JLabel makeRowLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return label;
    }
    
}
