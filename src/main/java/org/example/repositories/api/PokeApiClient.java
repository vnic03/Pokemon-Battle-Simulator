package org.example.repositories.api;

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
import java.util.stream.Collectors;

public class PokeApiClient {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static CompletableFuture<String> fetchData(String pokemonName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constants.POKE_API_BASE_URL + pokemonName.toLowerCase()))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return response.body();
                    } else {
                        throw new RuntimeException("HTTP Response Code: " + response.statusCode());
                    }
                }).exceptionally(e -> "Error: " + e.getMessage());
    }

    public static CompletableFuture<Integer> fetchPokeDexNumber(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            JSONObject obj = new JSONObject(data);
            return obj.getInt("id");
        });
    }

    public static CompletableFuture<List<Typing>> fetchTyping(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            List<Typing> types = new ArrayList<>();
            JSONObject obj = new JSONObject(data);
            JSONArray a = obj.getJSONArray("types");

            for (int i = 0; i < a.length(); i++) {
                JSONObject type = a.getJSONObject(i).getJSONObject("type");
                String name = type.getString("name").toUpperCase();
                try {
                    types.add(Typing.valueOf(name));
                } catch (IllegalArgumentException e) {
                    System.err.println("Typing not found for: " + name);
                }
            }
            return types;
        });
    }

    public static CompletableFuture<Map<String, Integer>> fetchPokemonStats(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            JSONObject obj = new JSONObject(data);
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
        });
    }

    public static CompletableFuture<List<String>> fetchAbilities(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            List<String> abilities = new ArrayList<>();
            JSONObject obj = new JSONObject(data);
            JSONArray a = obj.getJSONArray("abilities");

            for (int i = 0; i < a.length(); i++) {
                String ability = a.getJSONObject(i).getJSONObject("ability").getString("name");
                abilities.add(ability);
            }
            return abilities;
        });
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

        CompletableFuture<Void> backF= saveImage(back, "back", pokemonName);
        CompletableFuture<Void> frontF = saveImage(front, "front", pokemonName);
        CompletableFuture<Void> iconF = saveImage(icon, "icon", pokemonName);

        CompletableFuture.allOf(backF, frontF, iconF).join();
    }

    private static CompletableFuture<Void> saveImage(String imageUrl, String imageName, String pokemonName) {
        return CompletableFuture.runAsync(() -> {
            if (imageUrl == null || imageUrl.isEmpty()) {
                System.out.println("Image URL for " + imageName + " does not exist. . .");
                return;
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .GET()
                    .build();
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
                    String path = directoryPath + "/" + imageName + ".png";
                    File file = new File(path);

                    ImageIO.write(img, "png", file);
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

    private static String fetchAbilityDescription(final String abilityUrl, String name) {
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

    private static String clean(String description) {
        return description.replace("\n", " ")
                .replace("’", "'")
                .replace("\u00AD", "");
    }
}
