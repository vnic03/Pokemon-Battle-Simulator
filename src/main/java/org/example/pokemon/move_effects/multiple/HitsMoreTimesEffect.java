package org.example.pokemon.move_effects.multiple;


import org.example.battle.DamageCalculator;
import org.example.battle.Weather;
import org.example.pokemon.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;

import org.example.pokemon.Pokemon;

import java.util.Random;

public class HitsMoreTimesEffect extends MoveEffect {

    private final Random random = new Random();

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        // Hits the target 2-5 times in one turn
    }

    @Override
    public int applyMultiHitDamage(Pokemon user, Pokemon target, Moves move, Weather weather,BattleRoundResult result) {

        int hits = random.nextInt(4) + 2;
        int successfulHits = 0;

        int totalDamage = 0;

        for (int i = 0; i < hits; i++) {
            int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);

            target.takeDamage(damage);
            totalDamage += damage;

            successfulHits++;

            result.setMessage("hits");

            if (!target.isAlive()){
                break;
            }
        }
        result.setMessage("Hit " + successfulHits + " time(s)!");

        return totalDamage;
    }
}
