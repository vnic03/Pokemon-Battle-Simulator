package org.example.pokemon.ability;

import org.example.pokemon.Pokemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectHandler {

    private final Map<Ability.Name, AbilityEffect> effects = new HashMap<>();

    private final Map<Ability.Name, Boolean> states = new HashMap<>();

    private final Pokemon pokemon;

    public EffectHandler(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public EffectHandler() {
        this.pokemon = null;
    }

    public void setAbilityState(Ability.Name ability, boolean isActive) {
        states.put(ability, isActive);
    }

    public boolean getAbilityState(Ability.Name ability) {
        return states.getOrDefault(ability, false);
    }

    public void registerEffect(Ability.Name name, AbilityEffect effect) {
        effects.put(name, effect);
    }

    public void registerEffect(List<Ability.Name> names, AbilityEffect effect) {
        for (Ability.Name name : names) {
            effects.put(name, effect);
        }
    }

    public AbilityEffect getEffect(Ability.Name name) {
        return effects.get(name);
    }

    public void activateThickFat(boolean activate) {
        setAbilityState(Ability.Name.THICK_FAT, activate);
    }

    public boolean isThickFatActive() {
        return getAbilityState(Ability.Name.THICK_FAT);
    }

    public void activateGuts(boolean activate) {
        setAbilityState(Ability.Name.GUTS, activate);
    }

    public boolean isGutsActive() {
        return getAbilityState(Ability.Name.GUTS);
    }
}
