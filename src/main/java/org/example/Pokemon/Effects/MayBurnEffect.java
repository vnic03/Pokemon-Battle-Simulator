package org.example.Pokemon.Effects;

import org.example.Battle.DamageCalculator;
import org.example.Pokemon.Moves;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class MayBurnEffect implements MoveEffectWithDamage {

    private static final double CHANCE_TO_BURN = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target) {}

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move) {

        int damage = DamageCalculator.calculateDamage(user, target, move);
        target.takeDamage(damage);
        System.out.println(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        if (target.hasStatusCondition() || target.getTyping().contains(PokeTyping.FIRE)) {
            return;
        }
        if (new Random().nextDouble() < CHANCE_TO_BURN) {
            target.setBurned(true);
            System.out.println(target.getName() + " got burned!");
        }
    }
}
