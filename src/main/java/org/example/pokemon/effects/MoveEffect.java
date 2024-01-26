package org.example.pokemon.effects;


import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public interface MoveEffect {
    void apply(Pokemon user, Pokemon target, BattleRoundResult result);
    int getPriority();
}
