package org.example.api;

import org.example.Constants;
import org.example.ResourceManager;
import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.repositories.MovesRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * The PokeApiClient class is responsible for fetching data from the PokeAPI.
 * It fetches data for a specific Pokemon, such as the typing, abilities, stats, sprites, etc.
 */
public class PokeApiClient extends ApiClient {

    static {
        setBaseUrl(PokeApiClient.class.getName(), Constants.POKE_API_BASE_URL);
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

            return processJsonArray(a, json -> Typing.valueOf(
                    json.getJSONObject("type").getString("name").toUpperCase()
            ));
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
     * Retrieves a list of all moves that a specific Pokémon can learn
     *
     * @param pokemonName The name of the Pokemon
     * @return A CompletableFuture that, returns a list of all moves learnable by the Pokémon.
     */
    public static CompletableFuture<List<Moves>> fetchPokemonMoves(String pokemonName) {
        return fetchData(pokemonName).thenApply(data -> {
            JSONArray a = new JSONObject(data).getJSONArray("moves");
            return processJsonArray(a, json -> MovesRepository.getMoveByName(
                    MovesRepository.format(json.getJSONObject("move").getString("name"))
            ));
        });
    }

    /**
     * Generates and stores the sprites and animations for a specific Pokemon,
     * aligning them with the corresponding directory based on the Pokemon's name,
     * using the saveResource method.
     *
     * @param pokemonName The name of the pokemon
     */
    public static void createSprites(String pokemonName) {
        String data = fetchData(pokemonName).join();
        JSONObject obj = new JSONObject(data);
        JSONObject sprites = obj.getJSONObject("sprites");

        String front = sprites.getString("front_default");
        String icon = sprites.getJSONObject("versions")
                .getJSONObject("generation-viii")
                .getJSONObject("icons")
                .getString("front_default");

        CompletableFuture<Void> frontF = saveResource(pokemonName, front, "front", "png");
        CompletableFuture<Void> iconF = saveResource(pokemonName, icon,  "icon", "png");

        JSONObject gifs = sprites
                .getJSONObject("other")
                .getJSONObject("showdown");

        CompletableFuture<Void> back = saveResource(pokemonName,
                gifs.getString("back_default"), "back", "gif");

        CompletableFuture<Void> frontGf = saveResource(pokemonName,
                gifs.getString("front_default"), "front", "gif");

        CompletableFuture.allOf(frontF, iconF, back, frontGf).join();
    }

    /**
     * Generates and stores the cries for each pokemon at the same location
     * where also the sprites/animation are stored using the saveResource method.
     * The Api returns .ogg files which need to be converted into mp3 files using
     * the convert-method from the ResourceManger class
     *
     * @param pokemonName The name of the pokemon
     */
    public static void createCry(String pokemonName) {
        String data = fetchData(pokemonName).join();
        String cry = new JSONObject(data).getJSONObject("cries").getString("latest");

        CompletableFuture<Void> cryF = saveResource(pokemonName, cry, "cry", "ogg");
        cryF.join();

        ResourceManager.convertOggToMp3(pokemonName);
    }

    /**
     * Saves a resource (images, audios etc.) from a given URL to a specified location.
     * location -> src/main/resources/pokemon/{pokemonName}/{resourceName}.{type}
     *
     * @param url The URL of the resource
     * @param resourceName The name of the resource
     * @param pokemonName The name of the pokemon
     * @param type The type of the image (png, gif, etc.)
     * @return A CompletableFuture containing the image
     */
    private static CompletableFuture<Void> saveResource(
            String pokemonName, String url, String resourceName, String type)
    {
        return CompletableFuture.runAsync(() -> {
            if (url == null || url.isEmpty()) {
                LOGGER.warn("Image URL for {} does not exist.", resourceName);
                return;
            }
            HttpRequest request = buildRequest(url);
            try {
                HttpResponse<InputStream> response = CLIENT.send(
                        request, HttpResponse.BodyHandlers.ofInputStream()
                );
                int status = response.statusCode();

                if (status == Constants.HTTP_STATUS_OK) {
                    String directoryPath = Constants.PATH_TO_POKEMON_RESOURCE + pokemonName;
                    File directory = new File(directoryPath);

                    if (!directory.exists()) {
                        boolean created = directory.mkdirs();
                        if (!created) {
                            LOGGER.error("Could not create directory: {}", directoryPath);
                            return;
                        }
                    }
                    String path = directoryPath + "/" + resourceName + "." + type;
                    File file = new File(path);

                    try (InputStream is = response.body();
                         FileOutputStream os = new FileOutputStream(file)
                    ) {
                        byte[] buffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
                        int bytesRead;
                        while((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                    LOGGER.info("Saved: {}", path);
                } else {
                    LOGGER.error("Failed to fetch image for {}. HTTP-Response-Code: {}", resourceName, status);
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Error downloading resource for {}: {}", resourceName, e.getMessage(), e);
                if (e instanceof InterruptedException) Thread.currentThread().interrupt();
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
                LOGGER.info("Ability {} added with description.", name);
            }
        }
        try (FileWriter file = new FileWriter(Constants.PATH_TO_ABILITIES_JSON)) {
            file.write(abilitiesJson.toString(4));
            file.flush();
        } catch (IOException e) {
            LOGGER.error("Failed to save abilities to {}: {}",
                    Constants.PATH_TO_ABILITIES_JSON, e.getMessage(), e);
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
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status == Constants.HTTP_STATUS_OK) {
                JSONArray a = new JSONObject(response.body()).getJSONArray("flavor_text_entries");

                for (int i = 0; i < a.length(); i++) {
                    JSONObject entry = a.getJSONObject(i);
                    String language = entry.getJSONObject("language").getString("name");

                    if ("en".equals(language))  return entry.getString("flavor_text");
                }

            } else {
                if (status == Constants.HTTP_STATUS_NOT_FOUND) {
                    LOGGER.warn("Fetching failed for ability {}: HTTP status- Not Found", name);
                } else {
                    LOGGER.error("Unexpected HTTP response code {} for ability {}", status, name);
                    throw new IOException("HTTP Response Code: " + status);                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error fetching description for ability {}: {}", name, e.getMessage(), e);
            if (e instanceof InterruptedException) Thread.currentThread().interrupt();
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
                LOGGER.error("Failed to load abilities from {}: {}",
                        Constants.PATH_TO_ABILITIES_JSON, e.getMessage(), e);
            }
        }
        return new JSONObject();
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
