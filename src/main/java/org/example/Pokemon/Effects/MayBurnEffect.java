package org.example.Pokemon.Effects;

import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class MayBurnEffect implements MoveEffect {

    private static final double CHANCE_TO_BURN = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (new Random().nextDouble() < CHANCE_TO_BURN) {

            if (!target.isBurned() && !target.getTyping().contains(PokeTyping.FIRE)) {
                target.setBurned(true);
                System.out.println(target.getName() + " got burned!");
            }
        }
    }
}
