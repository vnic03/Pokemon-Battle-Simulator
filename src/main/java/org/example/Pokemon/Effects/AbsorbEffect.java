package org.example.Pokemon.Effects;

import org.example.Battle.DamageCalculator;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

public class AbsorbEffect implements MoveEffectWithDamage {

    @Override
    public void apply(Pokemon user, Pokemon target) {
    }

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move) {

        int damage = DamageCalculator.calculateDamage(user, target, move);

        user.heal(damage / 2);

    }
}
