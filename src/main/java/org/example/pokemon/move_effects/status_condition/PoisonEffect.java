package org.example.pokemon.move_effects.status_condition;

import org.example.pokemon.Typing;
import org.example.pokemon.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public class PoisonEffect extends MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            result.setMessage(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(Typing.POISON)) {
            result.setMessage(target.getName() + " can't be poisoned");
            return;
        }

        if (target.isPoisoned()) {
            result.setMessage(target.getName() + " is already poisoned !");
            return;
        }

        target.setPoisoned(true);
        result.setMessage(target.getName() + " got poisoned!");
    }
}
