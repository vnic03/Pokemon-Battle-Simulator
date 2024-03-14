package org.example.pokemon;

import org.example.pokemon.effects.ability_effects.AbilityEffect;
import org.example.screens.battleScene.BattleRoundResult;

public record Ability
        (String name, String description, AbilityEffect effect)
{

    public void activate(Pokemon pokemon, BattleRoundResult result) {
        effect.applyEffect(pokemon, result);
    }

    @Override
    public String toString() {
        return name;
    }
}
