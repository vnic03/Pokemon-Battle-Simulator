package org.example.repositories.api;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PokeApiClient {

    public static String fetchData(String pokemonName) {
        final String baseUrl = "https://pokeapi.co/api/v2/pokemon/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + pokemonName.toLowerCase()))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("HTTP Response Code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public static Map<String, Integer> fetchPokemonStats(String pokemonName) {
        JSONObject obj = new JSONObject(fetchData(pokemonName));
        JSONArray a = obj.getJSONArray("stats");
        Map<String, Integer> stats = new HashMap<>();

        for (int i = 0; i < a.length(); i++) {
            JSONObject statObj = a.getJSONObject(i);
            String name = statObj.getJSONObject("stat").getString("name");
            int baseStat = statObj.getInt("base_stat");

            // Adding to the base-stat to get the right amount for 31Ivs at level 50
            if ("hp".equals(name)) stats.put(name, baseStat + 75);
            else stats.put(name, baseStat + 20);
        }
        return stats;
    }

    public static void createSprites(String data, String pokemonName) {
        JSONObject obj = new JSONObject(data);
        JSONObject sprites = obj.getJSONObject("sprites");

        String back = sprites.getString("back_default");
        String front = sprites.getString("front_default");

        String icon = sprites.getJSONObject("versions")
                .getJSONObject("generation-viii")
                .getJSONObject("icons")
                .getString("front_default");

        saveImage(back, "back", pokemonName);
        saveImage(front, "front", pokemonName);
        saveImage(icon, "icon", pokemonName);
    }

    private static void saveImage(String imageUrl, String imageName, String pokemonName) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            System.out.println("Image URL for " + imageName + " does not exist. . .");
            return;
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .GET()
                .build();
        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == 200) {
                BufferedImage img = ImageIO.read(response.body());

                final String directoryPath = "src/main/resources/pokemon/" + pokemonName;
                File directory = new File(directoryPath);

                if (!directory.exists()) {
                    boolean created = directory.mkdirs();
                    if (!created) {
                        System.out.println("Could not create directory: " + directoryPath);
                        return;
                    }
                }
                final String path = directoryPath + "/" + imageName + ".png";
                File file = new File(path);

                ImageIO.write(img, "png", file);
                System.out.println("Saved: " + path);

            } else {
                System.err.println("Failed to fetch image. HTTP Response Code: " + response.statusCode());
            }
        }  catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void saveAbilities(String... abilities) {
        final String path = "src/main/java/org/example/pokemon/ability/abilities.json";
        JSONObject abilitiesJson = loadExisting(path);

        for (String name : abilities) {
            String description = fetchAbilityDescription(
                    "https://pokeapi.co/api/v2/ability/" + name + "/", name);
            if (!"Not Found".equals(description)) {
                abilitiesJson.put(name, clean(description));
            }
        }
        try (FileWriter file = new FileWriter(path)) {
            file.write(abilitiesJson.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fetchAbilityDescription(final String abilityUrl, String name) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(abilityUrl))
                .GET()
                .build();
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

    private static JSONObject loadExisting(String path) {
        File file = new File(path);
        if (file.exists()) {
            try (FileReader reader = new FileReader(path)) {
                String content = new BufferedReader(reader)
                        .lines().collect(Collectors.joining("\n"));
                return new JSONObject(content);
            } catch (IOException e) {
                System.err.println("Failed to read file: " + e.getMessage());
            }
        }
        return new JSONObject();
    }

    private static String clean(String description) {
        return description.replace("\n", " ")
                .replace("’", "'")
                .replace("\u00AD", "");
    }
}
