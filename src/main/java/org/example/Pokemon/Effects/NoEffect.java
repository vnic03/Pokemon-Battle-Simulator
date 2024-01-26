package org.example.Pokemon.Effects;

import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Pokemon;

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
