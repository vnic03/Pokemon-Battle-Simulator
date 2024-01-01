package org.example.Pokemon.Effects;

import org.example.Battle.BattleSimulator;
import org.example.Battle.Weather;
import org.example.Pokemon.Pokemon;

public class ChangeWeatherEffect implements MoveEffect {

    private final Weather weatherToChange;


    public ChangeWeatherEffect(Weather changeWeather) {
        this.weatherToChange = changeWeather;
    }

    @Override
    public void apply(Pokemon user, Pokemon target) {

        BattleSimulator.getInstance().setWeather(weatherToChange);

    }
}
