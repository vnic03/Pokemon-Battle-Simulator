package org.example.pokemon.effects.ability_effects;

import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

public class ThickFatEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {}

    @Override
    public void applyEffect(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        if (move.getType() == Typing.FIRE || move.getType() == Typing.ICE) {
            user.setThickFatActive(true);
        }
    }
}
