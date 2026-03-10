package ui.dashboard;

import javax.swing.Box;

import model.dashboard.Rolling;

public class WeeklyAveragePanel extends DashboardCardPanel {
    
    public WeeklyAveragePanel(Rolling rolling) {

        super("Weekly Average");

        contentPanel.add(makeRowLabel(rolling.range.start + " to " + rolling.range.end));
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(makeRowLabel("Average Calories: " + rolling.macros.avg_calories));
        contentPanel.add(makeRowLabel("Average Protein: " + rolling.macros.avg_protein));
        contentPanel.add(makeRowLabel("Days Logged: " + rolling.window_days));
    }

}
