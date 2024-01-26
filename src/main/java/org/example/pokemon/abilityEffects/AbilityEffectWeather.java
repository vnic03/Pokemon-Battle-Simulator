package org.example.pokemon.abilityEffects;

import org.example.battle.Weather;
import org.example.pokemon.Pokemon;

public interface AbilityEffectWeather extends AbilityEffect {
    void applyEffect(Pokemon pokemon, Weather currentWeather);
}
