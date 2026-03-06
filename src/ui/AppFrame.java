package ui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class AppFrame extends JFrame implements Navigator {

    public AppFrame() {
        super("MacroCoach");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new StartPanel(this), BorderLayout.CENTER);

        setPreferredSize(new Dimension(1024, 768));
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void showStart() {
        getContentPane().removeAll();
        add(new StartPanel(this), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void showDashboard(String username) {
        getContentPane().removeAll();
        add(new DashboardPanel(username), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
