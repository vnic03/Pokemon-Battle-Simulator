package org.example.Pokemon.Effects;

import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

public class BurnEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target) {

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
}
