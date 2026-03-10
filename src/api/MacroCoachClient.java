package api;

import model.dashboard.DashboardResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MacroCoachClient {

    public static DashboardResponse getDashboard(String username) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/dashboard?username=" + username)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to load dashboard. Status code: " + response.statusCode());
        }

        String json = response.body();
        return mapper.readValue(json, DashboardResponse.class);
    }
    
}
