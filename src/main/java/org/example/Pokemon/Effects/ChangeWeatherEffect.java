package org.example.Pokemon.Effects;

import org.example.Battle.BattleSimulator;
import org.example.Battle.Weather;
import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Pokemon;

public class ChangeWeatherEffect implements MoveEffect {

    private final Weather weatherToChange;


    public ChangeWeatherEffect(Weather changeWeather) {
        this.weatherToChange = changeWeather;
    }

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        BattleSimulator.getInstance().setWeather(weatherToChange);

    }

    @Override
    public int getPriority() {
        return 0;
    }
}
