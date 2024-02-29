package org.example.pokemon.abilityEffects;

import org.example.screens.battle.Weather;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class ChlorophyllEffect implements AbilityEffectWeather {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
    }

    @Override
    public void applyEffect(Pokemon pokemon, Weather currentWeather) {
        if (currentWeather == Weather.SUN) {
            int speed = pokemon.getStats().getSpeed();
            pokemon.getStats().setSpeed(speed * 2);
        } else {
            pokemon.resetStats();
        }
    }
}
