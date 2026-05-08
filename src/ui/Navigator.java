package ui;

public interface Navigator {
    void showStart();
    void showDashboard(String username);
    void showCharts(String username);
    void showHistory(String username);
}
