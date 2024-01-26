package org.example.pokemon;

import org.example.pokemon.abilityEffects.AbilityEffect;

public class Ability {

    private String name;
    private String description;

    private AbilityEffect effect;

    public Ability(String name, String description, AbilityEffect effect) {
        this.name = name;
        this.description = description;
        this.effect = effect;
    }

    public void activate(Pokemon pokemon) {
        effect.applyEffect(pokemon);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AbilityEffect getEffect() {
        return effect;
    }

    public void setEffect(AbilityEffect effect) {
        this.effect = effect;
    }


    @Override
    public String toString() {
        return name;
    }
}
