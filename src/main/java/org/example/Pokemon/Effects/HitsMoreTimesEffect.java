package org.example.Pokemon.Effects;


import org.example.Battle.DamageCalculator;
import org.example.Battle.Weather;
import org.example.Pokemon.Moves;

import org.example.Pokemon.Pokemon;

import java.util.Random;

public class HitsMoreTimesEffect implements MultiHitMoveEffect {

    private final Random random = new Random();

    @Override
    public void apply(Pokemon user, Pokemon target) {
        // Hits the target 2-5 times in one turn
    }

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather) {}

    @Override
    public int applyMultiHitDamage(Pokemon user, Pokemon target, Moves move, Weather weather) {

        int hits = random.nextInt(4) + 2;
        int successfulHits = 0;

        int totalDamage = 0;

        for (int i = 0; i < hits; i++) {
            int damage = DamageCalculator.calculateDamage(user, target, move, weather);

            target.takeDamage(damage);
            totalDamage += damage;


            successfulHits++;

            System.out.println("hits");


            if (!target.isAlive()){
                break;
            }
        }

        System.out.println("Hit " + successfulHits + " time(s)!");

        return totalDamage;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
