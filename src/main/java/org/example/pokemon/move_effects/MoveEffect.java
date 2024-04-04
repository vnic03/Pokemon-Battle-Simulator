package org.example.pokemon.move_effects;

import org.example.battle.Weather;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public abstract class MoveEffect {

    private final int priority;

    public MoveEffect() {
        this.priority = 0;
    }

    public MoveEffect(int priority) {
        this.priority = priority;
    }

    public abstract void apply(Pokemon user, Pokemon target, BattleRoundResult result);

    public int getPriority() {
        return this.priority;
    }

    public void applyWithDamage
            (Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) { }

   public int applyMultiHitDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        return 0;
   }
}
