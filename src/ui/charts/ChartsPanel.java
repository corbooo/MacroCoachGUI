package ui.charts;

import ui.Navigator;
import api.MacroCoachClient;
import model.history.*;

import java.util.Collections;
import javax.swing.*;
import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import java.time.LocalDate;


public class ChartsPanel extends JPanel {

    private static final Color BG = new Color(30, 30, 30);
    private static final Color CARD_BG = new Color(33, 33, 33);
    private static final Color ACCENT = new Color(0, 255, 255);
    private static final Color TEXT = Color.WHITE;

    private final String username;
    private final Navigator navigator;

    public ChartsPanel(String username, Navigator navigator) {
        this.username = username;
        this.navigator = navigator;

        setBackground(BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buildCharts();
    }

    private void buildCharts() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton backButton = new JButton("Back");
        JLabel titleLabel = new JLabel("Charts", SwingConstants.CENTER);
        JLabel userLabel = new JLabel("User: " + username);

        titleLabel.setForeground(ACCENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        userLabel.setForeground(ACCENT);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        backButton.setBackground(ACCENT);
        backButton.setForeground(TEXT);
        backButton.addActionListener(e -> navigator.showDashboard(username));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(userLabel, BorderLayout.EAST);

        JPanel chartContent = new JPanel(new GridLayout(1, 2, 20, 0));
        chartContent.setBackground(BG);

        JPanel weightChartCard = createChartCard("Weight Chart", "weight");
        JPanel macroChartCard = createChartCard("Macro Chart", "calories");

        chartContent.add(weightChartCard);
        chartContent.add(macroChartCard);

        add(topBar, BorderLayout.NORTH);
        add(chartContent, BorderLayout.CENTER);

    }

    private ChartPanel createWeightChartPanel(int limit) {
        TimeSeries series = new TimeSeries("Weight");

        double minWeight = Double.MAX_VALUE;
        double maxWeight = Double.MIN_VALUE;

        try {
            WeightHistoryResponse response = MacroCoachClient.getWeightHistory(username, limit);
            
            if (response.weights != null) {
                Collections.reverse(response.weights);
                
                for (WeightHistoryEntry entry : response.weights) {
                    LocalDate date = LocalDate.parse(entry.day);

                    series.add(
                        new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                        entry.weight_lbs
                    );

                    if (entry.weight_lbs < minWeight) {
                        minWeight = entry.weight_lbs;
                    }

                    if (entry.weight_lbs > maxWeight) {
                        maxWeight = entry.weight_lbs;
                }
            }
            }
        } catch (Exception e) {
            series.add(new Day(), 0);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("Weight History", "Date", "Weight (lbs)", dataset, false, true, false);
        styleChart(chart);

        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        if (minWeight != Double.MAX_VALUE && maxWeight != Double.MIN_VALUE) {
            double margin = 2.0;
            rangeAxis.setRange(minWeight - margin, maxWeight + margin);
        }

        return new ChartPanel(chart);
    }

    private ChartPanel createCaloriesChartPanel(int limit) {
        TimeSeries series = new TimeSeries("Calories");

        double minCalories = Double.MAX_VALUE;
        double maxCalories = Double.MIN_VALUE;

        try {
            MacroHistoryResponse response = MacroCoachClient.getMacroHistory(username, limit);

            if (response.macros != null) {
                Collections.reverse(response.macros);

                for (MacroHistoryEntry entry : response.macros) {
                    LocalDate date = LocalDate.parse(entry.day);

                    series.add(
                        new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                        entry.calories
                    );

                    if (entry.calories < minCalories) {
                        minCalories = entry.calories;
                    }

                    if (entry.calories > maxCalories) {
                        maxCalories = entry.calories;
                    }
                }
            }

        } catch (Exception e) {
            series.add(new Day(), 0);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("Calories History", "Date", "Calories", dataset, false, true, false);
        styleChart(chart);

        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        if (minCalories != Double.MAX_VALUE && maxCalories != Double.MIN_VALUE) {
            double margin = 200.0;
            rangeAxis.setRange(minCalories - margin, maxCalories + margin);
        }

        return new ChartPanel(chart);
    }

    private JPanel createChartCard(String title, String type) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ACCENT, 2), BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JPanel chartHolder = new JPanel(new BorderLayout());
        chartHolder.setBackground(CARD_BG);
        if (type.equals("weight")) {
            chartHolder.add(createWeightChartPanel(30), BorderLayout.CENTER);
        } else if (type.equals("calories")) {
            chartHolder.add(createCaloriesChartPanel(30), BorderLayout.CENTER);
        }
        
        String[] limitOptions = {"7", "14", "30", "60", "90", "180", "365", "1000"};
        JComboBox<String> limitDropdown = new JComboBox<>(limitOptions);
        limitDropdown.setSelectedItem("30");
        
        limitDropdown.addActionListener(e -> {
            int selectedLimit = Integer.parseInt((String) limitDropdown.getSelectedItem());
            chartHolder.removeAll();

            if (type.equals("weight")) {
                chartHolder.add(createWeightChartPanel(selectedLimit), BorderLayout.CENTER);
            } else if (type.equals("calories")) {
                chartHolder.add(createCaloriesChartPanel(selectedLimit), BorderLayout.CENTER);
            }

            chartHolder.revalidate();
            chartHolder.repaint();
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(CARD_BG);
        JLabel entriesLabel = new JLabel("Entries: ");
        entriesLabel.setForeground(TEXT);
        bottomPanel.add(entriesLabel);
        bottomPanel.add(limitDropdown);
        
        card.add(chartHolder, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void styleChart(JFreeChart chart) {
        chart.setBackgroundPaint(CARD_BG);

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(45, 45, 45));
        plot.setDomainGridlinePaint(new Color(80, 80, 80));
        plot.setRangeGridlinePaint(new Color(80, 80, 80));

        chart.getTitle().setPaint(TEXT);

        plot.getDomainAxis().setLabelPaint(TEXT);
        plot.getDomainAxis().setTickLabelPaint(TEXT);

        plot.getRangeAxis().setLabelPaint(TEXT);
        plot.getRangeAxis().setTickLabelPaint(TEXT);

        plot.getRenderer().setSeriesPaint(0, ACCENT);
    }
}
