package ui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class AppFrame extends JFrame {

    public AppFrame() {
        super("MacroCoach");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartPanel startPanel = new StartPanel();
        add(startPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1024, 768));
        pack();
        setLocationRelativeTo(null);
    }
}
