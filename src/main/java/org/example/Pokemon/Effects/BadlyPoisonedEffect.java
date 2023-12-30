package org.example.Pokemon.Effects;

import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

public class BadlyPoisonedEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target) {

        if (target.hasStatusCondition()) {
            System.out.println(target.getName() + " is already affected by a status condition!");
            return;
        }

        if (target.getTyping().contains(PokeTyping.POISON)) {
            System.out.println(target.getName() + " can't be poisoned");
            return;
        }

        if (target.isBadlyPoisoned()) {
            System.out.println(target.getName() + " is already badly poisoned !");
            return;
        }

        target.setBadlyPoisoned(true);
        System.out.println(target.getName() + " got badly poisoned !");
    }
}
