package org.example.Pokemon.Effects;

import org.example.Pokemon.Pokemon;

import java.util.Random;

public class SleepEffect implements MoveEffect {

    private static final int MAX_SLEEP_TURNS = 3;

    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (target.hasStatusCondition()) {
            System.out.println(target.getName() + " can't be affected !");
            return;
        }

        int sleepTurns = new Random().nextInt(MAX_SLEEP_TURNS) + 1;
        target.setAsleep(true, sleepTurns);
        System.out.println(target.getName() + " is fast asleep !");
    }
}
