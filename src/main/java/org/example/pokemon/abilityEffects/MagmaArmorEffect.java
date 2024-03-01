package org.example.pokemon.abilityEffects;

import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class MagmaArmorEffect implements AbilityEffect {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
        if (pokemon.isFrozen()) {
            pokemon.clearStatusCondition();
        }
        result.setMessage(pokemon.getName() + " is protected by Magma Armor");
    }
}
