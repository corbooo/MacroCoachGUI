package api;

import model.dashboard.DashboardResponse;

public class ApiTest {
    public static void main(String[] args) throws Exception {
        DashboardResponse data = MacroCoachClient.getDashboard("corbin");

        System.out.println("Username: " + data.username);

        System.out.println("Latest calories: " + data.latest_macro.calories);
        System.out.println("Latest protein: " + data.latest_macro.protein_g);

        System.out.println("Target calories: " + data.target.calories_target);
        System.out.println("Target protein: " + data.target.protein_target);

        System.out.println("Avg calories: " + data.rolling.macros.avg_calories);
        System.out.println("Avg protein: " + data.rolling.macros.avg_protein);

        System.out.println("Weight trend: " + data.rolling.weight.trend_lbs);
        System.out.println("Direction: " + data.rolling.weight.direction);
    }
}