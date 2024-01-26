package org.example.pokemon.effects;

import org.example.battle.DamageCalculator;
import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.PokeTyping;
import org.example.pokemon.Pokemon;

import java.util.Random;

public class MayPoisonEffect implements MoveEffectWithDamage {

    private static final double CHANCE_TO_POISON = 0.30;
    private static final double SMOG = 0.40;
    private static final double SLUDGE_WAVE = 0.10;

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}


    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {

       int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
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

    @Override
    public int getPriority() {
        return 0;
    }
}
