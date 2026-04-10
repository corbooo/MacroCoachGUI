package api;

import model.dashboard.DashboardResponse;
import model.history.*;
import model.macros.*;
import model.weight.*;
import model.target.*;

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

        if (response.statusCode() == 404) {
            throw new IOException("Failed to load dashboard. There is no user: " + username);
        }
        return MAPPER.readValue(response.body(), DashboardResponse.class);
    }

    public static WeightHistoryResponse getWeightHistory(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/weights?username=" + username))
            .GET()
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch weight history. Status Code: " + response.statusCode());
        }
        return MAPPER.readValue(response.body(), WeightHistoryResponse.class);
    }

    public static MacroHistoryResponse getMacroHistory(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/macros?username=" + username))
            .GET()
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch macro history. Status Code: " + response.statusCode());
        }
        return MAPPER.readValue(response.body(), MacroHistoryResponse.class);
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
    
    public static WeightUpsertResponse upsertWeight(String username, WeightEntryRequest entry) throws IOException, InterruptedException {
        String jsonBody = MAPPER.writeValueAsString(entry);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/weights?username=" + username))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to upsert weight. Status code: " + response.statusCode());
        }
        return MAPPER.readValue(response.body(), WeightUpsertResponse.class);
    }

    public static TargetUpsertResponse upsertTarget(String username, TargetEntryRequest entry) throws IOException, InterruptedException {
        String jsonBody = MAPPER.writeValueAsString(entry);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/targets?username=" + username))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to upsert target. Status code: " + response.statusCode());
        }
        return MAPPER.readValue(response.body(), TargetUpsertResponse.class);
    }
}
