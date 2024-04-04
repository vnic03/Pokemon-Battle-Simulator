package org.example.pokemon.ability;

import org.example.battle.Weather;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

@FunctionalInterface
public interface AbilityEffect {
    void applyEffect(
            Pokemon user, Pokemon target,
            Moves move,
            Weather weather,
            BattleRoundResult result
    );
}
