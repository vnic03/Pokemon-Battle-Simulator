package org.example.pokemon.abilityEffects;

import org.example.pokemon.MoveCategory;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

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
