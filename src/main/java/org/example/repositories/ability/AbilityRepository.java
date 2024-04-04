package org.example.repositories.ability;

import org.example.pokemon.Ability;
import org.example.pokemon.effects.ability_effects.*;
import org.example.repositories.api.PokeApiClient;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbilityRepository {

    private final Map<String, Ability> abilities;

    private final Map<String, Class<? extends AbilityEffect>> special;

    public AbilityRepository() {
        abilities = new HashMap<>();
        special = new HashMap<>();
        initSpecial();
        init();
    }

    public static void main(String[] args) {
        PokeApiClient.saveAbilities("");
    }

    public Ability getAbility(String name) {
        return abilities.get(name);
    }

    public void init() {
        final Map<String, String> data = loadFromJson(
                "src/main/java/org/example/repositories/ability/abilities.json");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String name = entry.getKey().replace("-", " ");
            String description = entry.getValue();
            AbilityEffect effect = getEffect(name);

            if (!abilities.containsKey(name)) {
                abilities.put(name, new Ability(name, description, effect));

            } else System.out.println("Ability " + name + " already exists.");
        }
    }

    private AbilityEffect getEffect(String name) {
        if (special.containsKey(name)) {
            try {
                return special.get(name).getConstructor().newInstance();

            } catch (Exception e) { e.printStackTrace(); }
        }
        String className = Arrays.stream(name.split("[ \\-]"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining("")) + "Effect";
        try {
            Class<?> clazz = Class.forName("org.example.pokemon.effects.ability_effects." + className);

            if (AbilityEffect.class.isAssignableFrom(clazz)) {
                return (AbilityEffect) clazz.getConstructor().newInstance();
            }
        } catch (Exception e) {
            System.err.println("Unable to make effect for: " + name + ". " + e.getMessage());
        }
        return null;
    }

    public Map<String, String> loadFromJson(String path) {
        Map<String, String> abilities = new HashMap<>();
        try {
            String text = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject obj = new JSONObject(text);

            for(String key : obj.keySet()) {
                abilities.put(key, obj.getString(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abilities;
    }

    private void initSpecial() {
        spCase(List.of("blaze", "overgrow", "torrent", "swarm"), StarterEffect.class);
        spCase(List.of("battle armor", "shell armor"), PreventCritsEffect.class);
        spCase(List.of("filter", "solid rock"), DamageReductionEffect.class);
        spCase(List.of("drizzle", "drought", "sand stream"), WeatherEffect.class);
    }

    private void spCase(List<String> names, Class<? extends AbilityEffect> effect) {
        for (String name : names) { special.put(name, effect); }
    }
}
