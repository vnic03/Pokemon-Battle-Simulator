package org.example.pokemon.effects;

import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.PokeTyping;
import org.example.pokemon.Pokemon;

public class ParalyzeEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            System.out.println(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(PokeTyping.ELECTRIC) || target.getTyping().contains(PokeTyping.GROUND)) {
            System.out.println(target.getName() + " can't be paralyzed!");
            return;
        }
        if (target.isParalyzed()) {
            System.out.println(target.getName() + " is already paralyzed!");
            return;
        }
        target.setParalyzed(true);
        System.out.println(target.getName() + " got paralyzed!");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
