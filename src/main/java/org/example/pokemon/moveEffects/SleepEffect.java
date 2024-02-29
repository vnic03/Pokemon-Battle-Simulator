package org.example.pokemon.moveEffects;

import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

import java.util.Random;

public class SleepEffect implements MoveEffect {

    private static final int MAX_SLEEP_TURNS = 3;

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            result.setMessage(target.getName() + " can't be affected !");
            return;
        }

        int sleepTurns = new Random().nextInt(MAX_SLEEP_TURNS) + 1;
        target.setAsleep(true, sleepTurns);
        result.setMessage(target.getName() + " is fast asleep !");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
