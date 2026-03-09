package api;

import model.DashboardResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiTest {
    public static void main(String[] args) throws Exception {
        DashboardResponse data = MacroCoachClient.getDashboard("corbin");
        
        System.out.println(data.username);
        System.out.println(data.latest_macro.calories);
    }
}
