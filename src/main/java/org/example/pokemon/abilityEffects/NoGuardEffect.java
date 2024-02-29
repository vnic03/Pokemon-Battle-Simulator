package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class NoGuardEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {}

    @Override
    public void applyEffect(Pokemon pokemon, Moves move, BattleRoundResult result) {
        if (pokemon.hasActiveAbility("No Guard")) {
            move.setAccuracy(100);
            // einbauen, dass auch moves auf das pokemon 100 % treffen
        }
    }
}
