package org.example.pokemon.effects.move_effects.absorb;

import org.example.battle.DamageCalculator;
import org.example.battle.Weather;
import org.example.pokemon.effects.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

public class AbsorbEffect extends MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
        target.takeDamage(damage);

        result.setMessage(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        user.heal(damage / 2);
    }
}
