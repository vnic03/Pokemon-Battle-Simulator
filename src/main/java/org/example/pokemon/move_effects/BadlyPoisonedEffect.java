package org.example.pokemon.move_effects;

import org.example.pokemon.Typing;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public class BadlyPoisonedEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            result.setMessage(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(Typing.POISON)) {
            result.setMessage(target.getName() + " can't be poisoned !");
            return;
        }

        if (target.isBadlyPoisoned()) {
            result.setMessage(target.getName() + " is already badly poisoned !");
            return;
        }

        target.setBadlyPoisoned(true);
        result.setMessage(target.getName() + " got badly poisoned !");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
