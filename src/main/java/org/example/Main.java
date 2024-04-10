package org.example;

import org.example.pokemon.Typing;
import org.example.repositories.api.PokeApiClient;
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
import java.util.concurrent.CompletableFuture;

import static org.example.repositories.PokemonRepository.countPokemon;

public class Main {

    private final static List<String> POKEMON = List.of("");

    private final static List<String> ABILITIES = List.of("");

    private final static ScriptEngineManager MANAGER = new ScriptEngineManager();
    private final static ScriptEngine ENGINE = MANAGER.getEngineByName("groovy");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("""
                    1 -> Pokemon & Sprites\
                    \s
                    2 -> Sprites \
                    \s
                    3 -> Pokemon\
                    \s
                    4 -> Abilities""");

            switch (scanner.nextInt()) {
                case 1:
                    createSprites();
                    createPokemon();
                    break;
                case 2: createSprites(); break;
                case 3: createPokemon(); break;
                case 4: createAbilities();break;
                default: break;
            }
        }
        System.out.printf("\nAmount of Pokemon: %d%n", countPokemon() + POKEMON.size() + (POKEMON.getFirst().isEmpty() ? -1 : 0));
    }

    private static void createSprites() {
        List<CompletableFuture<Void>> sprites = POKEMON.stream()
                .map(name -> PokeApiClient.fetchData(name)
                        .thenAccept(data -> PokeApiClient.createSprites(data, name)))
                .toList();
        CompletableFuture.allOf(sprites.toArray(new CompletableFuture[0])).join();
        System.out.println("Sprites Done!");
    }

    private static void createPokemon() {
        final String scriptPath = "scripts/WritePokemon.groovy";

        POKEMON.forEach(name -> {
            Integer dex = PokeApiClient.fetchPokeDexNumber(name).join();
            List<Typing> typings = PokeApiClient.fetchTyping(name).join();
            Map<String, Integer> stats = PokeApiClient.fetchPokemonStats(name).join();
            List<String> abilities = PokeApiClient.fetchAbilities(name).join();

            List<String> gameAbilities = getGameAbilities(abilities);
            try {
                String typingString = Typing.format(typings);
                String script = new String(Files.readAllBytes(Paths.get(scriptPath)));

                Bindings bindings = ENGINE.createBindings();
                bindings.put("path", Constants.PATH_TO_POKEMON_REPOSITORY);
                bindings.put("pokemonName", name);
                bindings.put("dex", dex);
                bindings.put("typing", typingString);
                bindings.put("stats", stats);
                bindings.put("abilities", gameAbilities);

                synchronized (ENGINE) {
                    System.out.println("Writing " + name + " to file...");
                    ENGINE.eval(script, bindings);
                }
            } catch (IOException | ScriptException e) {
                System.err.println("Error processing " + name + ": " + e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("Pokemon Done!");
    }

    private static void createAbilities() {
        PokeApiClient.saveAbilities(ABILITIES.toArray(new String[0]));

        final String scriptPath = "scripts/UpdateAbilityEnum.groovy";
        try {
            String script = new String(Files.readAllBytes(Paths.get(scriptPath)));

            Bindings bindings = ENGINE.createBindings();
            bindings.put("abilities", ABILITIES);
            bindings.put("path", Constants.PATH_TO_ABILITY_RECORD);

            synchronized (ENGINE) {
                ENGINE.eval(script, bindings);
                System.out.println("Abilities Done!");
            }
        } catch (IOException | ScriptException  e) {
            e.printStackTrace();
        }
    }

    private static List<String> getGameAbilities(List<String> apiAbilities) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameAbilities;
    }
}