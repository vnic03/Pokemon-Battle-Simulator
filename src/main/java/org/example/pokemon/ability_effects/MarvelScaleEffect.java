package org.example.pokemon.ability_effects;

import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class MarvelScaleEffect implements AbilityEffect {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {

        if (pokemon.hasStatusCondition()) {
            pokemon.getStats().setDefense((int) (pokemon.getStats().getDefense() * 1.5));
        } else {
            pokemon.resetStats();
        }
    }
}
