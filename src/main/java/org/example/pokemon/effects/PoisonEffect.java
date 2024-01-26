package org.example.pokemon.effects;

import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.PokeTyping;
import org.example.pokemon.Pokemon;

public class PoisonEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            System.out.println(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(PokeTyping.POISON)) {
            System.out.println(target.getName() + " can't be poisoned");
            return;
        }

        if (target.isPoisoned()) {
            System.out.println(target.getName() + " is already poisoned !");
            return;
        }

        target.setPoisoned(true);
        System.out.println(target.getName() + " got poisoned!");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
