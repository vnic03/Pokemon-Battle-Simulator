package org.example.pokemon.ability_effects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

public class NoGuardEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {}

    @Override
    public void applyEffect(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        if (user.hasActiveAbility("No Guard")) {
            move.setAccuracy(100);
            // einbauen, dass auch moves auf das pokemon 100 % treffen
        }
    }
}
