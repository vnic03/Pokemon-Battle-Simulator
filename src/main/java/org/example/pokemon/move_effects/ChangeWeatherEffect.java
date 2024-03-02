package org.example.pokemon.move_effects;

import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public class ChangeWeatherEffect implements MoveEffect {

    private final Weather weatherToChange;

    public ChangeWeatherEffect(Weather changeWeather) {
        this.weatherToChange = changeWeather;
    }

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        result.setCurrentWeather(weatherToChange);
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
