package model.history;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeightHistoryResponse {
    public int count;
    public List<WeightHistoryEntry> weights;
}
