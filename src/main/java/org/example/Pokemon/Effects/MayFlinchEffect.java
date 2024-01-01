package org.example.Pokemon.Effects;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class MayFlinchEffect implements MoveEffect{

    private static final double CHANCE_TO_FLINCH = 0.30;

    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (BattleSimulator.getInstance().doesTargetActAfterAttacker(user, target) && new Random().nextDouble() < CHANCE_TO_FLINCH) {
            target.setFlinching(true);
        }
    }
}
