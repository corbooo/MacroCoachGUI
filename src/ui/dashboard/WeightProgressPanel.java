package ui.dashboard;

import javax.swing.Box;

import model.dashboard.Rolling;

public class WeightProgressPanel extends DashboardCardPanel {

    public WeightProgressPanel(Rolling rolling) {

        super("Weight Progress");

        contentPanel.add(Box.createVerticalStrut(20));

        if (rolling == null || rolling.weight == null) {
            contentPanel.add(makeRowLabel("No weight data available"));
            contentPanel.add(makeRowLabel("Weight is trending -"));
            contentPanel.add(makeRowLabel("Number of Entries: -"));
        } else {
            contentPanel.add(makeRowLabel(rolling.weight.trend_lbs + " lbs"));
            contentPanel.add(makeRowLabel("Weight is trending " + rolling.weight.direction));
            contentPanel.add(makeRowLabel("Number of Entries: " + rolling.weight.entries));
        }
    }
}
