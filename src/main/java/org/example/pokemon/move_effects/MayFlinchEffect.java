package org.example.pokemon.move_effects;

import org.example.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;


public class MayFlinchEffect implements MoveEffectWithDamage {

    private final double chanceToFlinch;

    public MayFlinchEffect(double chanceToFlinch) {
        this.chanceToFlinch = chanceToFlinch;
    }

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result) {
        // do later
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
