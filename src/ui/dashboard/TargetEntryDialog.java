package ui.dashboard;

import api.MacroCoachClient;
import model.target.TargetEntryRequest;
import model.target.TargetUpsertResponse;

import javax.swing.*;
import java.awt.*;

public class TargetEntryDialog extends JDialog {

    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField carbsField;
    private JTextField fatField;

    private final Runnable onSuccess;

    public TargetEntryDialog(String username, Runnable onSuccess) {
        super((Frame) null, "Set Target", true);
        this.onSuccess = onSuccess;

        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // title
        JLabel label = new JLabel("Set the target macros for user: " + username);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // target macro fields
        JPanel entryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        caloriesField = new JTextField(15);
        proteinField = new JTextField(15);
        carbsField = new JTextField(15);
        fatField = new JTextField(15);

        addFormRow(entryPanel, gbc, 0, "Calories: ", caloriesField);
        addFormRow(entryPanel, gbc, 1, "Protein: ", proteinField);
        addFormRow(entryPanel, gbc, 2, "Carbs: ", carbsField);
        addFormRow(entryPanel, gbc, 3, "Fat: ", fatField);

        // bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> handleSave(username));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(label, BorderLayout.NORTH);
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

    private void handleSave(String username) {
        try {
            int calories = Integer.parseInt(caloriesField.getText().trim());
            double protein = Double.parseDouble(proteinField.getText().trim());
            double carbs = Double.parseDouble(carbsField.getText().trim());
            double fat = Double.parseDouble(fatField.getText().trim());

            TargetEntryRequest entry = new TargetEntryRequest();
            entry.calories_target = calories;
            entry.protein_target_g = protein;
            entry.carbs_target_g = carbs;
            entry.fat_target_g = fat;

            TargetUpsertResponse res = MacroCoachClient.upsertTarget(username, entry);

            JOptionPane.showMessageDialog(this, "Target " + res.action + " successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            onSuccess.run();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save target.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
