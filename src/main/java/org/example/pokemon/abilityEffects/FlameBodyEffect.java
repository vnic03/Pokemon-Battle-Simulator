package org.example.pokemon.abilityEffects;

import org.example.pokemon.MoveCategory;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

import java.util.Random;

public class FlameBodyEffect implements AbilityEffectWithMove {

    private static final double BURN_CHANCE = 0.3;

    @Override
    public void applyEffect(Pokemon pokemon) {}
    @Override
    public void applyEffect(Pokemon pokemon, Moves move) {
        if (move.getCategory() == MoveCategory.PHYSICAL) {

            if (new Random().nextDouble() < BURN_CHANCE) {
                Pokemon attacker = move.getAttacker();
                attacker.setBurned(true);
            }
        }
    }
}
