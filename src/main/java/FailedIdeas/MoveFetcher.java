package FailedIdeas;
/*

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MoveFetcher {
    public static String fetchMoveDetails(String moveName) {
        String url = "https://pokeapi.co/api/v2/move/" + moveName;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return httpClient.execute(request, httpResponse ->
                    EntityUtils.toString(httpResponse.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

 */
