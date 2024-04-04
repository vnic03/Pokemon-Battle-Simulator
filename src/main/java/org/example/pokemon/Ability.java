package org.example.pokemon;

import org.example.pokemon.ability.AbilityEffect;

public record Ability
        (String name, String description, AbilityEffect effect)
{

    @Override
    public String toString() {
        return name;
    }
}
