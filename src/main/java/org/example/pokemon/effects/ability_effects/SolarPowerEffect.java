package org.example.pokemon.effects.ability_effects;

import org.example.battle.Weather;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class SolarPowerEffect implements AbilityEffectWeather {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
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
