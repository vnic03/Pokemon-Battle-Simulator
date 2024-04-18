package org.example;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.api.MoveApiClient;
import org.example.pokemon.Typing;
import org.example.api.PokeApiClient;
import org.json.JSONException;
import org.json.JSONObject;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.example.repositories.PokemonRepository.countPokemon;



/**
 * This class is used to automatically create Pokemon, Abilities, Cries and Sprites/Animations.
 * For every Pokemon-Name in the list, it will write the Pokemon to the PokemonRepository class.
 * It can also create the sprites/animations for each Pokemon.
 * For every Ability-Name in the list, it will add the name and description to the abilities.json file.
 * Additionally, if there is a new Ability in the json file,
 * it will automatically update the Ability enum in the Ability class.
 *
 */
public class PokeFactory {

    private static final List<String> POKEMON = List.of("");

    private static final List<String> ABILITIES = List.of("");

    private static final List<String> MOVES = List.of("");

    private static final ScriptEngineManager MANAGER = new ScriptEngineManager();
    private static final ScriptEngine ENGINE = MANAGER.getEngineByName("groovy");

    private static final Logger LOGGER = LogManager.getLogger(PokeFactory.class);

    /**
     * Case 1 -> Saves the sprites/animations and cries for each Pokemon and writes the Pokemon to the PokemonRepository.
     * Case 2 -> Saves the sprites and animations for each Pokemon.
     * Case 3 -> Writes each Pokemon to the PokemonRepository.
     * Case 4 -> Writes the Abilities to the abilities.json file and updates the Ability enum in the Ability class.
     * Case 5 -> Writes each Move to the MovesRepository.
     * Case 6 -> Saves the cries for each Pokemon
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.setProperty(Constants.LOGGER_PROPERTY, Constants.LOGGER_CONFIG_PATH);
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("""
                    1 -> Pokemon & Sprites & Cries\
                    \s
                    2 -> Sprites\
                    \s
                    3 -> Pokemon\
                    \s
                    4 -> Abilities\
                    \s
                    5 -> Moves\
                    \s
                    6 -> Cries
                    """);

            switch (scanner.nextInt()) {
                case 1:
                    createSprites();
                    createCries();
                    createPokemon();
                    break;
                case 2: createSprites(); break;
                case 3: createPokemon(); break;
                case 4: createAbilities(); break;
                case 5: createMoves(); break;
                case 6: createCries(); break;
                default: break;
            }
        }
        System.out.printf("\nAmount of Pokemon: %d%n", countPokemon() + POKEMON.size() + (POKEMON.getFirst().isEmpty() ? -1 : 0));
    }

    private static void createSprites() {
        POKEMON.forEach(name -> {
            try { PokeApiClient.createSprites(name); }
            catch (Exception e) { LOGGER.error("Error creating sprite for {}: {}", name, e.getMessage(), e); }
        });
        LOGGER.info("Sprites Done!");
    }

    private static void createPokemon() {
        POKEMON.forEach(name -> {
            Integer dex = PokeApiClient.fetchPokeDexNumber(name).join();
            List<Typing> typings = PokeApiClient.fetchTyping(name).join();
            Map<String, Integer> stats = PokeApiClient.fetchPokemonStats(name).join();
            List<String> abilities = PokeApiClient.fetchAbilities(name).join();

            List<String> gameAbilities = GameAbilities(abilities);
            String typingString = Typing.format(typings);

            Bindings bindings = ENGINE.createBindings();
            bindings.put("path", Constants.PATH_TO_POKEMON_REPOSITORY);
            bindings.put("pokemonName", name);
            bindings.put("dex", dex);
            bindings.put("typing", typingString);
            bindings.put("stats", stats);
            bindings.put("abilities", gameAbilities);

            runScript(Constants.SCRIPT_PATH_POKEMON, bindings);
        });
        LOGGER.info("Pokemon Done!");
    }

    private static void createAbilities() {
        PokeApiClient.saveAbilities(ABILITIES.toArray(new String[0]));

        Bindings bindings = ENGINE.createBindings();
        bindings.put("abilities", ABILITIES);
        bindings.put("path", Constants.PATH_TO_ABILITY_RECORD);

        runScript(Constants.SCRIPT_PATH_ABILITIES, bindings);
    }

    private static void createMoves() {
        MOVES.forEach(name -> {
            MoveApiClient.MoveData data = MoveApiClient.fetchMove(name).join();

            Bindings bindings = ENGINE.createBindings();
            bindings.put("path", Constants.PATH_TO_MOVES_REPOSITORY);
            bindings.put("moveName", name);
            bindings.put("data", data);

            runScript(Constants.SCRIPT_PATH_MOVES, bindings);
        });
        LOGGER.info("Moves Done!");
    }

    private static void createCries() {
        POKEMON.forEach(name -> {
            try { PokeApiClient.createCry(name); }
            catch (Exception e) { LOGGER.error("Error creating cry for {}: {}", name, e.getMessage(), e); }
        });
        LOGGER.info("Cries Done!");
    }

    private static void runScript(String scriptPath, Bindings bindings) {
        try {
            String script = new String(Files.readAllBytes(Paths.get(scriptPath)));
            synchronized (ENGINE) {
                ENGINE.eval(script, bindings);
            }
        } catch (IOException | ScriptException e) {
            LOGGER.error("Error executing script at {}: {}", scriptPath, e.getMessage(), e);
        }
    }

    private static List<String> GameAbilities(List<String> apiAbilities) {
        List<String> gameAbilities = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(Constants.PATH_TO_ABILITIES_JSON)));
            JSONObject abilitiesJson = new JSONObject(content);

            for (String name : apiAbilities) {
                String key = name.toLowerCase().replaceAll(" ", "-");
                if (abilitiesJson.has(key)) {
                    gameAbilities.add(name.toUpperCase().replaceAll("-", "_"));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read abilities from {}: {}", Constants.PATH_TO_ABILITIES_JSON, e.getMessage(), e);
        } catch (JSONException e) {
            LOGGER.error("JSON parsing error when processing abilities: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error when processing game abilities: {}", e.getMessage(), e);
        }
        return gameAbilities;
    }
}