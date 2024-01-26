package org.example.Pokemon.Effects.Priority;

import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Pokemon;


public class PriorityMinusFive implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {}

    @Override
    public int getPriority() {
        return -5;
    }
}
