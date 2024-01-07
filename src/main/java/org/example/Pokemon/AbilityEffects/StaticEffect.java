package org.example.Pokemon.AbilityEffects;

import org.example.Pokemon.MoveCategory;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class StaticEffect implements AbilityEffectWithMove {

    private static final double PARALYSIS_CHANCE = 0.3;

    @Override
    public void applyEffect(Pokemon pokemon) {}

    @Override
    public void applyEffect(Pokemon pokemon, Moves move) {

        if (move.getCategory() == MoveCategory.PHYSICAL) {

            if (new Random().nextDouble() < PARALYSIS_CHANCE) {
                Pokemon attacker = move.getAttacker();
                attacker.setParalyzed(true);
            }
        }
    }
}
