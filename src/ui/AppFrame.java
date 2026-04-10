package ui;

import ui.dashboard.DashboardPanel;
import ui.history.HistoryPanel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class AppFrame extends JFrame implements Navigator {

    public AppFrame() {
        super("MacroCoach");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        add(new StartPanel(this), BorderLayout.CENTER);

        setPreferredSize(new Dimension(1024, 768));
        pack();
        setLocationRelativeTo(null);
    }

    public void showStart() {
        getContentPane().removeAll();
        add(new StartPanel(this), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showDashboard(String username) {
        getContentPane().removeAll();
        add(new DashboardPanel(username, this), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showHistory(String username) {
        getContentPane().removeAll();
        add(new HistoryPanel(username, this), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
