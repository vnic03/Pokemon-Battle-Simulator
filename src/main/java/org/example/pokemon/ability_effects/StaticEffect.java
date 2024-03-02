package org.example.pokemon.ability_effects;

import org.example.pokemon.MoveCategory;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

import java.util.Random;

public class StaticEffect implements AbilityEffectWithMove {

    private static final double PARALYSIS_CHANCE = 0.3;

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {}

    @Override
    public void applyEffect(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        if (move.getCategory() == MoveCategory.PHYSICAL) {

            if (new Random().nextDouble() < PARALYSIS_CHANCE) {
                Pokemon attacker = move.getAttacker();
                attacker.setParalyzed(true);
            }
        }
    }
}
