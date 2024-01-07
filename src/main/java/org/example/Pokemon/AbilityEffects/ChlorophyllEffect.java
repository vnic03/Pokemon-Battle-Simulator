package org.example.Pokemon.AbilityEffects;

import org.example.Battle.Weather;
import org.example.Pokemon.Pokemon;

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
