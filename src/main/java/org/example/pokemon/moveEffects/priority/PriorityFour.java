package org.example.pokemon.moveEffects.priority;

import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.moveEffects.MoveEffect;
import org.example.pokemon.Pokemon;

public class PriorityFour implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}


    @Override
    public int getPriority() {
        return 4;
    }
}
