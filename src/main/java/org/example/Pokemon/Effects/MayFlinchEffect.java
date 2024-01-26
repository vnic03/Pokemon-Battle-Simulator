package org.example.Pokemon.Effects;

import org.example.Battle.BattleSimulator;
import org.example.Battle.Weather;
import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class MayFlinchEffect implements MoveEffectWithDamage {

    private final double chanceToFlinch;

    public MayFlinchEffect(double chanceToFlinch) {
        this.chanceToFlinch = chanceToFlinch;
    }

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        if (BattleSimulator.getInstance().doesTargetActAfterAttacker(user, target, move.getEffect(), move.getEffect()) && new Random().nextDouble() < chanceToFlinch) {
            target.setFlinching(true);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
