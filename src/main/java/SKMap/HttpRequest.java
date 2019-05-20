package SKMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpRequest {
    private static String apiKey;

    public HttpRequest()
    {
        apiKey = "";
    }
    public HttpRequest(String googleApiKey)
    {
        apiKey = googleApiKey;
    }

    protected static JsonNode getFirstResult(String searchText) throws Exception {
        searchText=searchText.replace(" ", "+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + searchText + "&key=" + apiKey;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode array =  mapper.readValue(response.toString(), JsonNode.class);
        if(array.get("status").textValue().equalsIgnoreCase("OK")){
            JsonNode object = array.get("results").get(0);
            return object;
        }
        else{
            throw new IllegalArgumentException(array.get("error_message").textValue());
        }
    }
}

