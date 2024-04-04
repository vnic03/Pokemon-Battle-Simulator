package org.example.pokemon.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectHandler {

    private final Map<String, AbilityEffect> effects = new HashMap<>();

    public void registerEffect(String name, AbilityEffect effect) {
        effects.put(name, effect);
    }

    public void registerEffect(List<String> names, AbilityEffect effect) {
        for (String name : names) {
            effects.put(name, effect);
        }
    }

    public AbilityEffect getEffect(String name) {
        return effects.get(name);
    }
}
