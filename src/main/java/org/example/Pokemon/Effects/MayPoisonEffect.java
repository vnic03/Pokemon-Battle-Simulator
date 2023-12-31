package org.example.Pokemon.Effects;

import org.example.Battle.DamageCalculator;
import org.example.Battle.Weather;
import org.example.Pokemon.Moves;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class MayPoisonEffect implements MoveEffectWithDamage {

    private static final double CHANCE_TO_POISON = 0.30;
    private static final double SMOG = 0.40;
    private static final double SLUDGE_WAVE = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target) {}


    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather) {

       int damage = DamageCalculator.calculateDamage(user, target, move, weather);
       target.takeDamage(damage);
        System.out.println(user.getName() + " hits " + target.getName() + " with " + move.getName() + " for " + damage + " damage !");

        if (target.hasStatusCondition() || target.getTyping().contains(PokeTyping.POISON)) {
            return;
        }

        double chanceToPoison;

        switch (move.getName().toLowerCase()) {
            case "smog":
                chanceToPoison = SMOG;
                break;
            case "sludge wave":
                chanceToPoison = SLUDGE_WAVE;
                break;
            default:
                chanceToPoison = CHANCE_TO_POISON;
                break;
        }

        if (new Random().nextDouble() < chanceToPoison) {
            target.setPoisoned(true);
            System.out.println(target.getName() + " got poisoned !");
        }
    }
}
