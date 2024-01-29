package org.example.pokemon.abilityEffects;

import org.example.screens.battle.Weather;
import org.example.pokemon.Pokemon;

public class SolarPowerEffect implements AbilityEffectWeather {

    @Override
    public void applyEffect(Pokemon pokemon) {
    }

    @Override
    public void applyEffect(Pokemon pokemon, Weather currentWeather) {

        if (currentWeather == Weather.SUN) {
            int originalSpAtk = pokemon.getStats().getSpecialAttack();
            pokemon.getStats().setSpecialAttack((int) (originalSpAtk * 1.5));

            // passive damage implemented in Battle Simulator
        }
    }
}
