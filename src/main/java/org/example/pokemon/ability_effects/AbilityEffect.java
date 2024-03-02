package org.example.pokemon.ability_effects;

import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public interface AbilityEffect {
    void applyEffect(Pokemon pokemon, BattleRoundResult result);
}
