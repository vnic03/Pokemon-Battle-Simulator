package org.example.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.Constants;
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



/**
 * Abstract base class for API clients that provides common functionalities for sending HTTP requests,
 * processing JSON arrays, and managing base URLs for derived classes
 */
public abstract class ApiClient {

    protected static final Logger LOGGER = LogManager.getLogger(ApiClient.class);

    // HttpClient to make requests
    protected static final HttpClient CLIENT = HttpClient.newHttpClient();

    // Map storing base URLs for different classes
    private static final Map<String, String> BASE_URLS = new ConcurrentHashMap<>();


    /**
     * This method constructs a complete URL using the base URL associated with the calling class,
     * sends an asynchronous HTTP GET request, and handles the response.
     *
     * @param name The identifier used to generate the endpoint URL
     * @return A CompletableFuture that, when completed, provides the fetched data as a string
     */
    protected static CompletableFuture<String> fetchData(String name) {
        if (name == null || name.isEmpty()) {
            LOGGER.error("Name missing");
            return CompletableFuture.failedFuture(new IllegalArgumentException("Forgot to put name ?"));
        }
        String baseUrl = BASE_URLS.get(Thread.currentThread().getStackTrace()[2].getClassName());
        HttpRequest request = buildRequest(baseUrl + name.toLowerCase());

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    int status = response.statusCode();
                    var body = response.body();

                    if (status == Constants.HTTP_STATUS_OK) return body;
                    else {
                        LOGGER.warn("Received non-OK HTTP status {} from {}: {}", status, baseUrl + name, body);
                        LOGGER.info("Name-Check: {}", name);
                        throw new RuntimeException("HTTP Response Code: " + status);
                    }
                }).exceptionally(e -> {
                    LOGGER.error("Failed to fetch data from {}: {}", baseUrl + name, e.getMessage(), e);
                    return "Error: " + e.getMessage();
                });
    }

    /**
     * Builds an HTTP GET request for the specified URL
     *
     * @param url The URL of the request
     * @return An HttpRequest object configured for a GET operation
     */
    protected static HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    /**
     * Processes each element of a JSON array and returns a list of results
     *
     * @param a The JSON array
     * @param processor The processor, which processes the JSON object
     * @return A list containing non-null results of type T processed from the JSON array
     * @param <T> The type of the results
     */
    protected static <T> List<T> processJsonArray(JSONArray a, Function<JSONObject, T> processor) {
        List<T> results = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            T result = processor.apply(a.getJSONObject(i));
            if (result != null) results.add(result);
        }
        return results;
    }

    /**
     *  Sets a specific base URL for a class derived from ApiClient to use in the fetchData method
     *
     * @param className The name of the class
     * @param baseUrl It's specific Url
     */
    protected static void setBaseUrl(String className , String baseUrl) {
        BASE_URLS.put(className, baseUrl);
    }
}
