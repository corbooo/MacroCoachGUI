package ui.dashboard;

import javax.swing.Box;

import model.dashboard.LatestMacro;

public class LatestLoggedPanel extends DashboardCardPanel {

    public LatestLoggedPanel(LatestMacro latestMacro) {

        super("Latest Logged Macros");

        contentPanel.add(makeRowLabel(latestMacro.day));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(makeRowLabel("Calories: " + latestMacro.calories));
        contentPanel.add(makeRowLabel("Protein: " + latestMacro.protein_g + " g"));
        contentPanel.add(makeRowLabel("Carbs: " + latestMacro.carbs_g + " g"));
        contentPanel.add(makeRowLabel("Fat: " + latestMacro.fat_g + " g"));
    }
}