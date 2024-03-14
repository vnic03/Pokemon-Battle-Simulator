package org.example.pokemon.effects.move_effects.status_condition;

import org.example.battle.DamageCalculator;
import org.example.battle.Weather;
import org.example.pokemon.effects.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;

import java.util.Random;

public class MayFreezeEffect extends MoveEffect {

    private static final double CHANCE_TO_FREEZE = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}


    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {

        int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
        target.takeDamage(damage);
        result.setMessage(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        if (target.hasStatusCondition() || target.getTyping().contains(Typing.ICE)) {
            return;
        }

        if (new Random().nextDouble() < CHANCE_TO_FREEZE) {
            target.setFrozen(true);
            result.setMessage(target.getName() + " got frozen !");
        }
    }
}
