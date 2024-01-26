package org.example.pokemon.effects;

import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public class NoEffect implements MoveEffect{
    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        // no effect
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
