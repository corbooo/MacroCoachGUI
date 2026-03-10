package api;

import model.dashboard.DashboardResponse;
import model.macros.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MacroCoachClient {
    
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String BASE_URL = "http://localhost:8000";

    public static DashboardResponse getDashboard(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/dashboard?username=" + username))
            .GET()
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to load dashboard. Status code: " + response.statusCode());
        }

        String json = response.body();
        return MAPPER.readValue(json, DashboardResponse.class);
    }

    public static MacroUpsertResponse upsertMacros(String username, MacroEntryRequest entry) throws IOException, InterruptedException {
        String jsonBody = MAPPER.writeValueAsString(entry);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/macros?username=" + username))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to upsert macros. Status code: " + response.statusCode());
        }

        return MAPPER.readValue(response.body(), MacroUpsertResponse.class);
    }
    
}
