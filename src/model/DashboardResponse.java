package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardResponse {
    public String username;
    public LatestMacro latest_macro;
    public Target target;
    public Rolling rolling;
}
