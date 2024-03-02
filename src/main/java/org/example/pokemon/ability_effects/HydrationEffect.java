package org.example.pokemon.ability_effects;

import org.example.pokemon.Pokemon;
import org.example.screens.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

public class HydrationEffect implements AbilityEffect {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
        if (result.getCurrentWeather() == Weather.RAIN) {
            pokemon.clearStatusCondition();
        }
    }
}