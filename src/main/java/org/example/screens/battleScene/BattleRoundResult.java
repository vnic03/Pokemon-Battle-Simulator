package org.example.screens.battleScene;

import org.example.battle.Weather;

public class BattleRoundResult {

    private String message;
    private int damageDealt;
    private boolean wasSuccessful;
    private boolean didFaint;
    private Weather currentWeather;
    private double effectiveness = 1.0;

    public BattleRoundResult(
            String message, int damageDealt, boolean wasSuccessful, boolean didFaint, Weather weather)
    {
        this.message = message;
        this.damageDealt = damageDealt;
        this.wasSuccessful = wasSuccessful;
        this.didFaint = didFaint;
        this.currentWeather = weather;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public boolean wasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public boolean didFaint() {
        return didFaint;
    }

    public void setDidFaint(boolean didFaint) {
        this.didFaint = didFaint;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public double getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
    }
}
