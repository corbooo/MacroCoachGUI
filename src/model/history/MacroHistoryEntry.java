package model.history;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MacroHistoryEntry {
    public int id;
    public int user_id;
    public String day;
    public int calories;
    public double protein_g;
    public double carbs_g;
    public double fat_g;
}
