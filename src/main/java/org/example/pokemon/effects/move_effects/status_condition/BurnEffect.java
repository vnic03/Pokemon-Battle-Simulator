package org.example.pokemon.effects.move_effects.status_condition;

import org.example.pokemon.effects.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;

public class BurnEffect extends MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            result.setMessage(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(Typing.FIRE)) {
            result.setMessage(target.getName() +" can't be burned !");
            return;
        }

        if (target.isBurned()) {
            result.setMessage(target.getName() + " is already burned !");
            return;
        }

        target.setBurned(true);
        result.setMessage(target.getName() + " got burned !");
    }
}