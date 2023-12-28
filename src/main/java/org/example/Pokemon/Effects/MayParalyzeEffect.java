package org.example.Pokemon.Effects;

import org.example.Pokemon.Pokemon;
import java.util.Random;

public class MayParalyzeEffect implements MoveEffect {

    private static final double CHANCE_TO_PARALYZE = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target) {
        if (new Random().nextDouble() < CHANCE_TO_PARALYZE) {
            target.setParalyzed(true);
            System.out.println(target.getName() + " got paralyzed");
        }
    }
}
