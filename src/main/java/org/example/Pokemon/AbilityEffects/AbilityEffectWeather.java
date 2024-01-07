package org.example.Pokemon.AbilityEffects;

import org.example.Battle.Weather;
import org.example.Pokemon.Pokemon;

public interface AbilityEffectWeather extends AbilityEffect {
    void applyEffect(Pokemon pokemon, Weather currentWeather);
}
