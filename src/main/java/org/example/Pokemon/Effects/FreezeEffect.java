package org.example.Pokemon.Effects;

import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

import java.util.Random;

public class FreezeEffect implements MoveEffect {

    private static final double CHANCE_TO_FREEZE = 0.10;
    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (target.hasStatusCondition()) {
            return;
        }

        if (target.getTyping().contains(PokeTyping.ICE)) {
            return;
        }


        if (new Random().nextDouble() < CHANCE_TO_FREEZE) {
            target.setFrozen(true);
            System.out.println(target.getName() + " got frozen !");
        }

    }
}
