package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class ThickFatEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
    }

    @Override
    public void applyEffect(Pokemon pokemon, Moves move, BattleRoundResult result) {

        if (move.getType() == Typing.FIRE || move.getType() == Typing.ICE) {
            pokemon.setThickFatActive(true);
        }
    }
}
