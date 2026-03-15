package ui.dashboard;

import api.MacroCoachClient;
import model.weight.WeightEntryRequest;
import model.weight.WeightUpsertResponse;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class WeightEntryDialog extends JDialog {

    private JTextField dayField;
    private JTextField weightField;

    private final Runnable onSuccess;
    
    public WeightEntryDialog(String username, Runnable onSuccess) {
        super((Frame) null, "Add Weight Entry", Dialog.ModalityType.APPLICATION_MODAL);
        this.onSuccess = onSuccess;

        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // title
        JLabel label = new JLabel("Weight entry for user: " + username);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // weight entry fields
        JPanel entryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        dayField = new JTextField(LocalDate.now().toString(), 15);
        weightField = new JTextField(15);

        // bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> handleSave(username));
        cancelButton.addActionListener(e -> dispose());

        addFormRow(entryPanel, gbc, 0, "Day: ", dayField);
        addFormRow(entryPanel, gbc, 1, "Weight: ", weightField);

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
        String weightText = weightField.getText().trim();

        if (day.isEmpty() || weightText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);

            WeightEntryRequest entry = new WeightEntryRequest();
            entry.day = day;
            entry.weight_lbs = weight;

            WeightUpsertResponse result = MacroCoachClient.upsertWeight(username, entry);

            JOptionPane.showMessageDialog(this, "Weight entry " + result.action + " sucessfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            onSuccess.run();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Weight must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save weight entry.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
