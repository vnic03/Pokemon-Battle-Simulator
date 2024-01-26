package org.example.Pokemon.Effects;

import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

public class BurnEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        if (target.hasStatusCondition()) {
            System.out.println(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(PokeTyping.FIRE)) {
            System.out.println(target.getName() +" can't be burned !");
            return;
        }

        if (target.isBurned()) {
            System.out.println(target.getName() + " is already burned !");
            return;
        }

        target.setBurned(true);
        System.out.println(target.getName() + " got burned !");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
