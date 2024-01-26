package org.example.pokemon.effects.priority;

import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.effects.MoveEffect;
import org.example.pokemon.Pokemon;

public class PriorityTwo implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public int getPriority() {
        return 2;
    }
}
