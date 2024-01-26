package org.example.Pokemon.Effects;

import org.example.Battle.DamageCalculator;
import org.example.Battle.Weather;
import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

public class AbsorbEffect implements MoveEffectWithDamage {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {

        int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
        target.takeDamage(damage);
        System.out.println(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        user.heal(damage / 2);

    }

    @Override
    public int getPriority() {
        return 0;
    }
}
