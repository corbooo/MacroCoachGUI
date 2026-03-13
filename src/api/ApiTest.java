package api;

import model.macros.MacroEntryRequest;
import model.macros.MacroUpsertResponse;

public class ApiTest {
    public static void main(String[] args) throws Exception {
        MacroEntryRequest entry = new MacroEntryRequest();
        entry.day = "2026-03-13";
        entry.calories = 3098;
        entry.protein_g = 209.0;
        entry.carbs_g = 342.0;
        entry.fat_g = 105.0;

        MacroUpsertResponse result = MacroCoachClient.upsertMacros("corbin", entry);

        System.out.println(result.action);
        System.out.println(result.saved.calories);
    }
}