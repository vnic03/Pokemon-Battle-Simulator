package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public interface AbilityEffectWithMove extends AbilityEffect{
    void applyEffect(Pokemon pokemon, Moves move, BattleRoundResult result);
}
