package org.example.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class ApiClient {

    // HttpClient to make requests
    protected static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final Map<String, String> BASE_URLS = new ConcurrentHashMap<>();

    public static CompletableFuture<String> fetchData(String name) {
        String baseUrl = BASE_URLS.get(Thread.currentThread().getStackTrace()[2].getClassName());

        HttpRequest request = buildRequest(baseUrl + name.toLowerCase());
        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {

                    if (response.statusCode() == 200) return response.body();
                    else throw new RuntimeException("HTTP Response Code: " + response.statusCode());

                }).exceptionally(e -> "Error: " + e.getMessage());
    }

    /**
     * Builds a request
     *
     * @param url The URL of the request
     * @return The request
     */
    protected static HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    /**
     * Processes a JSON array
     *
     * @param a The JSON array
     * @param processor The processor, which processes the JSON object
     * @return The results
     * @param <T> The type of the results
     */
    protected static <T> List<T> processJsonArray(JSONArray a, Function<JSONObject, T> processor) {
        List<T> results = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            T result = processor.apply(a.getJSONObject(i));
            if  (result != null) results.add(result);
        }
        return results;
    }

    protected static void setBaseUrl(String className, String baseUrl) {
        BASE_URLS.put(className, baseUrl);
    }
}
