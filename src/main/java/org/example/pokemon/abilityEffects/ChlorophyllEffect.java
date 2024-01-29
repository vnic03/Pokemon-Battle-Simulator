package org.example.pokemon.abilityEffects;

import org.example.screens.battle.Weather;
import org.example.pokemon.Pokemon;

public class ChlorophyllEffect implements AbilityEffectWeather {

    @Override
    public void applyEffect(Pokemon pokemon) {
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
