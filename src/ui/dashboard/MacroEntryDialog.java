package ui.dashboard;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MacroEntryDialog extends JDialog {

    private JTextField dayField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField carbsField;
    private JTextField fatField;

    public MacroEntryDialog(String username) {
        
        setTitle("Add Macro Entry");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // title
        JLabel label = new JLabel("Macro entry for user: " + username);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // macro entry fields
        JPanel entryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        dayField = new JTextField(LocalDate.now().toString(), 15);
        caloriesField = new JTextField(15);
        proteinField = new JTextField(15);
        carbsField = new JTextField(15);
        fatField = new JTextField(15);

        addFormRow(entryPanel, gbc, 0, "Day: ", dayField);
        addFormRow(entryPanel, gbc, 1, "Calories: ", caloriesField);
        addFormRow(entryPanel, gbc, 2, "Protein: ", proteinField);
        addFormRow(entryPanel, gbc, 3, "Carbs: ", carbsField);
        addFormRow(entryPanel, gbc, 4, "Fat: ", fatField);

        // bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(label, BorderLayout.CENTER);
        add(entryPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }
}
