package org.example.pokemon.moveEffects;

import org.example.pokemon.Typing;
import org.example.screens.battle.DamageCalculator;
import org.example.screens.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

import java.util.Random;

public class MayBurnEffect implements MoveEffectWithDamage {

    private static final double CHANCE_TO_BURN = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {

        int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
        target.takeDamage(damage);
        System.out.println(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        if (target.hasStatusCondition() || target.getTyping().contains(Typing.FIRE)) {
            return;
        }
        if (new Random().nextDouble() < CHANCE_TO_BURN) {
            target.setBurned(true);
            System.out.println(target.getName() + " got burned!");
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
