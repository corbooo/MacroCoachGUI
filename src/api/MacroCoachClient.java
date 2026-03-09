package api;

import model.DashboardResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MacroCoachClient {

    public static DashboardResponse getDashboard(String username) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8000/dashboard?username=" + username)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        DashboardResponse data = mapper.readValue(json, DashboardResponse.class);

        return data;
    }
    
}
