package org.example.Pokemon.Effects;


import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Pokemon;

public interface MoveEffect {
    void apply(Pokemon user, Pokemon target, BattleRoundResult result);
    int getPriority();
}
