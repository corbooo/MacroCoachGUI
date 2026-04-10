package model.history;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MacroHistoryResponse {
    public int count;
    public List<MacroHistoryEntry> macros;
}
