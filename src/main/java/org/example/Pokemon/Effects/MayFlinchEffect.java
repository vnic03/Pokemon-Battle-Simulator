package org.example.Pokemon.Effects;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class MayFlinchEffect implements MoveEffect{

    private final double chanceToFlinch;

    public MayFlinchEffect(double chanceToFlinch) {
        this.chanceToFlinch = chanceToFlinch;
    }


    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (BattleSimulator.getInstance().doesTargetActAfterAttacker(user, target) && new Random().nextDouble() < chanceToFlinch) {
            target.setFlinching(true);
        }
    }
}
