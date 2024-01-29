package org.example.pokemon.moveEffects;

import org.example.screens.battle.DamageCalculator;
import org.example.screens.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.PokeTyping;
import org.example.pokemon.Pokemon;

import java.util.Random;

public class MayFreezeEffect implements MoveEffectWithDamage {

    private static final double CHANCE_TO_FREEZE = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}


    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {

        int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
        target.takeDamage(damage);
        System.out.println(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        if (target.hasStatusCondition() || target.getTyping().contains(PokeTyping.ICE)) {
            return;
        }

        if (new Random().nextDouble() < CHANCE_TO_FREEZE) {
            target.setFrozen(true);
            System.out.println(target.getName() + " got frozen !");
        }

    }

    @Override
    public int getPriority() {
        return 0;
    }
}
