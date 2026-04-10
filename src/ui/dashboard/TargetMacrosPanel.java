package ui.dashboard;

import javax.swing.Box;

import model.dashboard.Target;

public class TargetMacrosPanel extends DashboardCardPanel {

    public TargetMacrosPanel(Target target) {

        super("Target Macros");

        if (target == null) {
            contentPanel.add(makeRowLabel("Calories: No target set"));
            contentPanel.add(makeRowLabel("Protein: -"));
            contentPanel.add(makeRowLabel("Carbs: -"));
            contentPanel.add(makeRowLabel("Fat: -"));
        } else {
            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(makeRowLabel("Calories: " + target.calories_target));
            contentPanel.add(makeRowLabel("Protein: " + target.protein_target + " g"));
            contentPanel.add(makeRowLabel("Carbs: " + target.carbs_target + " g"));
            contentPanel.add(makeRowLabel("Fat: " + target.fat_target + " g"));
        }
    }
}