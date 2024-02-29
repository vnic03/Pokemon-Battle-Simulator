package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.screens.battle.DamageCalculator;
import org.example.screens.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

public class MultiScaleEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {}

    @Override
    public void applyEffect(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        if (target.getStats().getHp() == target.getStats().getMaxHp()) {
            int damage = (DamageCalculator.calculateDamage(user, target, move, weather, result)) / 2;
            user.takeDamage(damage);
        }
    }
}
