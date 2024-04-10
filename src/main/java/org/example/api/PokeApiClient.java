package org.example.api;

import org.example.Constants;
import org.example.pokemon.Typing;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The PokeApiClient class is responsible for fetching data from the PokeAPI.
 * It fetches data for a specific Pokemon, such as the typing, abilities, stats, sprites, etc.
 */
public class PokeApiClient {

    // HttpClient to make requests
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Fetches data from the PokeAPI, for a specific Pokemon using the name
     *
     * @param pokemonName The name of the pokemon
     * @return A CompletableFuture containing the data
     */
    public static CompletableFuture<String> fetchData(String pokemonName) {
        HttpRequest request = buildRequest(Constants.POKE_API_BASE_URL + pokemonName.toLowerCase());
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {

                    if (response.statusCode() == 200) return response.body();
                    else  throw new RuntimeException("HTTP Response Code: " + response.statusCode());

                }).exceptionally(e -> "Error: " + e.getMessage());
    }

    /**
     * Fetches the Pokedex number of a pokemon
     *
     * @param pokemonName The name of the pokemon
     * @return A CompletableFuture containing the Pokedex number
     */
    public static CompletableFuture<Integer> fetchPokeDexNumber(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> new JSONObject(data).getInt("id"));
    }

    /**
     * Fetches the typing of a pokemon
     *
     * @param pokemonName The name of the pokemon
     * @return A CompletableFuture containing the typing
     */
    public static CompletableFuture<List<Typing>> fetchTyping(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            JSONArray a = new JSONObject(data).getJSONArray("types");
            return processJsonArray(a, json -> {
                String name = json.getJSONObject("type").getString("name").toUpperCase();
                return Typing.valueOf(name);
            });
        });
    }

    /**
     * Fetches the abilities of a pokemon
     *
     * @param pokemonName The name of the pokemon
     * @return A CompletableFuture containing the abilities
     */
    public static CompletableFuture<List<String>> fetchAbilities(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            JSONArray a = new JSONObject(data).getJSONArray("abilities");
            return processJsonArray(a, json -> json.getJSONObject("ability").getString("name"));
        });
    }

    /**
     * Fetches the stats of a pokemon
     *
     * @param pokemonName The name of the pokemon
     * @return A CompletableFuture containing the stats
     */
    public static CompletableFuture<Map<String, Integer>> fetchPokemonStats(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            JSONArray a = new JSONObject(data).getJSONArray("stats");
            Map<String, Integer> stats = new HashMap<>();

            for (int i = 0; i < a.length(); i++) {
                JSONObject obj = a.getJSONObject(i);
                String name = obj.getJSONObject("stat").getString("name");
                int baseStat = obj.getInt("base_stat");

                // Adding to the base-stat to get the right amount for 31Ivs at level 50
                if ("hp".equals(name)) stats.put(name, baseStat + 75);
                else stats.put(name, baseStat + 20);
            }
            return stats;
        });
    }

    /**
     * Generates and stores the sprites and animations for a specific Pokemon,
     * aligning them with the corresponding directory based on the Pokemon's name,
     * using the saveImage method.
     *
     * @param data The data of the pokemon
     * @param pokemonName The name of the pokemon
     */
    public static void createSprites(String data, String pokemonName) {
        JSONObject sprites = new JSONObject(data).getJSONObject("sprites");

        String front = sprites.getString("front_default");
        String icon = sprites.getJSONObject("versions")
                .getJSONObject("generation-viii")
                .getJSONObject("icons")
                .getString("front_default");

        CompletableFuture<Void> frontF = saveImage(front, "front", pokemonName, "png");
        CompletableFuture<Void> iconF = saveImage(icon, "icon", pokemonName, "png");

        JSONObject gifs = new JSONObject(data)
                .getJSONObject("sprites")
                .getJSONObject("other")
                .getJSONObject("showdown");

        CompletableFuture<Void> back = saveImage(gifs.getString("back_default"),
                "back", pokemonName, "gif");
        CompletableFuture<Void> frontGf = saveImage(gifs.getString("front_default"),
                "front", pokemonName, "gif");

        CompletableFuture.allOf(frontF, iconF, back, frontGf).join();
    }

    /**
     * Saves an image from a given URL to a specified location.
     * location -> src/main/resources/pokemon/{pokemonName}/{imageName}.{type}
     *
     * @param imageUrl The URL of the image
     * @param imageName The name of the image
     * @param pokemonName The name of the pokemon
     * @param type The type of the image (png, gif, etc.)
     * @return A CompletableFuture containing the image
     */
    private static CompletableFuture<Void> saveImage(String imageUrl, String imageName, String pokemonName, String type) {
        return CompletableFuture.runAsync(() -> {
            if (imageUrl == null || imageUrl.isEmpty()) {
                System.out.println("Image URL for " + imageName + " does not exist. . .");
                return;
            }
            HttpRequest request = buildRequest(imageUrl);
            try {
                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                if (response.statusCode() == 200) {
                    BufferedImage img = ImageIO.read(response.body());

                    String directoryPath = "src/main/resources/pokemon/" + pokemonName;
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        boolean created = directory.mkdirs();
                        if (!created) {
                            System.out.println("Could not create directory: " + directoryPath);
                            return;
                        }
                    }
                    String path = directoryPath + "/" + imageName + "." + type;
                    File file = new File(path);

                    ImageIO.write(img, type, file);
                    System.out.println("Saved: " + path);
                } else {
                    System.err.println("Failed to fetch image. HTTP Response Code: " + response.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        });
    }

    /**
     * Saves the abilities of a pokemon to the abilities.json file
     *
     * @param abilities The abilities of the pokemon, which might not be implemented yet
     */
    public static void saveAbilities(String... abilities) {
        JSONObject abilitiesJson = loadExisting();

        for (String name : abilities) {
            String description = fetchAbilityDescription(
                    Constants.POKE_API_ABILITY_URL + name + "/", name
            );
            if (!"Not Found".equals(description)) {
                abilitiesJson.put(name, clean(description));
            }
        }
        try (FileWriter file = new FileWriter(Constants.PATH_TO_ABILITIES_JSON)) {
            file.write(abilitiesJson.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches the description of an ability from the PokeAPI
     *
     * @param abilityUrl The URL of the ability
     * @param name The name of the ability
     * @return The description of the ability
     */
    private static String fetchAbilityDescription(final String abilityUrl, String name) {
        HttpRequest request = buildRequest(abilityUrl);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                JSONArray a = obj.getJSONArray("flavor_text_entries");
                for (int i = 0; i < a.length(); i++) {
                    JSONObject entry = a.getJSONObject(i);
                    String language = entry.getJSONObject("language").getString("name");

                    if ("en".equals(language)) {
                        return entry.getString("flavor_text");
                    }
                }
            } else {
                if (response.statusCode() == 404)  System.err.println("Fetching failed: " + name);
                else throw new IOException("HTTP Response Code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching description: " + e.getMessage());
        }
        return "Not Found";
    }

    /**
     * Loads the existing abilities from the abilities.json file
     *
     * @return The existing abilities
     */
    private static JSONObject loadExisting() {
        File file = new File(Constants.PATH_TO_ABILITIES_JSON);
        if (file.exists()) {
            try (FileReader reader = new FileReader(Constants.PATH_TO_ABILITIES_JSON)) {
                String content = new BufferedReader(reader)
                        .lines().collect(Collectors.joining("\n"));
                return new JSONObject(content);
            } catch (IOException e) {
                System.err.println("Failed to read file: " + e.getMessage());
            }
        }
        return new JSONObject();
    }

    /**
     * Processes a JSON array
     *
     * @param a The JSON array
     * @param processor The processor, which processes the JSON object
     * @return The results
     * @param <T> The type of the results
     */
    private static <T> List<T> processJsonArray(JSONArray a, Function<JSONObject, T> processor) {
        List<T> results = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            T result = processor.apply(a.getJSONObject(i));
            if (result != null) results.add(result);
        }
        return results;
    }

    /**
     * Builds a request
     *
     * @param url The URL of the request
     * @return The request
     */
    private static HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    /**
     * Cleans the description, to remove unnecessary characters
     *
     * @param description The description of an ability
     * @return The cleaned description
     */
    private static String clean(String description) {
        return description.replace("\n", " ")
                .replace("’", "'")
                .replace("\u00AD", "");
    }
}
