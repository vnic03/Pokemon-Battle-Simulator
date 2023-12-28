package org.example.Pokemon.Effects;

import org.example.Pokemon.Pokemon;

import java.util.Random;

public class HitsMoreTimesEffect implements MoveEffect{

    private Random random = new Random();

    @Override
    public void apply(Pokemon user, Pokemon target) {
        // Hits the target 2-5 times in one turn
        int hits = random.nextInt(4) + 2;

        for (int i = 0; i < hits; i++) {
            // do the attack
        }
        // how many times the attack hit
    }
}
