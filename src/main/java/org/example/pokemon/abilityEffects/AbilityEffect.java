package org.example.pokemon.abilityEffects;

import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public interface AbilityEffect {
    void applyEffect(Pokemon pokemon, BattleRoundResult result);
}
