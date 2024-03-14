package org.example.pokemon.effects.move_effects.special;

import org.example.pokemon.effects.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public class NoEffect extends MoveEffect {
    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        // no effect
    }
}
