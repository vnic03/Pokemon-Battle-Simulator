package org.example;

import org.example.repositories.api.PokeApiClient;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private final static String POKEMON = "";

    private final static List<String> abilities = List.of("");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 -> Sprites \n2 -> Abilities \n3 -> Stats");

        switch (scanner.nextInt()) {
            case 1: createSprites(); break;
            case 2: createAbilities(); break;
            case 3: createStats(); break;
        }
        scanner.close();
    }

    private static void createSprites() {
        String data = PokeApiClient.fetchData(POKEMON);
        PokeApiClient.createSprites(data, POKEMON);
    }

    private static void createAbilities() {
        PokeApiClient.saveAbilities(abilities.toArray(new String[0]));

        try {
            final String groovy = "/opt/groovy/bin/groovy";
            final String script = "scripts/UpdateAbilityEnum.groovy";
            final String json = "src/main/java/org/example/pokemon/ability/abilities.json";
            final String ability = "src/main/java/org/example/pokemon/ability/Ability.java";

            ProcessBuilder builder = new ProcessBuilder(groovy, script, json, ability);
            Process process = builder.start();

            if (process.waitFor() == 0) System.out.println("Groovy-Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createStats() {
        Map<String, Integer> stats = PokeApiClient.fetchPokemonStats(POKEMON);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        final String scriptPath = "scripts/FillStats.groovy";
        final String javaPath = "src/main/java/org/example/repositories/PokemonRepository.java";

        try {
            String script = new String(Files.readAllBytes(Paths.get(scriptPath)));

            Bindings bindings = engine.createBindings();
            bindings.put("pokemonName", POKEMON);
            bindings.put("stats", stats);
            bindings.put("path", javaPath);

            engine.eval(script, bindings);
        } catch (IOException e) {
            System.err.println("Error while loading script: " + e.getMessage());
            e.printStackTrace();
        } catch (ScriptException e) {
            System.err.println("Error while running script: " + e.getMessage());
            e.printStackTrace();
        }
    }
}