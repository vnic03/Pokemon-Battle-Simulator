package org.example.Pokemon.AbilityEffects;

import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class CursedBodyEffect implements AbilityEffectWithMove {

    private static final double ACTIVATION_CHANCE = 0.3;
    private static final int DISABLE_DURATION = 3;

    @Override
    public void applyEffect(Pokemon pokemon) {
    }

    @Override
    public void applyEffect(Pokemon pokemon, Moves move) {
        if (new Random().nextDouble() < ACTIVATION_CHANCE) {

            Pokemon attacker = move.getAttacker();

            attacker.disableMove(move, DISABLE_DURATION);

            System.out.println(attacker.getName() + "'s " + move.getName() + " was disabled by Cursed Body!");
        }
    }
}
