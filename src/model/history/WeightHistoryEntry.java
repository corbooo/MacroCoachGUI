package model.history;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeightHistoryEntry {
    public int id;
    public int user_id;
    public String day;
    public double weight_lbs;
}
