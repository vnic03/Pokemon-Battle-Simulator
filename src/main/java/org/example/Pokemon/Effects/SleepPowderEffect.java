package org.example.Pokemon.Effects;

import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class SleepPowderEffect implements MoveEffect {

    private static final int MAX_SLEEP_TURNS = 3;

    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (target.hasStatusCondition() || target.getTyping().contains(PokeTyping.GRASS)) {
            System.out.println(target.getName() + " can't be affected !");
            return;
        }

        int sleepTurns = new Random().nextInt(MAX_SLEEP_TURNS) + 1;
        target.setAsleep(true, sleepTurns);
        System.out.println(target.getName() + " is fast asleep !");
    }
}
