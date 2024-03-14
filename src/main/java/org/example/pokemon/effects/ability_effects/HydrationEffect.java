package org.example.pokemon.effects.ability_effects;

import org.example.pokemon.Pokemon;
import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

public class HydrationEffect implements AbilityEffect {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
        if (result.getCurrentWeather() == Weather.RAIN) {
            pokemon.clearStatusCondition();
        }
    }
}