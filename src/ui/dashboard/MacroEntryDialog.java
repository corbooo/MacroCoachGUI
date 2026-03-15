package ui.dashboard;

import api.MacroCoachClient;
import model.macros.MacroEntryRequest;
import model.macros.MacroUpsertResponse;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MacroEntryDialog extends JDialog {

    private JTextField dayField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField carbsField;
    private JTextField fatField;

    private final Runnable onSuccess;

    public MacroEntryDialog(String username, Runnable onSuccess) {
        this.onSuccess = onSuccess;

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
        String day = dayField.getText().trim();
        String caloriesText = caloriesField.getText().trim();
        String proteinText = proteinField.getText().trim();
        String carbsText = carbsField.getText().trim();
        String fatText = fatField.getText().trim();

        if (day.isEmpty() || caloriesText.isEmpty() || proteinText.isEmpty() || carbsText.isEmpty() || fatText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int calories = Integer.parseInt(caloriesText);
            double protein = Double.parseDouble(proteinText);
            double carbs = Double.parseDouble(carbsText);
            double fat = Double.parseDouble(fatText);

            MacroEntryRequest entry = new MacroEntryRequest();
            entry.day = day;
            entry.calories = calories;
            entry.protein_g = protein;
            entry.carbs_g = carbs;
            entry.fat_g = fat;

            MacroUpsertResponse result = MacroCoachClient.upsertMacros(username, entry);
            
            JOptionPane.showMessageDialog(this, "Macro entry " + result.action + " sucessfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            onSuccess.run();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Calories must be a whole number, and protein/carbs/fat must be valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save macro entry.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
