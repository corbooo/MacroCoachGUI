import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MacroCoachGUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // a layout manager to help scaling
            frame.setLayout(new BorderLayout());

            JLabel label = new JLabel("MacroCoachGUI is running!");
            frame.add(label);

            frame.setPreferredSize(new Dimension(1024, 768));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}
